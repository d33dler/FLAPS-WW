package nl.rug.oop.flaps.aircraft_editor.view;

import lombok.Getter;
import nl.rug.oop.flaps.aircraft_editor.model.EditorCore;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;

@Getter
public class LogPanel extends JPanel {
    private JTextArea logBook;
    private EditorCore editorCore;
    private EditorFrame editorFrame;
    private DepartPanel departPanel;


    public LogPanel(EditorFrame editorFrame) {
        this.editorFrame = editorFrame;
        this.editorCore = editorFrame.getEditorCore();
        init();
    }

    private void init() {
        this.editorCore.getConfigurator().setLogPanel(this);
        JPanel log = new JPanel(new BorderLayout());
        JLabel title = new JLabel("■ COMMAND LOG HISTORY");
        title.setFont(Font.getFont(String.valueOf(Font.BOLD)));
        this.logBook = new JTextArea(8, 50);
        logBook.setLineWrap(true);
        logBook.setEditable(false);
        logBook.setWrapStyleWord(true);
        logBook.setFont(Font.getFont(Font.MONOSPACED));
        logBook.setBackground(Color.DARK_GRAY);
        logBook.setBorder(BorderFactory.createCompoundBorder());
        log.add(title, BorderLayout.NORTH);
        log.add(new JScrollPane(logBook), BorderLayout.CENTER);
        add(log, BorderLayout.NORTH);
        this.departPanel = new DepartPanel(editorCore);
        add(departPanel, BorderLayout.EAST);
    }

    public void updateLog(String logMsg) {
        logBook.append(getTimeNow() + logMsg + "\n");
    }

    private String getTimeNow() {
        return (java.time.LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy | HH:mm:ss")) + " ");
    }
}
