package Controller;

import Controller.ControllTableData.BooksTable;
import Controller.ControllTableData.ClientsTable;
import Controller.ControllTableData.LoansTable;
import View.ViewWindow;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;

public class LibraryController {
    private BooksTable booksModel;
    private ClientsTable clientsModel;
    private LoansTable loansModel;

    public LibraryController(ViewWindow view){
        booksModel = new BooksTable(view);
        clientsModel = new ClientsTable(view);
        loansModel = new LoansTable(view);

        setupDefaultTable(view);
    }

    private void setupDefaultTable(ViewWindow view) {
        view.getSideBar().getBoardBooksLoans().doClick();
    }
}
