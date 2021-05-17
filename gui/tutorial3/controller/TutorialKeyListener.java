package nl.rug.oop.tutorial3.controller;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

/**
 * A class that listens to changes in a "document"
 * It simply watches for changes in our text field.
 * Whenever there is an update, it updates the text area
 */
public class TutorialKeyListener implements DocumentListener {

    private final JTextArea textArea;

    /**
     * Creates a document listener
     *
     * @param textArea The text area that is updated when the user types something
     */
    public TutorialKeyListener(JTextArea textArea) {
        this.textArea = textArea;
    }

    /**
     * Update the text area
     *
     * @param e Document event
     */
    private void updateText(DocumentEvent e) {
        /*
         * Retrieve whatever this event came from. That is the component that this class listens to
         */
        Document document = e.getDocument();
        try {
            /*
             * Note that we set the text area here immediately. Ideally, we want to do this via our model instead
             */
            textArea.setText(document.getText(0, document.getLength()));
        } catch (BadLocationException badLocationException) {
            /* Keep in mind that you should not really print stacktraces... */
            badLocationException.printStackTrace();
        }
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        updateText(e);
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        updateText(e);
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        updateText(e);
    }
}
