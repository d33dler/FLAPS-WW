package nl.rug.oop.gui.view;

import lombok.Getter;
import nl.rug.oop.gui.control.CustomQueryAction;
import nl.rug.oop.gui.model.AppCore;
import nl.rug.oop.gui.util.UpdateInterface;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;

/**
 * QueryPanel - contains the following components:
 * Text field #1 (customQuery), Text field #2 (log), Submit button;
 */

@Getter
public class QueryPanel extends JPanel implements UpdateInterface {
    private AppCore model;
    private JTextArea customQuery;
    private JTextArea log;

    /**
     * Tokenized log messages;
     */
    private final String Q_ERROR = "Encountered an error while executing the query. Aborting.";
    private final String Q_POS = "Your query was executed successfully.";
    private final String EXPORT_ERROR = "Encountered an error while exporting database files. Aborting.";
    private final String EXPORT_POS = "Database data was exported successfully";

    /**
     * @param model Constructor, adds the class panel to the TableUpdater update listener list;
     */
    public QueryPanel(AppCore model) {
        this.model = model;
        model.getTableUpdater().addListener(this);
        init();
    }

    /**
     * init() -> setup Border Layout and call tertiary component initialization methods;
     */
    public void init() {
        setLayout(new BorderLayout());
        addLog();
        addCustomQueryField();
        addButton();
    }

    /**
     * Creates and configures the logbook text area for message reporting;
     */
    public void addLog() {
        log = new JTextArea(" ■ Command Log:\n", 8, 35);
        log.setWrapStyleWord(true);
        log.setLineWrap(true);
        log.setEditable(false);
        log.setFont(Font.getFont(Font.MONOSPACED));
        log.setBackground(Color.DARK_GRAY);
        log.setBorder(BorderFactory.createCompoundBorder());
        add(new JScrollPane(log), BorderLayout.NORTH);
    }

    /**
     * Creates and configures the custom query input text field for database updating;
     */
    public void addCustomQueryField() {
        customQuery = new JTextArea("Insert query here...", 7, 25);
        customQuery.setFont(Font.getFont(Font.DIALOG));
        customQuery.setLineWrap(true);
        customQuery.setWrapStyleWord(true);
        customQuery.setSelectedTextColor(Color.white);
        customQuery.setBorder(BorderFactory.createEtchedBorder());
        add(new JScrollPane(customQuery), BorderLayout.CENTER);
    }

    /**
     * Creates and configures the button for submission of SQL query update command
     * from the custom query text field.An action listener is added (CustomQueryAction);
     */
    public void addButton() {
        JButton submit = new JButton(new CustomQueryAction(model, "Submit", this.customQuery));
        add(submit, BorderLayout.EAST);
        submit.setSize(20, 10);
    }

    /**
     * update() -> clears the contents of the custom query text field.
     */
    @Override
    public void update(AppCore model) {
        customQuery.setText("");
    }

    /**
     * Updates log -> appends the time and result of the custom query submission
     * to inform the user;
     */
    public void updateLog(AppCore model) {
        if (model.getLogQuery() == 0) {
            getLog().append("\n" + getTimeNow() + ": " + Q_ERROR);
        } else {
            getLog().append("\n" + getTimeNow() + ": " + Q_POS);
        }
    }

    /**
     * Updates log -> appends the time and result of the database exportation to
     * a file format variant.
     */
    public void exportUpdateLog(AppCore model) {
        if (!model.isConfirmExport()) {
            getLog().append("\n" + getTimeNow() + ": " + EXPORT_ERROR);
        } else {
            getLog().append("\n" + getTimeNow() + ": " + EXPORT_POS);
        }
    }

    /**
     * Updates log ->
     *
     * @param error contains the error text mentioning the class the fields(>=1) of which were found
     *              to be null; The message originates from FetchUtils and is collected by the model;
     */

    public void entityExtractionError(String error) {
        getLog().append("\n" + getTimeNow() + ": " + error);
    }

    /**
     * @return the current time in the specified pattern format.
     */
    public String getTimeNow() {
        return java.time.LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy | HH:mm:ss"));
    }
}
