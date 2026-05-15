package View.Table;

import javax.swing.*;

public class Table {
    private JScrollPane wrapTable;
    private JTable table;
    private boolean editMode = false;

    public Table(){
        wrapTable = new JScrollPane();

        table = new JTable(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return editMode;
            }
        };

        wrapTable.add(table);
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
