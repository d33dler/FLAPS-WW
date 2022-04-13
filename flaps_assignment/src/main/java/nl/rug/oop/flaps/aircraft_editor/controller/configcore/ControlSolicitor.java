package nl.rug.oop.flaps.aircraft_editor.controller.configcore;


import java.util.Set;

public interface ControlSolicitor {
    void performPriorUpdates();
    Set<?> getDataSet();
}
