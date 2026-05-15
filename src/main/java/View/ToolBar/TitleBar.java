package Test.AppElements.ToolBar;

import javax.swing.*;
import java.awt.*;

public class TitleBar {
    private JPanel wrapTitle;
    private JLabel titleLabel;

    public TitleBar(){
        wrapTitle = new JPanel(new BorderLayout());
        wrapTitle.setPreferredSize(new Dimension(150,30));
        wrapTitle.setOpaque(false);

        titleLabel = new JLabel("List books");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 15));
        titleLabel.setForeground(new Color(255,255,255));

        wrapTitle.add(titleLabel, BorderLayout.WEST);
    }

    public JLabel getTitleLabel(){
        return titleLabel;
    }

    public JPanel getWrapTitle() {
        return wrapTitle;
    }
}
