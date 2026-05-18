package View.Forms;

import View.CustomElements.CustomButton;
import View.CustomElements.RoundTextField;
import View.CustomElements.TextPlaceholder;
import View.Forms.ModelData.DataModels;

import javax.swing.*;
import java.awt.*;

public class ClientForm {
    private JDialog wrapForm;
    private JTextField nameField;
    private JTextField phoneField;
    JComboBox<String> codeComboBox;
    private JTextField emailField;
    private JTextField addressField;
    private DataModels codeCountry;

    private CustomButton saveButton;
    private CustomButton cancelButton;

    private final Color backgroundColor = new Color(93, 93, 101);
    private final Color textColor = new Color(255, 255, 255);
    private final Font labelFont = new Font("Arial", Font.BOLD, 13);

    private int mouseX, mouseY;

    public ClientForm(JFrame owner) {
        wrapForm = new JDialog(owner, "Add New Client", true);
        wrapForm.setUndecorated(true);

        wrapForm.setSize(350, 390);
        wrapForm.setLocationRelativeTo(owner);
        wrapForm.setLayout(new BorderLayout());
        wrapForm.setMinimumSize(new Dimension(350, 390));

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(backgroundColor);
        mainPanel.setBorder(BorderFactory.createLineBorder(new Color(120, 120, 128), 1));

        JPanel customTitleBar = new JPanel(new BorderLayout());
        customTitleBar.setBackground(backgroundColor);
        customTitleBar.setPreferredSize(new Dimension(350, 40));

        JLabel titleWindowLabel = new JLabel("Add New Client", SwingConstants.CENTER);
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

        JPanel fieldsPanel = new JPanel(new GridLayout(0, 1, 3, 3));
        fieldsPanel.setBackground(backgroundColor);
        fieldsPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setForeground(textColor);
        nameLabel.setFont(labelFont);
        fieldsPanel.add(nameLabel);
        nameField = new RoundTextField(8, 20);
        nameField.setPreferredSize(new Dimension(250, 30));
        fieldsPanel.add(nameField);


        JLabel phoneLabel = new JLabel("Phone:");
        phoneLabel.setForeground(textColor);
        phoneLabel.setFont(labelFont);
        fieldsPanel.add(phoneLabel);
        codeCountry = new DataModels();
        codeComboBox = new JComboBox<>(codeCountry.getCountryCodes());
        fieldsPanel.add(codeComboBox);
        phoneField = new RoundTextField(8, 20);
        phoneField.setPreferredSize(new Dimension(250, 30));
        fieldsPanel.add(phoneField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setForeground(textColor);
        emailLabel.setFont(labelFont);
        fieldsPanel.add(emailLabel);
        emailField = new RoundTextField(8, 20);
        emailField.setPreferredSize(new Dimension(250, 30));
        fieldsPanel.add(emailField);

        emailField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                String text = emailField.getText().trim();

                if (!text.isEmpty() && !text.contains("@")) {
                    emailField.setText(text + "@gmail.com");
                }
            }
        });

        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setForeground(textColor);
        addressLabel.setFont(labelFont);
        fieldsPanel.add(addressLabel);
        addressField = new RoundTextField(8, 20);
        addressField.setPreferredSize(new Dimension(250, 30));
        fieldsPanel.add(addressField);

        TextPlaceholder.addPlaceholder(nameField, "e.g. John Doe");
        TextPlaceholder.addPlaceholder(phoneField, "345 765 234");
        TextPlaceholder.addPlaceholder(emailField, "example");

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

    private String getPhoneNumber(){
        String countryCode = codeComboBox.getSelectedItem().toString().split(" ")[0];
        String rawPhone = phoneField.getText().trim().replaceAll("\\s+", "");

        String formattedNumber = rawPhone.replaceAll("(\\d{3})(?=\\d)", "$1 ");

        String fullPhone = countryCode + " " + formattedNumber;

        return fullPhone;
    }

    public JDialog getWrapForm() { return wrapForm; }
    public String getClientName() { return nameField.getText(); }

    public String getClientPhone() {
        return getPhoneNumber();
    }

    public String getClientEmail() { return emailField.getText(); }
    public String getClientAddress() { return addressField.getText(); }
    public JButton getSaveButton() { return saveButton; }
}
