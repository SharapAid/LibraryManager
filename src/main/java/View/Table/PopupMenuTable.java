package View.Table;

import View.CustomElements.CustomButton;

import javax.swing.*;
import java.awt.*;

public class PopupMenuTable {
    private JPopupMenu menuTable;
    private CustomButton delete;
    private CustomButton edit;

    public PopupMenuTable(){
        menuTable = new JPopupMenu();
        menuTable.setBackground(new Color(93, 93, 101));
        menuTable.setBorderPainted(false);

        delete = new CustomButton("Delete row");
        edit = new CustomButton("Edit");

        menuTable.add(edit);
        menuTable.add(delete);
    }

    public CustomButton getDelete() {
        return delete;
    }

    public JPopupMenu getMenuTable() {
        return menuTable;
    }

    public JButton getEdit() {
        return edit;
    }
}
