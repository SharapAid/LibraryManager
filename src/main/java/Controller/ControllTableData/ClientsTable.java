package Controller.ControllTableData;

import DAO.ClientDAO;
import Model.Entity.Client;
import View.CustomElements.CustomAlert;
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

                View.Forms.ClientForm form = new View.Forms.ClientForm(view.getWindow());

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
    }
}
