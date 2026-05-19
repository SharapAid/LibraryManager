package Controller.ControllTableData;

import DAO.ClientDAO;
import Model.Entity.Client;
import View.CustomElements.CustomAlert;
import View.CustomElements.CustomTableDisplay;
import View.Forms.ClientForm;
import View.ModelTable.ClientsModel;
import View.Table.PopupMenuTable;
import View.ViewWindow;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ClientsTable {
    private ViewWindow view;
    private ClientDAO clientDAO;
    private PopupMenuTable popupMenu;

    public ClientsTable(ViewWindow view, PopupMenuTable popupMenu){
       this.view = view;
       this.popupMenu = popupMenu;
       clientDAO = new ClientDAO();

       initListeners();
    }

    private void initListeners() {
        view.getSideBar().getBoardClients().addActionListener(e -> {
            view.getToolBar().getButtonBar().getDeleteButton().setText("Delete");
            view.getToolBar().getTitleBar().getTitleLabel().setText("List clients");
            view.getToolBar().getSearchBar().getSearchBar().setText("");
            loadClientsToTable();
        });

        view.getToolBar().getButtonBar().getRefreshButton().addActionListener(e -> {
            if (view.getToolBar().getTitleBar().getTitleLabel().getText().equals("List clients")) {
                view.getToolBar().getSearchBar().getSearchBar().setText("");
                loadClientsToTable();
                view.getStatusBar().getInfoBar().getRowsText().setText("Clients table refreshed!");
            }
        });

        view.getToolBar().getButtonBar().getAddButton().addActionListener(e -> {
            if (view.getToolBar().getTitleBar().getTitleLabel().getText().equals("List clients")) {

                ClientForm form = new ClientForm(view.getWindow());

                form.getSaveButton().addActionListener(ev -> {
                    String name = form.getClientName();
                    String phone = form.getClientPhone();
                    String email = form.getClientEmail();
                    String address = form.getClientAddress();

                    if (!name.isEmpty() && !phone.isEmpty()) {
                        Client newClient = new Client(name, phone, email, address, 0);
                        clientDAO.insert(newClient);

                        form.getWrapForm().dispose();
                        loadClientsToTable();
                        view.getStatusBar().getInfoBar().getRowsText().setText("The client has been successfully added!");
                    }
                    else {
                        CustomAlert.showWarning(view.getWindow(), "Please fill all fields!");
                    }
                });
                form.getWrapForm().setVisible(true);
            }
        });

        view.getToolBar().getButtonBar().getDeleteButton().addActionListener(e -> {
           deleteClient();
        });

        view.getToolBar().getSearchBar().getSearchBar().getDocument().addDocumentListener(new DocumentListener() {
            private void triggerFilter() {
                if (view.getToolBar().getTitleBar().getTitleLabel().getText().equals("List clients")) {
                    String text = view.getToolBar().getSearchBar().getSearchBar().getText();
                    JTable table = view.getDataTable().getTable();
                    CustomTableDisplay.applySearchFilter(table, text);

                    int visibleRows = table.getRowCount();
                    view.getStatusBar().getStatusLabel().setText("Rows: " + visibleRows);
                }
            }
            @Override
            public void insertUpdate(DocumentEvent e) {
                triggerFilter();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                triggerFilter();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                triggerFilter();
            }
        });

        popupMenu.getDelete().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteClient();
            }
        });

        popupMenu.getEdit().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!view.getToolBar().getTitleBar().getTitleLabel().getText().equals("List clients")) {
                    return;
                }

                JTable table = view.getDataTable().getTable();
                int row = table.getSelectedRow();
                int col = table.getSelectedColumn();

                if (col == 0) {
                    CustomAlert.showWarning(view.getWindow(), "This column cannot be edited directly!");
                    return;
                }

                if (row != -1 && col != -1) {
                    view.getDataTable().setEditMode(true);

                    table.editCellAt(row, col);

                    Component editor = table.getEditorComponent();
                    if (editor instanceof JTextField) {
                        editor.requestFocusInWindow();
                        ((JTextField) editor).selectAll();
                    }
                }
                else {
                    CustomAlert.showWarning(view.getWindow(), "Select a cell to edit!");
                }
            }
        });
    }

    private void deleteClient(){
        if (!view.getToolBar().getTitleBar().getTitleLabel().getText().equals("List clients")) {
            return;
        }

        int selectedRow = view.getDataTable().getTable().getSelectedRow();
        if (selectedRow == -1) {
            CustomAlert.showWarning(view.getWindow(), "Select a client from the table!");
            return;
        }

        int clientId = Integer.parseInt(view.getDataTable().getTable().getValueAt(selectedRow, 0).toString());
        String clientName = view.getDataTable().getTable().getValueAt(selectedRow, 1).toString();

        if (ClientDAO.hasActiveLoans(clientId)) {
            CustomAlert.showWarning(view.getWindow(), "Cannot delete client! " + clientName + " currently has unreturned books.");
            return;
        }

        ClientDAO.delete(clientId);
        view.getStatusBar().getInfoBar().getRowsText().setText("The client has been successfully deleted!");

        loadClientsToTable();
    }

    private void loadClientsToTable() {
        List<Client> clients = ClientDAO.getAll();
        ClientsModel model = new ClientsModel();

        for (Client c : clients) {
            model.getModel().addRow(new Object[]{
                    c.getIndex(), c.getName(), c.getPhone(), c.getEmail(), c.getAddress()
            });
        }
        view.getDataTable().getTable().setModel(model.getModel());

        model.getModel().addTableModelListener(e -> {
            if (e.getType() == TableModelEvent.UPDATE) {
                int row = e.getFirstRow();
                int col = e.getColumn();

                JTable table = view.getDataTable().getTable();

                int clientId = Integer.parseInt(table.getValueAt(row, 0).toString());
                String newValue = table.getValueAt(row, col).toString().trim();

                if (newValue.isEmpty()) {
                    CustomAlert.showWarning(view.getWindow(), "Field cannot be empty! Reverting changes.");
                    loadClientsToTable();
                    return;
                }

                Client editableClient = ClientDAO.getById(clientId);

                if (editableClient != null) {
                    if (col == 1) editableClient.setName(newValue);
                    else if (col == 2) editableClient.setPhone(newValue);
                    else if (col == 3) editableClient.setEmail(newValue);
                    else if (col == 4) editableClient.setAddress(newValue);

                    ClientDAO.update(editableClient);

                    view.getDataTable().setEditMode(false);

                    view.getStatusBar().getInfoBar().getRowsText().setText("Client has been successfully updated");
                }
            }
        });

        int rowCount = view.getDataTable().getTable().getModel().getRowCount();
        view.getStatusBar().getStatusLabel().setText("Rows: " + rowCount);

        CustomTableDisplay.customizeTableDisplay(view.getDataTable().getTable());

        String currentSearchText = view.getToolBar().getSearchBar().getSearchBar().getText();
        CustomTableDisplay.applySearchFilter(view.getDataTable().getTable(), currentSearchText);
    }
}
