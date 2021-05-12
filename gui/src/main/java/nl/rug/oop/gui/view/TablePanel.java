package nl.rug.oop.gui.view;

import lombok.SneakyThrows;
import nl.rug.oop.gui.model.AppCore;
import nl.rug.oop.gui.model.Table;
import nl.rug.oop.gui.util.UpdateListeners;

import javax.swing.*;
import java.awt.*;

public class TablePanel extends JPanel implements UpdateListeners {
    private JTable table;
    AppCore model;

    public TablePanel(AppCore model) {
        this.model = model;
        init(model);
    }

    @SneakyThrows
    public void init(AppCore model) {
        setLayout(new BorderLayout());
        this.table = new JTable(model.getDatabase().getTable()) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table.setFillsViewportHeight(true);
        table.setPreferredScrollableViewportSize(new Dimension(470, 250));
        add(new JScrollPane(table), BorderLayout.NORTH);
        editTableView(table);
        add(new DetailsPanel(), BorderLayout.WEST);
        validate();
    }

    public void editTableView(JTable table) {
        for (int i = 0; i < 5; i++) {
            table.removeColumn(table.getColumnModel().getColumn(3));
        }
    }

    @Override
    public void update() {
        this.table = new JTable( model.getDatabase().getTable());
    }

}
