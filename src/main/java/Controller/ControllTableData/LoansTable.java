package Controller.ControllTableData;

import DAO.BookDAO;
import DAO.ClientDAO;
import DAO.LoanDAO;
import Model.Entity.Book;
import Model.Entity.Client;
import Model.Entity.Loan;
import View.CustomElements.CustomAlert;
import View.Forms.LoanForm;
import View.ModelTable.LoansModel;
import View.ViewWindow;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class LoansTable {
    private ViewWindow view;
    private LoanDAO loanDAO;

    public LoansTable(ViewWindow view) {
        this.view = view;
        this.loanDAO = new LoanDAO();

        initListeners();
    }

    private void initListeners() {
        view.getSideBar().getBoardBooksLoans().addActionListener(e -> {
            view.getToolBar().getTitleBar().getTitleLabel().setText("List loans");
            loadLoansToTable();
        });

        view.getToolBar().getButtonBar().getRefreshButton().addActionListener(e -> {
            if (view.getToolBar().getTitleBar().getTitleLabel().getText().equals("List loans")) {
                loadLoansToTable();
                view.getStatusBar().getInfoBar().getRowsText().setText("Loans table refreshed!");
            }
        });

        view.getToolBar().getButtonBar().getAddButton().addActionListener(e -> {
            if (view.getToolBar().getTitleBar().getTitleLabel().getText().equals("List loans")) {

                List<Book> availableBooks = BookDAO.getAllAvailable();
                List<Client> allClients = ClientDAO.getAll();

                if (availableBooks.isEmpty()) {
                    CustomAlert.showWarning(view.getWindow(), "No books available for loan!");
                    return;
                }
                if (allClients.isEmpty()) {
                    CustomAlert.showWarning(view.getWindow(), "No registered clients found!");
                    return;
                }

                LoanForm form = new LoanForm(view.getWindow(), availableBooks, allClients);

                form.getSaveButton().addActionListener(ev -> {
                    Book selectedBook = form.getSelectedBook();
                    Client selectedClient = form.getSelectedClient();
                    String loanDate = form.getLoanDate();

                    if (selectedBook != null && selectedClient != null && !loanDate.isEmpty()) {
                        Loan newLoan = new Loan(
                                selectedBook.getIndex(),
                                selectedClient.getIndex(),
                                loanDate,
                                null,
                                0
                        );
                        loanDAO.insert(newLoan);

                        selectedBook.setAvailable(false);
                        BookDAO.update(selectedBook);

                        form.getWrapForm().dispose();

                        view.getStatusBar().getInfoBar().getRowsText().setText("The loan has been successfully added!");

                        loadLoansToTable();
                    }
                    else {
                        CustomAlert.showWarning(form.getWrapForm(), "Please check all fields!");
                    }
                });
                form.getWrapForm().setVisible(true);
            }
        });

        view.getToolBar().getButtonBar().getDeleteButton().addActionListener(e -> {
            if (!view.getToolBar().getTitleBar().getTitleLabel().getText().equals("List loans")) {
                return;
            }

            int selectedRow = view.getDataTable().getTable().getSelectedRow();
            if (selectedRow == -1) {
                CustomAlert.showWarning(view.getWindow(), "Select a loan from the table!");
                return;
            }

            String dateReturned = view.getDataTable().getTable().getValueAt(selectedRow, 4).toString();
            if (!dateReturned.equals("Not returned")) {
                CustomAlert.showWarning(view.getWindow(), "This book has already been returned!");
                return;
            }

            int loanId = Integer.parseInt(view.getDataTable().getTable().getValueAt(selectedRow, 0).toString());

            LoanDAO.returnBookByLoanId(loanId);
            view.getStatusBar().getInfoBar().getRowsText().setText("The book returned successfully!");

            loadLoansToTable();
        });
    }

    private void loadLoansToTable() {
        List<Object[]> loansData = loanDAO.getAllDetailed();
        LoansModel model = new LoansModel();

        for (Object[] row : loansData) {
            model.getModel().addRow(row);
        }

        view.getDataTable().getTable().setModel(model.getModel());
        int rowCount = view.getDataTable().getTable().getRowCount();
        view.getStatusBar().getStatusLabel().setText("Rows: " + rowCount);
        CustomTableDisplay.customizeTableDisplay(view.getDataTable().getTable());
    }
}
