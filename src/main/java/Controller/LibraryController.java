package Controller;

import Controller.ControllTableData.BooksTable;
import Controller.ControllTableData.ClientsTable;
import Controller.ControllTableData.LoansTable;
import Controller.TableController.TableControl;
import View.ViewWindow;

public class LibraryController {
    private BooksTable booksModel;
    private ClientsTable clientsModel;
    private LoansTable loansModel;
    private TableControl tableControl;

    public LibraryController(ViewWindow view){
        tableControl = new TableControl(view);
        booksModel = new BooksTable(view, tableControl.getPopupMenu());
        clientsModel = new ClientsTable(view, tableControl.getPopupMenu());
        loansModel = new LoansTable(view);

        setupDefaultTable(view);
    }

    private void setupDefaultTable(ViewWindow view) {
        view.getSideBar().getBoardBooksLoans().doClick();
    }
}
