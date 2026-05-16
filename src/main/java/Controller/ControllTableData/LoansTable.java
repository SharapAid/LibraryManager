package Controller.ControllTableData;

import DAO.BookDAO;
import DAO.ClientDAO;
import DAO.LoanDAO;
import Model.Entity.Book;
import Model.Entity.Client;
import View.Forms.LoanForm;
import View.ModelTable.LoansModel;
import View.ViewWindow;
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
                System.out.println("Loans table refreshed!");
            }
        });

        view.getToolBar().getButtonBar().getAddButton().addActionListener(e -> {
            if (view.getToolBar().getTitleBar().getTitleLabel().getText().equals("List loans")) {

                List<Model.Entity.Book> availableBooks = BookDAO.getAllAvailable();
                List<Model.Entity.Client> allClients = ClientDAO.getAll();

                if (availableBooks.isEmpty()) {
                    View.CustomElements.CustomAlert.showWarning(view.getWindow(), "No books available for loan!");
                    return;
                }
                if (allClients.isEmpty()) {
                    View.CustomElements.CustomAlert.showWarning(view.getWindow(), "No registered clients found!");
                    return;
                }

                LoanForm form = new LoanForm(view.getWindow(), availableBooks, allClients);

                form.getSaveButton().addActionListener(ev -> {
                    Book selectedBook = form.getSelectedBook();
                    Client selectedClient = form.getSelectedClient();
                    String loanDate = form.getLoanDate();

                    if (selectedBook != null && selectedClient != null && !loanDate.isEmpty()) {
                        Model.Entity.Loan newLoan = new Model.Entity.Loan(
                                selectedBook.getIndex(),
                                selectedClient.getIndex(),
                                loanDate,
                                0
                        );
                        loanDAO.insert(newLoan);

                        selectedBook.setAvailable(false);
                        BookDAO.update(selectedBook);

                        form.getWrapForm().dispose();
                        loadLoansToTable();
                    } else {
                        View.CustomElements.CustomAlert.showWarning(form.getWrapForm(), "Please check all fields!");
                    }
                });
                form.getWrapForm().setVisible(true);
            }
        });
    }

    private void loadLoansToTable() {
        List<Object[]> loansData = loanDAO.getAllDetailed();
        LoansModel model = new LoansModel();

        for (Object[] row : loansData) {
            model.getModel().addRow(row);
        }

        view.getDataTable().getTable().setModel(model.getModel());
    }
}
