package nl.rug.oop.flaps.aircraft_editor.view.maineditor;

import lombok.Setter;
import lombok.SneakyThrows;
import nl.rug.oop.flaps.aircraft_editor.controller.AircraftDataTracker;
import nl.rug.oop.flaps.aircraft_editor.model.EditorCore;
import nl.rug.oop.flaps.aircraft_editor.model.listeners.interfaces.BlueprintSelectionListener;
import nl.rug.oop.flaps.aircraft_editor.model.listeners.interfaces.FuelSupplyListener;
import nl.rug.oop.flaps.simulation.model.aircraft.Compartment;
import nl.rug.oop.flaps.simulation.model.aircraft.FuelTank;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * FuelPanel class - dynamic panel on the north side of the frame. Contains the slider used to update the fuel levels
 * in selected fuel tanks.
 */
@Setter
public class FuelPanel extends JPanel implements BlueprintSelectionListener, ChangeListener, FuelSupplyListener {
    private EditorCore editorCore;
    private JSlider fuelSlider = null;
    private JPanel sliderPanel = null;
    private JLabel fuelAmount;
    private FuelTank fuelTank;
    private SettingsPanel settingsPanel;
    private static final int LARGE_TANK = 50000;
    private static final String listenerId = EditorCore.fuelListenerID;

    public FuelPanel(EditorCore editorCore, SettingsPanel settingsPanel) {
        this.editorCore = editorCore;
        this.settingsPanel = settingsPanel;
        editorCore.getBpSelectionModel().addListener(listenerId, this);
        editorCore.getAircraftLoadingModel().addListener(this);
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        setPreferredSize(new Dimension(1000, 120));
        addFuelSlider();
    }

    /**
     * Initiates and configures the fuel JSlider and the panel containing the JSlider
     */
    private void addFuelSlider() {
        this.sliderPanel = new JPanel();
        sliderPanel.setLayout(new FlowLayout());
        sliderPanel.setPreferredSize(new Dimension(900, 120));
        this.fuelSlider = new JSlider(SwingConstants.HORIZONTAL, 0, 0, 0);
        fuelSlider.setPaintTicks(true);
        fuelSlider.setPaintLabels(true);
        fuelSlider.setPreferredSize(new Dimension(550, 40));
        fuelSlider.setFont(Font.getFont(Font.DIALOG));
        fuelSlider.addChangeListener(this);
        this.fuelAmount = new JLabel("0");
        fuelAmount.setBorder(BorderFactory.createEtchedBorder());
        sliderPanel.add(fuelSlider);
        sliderPanel.add(confirmButton());
        sliderPanel.add(fuelAmount);
        add(sliderPanel);
    }

    /**
     * @return the button used to confirm the refuel action by the user;
     */
    private JButton confirmButton() {
        JButton confirm = new JButton("Load");
        confirm.addActionListener(new AbstractAction() {
            @SneakyThrows
            @Override
            public void actionPerformed(ActionEvent e) {
                editorCore.getConfigurator().updateFuelStatus(fuelTank, fuelSlider.getValue());
            }
        });
        return confirm;
    }


    @Override
    public void stateChanged(ChangeEvent e) {
        fuelAmount.setText(String.valueOf(fuelSlider.getValue()));
    }

    private void displaySlider() {
        adjustSlider(fuelTank.getCapacity());
        fuelSlider.setValue((int) editorCore.getAircraft().getFuelAmountForFuelTank(fuelTank));
    }

    /**
     * @param capacity - capacity of the selected fuel tank
     *                 used to adjust the displaying of
     *                 JSlider ticks spacing based on the fuel Tanks capacity;
     */
    private void adjustSlider(int capacity) {
        fuelSlider.setMaximum(capacity);
        fuelSlider.setMajorTickSpacing((capacity / 5));
        fuelSlider.setMinorTickSpacing(capacity > LARGE_TANK ? capacity / 20 : capacity / 80);
    }


    /**
     * @param fuelTank    selected fuel tank
     * @param dataTracker -
     *           sets this panel as visible and hides the concurrent cargo panel;
     */
    @Override
    public void compartmentSelected(Compartment fuelTank, AircraftDataTracker dataTracker) {
        this.fuelTank = (FuelTank) fuelTank;
        displaySlider();
        settingsPanel.getCargoPanel().setVisible(false);
        setVisible(true);
    }


    @Override
    public void fireFuelSupplyUpdate(AircraftDataTracker dataTracker) {
        if (this.isVisible()) {
            fuelSlider.setValue((int) editorCore.getAircraft().getFuelAmountForFuelTank(fuelTank));
        }
    }
}
