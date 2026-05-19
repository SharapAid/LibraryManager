package Controller.TableController;

import Controller.ControllTableData.BooksTable;
import View.Table.PopupMenuTable;
import View.ViewWindow;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TableControl{
    private ViewWindow view;
    private PopupMenuTable popupMenu;

    public TableControl(ViewWindow view){
        this.view = view;
        popupMenu = new PopupMenuTable();

        initListeners();
    }

    private void initListeners(){
        view.getDataTable().getTable().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                updateSelectedRowStatus();
            }
        });

        view.getDataTable().getTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                checkForPopup(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                checkForPopup(e);
            }

            private void checkForPopup(MouseEvent e) {
                if(!view.getToolBar().getTitleBar().getTitleLabel().getText().equals("List loans")){
                    if (e.isPopupTrigger()) {
                        int row = view.getDataTable().getTable().rowAtPoint(e.getPoint());

                        if (row >= 0 && row < view.getDataTable().getTable().getRowCount()) {
                            view.getDataTable().getTable().setRowSelectionInterval(row, row);
                            popupMenu.getMenuTable().show(view.getDataTable().getTable(), e.getX(), e.getY());
                        }
                    }
                }
            }
        });
    }

    private void updateSelectedRowStatus(){
        int selectedRow = view.getDataTable().getTable().getSelectedRow() + 1;
        int selectedColumn = view.getDataTable().getTable().getSelectedColumn() + 1;

        if (selectedRow - 1 == -1) {
            view.getStatusBar().getSelectedRowLabel().setText("");
        }
        else {
            view.getStatusBar().getSelectedRowLabel().setText("Selected Row: " + selectedRow + "    " + "Selected column: " + selectedColumn);
        }
    }

    public PopupMenuTable getPopupMenu(){
        return popupMenu;
    }
}
