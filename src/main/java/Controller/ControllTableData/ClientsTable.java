package Controller.ControllTableData;

import DAO.ClientDAO;
import Model.Entity.Client;
import View.CustomElements.CustomAlert;
import View.Forms.ClientForm;
import View.ModelTable.ClientsModel;
import View.ViewWindow;

import javax.swing.*;
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
            loadClientsToTable();
        });

        view.getToolBar().getButtonBar().getRefreshButton().addActionListener(e -> {
            if (view.getToolBar().getTitleBar().getTitleLabel().getText().equals("List clients")) {
                loadClientsToTable();
                System.out.println("Clients table refreshed!");
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

            DAO.ClientDAO.delete(clientId);

            loadClientsToTable();
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
        CustomTableDisplay.customizeTableDisplay(view.getDataTable().getTable());
    }
}
