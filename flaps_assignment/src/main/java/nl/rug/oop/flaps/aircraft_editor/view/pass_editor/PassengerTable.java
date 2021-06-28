package nl.rug.oop.flaps.aircraft_editor.view.pass_editor;

import lombok.Getter;
import nl.rug.oop.flaps.aircraft_editor.controller.AircraftDataTracker;
import nl.rug.oop.flaps.aircraft_editor.model.EditorCore;
import nl.rug.oop.flaps.aircraft_editor.model.listeners.interfaces.PassengerListener;
import nl.rug.oop.flaps.aircraft_editor.view.cargo_editor.DatabaseTablePanel;
import nl.rug.oop.flaps.aircraft_editor.view.cargo_editor.TableBuilder;
import nl.rug.oop.flaps.simulation.model.aircraft.Aircraft;
import nl.rug.oop.flaps.simulation.model.loaders.FileUtils;
import nl.rug.oop.flaps.simulation.model.passengers.Passenger;

import javax.swing.*;
import java.awt.*;
import java.util.Set;

public class PassengerTable extends JPanel implements PassengerListener {
    private final EditorCore editorCore;
    private final Aircraft aircraft;
    private final PassengersFrame boardFrame;
    @Getter
    private DatabaseTablePanel<Passenger> passengerTable;
    private Set<Passenger> passengerSet;
    private final String TITLE = "Passenger Database";
    private final int T_WIDTH = 850, T_HEIGHT = 300;

    public PassengerTable(EditorCore editorCore, PassengersFrame boardFrame) {
        this.editorCore = editorCore;
        this.aircraft = editorCore.getAircraft();
        this.boardFrame = boardFrame;
        init();
    }

    private void init() {
        editorCore.getAircraftLoadingModel().addListener(this);
        setLayout(new FlowLayout());
        addTable();
    }

    private void addTable() {
        passengerSet = FileUtils.addUnitSet(boardFrame.getCabinArea(), aircraft.getCabinPassengers());
        passengerTable = new TableBuilder<>()
                .core(editorCore)
                .frame(boardFrame)
                .db(passengerSet)
                .title(TITLE)
                .size(T_WIDTH, T_HEIGHT)
                .model(editorCore.getDatabaseBuilder().getDatabase(passengerSet, Passenger.class))
                .pos(BorderLayout.CENTER)
                .buildPassengerDb();
        add(passengerTable);
    }

    @Override
    public void firePassUpdate(AircraftDataTracker dataTracker) {
        passengerTable.update();
    }
}
