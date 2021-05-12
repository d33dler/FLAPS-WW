package nl.rug.oop.gui.control;

import lombok.SneakyThrows;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

public class SearchListener implements DocumentListener {
    private JTable table;

    public SearchListener(JTable table){
        this.table = table;
    }
    @SneakyThrows
    @Override
    public void insertUpdate(DocumentEvent event) {
        Document search = event.getDocument();
        String query = search.getText(0, search.getLength());
        //this.table =
    }

    @Override
    public void removeUpdate(DocumentEvent event) {

    }

    @Override
    public void changedUpdate(DocumentEvent event) {

    }
}
