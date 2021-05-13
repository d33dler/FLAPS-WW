package nl.rug.oop.gui.view;

import nl.rug.oop.gui.model.AppCore;
import nl.rug.oop.gui.util.UpdateInterface;

import javax.swing.*;
import java.awt.*;

public class DetailsPanel extends JPanel implements UpdateInterface {
    AppCore model;
    JTextArea detailsText, descText;
    JLabel id, created, name, type, health, attack, defense;
    JLabel description;

    public DetailsPanel(AppCore model) {
        this.model = model;
        model.getDetailsUpdater().addListener(this);
        init();
    }

    public void init() {

        this.setBorder(BorderFactory.createEtchedBorder());
        setPreferredSize(new Dimension(500, 400));
        add(new ImagePanel(), BorderLayout.WEST);
        setDetailsData();
        setDetailsDescription();
        validate();
    }

    public void setDetailsData() {
        JPanel detailsData = new JPanel();
        detailsData.setPreferredSize(new Dimension(250, 200));
        detailsData.setLayout(new BoxLayout(detailsData, BoxLayout.PAGE_AXIS));
        this.detailsText = new JTextArea("Details:", 1, 1);
        this.detailsText.setPreferredSize(new Dimension(30, 20));
        this.detailsText.setEditable(false);
        detailsData.add(detailsText);
        add(detailsData, BorderLayout.CENTER);
    }

    public void setDetailsDescription() {
        this.description = new JLabel("N/A", null, SwingConstants.LEFT);
        description.setPreferredSize(new Dimension(400,50));
        JPanel descriptionData = new JPanel();
        descriptionData.setPreferredSize(new Dimension(500, 150));
        descriptionData.setLayout(new BoxLayout(descriptionData, BoxLayout.PAGE_AXIS));
        this.descText = new JTextArea("Description:\n ", 1, 1);
        this.descText.setEditable(false);
        descriptionData.add(descText);
        descriptionData.add(description);
        add(descriptionData, BorderLayout.SOUTH);
    }
    @Override
    public void update(AppCore model) {
        JTable table = model.getGui().getJTable();
        this.description.setText(model.getDatabase().getTable().getValueAt(table.getSelectedRow(), 2).toString());
    }
}
