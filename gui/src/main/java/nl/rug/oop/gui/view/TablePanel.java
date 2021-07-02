package nl.rug.oop.gui.view;

import lombok.Getter;
import lombok.SneakyThrows;
import nl.rug.oop.gui.control.SearchListener;
import nl.rug.oop.gui.model.AppCore;
import nl.rug.oop.gui.util.UpdateInterface;

import javax.swing.*;
import java.awt.*;

/**
 * TablePanel class panel includes the following components: Table, Search bar.
 * TablePanel uses BorderLayout.
 */

@Getter
public class TablePanel extends JPanel implements UpdateInterface {
    private AppCore model;
    private JTable table;
    private SearchBarPanel searchField;

    /**
     * @param model to establish link between the panel and the model.
     */
    public TablePanel(AppCore model) {
        this.model = model;
        init(model);
    }

    /**
     * Initialize tertiary components and adding the class to TableUpdater listener list.
     */
    @SneakyThrows
    public void init(AppCore model) {
        model.getTableUpdater().addListener(this);
        setLayout(new BorderLayout());
        addSearchField(model);
        addTable(model);
        validate();
    }

    /**
     * Initialize JTable by collecting from Database.class the DefaultTableModel containing
     * the quarried database data.
     * Set the the cells unalterable and the reordering of columns to false;
     * setting the size, setting selection mode(to single), eliminating excess columns;
     */

    public void addTable(AppCore model) {
        table = new JTable(model.getDatabase().getTable()) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

        };
        table.getTableHeader().setReorderingAllowed(false);
        table.setFillsViewportHeight(true);
        table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setPreferredScrollableViewportSize(new Dimension(470, 240));
        addTableSelectionListener();
        editTableView(table);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    /**
     * Initialize and add the Searchbar component to the panel; Add Document listener
     * fixed on the class SearchListener which implements the overridden methods.
     */

    public void addSearchField(AppCore model) {
        searchField = new SearchBarPanel(model);
        searchField.getDocument().addDocumentListener(new SearchListener(model));
        add(searchField, BorderLayout.NORTH);
    }

    /**
     * Adding a listener to the selection of JTable rows.
     * The conditional statement explained:
     * event.getValueIsAdjusting() -> optimize double quarrying
     * table.getSelectionModel().isSelectionEmpty() -> avoid listener conflicts for case:
     * a row is selected (listener reacts), next, the search bar is accessed, altering the selection
     * and leading to flooding calls to the ListSelectionListener which glitches the searchbar input as well.
     */
    public void addTableSelectionListener() {
        table.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && !table.getSelectionModel().isSelectionEmpty()) {
                model.fetchEntityDetails();
            }
        });
    }

    /**
     * Remove excess columns from JTable component (as per requirements)
     */
    public void editTableView(JTable table) {
        for (int i = 0; i < 6; i++) {
            table.removeColumn(table.getColumnModel().getColumn(3));
        }
    }

    /**
     * update() -> updates the JTable view :
     * clear selection to avoid listener conflicts;
     * reinstall table model with the new database;
     * remove excess columns and revalidate.
     */
    @Override
    public void update(AppCore model) {
        table.clearSelection();
        table.setModel(model.getDatabase().getTable());
        editTableView(table);
        revalidate();
    }
}
