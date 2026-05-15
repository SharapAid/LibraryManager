package View.ToolBar;

import View.CustomElements.RoundTextField;

import javax.swing.*;
import java.awt.*;

public class SearchBar {
    private JTextField searchBar;
    private JPanel wrapSearchbar;

    public SearchBar(){
        wrapSearchbar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        wrapSearchbar.setOpaque(false);
        wrapSearchbar.setBorder(BorderFactory.createEmptyBorder(5,15,5,15));

        JLabel search = new JLabel("Search: ");
        search.setForeground(new Color(255,255,255));
        wrapSearchbar.add(search);

        searchBar = new RoundTextField(8,20);
        searchBar.setPreferredSize(new Dimension(150,25));

        wrapSearchbar.add(searchBar);
    }

    public JTextField getSearchBar(){
        return searchBar;
    }

    public JPanel getWrapSearchbar(){
        return wrapSearchbar;
    }
}
