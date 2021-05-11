package nl.rug.oop.gui.view;

import lombok.SneakyThrows;

import javax.swing.*;
import java.awt.*;


public class GuiMain extends JFrame {

    @SneakyThrows
    public GuiMain() {
        setLayout(new BorderLayout());
        Table tab = new Table();
        JTable table = new JTable(tab.getTable());
        initTableView(table);
        add(new JScrollPane(table), BorderLayout.CENTER);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(500,2000));
        pack();
       add( new JButton("Destroy"), BorderLayout.LINE_END);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }
    public void initTableView(JTable table) {
        for(int i = 0; i<5;i++){
            table.removeColumn(table.getColumnModel().getColumn(3));
        }
    }
}
