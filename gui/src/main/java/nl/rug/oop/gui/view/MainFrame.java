package nl.rug.oop.gui.view;

import lombok.Getter;
import lombok.SneakyThrows;
import nl.rug.oop.gui.control.MenuAction;
import nl.rug.oop.gui.model.AppCore;

import javax.swing.*;
import java.awt.*;

/**
 * MainFrame - general frame holding all secondary component panels:
 * TablePanel, DetailsPanel, QueryPanel, MenuBar and the FileChooser class.
 */
@Getter
public class MainFrame extends JFrame {
    private AppCore model;
    private TablePanel tablePanel;
    private DetailsPanel detailsPanel;
    private QueryPanel queryPanel;
    private FileChooser fileChooser;

    private final static int WIDTH = 520, HEIGHT = 950;

    /**
     * Constructor sets the frame naming and initiates the addition of components;
     */
    public MainFrame(AppCore model) {
        super("Fantasy Database");
        this.model = model;
        init();
    }

    /**
     * init() : configures the dimensions, layout, launch location etc;
     * calls void methods to initiate and add secondary components;
     */
    @SneakyThrows
    public void init() {
        setFocusTraversalPolicy(null);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
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

    /**
     * Initiates all panels, sets their position on a BorderLayout
     * and adds them to the frame.
     */
    public void addMainPanels() {
        tablePanel = new TablePanel(model);
        add(this.tablePanel, BorderLayout.NORTH);
        detailsPanel = new DetailsPanel(model);
        add(this.detailsPanel, BorderLayout.WEST);
        queryPanel = new QueryPanel(model);
        add(this.queryPanel, BorderLayout.SOUTH);
    }

    /**
     * Initiates a menu bar object, 2 menus and a menu item for export variants.
     * The secondary menu is fused into the main (File) menu and the main menu is integrated into
     * the menu bar.
     */
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
