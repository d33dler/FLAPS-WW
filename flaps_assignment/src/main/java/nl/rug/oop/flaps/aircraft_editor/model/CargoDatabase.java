package nl.rug.oop.flaps.aircraft_editor.model;

import lombok.Getter;
import lombok.SneakyThrows;
import nl.rug.oop.flaps.aircraft_editor.view.SettingsPanel;
import nl.rug.oop.flaps.simulation.model.aircraft.CargoArea;
import nl.rug.oop.flaps.simulation.model.cargo.CargoType;
import nl.rug.oop.flaps.simulation.model.cargo.CargoUnit;
import nl.rug.oop.flaps.simulation.model.loaders.FileUtils;

import javax.swing.table.DefaultTableModel;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;
import java.util.Vector;

@Getter
public class CargoDatabase extends DefaultTableModel {
    private DefaultTableModel tableModel;
    private EditorCore editorCore;
    private CargoArea cargoArea;
    
    public CargoDatabase(EditorCore editorCore, SettingsPanel settingsPanel) {
        this.editorCore = editorCore;
        this.cargoArea = (CargoArea) settingsPanel.getCompartmentArea();

    }
    public DefaultTableModel getDatabase(Set<CargoType> set){
        Class<CargoType> clazz = CargoType.class;
        return new DefaultTableModel(getData(set,clazz),getColumns(FileUtils.getAllFields(clazz)));
    }
    public DefaultTableModel getAircraftHold(Set<CargoUnit> set){
        Class<CargoUnit> clazz = CargoUnit.class;
        return new DefaultTableModel(getData(set,clazz),getColumns(FileUtils.getAllFields(clazz)));
    }
    /**
     * @param  - to obtain column count and column names for the DefaultTableModel
     * @param fields
     * @return columnIds Strings vector list
     */
    @SneakyThrows
    public Vector<String> getColumns(List<Field> fields) {
        Vector<String> columnIds = new Vector<>();
        for (Field field : fields) {
            columnIds.add(field.getName());
        }
        return columnIds;
    }

    /**
     * @return all fields data from all entities in the current resultSet.
     * @param objectSet
     */
    @SneakyThrows
    public Vector<Vector<Object>> getData(Set<?> objectSet, Class clazz) {
        Vector<Vector<Object>> data = new Vector<>();
        List<Field> fields = FileUtils.getAllFields(clazz);
        objectSet.forEach(obj -> {
            Vector<Object> entityObj = new Vector<>();
            fields.forEach(field -> {
                field.setAccessible(true);
                try {
                    entityObj.add(field.get(obj));
                } catch (IllegalAccessException e) {
                    System.out.println("Error returning all fields");
                }
            });
            data.add(entityObj);
        });

        return data;
    }
}
