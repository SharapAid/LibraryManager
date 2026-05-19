package Controller.ControllTableData;

import DAO.BookDAO;
import Model.Entity.Book;
import View.CustomElements.CustomAlert;
import View.CustomElements.CustomTableDisplay;
import View.Forms.BookForm;
import View.ModelTable.BooksModel;
import View.Table.PopupMenuTable;
import View.ViewWindow;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class BooksTable {
    private ViewWindow view;
    private BookDAO bookDAO;
    private PopupMenuTable popupMenuTable;

    public BooksTable(ViewWindow view, PopupMenuTable popupMenuTable){
        this.view = view;
        this.popupMenuTable = popupMenuTable;
        bookDAO = new BookDAO();

        initListeners();
    }

    private void initListeners() {
        view.getSideBar().getBoardBooks().addActionListener(e -> {
            view.getToolBar().getButtonBar().getDeleteButton().setText("Delete");
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

                BookForm form = new BookForm(view.getWindow());

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
            deleteBook();
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

        popupMenuTable.getDelete().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteBook();
            }
        });

        popupMenuTable.getEdit().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!view.getToolBar().getTitleBar().getTitleLabel().getText().equals("List books")) {
                    return;
                }

                JTable table = view.getDataTable().getTable();
                int row = table.getSelectedRow();
                int col = table.getSelectedColumn();

                if (col == 0 || col == 4) {
                    CustomAlert.showWarning(view.getWindow(), "This column cannot be edited directly!");
                    return;
                }

                if (row != -1 && col != -1) {
                    view.getDataTable().setEditMode(true);

                    table.editCellAt(row, col);

                    Component editor = table.getEditorComponent();
                    if (editor instanceof JTextField) {
                        editor.requestFocusInWindow();
                        ((JTextField) editor).selectAll();
                    }
                }
                else {
                    CustomAlert.showWarning(view.getWindow(), "Select a cell to edit!");
                }
            }
        });
    }

    public void deleteBook(){
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
    }

    private void loadBooksToTable() {
        List<Book> books = BookDAO.getAll();
        BooksModel model = new BooksModel();

        for (Book b : books) {
            model.getModel().addRow(new Object[]{
                    b.getIndex(), b.getTitle(), b.getAuthor(), b.getGenre(), b.isAvailable()
            });
        }
        view.getDataTable().getTable().setModel(model.getModel());

        model.getModel().addTableModelListener(e -> {
            if (e.getType() == TableModelEvent.UPDATE) {
                int row = e.getFirstRow();
                int col = e.getColumn();

                JTable table = view.getDataTable().getTable();

                int bookId = Integer.parseInt(table.getValueAt(row, 0).toString());
                String newValue = table.getValueAt(row, col).toString().trim();

                if (newValue.isEmpty()) {
                    CustomAlert.showWarning(view.getWindow(), "Field cannot be empty! Reverting changes.");
                    loadBooksToTable();
                    return;
                }

                Book editableBook = BookDAO.getById(bookId);

                if (editableBook != null) {
                    if (col == 1) editableBook.setTitle(newValue);
                    else if (col == 2) editableBook.setAuthor(newValue);
                    else if (col == 3) editableBook.setGenre(newValue);

                    BookDAO.update(editableBook);

                    view.getDataTable().setEditMode(false);

                    view.getStatusBar().getInfoBar().getRowsText().setText("Book has been successfully updated");
                }
            }
        });

        int rowCount = view.getDataTable().getTable().getModel().getRowCount();
        view.getStatusBar().getStatusLabel().setText("Rows: " + rowCount);

        CustomTableDisplay.customizeTableDisplay(view.getDataTable().getTable());

        String currentSearchText = view.getToolBar().getSearchBar().getSearchBar().getText();
        CustomTableDisplay.applySearchFilter(view.getDataTable().getTable(), currentSearchText);
    }
}
