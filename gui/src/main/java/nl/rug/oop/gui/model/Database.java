package nl.rug.oop.gui.model;
import lombok.Getter;

import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;

@Getter
public class Database extends DefaultTableModel {
    ResultSet rset;
    DefaultTableModel table;

    public Database(AppCore model) {
        this.rset = model.getDm().getResultSet(model, model.getQueryCommand());
        this.table = new TableBuilder().buildTable(rset, model);
    }

    public void setRset(ResultSet rset) {
        this.rset = rset;
    }

}
