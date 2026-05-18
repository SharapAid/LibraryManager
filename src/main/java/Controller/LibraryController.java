package Controller;

import Controller.ControllTableData.BooksTable;
import Controller.ControllTableData.ClientsTable;
import Controller.ControllTableData.LoansTable;
import View.ViewWindow;

import javax.swing.*;
import java.awt.*;

public class LibraryController {
    private BooksTable booksModel;
    private ClientsTable clientsModel;
    private LoansTable loansModel;
    private TableControl tableControl;

    public LibraryController(ViewWindow view){
        booksModel = new BooksTable(view);
        clientsModel = new ClientsTable(view);
        loansModel = new LoansTable(view);
        tableControl = new TableControl(view);

        setupDefaultTable(view);
    }

    private void setupDefaultTable(ViewWindow view) {
        view.getSideBar().getBoardBooksLoans().doClick();
    }
}
