package View.ModelTable;

import javax.swing.table.DefaultTableModel;

public class LoansModel {
    private DefaultTableModel model;
    private final String[] columns = {"Name book", "Name reader", "Date borrowed", "Date returned", "Status"};

    public LoansModel(){
        model = new DefaultTableModel(columns, 0);
    }

    public DefaultTableModel getModel() {
        return model;
    }
}
