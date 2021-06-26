package nl.rug.oop.flaps.aircraft_editor.model.listeners.interfaces;

import nl.rug.oop.flaps.aircraft_editor.model.listener_models.ChangeListenerModel;

import java.util.ArrayList;
import java.util.List;

public interface ChangeListenerInterface {
    void addListener(String identity, ChangeListener changeListener);
    default void addNewListener(ChangeListenerModel model,
                                String identity,
                                ChangeListener changeListener) {
        if (!model.getListenerMap().containsKey(identity)) {
            List<ChangeListener> list = new ArrayList<>();
            list.add(changeListener);
            model.getListenerMap().put(identity, list);
        } else {
            model.getListenerMap().get(identity).add(changeListener);
        }
    }
}
