package nl.rug.oop.gui.view;
import nl.rug.oop.gui.util.DataManager;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Table extends AbstractTableModel {
    DataManager dm = new DataManager();
    ResultSet rset = dm.populateTable();
    DefaultTableModel table = TableBuilder.buildTable(rset);


    public Table() throws SQLException {
    }

    @Override
    public int getRowCount() {
        return this.table.getRowCount();
    }

    @Override
    public int getColumnCount() {
       return this.table.getColumnCount();
    }

    @Override
    public Object getValueAt(int i, int i1) {
        return this.table.getValueAt(i,i1);
    }

    public DefaultTableModel getTable() {
        return table;
    }

}
