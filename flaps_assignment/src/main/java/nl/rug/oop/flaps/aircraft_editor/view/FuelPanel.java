package nl.rug.oop.flaps.aircraft_editor.view;

import lombok.Setter;
import lombok.SneakyThrows;
import nl.rug.oop.flaps.aircraft_editor.controller.AircraftDataTracker;
import nl.rug.oop.flaps.aircraft_editor.model.listeners.interfaces.BlueprintSelectionListener;
import nl.rug.oop.flaps.aircraft_editor.model.EditorCore;
import nl.rug.oop.flaps.simulation.model.aircraft.Compartment;
import nl.rug.oop.flaps.simulation.model.aircraft.FuelTank;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;

@Setter
public class FuelPanel extends JPanel implements BlueprintSelectionListener, ChangeListener {
    private EditorCore editorCore;
    private JSlider fuelSlider = null;
    private JPanel sliderPanel = null;
    private JLabel fuelAmount;
    private HashMap<Integer, Integer> sliderSaves;
    private FuelTank fuelTank;
    private SettingsPanel settingsPanel;
    private static final int LARGE_TANK = 50000;
    private static final String listenerId = EditorCore.fuelListenerID;

    public FuelPanel(EditorCore editorCore, SettingsPanel settingsPanel) {
        this.editorCore = editorCore;
        this.settingsPanel = settingsPanel;
        editorCore.getSelectionModel().addListener(listenerId, this);
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        setPreferredSize(new Dimension(1000,120));
        addFuelSlider();
    }

    private void addFuelSlider() {
        this.sliderSaves = new HashMap<>();
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

    private JButton confirmButton() {
        JButton confirm = new JButton("Load");
        confirm.addActionListener(new AbstractAction() {
            @SneakyThrows
            @Override
            public void actionPerformed(ActionEvent e) {
                sliderSaves.put(fuelTank.getCoordsHash(), fuelSlider.getValue());
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
        System.out.println(fuelTank.getName());
        if (sliderSaves.containsKey(fuelTank.getCoordsHash())) {
            fuelSlider.setValue(sliderSaves.get(fuelTank.getCoordsHash()));
        } else {
            sliderSaves.put(fuelTank.getCoordsHash(), 0);
            fuelSlider.setValue(0);
        }
    }

    private void adjustSlider(int capacity) {
        fuelSlider.setMaximum(capacity);
        fuelSlider.setMajorTickSpacing((capacity / 5));
        fuelSlider.setMinorTickSpacing(capacity > LARGE_TANK ? capacity / 20 : capacity / 80);
    }

    @Override
    public void compartmentSelected(Compartment area, AircraftDataTracker dataTracker) {
        this.fuelTank = (FuelTank) area;
        displaySlider();
        settingsPanel.getCargoPanel().setVisible(false);
        setVisible(true);
    }
}
