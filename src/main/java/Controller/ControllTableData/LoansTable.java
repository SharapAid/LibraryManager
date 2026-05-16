package Controller.ControllTableData;

import DAO.LoanDAO;
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
