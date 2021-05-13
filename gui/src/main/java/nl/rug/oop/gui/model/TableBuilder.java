package nl.rug.oop.gui.model;

import lombok.SneakyThrows;

import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Vector;

public class TableBuilder extends DefaultTableModel {
    @SneakyThrows
    public DefaultTableModel buildTable(ResultSet rSet, AppCore model) {
        ResultSetMetaData metaData = rSet.getMetaData();
        Vector<String> columnIds = model.getDm().getColumns(metaData);
        Vector<Vector<Object>> data = model.getDm().getData(rSet,metaData);
        return new DefaultTableModel(data, columnIds);
    }
}
