package Controller.ControllTableData;

import DAO.BookDAO;
import Model.Entity.Book;
import View.CustomElements.CustomAlert;
import View.ModelTable.BooksModel;
import View.ViewWindow;

import javax.swing.*;
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
            view.getToolBar().getTitleBar().getTitleLabel().setText("List books");
            loadBooksToTable();
        });

        view.getToolBar().getButtonBar().getRefreshButton().addActionListener(e -> {
            if (view.getToolBar().getTitleBar().getTitleLabel().getText().equals("List books")) {
                loadBooksToTable();
                System.out.println("Books table refreshed!");
            }
        });

        view.getToolBar().getButtonBar().getAddButton().addActionListener(e -> {
            if (view.getToolBar().getTitleBar().getTitleLabel().getText().equals("List books")) {

                View.Forms.BookForm form = new View.Forms.BookForm(view.getWindow());

                form.getSaveButton().addActionListener(ev -> {

                    String title = form.getBookTitle();
                    String author = form.getBookAuthor();
                    String genre = form.getBookGenre();

                    if (!title.isEmpty() && !author.isEmpty() && !genre.isEmpty()) {
                        Book newBook = new Book(title, author, genre, true, 0);
                        bookDAO.insert(newBook);

                        form.getWrapForm().dispose();

                        loadBooksToTable();
                    }
                    else {
                        CustomAlert.showWarning(view.getWindow(), "Please fill all fields!");
                    }
                });
                form.getWrapForm().setVisible(true);
            }
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
