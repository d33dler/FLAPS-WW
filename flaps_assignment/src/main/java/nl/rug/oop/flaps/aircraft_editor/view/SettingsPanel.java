package nl.rug.oop.flaps.aircraft_editor.view;

import lombok.Getter;
import lombok.SneakyThrows;
import nl.rug.oop.flaps.aircraft_editor.controller.actions.BlueprintSelectionListener;
import nl.rug.oop.flaps.aircraft_editor.model.BlueprintSelectionModel;
import nl.rug.oop.flaps.aircraft_editor.model.EditorCore;
import nl.rug.oop.flaps.simulation.model.aircraft.CargoArea;
import nl.rug.oop.flaps.simulation.model.aircraft.Compartment;
import nl.rug.oop.flaps.simulation.model.aircraft.FuelTank;
import nl.rug.oop.flaps.simulation.model.loaders.FileUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

@Getter
public class SettingsPanel extends JPanel implements BlueprintSelectionListener {
    private EditorCore model;
    private BlueprintSelectionModel selectionModel;
    private JTextArea areaData;
    private FuelSettings fuelSettings;
    private Compartment compartmentArea;
    private JButton exCargoLoader;

    public SettingsPanel(EditorCore model) {
        this.model = model;
        this.selectionModel = model.getSelectionModel();
        this.selectionModel.addListener(this);
        init();
    }

    private void init() {
        setLayout(new FlowLayout());
        setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        setPreferredSize(new Dimension(500, 150));
        areaData = new JTextArea("Selected compartment:\n\nN/A",7,17);
        areaData.setBorder(BorderFactory.createEtchedBorder());
        areaData.setWrapStyleWord(true);
        areaData.setLineWrap(true);
        areaData.setEditable(false);
        areaData.setEnabled(false);
        areaData.setDisabledTextColor(Color.white);
        areaData.setFont(Font.getFont(Font.MONOSPACED));
        this.fuelSettings = new FuelSettings(model);
        this.setBorder(BorderFactory.createEtchedBorder());
        add(new JScrollPane(areaData));
        add(fuelSettings);
        addCargoLoaderButton();
        fuelSettings.setVisible(false);
    }
    private void addCargoLoaderButton() {
        this.exCargoLoader = new JButton("Cargo Load");
        exCargoLoader.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

                SwingUtilities.invokeLater(() ->
                        new CargoSettings(model,(CargoArea) compartmentArea,model.getWorld().getCargoSet()));
            }
        });
        add(exCargoLoader);
        exCargoLoader.setVisible(false);
    }

    @Override
    public void compartmentSelected(Compartment area) {
        BlueprintSelectionListener.super.compartmentSelected(area);
        this.compartmentArea = area;
        this.areaData.setText("Selected compartment:\n");
        displayPrimaryData(area);
        if (compartmentArea instanceof FuelTank) {
            fuelSettings.setArea((FuelTank) area);
            fuelSettings.displaySlider();
            fuelSettings.setVisible(true);
        } else {
            model.getConfigurator().updateDatabaseTables(this);
            fuelSettings.setVisible(false);
            exCargoLoader.setVisible(true);
        }
    }

    @SneakyThrows
    private void displayPrimaryData(Compartment area) {
        for (var field : FileUtils.getAllFields(area.getClass())) {
            field.setAccessible(true);
            this.areaData.append("\n" + field.getName().toUpperCase() + ": " + field.get(area).toString());
        }
    }

}
     /*Hashtable<Integer, JLabel> labels = new Hashtable<>();
        labels.put(0, new JLabel("Empty"));
        labels.put(capacity / 2, new JLabel("Medium"));
        labels.put(capacity, new JLabel("MAX"));
        fuelSlider.setLabelTable(labels);
        */