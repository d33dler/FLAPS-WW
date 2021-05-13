package nl.rug.oop.gui.view;

import lombok.SneakyThrows;
import nl.rug.oop.gui.control.SearchListener;
import nl.rug.oop.gui.model.AppCore;
import nl.rug.oop.gui.util.UpdateListeners;

import javax.swing.*;
import java.awt.*;

public class TablePanel extends JPanel implements UpdateListeners {

    private JTable table;
    private SearchBarPanel searchField;

    public TablePanel(AppCore model) {
        init(model);
    }

    @SneakyThrows
    public void init(AppCore model) {
        model.addListener(this);
        setLayout(new BorderLayout());
        addSearchField(model);
        addTable(model);
        addDetails();
        validate();
    }

    public void addTable(AppCore model) {
        table = new JTable(model.getDatabase().getTable()) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table.setFillsViewportHeight(true);
        table.setPreferredScrollableViewportSize(new Dimension(470, 250));
        add(new JScrollPane(table), BorderLayout.CENTER);
        editTableView(table);
    }

    public void addSearchField(AppCore model) {
        searchField = new SearchBarPanel(model);
        searchField.getDocument().addDocumentListener(new SearchListener(model));
        add(searchField, BorderLayout.NORTH);
    }

    public void addDetails() {
        add(new DetailsPanel(), BorderLayout.SOUTH);
    }

    public void editTableView(JTable table) {
        for (int i = 0; i < 5; i++) {
            table.removeColumn(table.getColumnModel().getColumn(3));
        }
    }

    @Override
    public void update(AppCore model) {
        System.out.println("I Update");
        table.setModel(model.getDatabase().getTable());
        editTableView(table);
    }

    public JTable getTable() {
        return table;
    }
}
