package nl.rug.oop.tutorial3.util;

import java.util.ArrayList;
import java.util.List;

/**
 * An object that UpdateListeners can listen to
 * The Updater can notify all listeners
 *
 * You can think of this as a mailing list
 * Here we keep track of all our subscribers
 * Once we have a meaningful update, we send out an update
 */
public class Updater {
    /**
     * The list of our listeners
     */
    private List<UpdateListener> listeners;

    /**
     * Creates a new updater
     */
    public Updater() {
        this.listeners = new ArrayList<>();
    }

    /**
     * Adds a listener to this object
     * I.e. adds a subscriber to our mailing list
     *
     * @param listener The subscriber
     */
    public void addListener(UpdateListener listener) {
        listeners.add(listener);
    }

    /**
     * Let all the listeners know that something changed in the updater class
     */
    public void notifyListeners() {
        /*
         * Loop through every listener and notify them
         * We "notify" them by calling the update() method
         */
        for (UpdateListener listener : listeners) {
            listener.update();
        }
    }
}
