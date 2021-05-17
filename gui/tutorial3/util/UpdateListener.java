package nl.rug.oop.tutorial3.util;

/**
 * In the example of mailing lists, this is the subscriber
 * The subscriber can be notified whenever whatever it is subscribed determines to do so
 */
public interface UpdateListener {
    /**
     * Every UpdateListener should implement this method
     * This dictates how the classes react to an update
     */
    void update();
}
