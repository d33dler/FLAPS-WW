package nl.rug.oop.gui.view;

import javax.swing.*;
import java.awt.*;

public class SearchPanel extends JPanel {
    public SearchPanel() {
        init();
    }

    public void init() {
        setPreferredSize(new Dimension(500, 50));
        this.setLayout(new FlowLayout());
        JTextArea txt = new JTextArea("Search: ");
        txt.setEnabled(false);
        add(txt);
        add(new SearchBarPanel());
        this.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        validate();
    }
}
