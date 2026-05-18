package Controller.ControllTableData;

import DAO.ClientDAO;
import Model.Entity.Client;
import View.CustomElements.CustomAlert;
import View.CustomElements.CustomTableDisplay;
import View.Forms.ClientForm;
import View.ModelTable.ClientsModel;
import View.ViewWindow;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.List;

public class ClientsTable {
    private ViewWindow view;
    private ClientDAO clientDAO;

    public ClientsTable(ViewWindow view){
       this.view = view;
       clientDAO = new ClientDAO();

       initListeners();
    }

    private void initListeners() {
        view.getSideBar().getBoardClients().addActionListener(e -> {
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
    }

    private void loadClientsToTable() {
        List<Client> clients = clientDAO.getAll();
        ClientsModel model = new ClientsModel();

        for (Client c : clients) {
            model.getModel().addRow(new Object[]{
                    c.getIndex(), c.getName(), c.getPhone(), c.getEmail(), c.getAddress()
            });
        }

        view.getDataTable().getTable().setModel(model.getModel());

        int rowCount = view.getDataTable().getTable().getModel().getRowCount();
        view.getStatusBar().getStatusLabel().setText("Rows: " + rowCount);

        CustomTableDisplay.customizeTableDisplay(view.getDataTable().getTable());

        String currentSearchText = view.getToolBar().getSearchBar().getSearchBar().getText();
        CustomTableDisplay.applySearchFilter(view.getDataTable().getTable(), currentSearchText);
    }
}
