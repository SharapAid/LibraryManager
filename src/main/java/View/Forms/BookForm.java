package View.Forms;

import View.CustomElements.CustomButton;
import View.CustomElements.RoundTextField;
import View.CustomElements.TextPlaceholder;
import View.Forms.ModelData.DataModels;

import javax.swing.*;
import java.awt.*;

public class BookForm{
    private JDialog wrapForm;
    private JTextField titleField;
    private JTextField authorField;
    private JComboBox genreBox;
    private DataModels genre;

    private CustomButton saveButton;
    private CustomButton cancelButton;

    private final Color backgroundColor = new Color(93, 93, 101);
    private final Color textColor = new Color(255, 255, 255);
    private final Font labelFont = new Font("Arial", Font.BOLD, 13);

    private int mouseX, mouseY;

    public BookForm(JFrame owner) {
        wrapForm = new JDialog(owner, "Add New Book", true);
        wrapForm.setUndecorated(true);
        wrapForm.setSize(350, 320);
        wrapForm.setLocationRelativeTo(owner);
        wrapForm.setLayout(new BorderLayout());
        wrapForm.setMinimumSize(new Dimension(350, 320));

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(backgroundColor);
        mainPanel.setBorder(BorderFactory.createLineBorder(new Color(120, 120, 128), 1));

        JPanel customTitleBar = new JPanel(new BorderLayout());
        customTitleBar.setBackground(backgroundColor);
        customTitleBar.setPreferredSize(new Dimension(350, 40));

        JLabel titleWindowLabel = new JLabel("Add New Book", SwingConstants.CENTER);
        titleWindowLabel.setForeground(textColor);
        titleWindowLabel.setFont(new Font("Arial", Font.BOLD, 16));
        customTitleBar.add(titleWindowLabel, BorderLayout.CENTER);

        customTitleBar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                mouseX = evt.getX();
                mouseY = evt.getY();
            }
        });

        customTitleBar.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                wrapForm.setLocation(evt.getXOnScreen() - mouseX, evt.getYOnScreen() - mouseY);
            }
        });

        JPanel fieldsPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        fieldsPanel.setBackground(backgroundColor);
        fieldsPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel titleLabel = new JLabel("Title:");
        titleLabel.setForeground(textColor);
        titleLabel.setFont(labelFont);
        fieldsPanel.add(titleLabel);

        titleField = new RoundTextField(8, 20);
        titleField.setPreferredSize(new Dimension(250, 30));
        fieldsPanel.add(titleField);

        JLabel authorLabel = new JLabel("Author:");
        authorLabel.setForeground(textColor);
        authorLabel.setFont(labelFont);
        fieldsPanel.add(authorLabel);

        authorField = new RoundTextField(8, 20);
        authorField.setPreferredSize(new Dimension(250, 30));
        fieldsPanel.add(authorField);

        TextPlaceholder.addPlaceholder(titleField, "Harry Potter");
        TextPlaceholder.addPlaceholder(authorField, "e.g. John Doe");

        JLabel genreLabel = new JLabel("Genre:");
        genreLabel.setForeground(textColor);
        genreLabel.setFont(labelFont);
        fieldsPanel.add(genreLabel);

        genre = new DataModels();
        genreBox = new JComboBox<>(genre.getGenres());
        genreBox.setSelectedIndex(0);
        fieldsPanel.add(genreBox);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        buttonPanel.setBackground(backgroundColor);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 10));

        saveButton = new CustomButton("Save");
        cancelButton = new CustomButton("Cancel");

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        mainPanel.add(customTitleBar, BorderLayout.NORTH);
        mainPanel.add(fieldsPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        wrapForm.add(mainPanel);

        cancelButton.addActionListener(e -> wrapForm.dispose());
    }

    public JDialog getWrapForm() {
        return wrapForm;
    }

    public JTextField getTitleField(){
        return titleField;
    }

    public JTextField getAuthorField(){
        return authorField;
    }

    public String getBookTitle() {
        return titleField.getText();
    }

    public String getBookAuthor() {
        return authorField.getText();
    }

    public String getBookGenre() {
        return String.valueOf(genreBox.getSelectedItem());
    }

    public JButton getSaveButton() {
        return saveButton;
    }
}