package View.CustomElements;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CustomButtonSideBar extends JButton{
    private static int activeButton = 3;
    private int indexSelf;

    public CustomButtonSideBar(String text, int index){
        super(text);
        this.indexSelf = index;

        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setOpaque(false);

        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        setForeground(new Color(185, 185, 185));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setFont(new Font("Segoe UI", Font.BOLD, 12));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                activeButton = indexSelf;
                if(getParent() != null){
                    getParent().repaint();
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                if(activeButton != indexSelf){
                    setForeground( new  Color(209, 209, 209));
                    setFont(new Font("Segoe UI", Font.BOLD, 14));
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                if(activeButton != indexSelf){
                    setForeground( new  Color(176, 176, 176));
                    setFont(new Font("Segoe UI", Font.BOLD, 12));
                }
            }
        });
    }


    @Override
    protected void paintComponent(Graphics g){
        if(activeButton == indexSelf){
            setForeground(new  Color(255, 255, 255));
            setFont(new Font("Segoe UI", Font.BOLD, 14));
        }
        else{
            if(!getModel().isRollover()){
                setForeground( new  Color(176, 176, 176));
                setFont(new Font("Segoe UI", Font.BOLD, 12));
            }
        }
        super.paintComponent(g);
    }
}
