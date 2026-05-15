package View.StatusBar;

import javax.swing.*;
import java.awt.*;

public class InfoBar {
    private JPanel wrapInfoBar;
    private JLabel saveText;
    private JLabel rowsText;

    public InfoBar(){
        wrapInfoBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        wrapInfoBar.setOpaque(false);
        wrapInfoBar.setBorder(BorderFactory.createEmptyBorder(5,15,5,15));

        //add animation of the saving.
        saveText = new JLabel("saving...");
        saveText.setForeground(new Color(255,255,255));

        rowsText = new JLabel();
        rowsText.setForeground(new Color(255,255,255));

        wrapInfoBar.add(rowsText);
        wrapInfoBar.add(Box.createHorizontalStrut(20));
        wrapInfoBar.add(saveText);
    }

    public JLabel getRowsText() {
        return rowsText;
    }

    public JPanel getWrapInfoBar() {
        return wrapInfoBar;
    }

    public JLabel getSaveText() {
        return saveText;
    }
}
