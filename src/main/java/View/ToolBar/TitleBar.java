package View.ToolBar;

import javax.swing.*;
import java.awt.*;

public class TitleBar {
    private JPanel wrapTitle;
    private JLabel titleLabel;

    public TitleBar(){
        wrapTitle = new JPanel(new BorderLayout());
        wrapTitle.setPreferredSize(new Dimension(120,30));
        wrapTitle.setOpaque(false);

        titleLabel = new JLabel();
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
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
