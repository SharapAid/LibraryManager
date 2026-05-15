package View.ModelTable;

import javax.swing.table.DefaultTableModel;

public class ClientsModel {
    private DefaultTableModel model;
    private final String[] columns = {"ID", "Name", "Phone", "Email", "Address"};

    public ClientsModel(){
        model = new DefaultTableModel(columns, 0);
    }

    public DefaultTableModel getModel() {
        return model;
    }
}
