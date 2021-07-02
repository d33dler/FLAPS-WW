package nl.rug.oop.gui.control;

import lombok.SneakyThrows;
import nl.rug.oop.gui.model.AppCore;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

/**
 * SearchListener overrides the methods of the DocumentListener interface;
 */
public class SearchListener implements DocumentListener {

    AppCore model;
    public SearchListener(AppCore model) {
        this.model = model;
    }

    /**
     *
     * @param event any change in the searchBar field text content
     *              execUpdate() : extracts the text,
     *              updates the model by executing the AppCore method executeSearchQuery
     */
    private void execUpdate(DocumentEvent event) throws BadLocationException {
        Document search = event.getDocument();
        String query = search.getText(0, search.getLength());
        model.executeSearchQuery(query);
    }

    @SneakyThrows
    @Override
    public void insertUpdate(DocumentEvent event) {
        execUpdate(event);
    }

    @SneakyThrows
    @Override
    public void removeUpdate(DocumentEvent event) {
        execUpdate(event);
    }

    @SneakyThrows
    @Override
    public void changedUpdate(DocumentEvent event) {
        execUpdate(event);
    }
}
