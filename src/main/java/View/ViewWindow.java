package View;

import javax.swing.*;
import java.awt.*;

public class ViewWindow{
    private final JFrame window;
    private final String nameApp = "Library manager";

    public ViewWindow(){
        window = new JFrame(nameApp);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setExtendedState(JFrame.MAXIMIZED_BOTH);
        window.setLayout(new BorderLayout());
        window.setLocationRelativeTo(null);

        window.setVisible(true);
    }

    public JFrame getWindow() {
        return window;
    }
}
