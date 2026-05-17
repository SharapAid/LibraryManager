package Controller.ControllTableData;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;

public class CustomTableDisplay {

    public static void customizeTableDisplay(JTable table) {
        DefaultTableCellRenderer centerAndStatusRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {

                Component c = super.getTableCellRendererComponent(t, value, isSelected, hasFocus, row, column);
                setHorizontalAlignment(SwingConstants.CENTER);

                String dateReturned = t.getValueAt(row, 4).toString();

                if (isSelected) {
                    c.setForeground(t.getSelectionForeground());
                }
                else {
                    c.setForeground(t.getForeground());
                }

                if (column == 4) {
                    if (dateReturned.equals("Not returned")) {
                        c.setForeground(new Color(231, 76, 60));
                    }
                    else {
                        c.setForeground(new Color(0, 0, 0));
                    }
                }

                if (column == 5 && value instanceof Boolean) {
                    if ((Boolean) value) {
                        setText("Available");
                        c.setForeground(new Color(46, 204, 113));
                    }
                    else {
                        setText("Borrowed");
                        c.setForeground(new Color(231, 76, 60));
                    }
                }
                return c;
            }
        };
        for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerAndStatusRenderer);
        }
    }
}
