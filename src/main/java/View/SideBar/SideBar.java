package View.SideBar;

import View.CustomElements.CustomButtonSideBar;

import javax.swing.*;
import java.awt.*;

public class SideBar{
    private JPanel sidebar;

    private JPanel wrapButton;

    private CustomButtonSideBar boardBooks;
    private CustomButtonSideBar boardClients;
    private CustomButtonSideBar boardBooksLoans;

    public SideBar(){
        sidebar = new JPanel(new BorderLayout());
        sidebar.setBackground(new Color(93, 93, 101));
        sidebar.setPreferredSize(new Dimension(150,0));
        sidebar.setBorder(BorderFactory.createEtchedBorder());

        wrapButton = new JPanel();
        wrapButton.setLayout(new GridLayout(0, 1, 0, 0));
        wrapButton.setOpaque(false);

        boardBooks = new CustomButtonSideBar("Board of books",1);
        boardClients = new CustomButtonSideBar("Board of clients",2);
        boardBooksLoans = new CustomButtonSideBar("Board of loans",3);

        wrapButton.add(boardBooks);
        wrapButton.add(boardClients);
        wrapButton.add(boardBooksLoans);

        sidebar.add(wrapButton, BorderLayout.NORTH);
        sidebar.add(new JPanel() {{ setOpaque(false); }}, BorderLayout.CENTER);
    }

    public JPanel getSidebar(){
        return sidebar;
    }

    public  JButton getBoardBooks(){
        return boardBooks;
    }

    public  JButton getBoardClients(){
        return boardClients;
    }

    public  JButton getBoardBooksLoans(){
        return boardBooksLoans;
    }
}
