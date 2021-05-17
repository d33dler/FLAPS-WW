package nl.rug.oop.tutorial3.model;

import nl.rug.oop.tutorial3.util.Updater;

/**
 * Our extremely simplistic model
 */
public class TutorialModel extends Updater {
    /**
     * The text that our model displays
     */
    private String text;

    /**
     * Getter
     *
     * @return The text of our model
     */
    public String getText() {
        return text;
    }

    /**
     * Updates the text in our model
     *
     * @param text The new text
     */
    public void setText(String text) {
        this.text = text;
        /* Our model changes, so we fire an update to make sure everyone that is listening to this class knows it */
        notifyListeners();
    }
}
