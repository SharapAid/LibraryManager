package Controller.ControllTableData;

import DAO.BookDAO;
import Model.Entity.Book;
import View.ModelTable.BooksModel;
import View.ViewWindow;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class BooksTable {
    private ViewWindow view;
    private BookDAO bookDAO;

    public BooksTable(ViewWindow view){
        this.view = view;
        bookDAO = new BookDAO();

        initListeners();
    }

    private void initListeners() {
        view.getSideBar().getBoardBooks().addActionListener(e -> {
            loadBooksToTable();
        });
    }

    private void loadBooksToTable() {
        List<Book> books = bookDAO.getAll();
        BooksModel model = new BooksModel();

        for (Book b : books) {
            model.getModel().addRow(new Object[]{
                    b.getIndex(), b.getTitle(), b.getAuthor(), b.getGenre(), b.isAvailable()
            });
        }
        view.getDataTable().getTable().setModel(model.getModel());
    }
}
