package View.CustomElements;

import javax.swing.*;
import java.awt.*;

public class RoundTextField extends JTextField {
    private int radius;

    public RoundTextField(int columns, int radius){
        super(columns);
        this.radius = radius;
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(5,10,5,10));
    }

    @Override
    protected void paintComponent(Graphics g){
        Graphics2D g2 =(Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(getBackground());
        g2.fillRoundRect(0,0,getWidth() - 1, getHeight() - 1, radius, radius);

        super.paintComponent(g2);
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 =(Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(Color.WHITE);
        g2.fillRoundRect(0,0,getWidth() - 1, getHeight() - 1, radius, radius);

        super.paintComponent(g2);
        g2.dispose();
    }
}
