package View.Table;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Table {
    private JScrollPane wrapTable;
    private JTable table;
    private boolean editMode = false;

    public Table(){
        table = new JTable(new DefaultTableModel()){
            @Override
            public boolean isCellEditable(int row, int column) {
                return editMode;
            }
        };

        wrapTable = new JScrollPane(table);
    }

    public JTable getTable() {
        return table;
    }

    public boolean isEditMode() {
        return editMode;
    }

    public JScrollPane getWrapTable() {
        return wrapTable;
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }
}
