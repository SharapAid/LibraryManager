package View;

import View.SideBar.SideBar;
import View.StatusBar.StatusBar;
import View.Table.Table;
import View.ToolBar.ToolBar;

import javax.swing.*;
import java.awt.*;

public class ViewWindow{
    private final JFrame window;
    private final String nameApp = "Library manager";

    private SideBar sideBar;
    private ToolBar toolBar;
    private Table dataTable;
    private StatusBar statusBar;

    public ViewWindow(){
        window = new JFrame(nameApp);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setExtendedState(JFrame.MAXIMIZED_BOTH);
        window.setLayout(new BorderLayout());
        window.setLocationRelativeTo(null);

        sideBar = new SideBar();
        toolBar = new ToolBar();
        dataTable = new Table();
        statusBar = new StatusBar();

        window.add(toolBar.getToolBar(), BorderLayout.NORTH);
        window.add(sideBar.getSidebar(), BorderLayout.WEST);
        window.add(dataTable.getWrapTable(), BorderLayout.CENTER);
        window.add(statusBar.getStatusBar(), BorderLayout.SOUTH);

        window.setVisible(true);
    }

    public JFrame getWindow() {
        return window;
    }
}
