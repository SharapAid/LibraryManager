package View.CustomElements;

import javax.swing.*;
import java.awt.*;

public class CustomAlert{

    public CustomAlert(){}

    public static void showWarning(Component parent, String message) {
        Color backgroundColor = new Color(93, 93, 101);
        Color textColor = new Color(255, 255, 255);

        UIManager.put("OptionPane.background", backgroundColor);
        UIManager.put("Panel.background", backgroundColor);
        UIManager.put("OptionPane.messageForeground", textColor);
        UIManager.put("OptionPane.messageFont", new Font("Arial", Font.BOLD, 13));

        JOptionPane optionPane = new JOptionPane(
                message,
                JOptionPane.WARNING_MESSAGE,
                JOptionPane.DEFAULT_OPTION,
                null,
                new Object[]{},
                null
        );

        optionPane.setBorder(BorderFactory.createEmptyBorder(20, 25, 10, 25));

        Window parentWindow = SwingUtilities.getWindowAncestor(parent);
        JDialog dialog;
        if (parentWindow instanceof Frame) {
            dialog = new JDialog((Frame) parentWindow, "Warning", true);
        } else {
            dialog = new JDialog((Dialog) parentWindow, "Warning", true);
        }
        dialog.setUndecorated(true);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(backgroundColor);
        mainPanel.setBorder(BorderFactory.createLineBorder(new Color(120, 120, 128), 1));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(backgroundColor);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        CustomButton okButton = new CustomButton("OK");
        okButton.setPreferredSize(new Dimension(80, 30));

        okButton.addActionListener(e -> dialog.dispose());
        buttonPanel.add(okButton);

        mainPanel.add(optionPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setContentPane(mainPanel);

        dialog.pack();
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);
    }
}
