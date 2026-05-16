package Controller.ControllTableData;

import DAO.ClientDAO;
import Model.Entity.Client;
import View.ModelTable.ClientsModel;
import View.ViewWindow;

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
    }
}
