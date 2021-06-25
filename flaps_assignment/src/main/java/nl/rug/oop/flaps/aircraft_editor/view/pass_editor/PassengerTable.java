package nl.rug.oop.flaps.aircraft_editor.view.pass_editor;

import nl.rug.oop.flaps.aircraft_editor.model.EditorCore;
import nl.rug.oop.flaps.aircraft_editor.view.cargo_editor.DatabaseTablePanel;
import nl.rug.oop.flaps.aircraft_editor.view.cargo_editor.TableBuilder;
import nl.rug.oop.flaps.simulation.model.aircraft.Aircraft;
import nl.rug.oop.flaps.simulation.model.loaders.FileUtils;
import nl.rug.oop.flaps.simulation.model.passengers.Passenger;

import javax.swing.*;
import java.awt.*;
import java.util.Set;

public class PassengerTable extends JPanel {
    private EditorCore editorCore;
    private Aircraft aircraft;
    private PassengerBoardFrame boardFrame;
    private DatabaseTablePanel<Passenger> passengerTable;
    private Set<?> passengerSet;
    private final String TITLE = "Passenger Database";

    public PassengerTable(EditorCore editorCore, PassengerBoardFrame boardFrame) {
        this.editorCore = editorCore;
        this.aircraft = editorCore.getAircraft();
        this.boardFrame = boardFrame;
        init();
    }

    private void init() {
       // setPreferredSize(new Dimension(250, 300));
        setLayout(new FlowLayout());
        addTable();
    }

    private void addTable() {
        passengerSet = FileUtils.addUnitSet(boardFrame.getCabinArea(),aircraft.getCabinPassengers());
        passengerTable = new TableBuilder<>()
                .core(editorCore)
                .frame(boardFrame)
                .db(passengerSet)
                .title(TITLE)
                .model(editorCore.getDatabaseBuilder().getDatabase(passengerSet, Passenger.class))
                .pos(BorderLayout.CENTER)
                .buildPassengerDb();
        add(passengerTable);
    }
}
