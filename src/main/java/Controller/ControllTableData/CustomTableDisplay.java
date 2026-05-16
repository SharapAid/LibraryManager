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

                if (value instanceof Boolean) {
                    if ((Boolean) value) {
                        setText("Available");
                        c.setForeground(new java.awt.Color(46, 204, 113));
                    }
                    else {
                        setText("Borrowed");
                        c.setForeground(new java.awt.Color(231, 76, 60));
                    }
                }
                else {
                    if (isSelected) {
                        c.setForeground(t.getSelectionForeground());
                    }
                    else {
                        c.setForeground(t.getForeground());
                    }
                }
                return c;
            }
        };

        TableColumnModel columnModel = table.getColumnModel();
        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            columnModel.getColumn(i).setCellRenderer(centerAndStatusRenderer);
        }
    }
}
