import DAO.DataBaseManager;
import View.ViewWindow;

public class Main {
    public static void main(String[] args){
        DataBaseManager.initialize();
        ViewWindow window = new ViewWindow();
    }
}
