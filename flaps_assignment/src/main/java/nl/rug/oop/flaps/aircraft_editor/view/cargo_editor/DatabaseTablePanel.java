package nl.rug.oop.flaps.aircraft_editor.view.cargo_editor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.rug.oop.flaps.aircraft_editor.controller.configcore.ControlSolicitor;
import nl.rug.oop.flaps.aircraft_editor.model.EditorCore;
import nl.rug.oop.flaps.simulation.model.loaders.utils.FileUtils;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Set;

/**
 * CargoTablesPanel class - contains the JTable itself to separate additional functions implementations;
 */
@Getter
@Setter
@NoArgsConstructor
public class DatabaseTablePanel<T> extends JPanel {
    protected EditorCore editorCore;
    protected JTable databaseTable;
    protected JFrame holderFrame;
    protected Set<?> objectSet;
    protected String pos, tableName;
    protected DefaultTableModel model;
    protected ControlSolicitor solicitor;
    protected int WIDTH = 500 , HEIGHT = 300;
    /**
     *
     * Set common settings and construct
     * the appropriate JTable and its Table model according to the command;
     */
    protected void init(DatabaseTablePanel<?> tPanel, Object in) {
        FileUtils.cloneFields(tPanel, in, FileUtils.getFields(DatabaseTablePanel.class));
        tPanel.setBorder(BorderFactory.createTitledBorder
                (BorderFactory.createEtchedBorder(1),
                        tableName, TitledBorder.ABOVE_TOP, TitledBorder.TOP));
        tPanel.add(new JScrollPane(databaseTable), pos);
    }

    /**
     *
     * @return configured JTable with the required JTable model;
     */
    JTable addTable() {
        JTable table = new JTable() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table.setModel(model);
        table.getTableHeader().setReorderingAllowed(false);
        table.setFillsViewportHeight(true);
        table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setPreferredScrollableViewportSize(new Dimension(WIDTH, HEIGHT));
        return table;
    }

    /**
     *
     * @param table table to edit
     * @param start beginning column to eliminate
     * @param interval nr of eliminations
                Trims the number of columns for the specific table;
     */
    protected void editTableView(JTable table, int start, int interval) {
        for (int i = 0; i < interval; i++) {
            table.removeColumn(table.getColumnModel().getColumn(start));
        }
    }

    /**
     * repaints the view of the JTable
     */
    public void update() {
        databaseTable.repaint();
    }
}
