package Controller.ControllTableData;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class CustomTableDisplay {

    public static void customizeTableDisplay(JTable table) {
        if (table.getColumnCount() > 0) {
            table.getColumnModel().getColumn(0).setMinWidth(40);
            table.getColumnModel().getColumn(0).setMaxWidth(60);
            table.getColumnModel().getColumn(0).setPreferredWidth(50);
        }

        DefaultTableCellRenderer centerAndStatusRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

                Component c = super.getTableCellRendererComponent(t, value, isSelected, hasFocus, row, column);
                setHorizontalAlignment(SwingConstants.CENTER);

                if (isSelected) {
                    c.setForeground(t.getSelectionForeground());
                }
                else {
                    c.setForeground(t.getForeground());
                }

                String dateReturned = t.getValueAt(row, 4).toString();

                if (column == 4) {
                    if (dateReturned.equals("Not returned")) {
                        c.setForeground(new Color(231, 76, 60));
                    }
                    else {
                        c.setForeground(new Color(0, 0, 0));
                    }
                }

                if (column == 4 && value instanceof Boolean) {
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
