package nl.rug.oop.flaps.aircraft_editor.view.maineditor.main_panels;

import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import nl.rug.oop.flaps.aircraft_editor.model.EditorCore;
import nl.rug.oop.flaps.aircraft_editor.view.MessagesDb;
import nl.rug.oop.flaps.aircraft_editor.view.maineditor.EditorFrame;
import nl.rug.oop.flaps.simulation.model.airport.Airport;
import nl.rug.oop.flaps.simulation.model.cargo.CargoFreight;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Class LogPanel - panel containing the Log component and the Depart button(in the Depart Panel)
 * The log component (logBook JTextArea) is used to notify the user about the successful execution of commands, and
 * serves as a session archive;
 */

@Log
@Getter
public class LogPanel extends JPanel {
    private JTextArea logBook;
    private final EditorCore editorCore;
    private final EditorFrame editorFrame;
    private DepartPanel departPanel;
    private final ArrayList<String> unloadList = new ArrayList<>();

    public LogPanel(EditorFrame editorFrame) {
        this.editorFrame = editorFrame;
        this.editorCore = editorFrame.getEditorCore();
        this.editorCore.getConfigurator().setLogPanel(this);
        init();
    }


    private void init() {
        JPanel log = new JPanel(new BorderLayout());
        JLabel title = new JLabel(MessagesDb.LOG_TITLE);
        title.setFont(Font.getFont(String.valueOf(Font.BOLD)));
        createLogBook();
        log.add(title, BorderLayout.NORTH);
        log.add(new JScrollPane(logBook), BorderLayout.CENTER);
        add(log, BorderLayout.NORTH);
        createDepartPanel();
    }

    /**
     * Initializes and configures the logBook text area component.
     */
    private void createLogBook() {
        this.logBook = new JTextArea(8, 50);
        logBook.setLineWrap(true);
        logBook.setEditable(false);
        logBook.setWrapStyleWord(true);
        logBook.setFont(Font.getFont(Font.MONOSPACED));
        logBook.setBackground(Color.DARK_GRAY);
        logBook.setBorder(BorderFactory.createCompoundBorder());
    }

    /**
     * Initializes and configures the departPanel
     */
    private void createDepartPanel() {
        this.departPanel = new DepartPanel(editorCore);
        add(departPanel, BorderLayout.EAST);
    }

    /**
     *
     * @param logMsg - message to insert into the textarea;
     *               Appends new formatted messages to the logBook text area;
     */
    public void updateLog(String logMsg) {
        logBook.append(getTimeNow() + logMsg + "\n");
    }

    public void updateLogDelay(String logMsg) {
        logBook.append(getTimeNow() + logMsg + "\n");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            log.warning("thread sleep failed");
        }
    }

    /**
     *
     * @param airport - origin; notifies the user about the departure with explicit airport data
     */
    public void notifyDepart(Airport airport) {
        JOptionPane.showMessageDialog(editorFrame, MessagesDb.DEPART_1 +
                airport.getName());
    }

    /**
     *
     * @param airport - destination; notifies the user about the arrival;
     */
    @SneakyThrows
    public void notifyArrive(Airport airport) {
        Thread.sleep(12000);
        JOptionPane.showMessageDialog(editorFrame, MessagesDb.ARRIVE_1 +
                airport.getName());
    }

    /**
     *
     * @param airport - destination; friendly message with additional data for the user;
     */
    @SneakyThrows
    public void notifyArrivalUpdates(Airport airport) {
        String air = airport.getName();
        String n = "\n";
        JOptionPane.showMessageDialog(editorFrame, MessagesDb.ARRIVE_CARGO_UN + air + n
                + MessagesDb.ARRIVE_FUEL_UN + n
                + MessagesDb.END_MSG + n
                + MessagesDb.TY_MSG);
    }

    /**
     * Shows the user the list of all the cargo that was unloaded from the aircraft;
     */
    public void notifyUnloadList() {
        String n = "\n";
        StringBuilder builder = new StringBuilder();
        unloadList.forEach(builder::append);
        String unloadData = builder.toString();
        JOptionPane.showMessageDialog(editorFrame, MessagesDb.UNLOAD_TITLE
                + n + unloadData);
    }

    /**
     *
     * @param freight freightUnit to be unloaded; adds list members to unloadedFreights list;
     */
    @SneakyThrows
    public void logFreightUnload(CargoFreight freight) {
        unloadList.add(freight.getCargoType().getName() + " : " + freight.getUnitCount() + "\n");
    }

    private String getTimeNow() {
        return (java.time.LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy | HH:mm:ss")) + ": ");
    }
}
