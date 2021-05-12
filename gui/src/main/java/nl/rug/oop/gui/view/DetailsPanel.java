package nl.rug.oop.gui.view;

import javax.swing.*;
import java.awt.*;

public class DetailsPanel extends JPanel {

    public DetailsPanel() {
        init();
    }

    public void init() {
        this.setBorder(BorderFactory.createEtchedBorder());
        setPreferredSize(new Dimension(500,200));
        add(new ImagePanel(),BorderLayout.WEST);
        JPanel details = new JPanel();
        details.setPreferredSize(new Dimension(250,200));
        details.setLayout(new BoxLayout(details,BoxLayout.PAGE_AXIS));
        JTextArea dText = new JTextArea("Details:",1,1);
        dText.setPreferredSize(new Dimension(30,20));
        dText.setEditable(false);
        details.add(dText);
        add(details, BorderLayout.CENTER);
        validate();
    }
}
