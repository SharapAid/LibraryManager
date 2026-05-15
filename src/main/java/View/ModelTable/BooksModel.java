package View.ModelTable;

import javax.swing.table.DefaultTableModel;

public class BooksModel {
    private DefaultTableModel model;
    private final String[] columns = {"ID", "Title", "Author", "Genre", "Status"};

    public BooksModel(){
        model = new DefaultTableModel(columns, 0);
    }

    public DefaultTableModel getModel() {
        return model;
    }
}
