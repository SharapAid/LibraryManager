package View.StatusBar;

import javax.swing.*;
import java.awt.*;

public class StatusBar {
    private JPanel statusBar;
    private JLabel statusLabel;
    private InfoBar infoBar = new InfoBar();

    public StatusBar(){
        statusBar = new JPanel(new BorderLayout());
        statusBar.setPreferredSize(new Dimension(300,30));
        statusBar.setBackground(new Color(93, 93, 101));

        statusLabel = new JLabel();
        statusLabel.setForeground(new Color(255,255,255));
        statusLabel.setBorder(BorderFactory.createEmptyBorder(2,5,2,5));

        statusBar.add(statusLabel, BorderLayout.WEST);
        statusBar.add(infoBar.getWrapInfoBar(), BorderLayout.EAST);
    }

    public JLabel getStatusLabel(){
        return statusLabel;
    }

    public JPanel getStatusBar(){
        return statusBar;
    }

    public InfoBar getInfoBar() {
        return infoBar;
    }
}
