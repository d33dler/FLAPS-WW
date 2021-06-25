package nl.rug.oop.flaps.aircraft_editor.model;

import lombok.Getter;
import nl.rug.oop.flaps.aircraft_editor.model.listeners.interfaces.ChangeListener;

import java.util.HashMap;
import java.util.List;

@Getter
public abstract class ChangeListenerModel {
    protected HashMap<String, List<ChangeListener>> listenerMap;
}
