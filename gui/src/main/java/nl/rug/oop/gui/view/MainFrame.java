package nl.rug.oop.gui.view;

import lombok.Getter;
import lombok.SneakyThrows;
import nl.rug.oop.gui.control.MenuAction;
import nl.rug.oop.gui.model.AppCore;

import javax.swing.*;
import java.awt.*;

@Getter
public class MainFrame extends JFrame {
    private AppCore model;
    private TablePanel tablePanel;
    private DetailsPanel detailsPanel;
    private QueryPanel queryPanel;
    private FileChooser fileChooser;


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
        addFileChooser();
        addMenuBar();
        addMainPanels();
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    public void addMainPanels() {
        tablePanel = new TablePanel(model);
        add(this.tablePanel, BorderLayout.NORTH);
        detailsPanel = new DetailsPanel(model);
        add(this.detailsPanel, BorderLayout.WEST);
        queryPanel = new QueryPanel(model);
        add(this.queryPanel, BorderLayout.SOUTH);
    }

    public void addMenuBar() {
        JMenuBar bar = new JMenuBar();
        JMenu menu = new JMenu("File");
        JMenu fileFormat = new JMenu("Export to...");
        JMenuItem json = new JMenuItem(new MenuAction("JSON", this));
        fileFormat.add(json);
        menu.add(fileFormat);
        bar.add(menu);
        setJMenuBar(bar);
    }

    public void addFileChooser() {
        this.fileChooser = new FileChooser();
    }

}
