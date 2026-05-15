package View.Table;

import javax.swing.*;

public class PopupMenuTable {
    private JPopupMenu menuTable;
    private JMenuItem delete;
    private JMenuItem edit;

    public PopupMenuTable(){
        menuTable = new JPopupMenu();

        delete = new JMenuItem("Delete row");
        edit = new JMenuItem("Edit");

        menuTable.add(edit);
        menuTable.add(delete);
    }

    public JMenuItem getDelete() {
        return delete;
    }

    public JPopupMenu getMenuTable() {
        return menuTable;
    }

    public JMenuItem getEdit() {
        return edit;
    }
}
