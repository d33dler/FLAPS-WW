package nl.rug.oop.gui.model;
import lombok.Getter;

import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;

/**
 * Database - holds the local (application) database created by the resultSet yielded
 * by the query command and the table model created based on the supplied resultSet;
 */
@Getter
public class Database extends DefaultTableModel {
    ResultSet resultSet;
    DefaultTableModel table;

    public Database(AppCore model) {
        this.resultSet = model.getDm().getResultSet(model, model.getQueryCommand());
        this.table = new TableBuilder().buildTable(resultSet, model);
    }
}
