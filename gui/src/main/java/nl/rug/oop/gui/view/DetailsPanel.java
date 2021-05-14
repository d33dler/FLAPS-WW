package nl.rug.oop.gui.view;

import nl.rug.oop.gui.model.AppCore;
import nl.rug.oop.gui.model.Table;
import nl.rug.oop.gui.util.UpdateInterface;

import javax.swing.*;
import java.awt.*;

public class DetailsPanel extends JPanel implements UpdateInterface {
    AppCore model;
    JTextArea detailsText, descText;
    JLabel id, created, name, type, health, attack, defense;

    public DetailsPanel(AppCore model) {
        this.model = model;
        model.getDetailsUpdater().addListener(this);
        init();
    }

    public void init() {

        this.setBorder(BorderFactory.createEtchedBorder());
        setPreferredSize(new Dimension(500, 400));
        add(new ImagePanel(model), BorderLayout.WEST);
        setDetailsData();
        setDetailsDescription();
        validate();
    }

    public void setDetailsData() {
        JPanel detailsData = new JPanel();
        detailsData.setPreferredSize(new Dimension(250, 200));
        detailsData.setLayout(new BoxLayout(detailsData, BoxLayout.PAGE_AXIS));
        this.detailsText = new JTextArea("Details:", 1, 1);
        this.detailsText.setEditable(false);
        detailsData.add(detailsText);
        detailsData.add(id = new JLabel());
        detailsData.add(created = new JLabel());
        detailsData.add(name = new JLabel());
        detailsData.add(type = new JLabel());
        detailsData.add(health = new JLabel());
        detailsData.add(attack = new JLabel());
        detailsData.add(defense = new JLabel());
        add(detailsData, BorderLayout.CENTER);
    }

    public void setDetailsDescription() {
        JPanel descriptionData = new JPanel();
        descriptionData.setPreferredSize(new Dimension(500, 150));
        descriptionData.setLayout(new BoxLayout(descriptionData, BoxLayout.PAGE_AXIS));
        this.descText = new JTextArea("Description:\n ", 1, 1);
        this.descText.setEditable(false);
        this.descText.setWrapStyleWord(true);
        this.descText.setLineWrap(true);
        descriptionData.add(new JScrollPane(descText));
        add(descriptionData, BorderLayout.SOUTH);
    }

    @Override
    public void update(AppCore model) {
        Table database = model.getDatabase();
        JTable guiTable = model.getGui().getTablePanel().getTable();
        updateDetails(database, guiTable);
        updateDescription(database, guiTable);
        revalidate();
    }

    public void updateDescription(Table database, JTable guiTable) {
        descText.setText("Description:\n " + database.getTable().getValueAt(guiTable.getSelectedRow(), 5).toString());
    }
    public void updateDetails(Table database, JTable guiTable) {
        id.setText("Id: " +  database.getTable().getValueAt(guiTable.getSelectedRow(), 0).toString());
        created.setText("Created: " +  database.getTable().getValueAt(guiTable.getSelectedRow(), 1).toString());
        name.setText("Name: " +  database.getTable().getValueAt(guiTable.getSelectedRow(), 2).toString());
        type.setText("Type: " +  database.getTable().getValueAt(guiTable.getSelectedRow(), 4).toString());
        health.setText("Health: " +  database.getTable().getValueAt(guiTable.getSelectedRow(), 6).toString());
        attack.setText("Attack: " +  database.getTable().getValueAt(guiTable.getSelectedRow(), 7).toString());
        defense.setText("Defense: " +  database.getTable().getValueAt(guiTable.getSelectedRow(), 8).toString());
    }

}
