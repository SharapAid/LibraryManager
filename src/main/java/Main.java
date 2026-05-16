import Controller.LibraryController;
import DAO.DataBaseManager;
import View.ViewWindow;

import javax.swing.*;

public class Main {
    public static void main(String[] args){
        DataBaseManager.initialize();

        SwingUtilities.invokeLater(() -> {
            ViewWindow view = new ViewWindow();
            new LibraryController(view);
        });
    }
}
