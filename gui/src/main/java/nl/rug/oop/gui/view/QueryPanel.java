package nl.rug.oop.gui.view;

import javax.swing.*;
import java.awt.*;

public class QueryPanel extends JPanel  {
    public QueryPanel(){
        init();
    }
    public void init() {
        setLayout(new FlowLayout());
        JTextArea query = new JTextArea("Insert query here...",7,25);
        query.setFont(Font.getFont(Font.DIALOG));
        query.setLineWrap(true);
        query.setWrapStyleWord(true);
        query.setSelectedTextColor(Color.white);
        query.setBorder(BorderFactory.createEtchedBorder());
        add(new JScrollPane(query));
        addButton();
    }

    public void addButton(){
        JButton submit = new JButton("Submit");
        add(submit);
    }
}
