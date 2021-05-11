package nl.rug.oop.gui.view;

import lombok.SneakyThrows;

import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Vector;

public class TableBuilder {
    @SneakyThrows
    public static DefaultTableModel buildTable(ResultSet rSet) {
        ResultSetMetaData mData = rSet.getMetaData();
        int colNr = mData.getColumnCount();
        Vector<String> columnId = new Vector<>();
        for(int i = 1; i < colNr; i++) {
            columnId.add(mData.getColumnName(i));
        }
        Vector<Vector<Object>> data = new Vector<>();
        while(rSet.next()) {
            Vector<Object> objData = new Vector<>();
            for(int i = 1; i< colNr; i++) {
                objData.add(rSet.getObject(i));
            }
            data.add(objData);
        }
        return new DefaultTableModel(data,columnId);
    }
}
