package nl.rug.oop.gui.control;

import lombok.SneakyThrows;
import nl.rug.oop.gui.model.AppCore;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public class SearchListener implements DocumentListener {
    AppCore model;

    public SearchListener(AppCore model) {
    this.model = model;
    }

    private void execUpdate(DocumentEvent event) throws BadLocationException {
        Document search = event.getDocument();
        String query = search.getText(0, search.getLength());
        model.setSearchField(query);
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
