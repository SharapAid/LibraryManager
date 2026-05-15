package View.ToolBar;

import View.CustomElements.CustomButton;

import javax.swing.*;
import java.awt.*;

public class ButtonBar {
    private JPanel wrapButton;
    private CustomButton addButton;
    private CustomButton deleteButton;
    private CustomButton refreshButton;

    public ButtonBar(){
        wrapButton = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 5));
        wrapButton.setOpaque(false);

        addButton = new CustomButton("Add");
        deleteButton = new CustomButton("Delete");
        refreshButton = new CustomButton("Refresh");

        wrapButton.add(addButton);
        wrapButton.add(deleteButton);
        wrapButton.add(refreshButton);
    }

    public JPanel getWrapButton(){
        return wrapButton;
    }

    public JButton getAddButton(){
        return addButton;
    }

    public JButton getDeleteButton(){
        return deleteButton;
    }

    public JButton getRefreshButton(){
        return refreshButton;
    }
}
