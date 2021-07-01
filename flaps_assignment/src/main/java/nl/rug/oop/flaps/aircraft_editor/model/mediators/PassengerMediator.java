package nl.rug.oop.flaps.aircraft_editor.model.mediators;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import nl.rug.oop.flaps.aircraft_editor.controller.configcore.ControlSolicitor;
import nl.rug.oop.flaps.aircraft_editor.view.maineditor.main_panels.Logger;
import nl.rug.oop.flaps.aircraft_editor.view.pass_editor.DataBlanksPanel;
import nl.rug.oop.flaps.simulation.model.passengers.Passenger;
import nl.rug.oop.flaps.simulation.model.passengers.PassengerType;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.util.List;
import java.util.*;

@Setter
@Getter
public class PassengerMediator implements ControlSolicitor {

    private Passenger selectedPass;
    private PassengerType selectedType;
    private HashMap<String, Passenger> passHashMap = new HashMap<>();
    private List<JTextField> blankSet = new ArrayList<>();
    private LinkedHashSet<JTextField> invalidFields = new LinkedHashSet<>();
    private Set<Passenger> passengerSet;
    private JFileChooser fileChooser;
    private Image pass_photo;
    public static final double MAX_IMG_SZ = 6e6;

    public PassengerMediator() {
    }

    public void updateHashedPassenger(Passenger p, boolean b) {
        if (b) passHashMap.put(p.getDatabase_id(), p);
        else passHashMap.remove(p.getDatabase_id());
    }

    public boolean checkFields() {
        return blankSet.stream().anyMatch(field -> field.getText().isBlank());
    }

    @Override
    public void performPriorUpdates() {
        //TODO
    }

    public void initPhotoChooser(DataBlanksPanel panel) {
        fileChooser = new JFileChooser();
        fileChooser.setFileFilter(
                new FileNameExtensionFilter("JPG file, PNG file", "jpg", "png"));
        int confirm = fileChooser.showSaveDialog(panel);
        initUpload(confirm, panel);
    }

    @Override
    public Set<?> getDataSet() {
        return passengerSet;
    }

    @SneakyThrows
    public void initUpload(int confirm, DataBlanksPanel panel) {
        if (confirm == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            if (file.length() < MAX_IMG_SZ) {
                panel.updatePhoto(file);
                pass_photo = panel.getAva_pic();
            } else {
                Logger.notifyError(panel,"File size too large!");
            }
        }
        panel.setEnabled(true);
    }
}
