package nl.rug.oop.gui.model;

import lombok.SneakyThrows;

import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Vector;

public class TableBuilder extends DefaultTableModel {
    @SneakyThrows
    public static DefaultTableModel buildTable(ResultSet rSet) {
        ResultSetMetaData mData = rSet.getMetaData();
        Vector<String> columnId = new Vector<>();
        for (int i = 1; i < mData.getColumnCount(); i++) {
            columnId.add(mData.getColumnName(i));
        }
        Vector<Vector<Object>> data = new Vector<>();
        while (rSet.next()) {
            Vector<Object> objData = new Vector<>();
            for (int i = 1; i < mData.getColumnCount(); i++) {
                objData.add(rSet.getObject(i));
            }
            data.add(objData);
        }
        return new DefaultTableModel(data, columnId);
    }

}
