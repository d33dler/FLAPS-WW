package nl.rug.oop.gui.model;
import nl.rug.oop.gui.util.DataManager;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Table extends DefaultTableModel {
    ResultSet rset;
    DefaultTableModel table;

    public Table(AppCore model) throws SQLException {
        this.rset = model.getDm().getResultSet(model.getSearchQuery());
        this.table = TableBuilder.buildTable(rset);
    }
    public DefaultTableModel getTable() {
        return table;
    }
}
