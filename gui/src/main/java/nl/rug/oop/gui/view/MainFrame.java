package nl.rug.oop.gui.view;

import lombok.Getter;
import lombok.SneakyThrows;
import nl.rug.oop.gui.model.AppCore;

import javax.swing.*;
import java.awt.*;

@Getter
public class MainFrame extends JFrame {
    AppCore model;
    TablePanel tablePanel;
    DetailsPanel detailsPanel;
    QueryPanel queryPanel;

    public MainFrame(AppCore model) {
        super("Fantasy Database");
        this.model = model;
        init();
    }

    @SneakyThrows
    public void init() {
        setFocusTraversalPolicy(null);
        setPreferredSize(new Dimension(500, 920));
        setLayout(new BorderLayout());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        menuBar();
        tablePanel = new TablePanel(model);
        add(this.tablePanel, BorderLayout.NORTH);
        detailsPanel = new DetailsPanel(model);
        add(this.detailsPanel, BorderLayout.WEST);
        queryPanel = new QueryPanel(model);
        add(this.queryPanel, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    public void menuBar() {
        JMenuBar bar = new JMenuBar();
        JMenu menu = new JMenu("File");
        JMenuItem export = new JMenuItem("Export");
        menu.add(export);
        bar.add(menu);
        setJMenuBar(bar);
    }

}
