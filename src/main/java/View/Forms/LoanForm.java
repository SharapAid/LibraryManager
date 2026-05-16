package View.Forms;

import View.CustomElements.CustomButton;
import View.CustomElements.RoundTextField;
import Model.Entity.Book;
import Model.Entity.Client;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class LoanForm {
    private JDialog wrapForm;
    private JComboBox<Book> bookBox;
    private JComboBox<Client> clientBox;
    private JTextField dateField;

    private CustomButton saveButton;
    private CustomButton cancelButton;

    private final Color backgroundColor = new Color(93, 93, 101);
    private final Color textColor = new Color(255, 255, 255);
    private final Font labelFont = new Font("Arial", Font.BOLD, 13);

    private int mouseX, mouseY;

    public LoanForm(JFrame owner, List<Book> availableBooks, List<Client> allClients) {
        wrapForm = new JDialog(owner, "Issue a Book", true);
        wrapForm.setUndecorated(true);
        wrapForm.setSize(350, 320);
        wrapForm.setLocationRelativeTo(owner);
        wrapForm.setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(backgroundColor);
        mainPanel.setBorder(BorderFactory.createLineBorder(new Color(120, 120, 128), 1));

        JPanel customTitleBar = new JPanel(new BorderLayout());
        customTitleBar.setBackground(backgroundColor);
        customTitleBar.setPreferredSize(new Dimension(350, 40));

        JLabel titleWindowLabel = new JLabel("Issue a Book", SwingConstants.CENTER);
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

        JLabel bookLabel = new JLabel("Select Book:");
        bookLabel.setForeground(textColor);
        bookLabel.setFont(labelFont);
        fieldsPanel.add(bookLabel);

        bookBox = new JComboBox<>();
        for (Book b : availableBooks) {
            bookBox.addItem(b);
        }
        fieldsPanel.add(bookBox);

        JLabel clientLabel = new JLabel("Select Client:");
        clientLabel.setForeground(textColor);
        clientLabel.setFont(labelFont);
        fieldsPanel.add(clientLabel);

        clientBox = new JComboBox<>();
        for (Client c : allClients) {
            clientBox.addItem(c);
        }
        fieldsPanel.add(clientBox);

        JLabel dateLabel = new JLabel("Loan Date (yyyy-MM-dd):");
        dateLabel.setForeground(textColor);
        dateLabel.setFont(labelFont);
        fieldsPanel.add(dateLabel);

        dateField = new RoundTextField(8, 20);
        dateField.setPreferredSize(new Dimension(250, 30));
        dateField.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        fieldsPanel.add(dateField);

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

    public JDialog getWrapForm() { return wrapForm; }
    public Book getSelectedBook() { return (Book) bookBox.getSelectedItem(); }
    public Client getSelectedClient() { return (Client) clientBox.getSelectedItem(); }
    public String getLoanDate() { return dateField.getText(); }
    public JButton getSaveButton() { return saveButton; }
}
