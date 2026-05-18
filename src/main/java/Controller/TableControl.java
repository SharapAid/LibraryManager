package Controller;

import View.ViewWindow;

import javax.swing.*;

public class TableControl{
    private ViewWindow view;

    public TableControl(ViewWindow view){
        this.view = view;

        initListeners();
    }

    private void initListeners(){
        view.getDataTable().getTable().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                updateSelectedRowStatus();
            }
        });
    }

    private void updateSelectedRowStatus(){
        JTable table = view.getDataTable().getTable();
        JLabel label = view.getStatusBar().getSelectedRowLabel();

        int selectedRow = table.getSelectedRow();
        int selectedColumn = table.getSelectedColumn();

        if (selectedRow == -1) {
            label.setText("");
        }
        else {
            int index = selectedRow + 1;
            label.setText("<html>Selected Row: " + index + "&nbsp;&nbsp;&nbsp;&nbsp;" + "Selected column: " + selectedColumn + "</html>");
        }
    }
}
