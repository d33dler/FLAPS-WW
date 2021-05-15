package nl.rug.oop.gui.view;

import lombok.Getter;
import nl.rug.oop.gui.control.CustomQueryAction;
import nl.rug.oop.gui.model.AppCore;
import nl.rug.oop.gui.util.UpdateInterface;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.time.format.DateTimeFormatter;

@Getter
public class QueryPanel extends JPanel implements UpdateInterface {
    AppCore model;


    JTextArea customQuery;
    JTextArea log;

    public QueryPanel(AppCore model) {
        this.model = model;
        model.getTableUpdater().addListener(this);
        init();
    }

    public void init() {
        setLayout(new BorderLayout());
        log = new JTextArea("Log:\n",5,35);
        log.setWrapStyleWord(true);
        log.setLineWrap(true);
        log.setEditable(false);
        log.setFont(Font.getFont(Font.MONOSPACED));
        log.setBackground(Color.DARK_GRAY);
        log.setBorder(BorderFactory.createCompoundBorder());
        add(new JScrollPane(log), BorderLayout.NORTH);
        customQuery = new JTextArea("Insert query here...", 7, 25);
        customQuery.setFont(Font.getFont(Font.DIALOG));
        customQuery.setLineWrap(true);
        customQuery.setWrapStyleWord(true);
        customQuery.setSelectedTextColor(Color.white);
        customQuery.setBorder(BorderFactory.createEtchedBorder());
        add(new JScrollPane(customQuery), BorderLayout.CENTER);
        addButton();
    }

    public void addButton() {
        JButton submit = new JButton(new CustomQueryAction(model, "Submit", this.customQuery));
        add(submit,BorderLayout.EAST);
        submit.setSize(20,10);
    }

    @Override
    public void update(AppCore model) {
       customQuery.setText("");
    }
    public void updateLog(AppCore model) {
        String time = java.time.LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy | HH:mm:ss"));
        if (model.getLogQuery() == 0) {
            getLog().append("\n" + time +": " + model.getQ_ERROR());
        } else {
            getLog().append("\n" + time +": " + model.getQ_POS());
        }
    }
}
