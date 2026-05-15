package Test.AppElements.ToolBar;

import javax.swing.*;
import java.awt.*;

public class ToolBar {
    private JPanel toolBar;
    private TitleBar titleBar = new TitleBar();
    private SearchBar searchBar = new SearchBar();
    private ButtonBar buttonBar = new ButtonBar();

    public ToolBar(){
        toolBar = new JPanel(new BorderLayout());
        toolBar.setBackground(new Color(93, 93, 101));
        toolBar.setBorder(BorderFactory.createEmptyBorder(5,15,5,15));


        toolBar.add(titleBar.getWrapTitle(), BorderLayout.WEST);
        toolBar.add(buttonBar.getWrapButton());
        toolBar.add(searchBar.getWrapSearchbar(), BorderLayout.EAST);
    }

    public JPanel getToolBar(){
        return toolBar;
    }

    public ButtonBar getButtonBar() {
        return buttonBar;
    }

    public SearchBar getSearchBar() {
        return searchBar;
    }

    public TitleBar getTitleBar() {
        return titleBar;
    }
}
