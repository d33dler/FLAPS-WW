package nl.rug.oop.gui.model;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;

public class Table extends DefaultTableModel {
    ResultSet rset;
    DefaultTableModel table;

    public Table(AppCore model) {
        this.rset = model.getDm().getResultSet(model, model.getQueryCommand());
        this.table = new TableBuilder().buildTable(rset, model);
    }

    public ResultSet getRset() {
        return rset;
    }

    public void setRset(ResultSet rset) {
        this.rset = rset;
    }

    public DefaultTableModel getTable() {
        return table;
    }

    public void setTable(DefaultTableModel table) {
        this.table = table;
    }
}
