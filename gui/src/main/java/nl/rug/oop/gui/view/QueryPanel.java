package nl.rug.oop.gui.view;

import nl.rug.oop.gui.control.CustomQueryAction;
import nl.rug.oop.gui.model.AppCore;
import nl.rug.oop.gui.util.UpdateInterface;

import javax.swing.*;
import java.awt.*;

public class QueryPanel extends JPanel {
    AppCore model;
    JTextArea customQuery;

    public QueryPanel(AppCore model) {
        this.model = model;
        init();
    }

    public void init() {
        setLayout(new FlowLayout());
        customQuery = new JTextArea("Insert query here...", 7, 25);
        customQuery.setFont(Font.getFont(Font.DIALOG));
        customQuery.setLineWrap(true);
        customQuery.setWrapStyleWord(true);
        customQuery.setSelectedTextColor(Color.white);
        customQuery.setBorder(BorderFactory.createEtchedBorder());
        add(new JScrollPane(customQuery));
        addButton();
    }

    public void addButton() {
        JButton submit = new JButton(new CustomQueryAction(model, "Submit", this.customQuery));
        add(submit);
    }

}
