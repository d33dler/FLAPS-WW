package nl.rug.oop.gui.view;

import lombok.Getter;
import lombok.SneakyThrows;
import nl.rug.oop.gui.control.SearchListener;
import nl.rug.oop.gui.model.AppCore;
import nl.rug.oop.gui.util.UpdateInterface;

import javax.swing.*;
import java.awt.*;

@Getter
public class TablePanel extends JPanel implements UpdateInterface {
    private AppCore model;
    private JTable table;
    private SearchBarPanel searchField;


    public TablePanel(AppCore model) {
        this.model = model;
        init(model);
    }

    @SneakyThrows
    public void init(AppCore model) {
        model.getTableUpdater().addListener(this);
        setLayout(new BorderLayout());
        addSearchField(model);
        addTable(model);
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
        table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setPreferredScrollableViewportSize(new Dimension(470, 240));
        addTableSelectionListener();
        editTableView(table);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    public void addTableSelectionListener() {
        table.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && !table.getSelectionModel().isSelectionEmpty()) {
                model.fetchEntityDetails();
            }
        });
    }

    public void addSearchField(AppCore model) {
        searchField = new SearchBarPanel(model);
        searchField.getDocument().addDocumentListener(new SearchListener(model));
        add(searchField, BorderLayout.NORTH);
    }

    public void editTableView(JTable table) {
        for (int i = 0; i < 6; i++) {
            table.removeColumn(table.getColumnModel().getColumn(3));
        }
    }

    @Override
    public void update(AppCore model) {
        table.clearSelection();
        table.setModel(model.getDatabase().getTable());
        editTableView(table);
        revalidate();
    }
}
