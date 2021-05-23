package nl.rug.oop.gui.model;

import lombok.SneakyThrows;
import nl.rug.oop.gui.util.DataManager;

import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Vector;

/**
 * Creates the table model (DefaultTableModel) :
 *  extracts resultSet metadata,
 *  collects all column id names,
 *  collects all rows data.
 */
public class TableBuilder extends DefaultTableModel {
    @SneakyThrows
    public DefaultTableModel buildTable(ResultSet resultSet, AppCore model) {
        DataManager dManager = model.getDm();
        ResultSetMetaData metaData = resultSet.getMetaData();
        Vector<String> columnIds = dManager.getColumns(metaData);
        Vector<Vector<Object>> data = dManager.getData(resultSet,metaData);
        return new DefaultTableModel(data, columnIds);
    }
}
