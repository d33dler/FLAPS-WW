package nl.rug.oop.gui.view;

import nl.rug.oop.gui.model.AppCore;
import nl.rug.oop.gui.model.Database;
import nl.rug.oop.gui.util.UpdateInterface;

import javax.swing.*;
import java.awt.*;

/**
 * DetailsPanel holds two secondary components: Image, Details and Description panel;
 */
public class DetailsPanel extends JPanel implements UpdateInterface {
    private AppCore model;
    private JTextArea detailsText, descriptionText;
    private JPanel detailsPanel, descriptionPanel;

    private final static String DESC = " » Description:\n";
    private final static String DETS = "» Details:\n";
    private final static String N_A = "Not available!";

    /**
     * Adds the class to the listener list of the Details updater and inits
     * the initialization, configuration and inclusion of secondary components;
     */
    public DetailsPanel(AppCore model) {
        this.model = model;
        model.getDetailsUpdater().addListener(this);
        init();
    }

    /**
     * init() : sets the border type, adds a new instance of the image panel,
     * calls methods to initiate secondary component panels;
     */
    public void init() {
        this.setBorder(BorderFactory.createEtchedBorder());
        setPreferredSize(new Dimension(515, 350));
        add(new ImagePanel(model), BorderLayout.WEST);
        addDetailsPanel();
        addDescriptionPanel();
        validate();
    }

    /**
     * Initiates a JPanel and a JTextArea, sets the layout and configures the
     * details text area, adding it as a JScrollPane to the panel.
     */
    public void addDetailsPanel() {
        this.detailsPanel = configurePanel(270, 200);
        this.detailsText = new JTextArea(12, 27);
        configTextArea(this.detailsText);
        this.detailsPanel.add(new JScrollPane(detailsText));
        add(detailsPanel, BorderLayout.EAST);
    }

    /**
     * Initiates a JPanel and a JTextArea, sets the layout and configures the
     * description text area, adding it as a JScrollPane to the panel.
     */
    public void addDescriptionPanel() {
        this.descriptionPanel = configurePanel(500,100);
        this.descriptionText = new JTextArea(DESC, 1, 1);
        configTextArea(this.descriptionText);
        this.descriptionText.setBorder(BorderFactory.createCompoundBorder());
        this.descriptionPanel.add(new JScrollPane(descriptionText));
        add(descriptionPanel, BorderLayout.SOUTH);
    }

    /**
     *
     * @param area - to undergo configuration
     */
    public void configTextArea(JTextArea area) {
        area.setEditable(false);
        area.setEnabled(false);
        area.setDisabledTextColor(Color.LIGHT_GRAY);
        area.setWrapStyleWord(true);
        area.setLineWrap(true);
    }

    /**
     *
     * @params width & height of panel
     * @return initialised panel with BoxLayout
     */
    public JPanel configurePanel(int width, int height) {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(width, height));
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        return panel;
    }

    /**
     *
     * @param model - supplies the database model and JTable component,
     *              both required to update details and description
     *              based on the  user's current table selection;
     */
    @Override
    public void update(AppCore model) {
        Database database = model.getDatabase();
        JTable guiTable = model.getGui().getTablePanel().getTable();
        configureDetails(database, guiTable);
        updateDescription(database, guiTable);
        revalidate();
    }

    public void updateDescription(Database database, JTable guiTable) {
        descriptionText.setText(DESC + database.getTable().getValueAt(guiTable.getSelectedRow(), 5).toString());
    }

    /**
     * Clear previous details and loop through the column data of the current selected entity
     * append new column data to the text; if data == null -> append "Not available" (N_A)
     */
    public void configureDetails(Database database, JTable guiTable) {
        String detailInfo;
        this.detailsText.setText(DETS);
        for (int i = 0; i < database.getTable().getColumnCount(); i++) {
            if (i != 5) {
                String columnId = database.getTable().getColumnName(i);
                try {
                    detailInfo = database.getTable().getValueAt(guiTable.getSelectedRow(), i).toString();
                } catch (NullPointerException e) {
                    detailInfo = N_A;
                }
                this.detailsText.append(columnId + " : " + detailInfo + "\n");
            }
        }
    }
}
