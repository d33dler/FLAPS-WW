package nl.rug.oop.flaps.aircraft_editor.view.pass_editor;

import lombok.Getter;
import nl.rug.oop.flaps.aircraft_editor.controller.AircraftDataTracker;
import nl.rug.oop.flaps.aircraft_editor.model.EditorCore;
import nl.rug.oop.flaps.aircraft_editor.model.listeners.interfaces.PassengerListener;
import nl.rug.oop.flaps.aircraft_editor.model.mediators.PassengerMediator;
import nl.rug.oop.flaps.aircraft_editor.view.cargo_editor.DatabaseTablePanel;
import nl.rug.oop.flaps.aircraft_editor.view.cargo_editor.TableBuilder;
import nl.rug.oop.flaps.simulation.model.aircraft.Aircraft;
import nl.rug.oop.flaps.simulation.model.loaders.utils.FileUtils;
import nl.rug.oop.flaps.simulation.model.passengers.Passenger;

import javax.swing.*;
import java.awt.*;

/**
 * Panel holding the Database JTable of the listed registered passengers;
 */
public class PassengerTable extends JPanel implements PassengerListener {
    private final EditorCore editorCore;
    private final Aircraft aircraft;
    private final PassengersFrame boardFrame;
    @Getter
    private DatabaseTablePanel<Passenger> passengerTable;

    private final String TITLE = "Passenger Database";
    private final int T_WIDTH = 850, T_HEIGHT = 300;
    private PassengerMediator mediator;

    public PassengerTable(EditorCore editorCore, PassengersFrame boardFrame) {
        this.editorCore = editorCore;
        this.aircraft = editorCore.getAircraft();
        this.boardFrame = boardFrame;
        this.mediator = boardFrame.getMediator();
        init();
    }

    private void init() {
        editorCore.getAircraftLoadingModel().addListener(this);
        setLayout(new FlowLayout());
        addTable();
        addTableListener(passengerTable.getDatabaseTable());
    }

    /**
     * Init Table
     */
    private void addTable() {
        mediator.setPassengerSet(FileUtils.addUnitSet(boardFrame.getCabinArea(), aircraft.getCabinPassengers()));
        passengerTable = new TableBuilder<>()
                .core(editorCore)
                .frame(boardFrame)
                .db(mediator.getPassengerSet())
                .title(TITLE)
                .size(T_WIDTH, T_HEIGHT)
                .model(editorCore.getDatabaseLoader().getDatabase(mediator.getPassengerSet(), Passenger.class))
                .mediator(mediator)
                .pos(BorderLayout.CENTER)
                .buildPassengerDb();
        add(passengerTable);
    }

    /**
     *
     * @param table passenger list JTable
     */
    private void addTableListener(JTable table) {
      table.getSelectionModel().addListSelectionListener(e -> {
            if(!e.getValueIsAdjusting() && !table.getSelectionModel().isSelectionEmpty()){
                mediator.setSelectedPass(mediator.getPassHashMap().
                        get(table.getValueAt(table.getSelectedRow(), 0).toString()));
          }
        });

    }


    @Override
    public void firePassUpdate(AircraftDataTracker dataTracker) {
        passengerTable.update();
    }
}
