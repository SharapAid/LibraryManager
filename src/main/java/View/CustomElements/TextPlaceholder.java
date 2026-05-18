package View.CustomElements;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class TextPlaceholder {

    public static void addPlaceholder(JTextField field, String placeholderText) {
        field.setText(placeholderText);
        field.setForeground(Color.GRAY);

        field.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(placeholderText)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (field.getText().trim().isEmpty()) {
                    field.setText(placeholderText);
                    field.setForeground(Color.GRAY);
                }
            }
        });
    }
}