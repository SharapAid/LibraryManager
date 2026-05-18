package Controller.ControllTableData;

import DAO.BookDAO;
import Model.Entity.Book;
import View.CustomElements.CustomAlert;
import View.CustomElements.CustomTableDisplay;
import View.CustomElements.TextPlaceholder;
import View.Forms.BookForm;
import View.ModelTable.BooksModel;
import View.ViewWindow;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
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
            view.getToolBar().getSearchBar().getSearchBar().setText("");
            loadBooksToTable();
        });

        view.getToolBar().getButtonBar().getRefreshButton().addActionListener(e -> {
            if (view.getToolBar().getTitleBar().getTitleLabel().getText().equals("List books")) {
                view.getToolBar().getSearchBar().getSearchBar().setText("");
                loadBooksToTable();
                view.getStatusBar().getInfoBar().getRowsText().setText("Books table refreshed!");
            }
        });

        view.getToolBar().getButtonBar().getAddButton().addActionListener(e -> {
            if (view.getToolBar().getTitleBar().getTitleLabel().getText().equals("List books")) {

                BookForm form = new View.Forms.BookForm(view.getWindow());

                form.getSaveButton().addActionListener(ev -> {

                    String title = form.getBookTitle();
                    String author = form.getBookAuthor();
                    String genre = form.getBookGenre();

                    if (!title.isEmpty() && !author.isEmpty() && !genre.isEmpty()) {
                        Book newBook = new Book(title, author, genre, true, 0);
                        bookDAO.insert(newBook);

                        form.getWrapForm().dispose();

                        loadBooksToTable();
                        view.getStatusBar().getInfoBar().getRowsText().setText("The book has been successfully added!");
                    }
                    else {
                        CustomAlert.showWarning(view.getWindow(), "Please fill all fields!");
                    }
                });
                form.getWrapForm().setVisible(true);
            }
        });

        view.getToolBar().getButtonBar().getDeleteButton().addActionListener(e -> {
            if (!view.getToolBar().getTitleBar().getTitleLabel().getText().equals("List books")) {
                return;
            }

            int selectedRow = view.getDataTable().getTable().getSelectedRow();
            if (selectedRow == -1) {
                CustomAlert.showWarning(view.getWindow(), "Select a book from the table!");
                return;
            }

            int bookId = Integer.parseInt(view.getDataTable().getTable().getValueAt(selectedRow, 0).toString());
            String bookTitle = view.getDataTable().getTable().getValueAt(selectedRow, 1).toString();

            if (BookDAO.isCurrentlyBorrowed(bookId)) {
                CustomAlert.showWarning(view.getWindow(), "Cannot delete! The book '" + bookTitle + "' is currently borrowed by a reader.");
                return;
            }

            BookDAO.delete(bookId);
            view.getStatusBar().getInfoBar().getRowsText().setText("The book has been successfully deleted!");

            loadBooksToTable();
        });

        view.getToolBar().getSearchBar().getSearchBar().getDocument().addDocumentListener(new DocumentListener() {
            private void triggerFilter() {
                if (view.getToolBar().getTitleBar().getTitleLabel().getText().equals("List books")) {
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

    private void loadBooksToTable() {
        List<Book> books = bookDAO.getAll();
        BooksModel model = new BooksModel();

        for (Book b : books) {
            model.getModel().addRow(new Object[]{
                    b.getIndex(), b.getTitle(), b.getAuthor(), b.getGenre(), b.isAvailable()
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
