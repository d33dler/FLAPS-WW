package nl.rug.oop.flaps.aircraft_editor.model;

import lombok.Getter;
import lombok.SneakyThrows;
import nl.rug.oop.flaps.aircraft_editor.view.SettingsPanel;
import nl.rug.oop.flaps.simulation.model.aircraft.CargoArea;
import nl.rug.oop.flaps.simulation.model.loaders.FileUtils;

import javax.swing.table.DefaultTableModel;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

@Getter
public class CargoDatabase extends DefaultTableModel {
    private DefaultTableModel tableModel;
    private EditorCore editorCore;
    private CargoArea cargoArea;
    private static final String DATA_OBJ_Pkg = "nl.rug.oop.flaps.simulation.model.cargo";
    public CargoDatabase(EditorCore editorCore, SettingsPanel settingsPanel) {
        this.editorCore = editorCore;
        this.cargoArea = (CargoArea) settingsPanel.getCompartmentArea();
    }

    public DefaultTableModel getDatabase(Set<?> set, Class<?> clazz) {
        return new DefaultTableModel(getDatabaseData(set, clazz), getColumns(FileUtils.getAllFields(clazz)));
    }

    /**
     * @param -      to obtain column count and column names for the DefaultTableModel
     * @param fields
     * @return columnIds Strings vector list
     */
    @SneakyThrows
    public Vector<String> getColumns(List<Field> fields) {
        Vector<String> columnIds = new Vector<>();
        for (Field field : fields) {
            field.setAccessible(true);
            if (FileUtils.isFieldPrimitiveDeserializable(field)) {
                columnIds.add(FileUtils.toSnakeCase(field.getName()));
            } else {
                if (!field.getType().getPackage().getName().equals(DATA_OBJ_Pkg)) {
                    continue;
                }
                Vector<String> nestedFields = getColumns(FileUtils.getAllFields(field.getType()));
                columnIds.addAll(nestedFields);
            }
        }
        return columnIds;
    }

    /**
     * @param objectSet
     * @return all fields data from all entities in the current resultSet.
     */
    @SneakyThrows
    public Vector<Vector<Object>> getDatabaseData(Set<?> objectSet, Class<?> clazz) {
        Vector<Vector<Object>> data = new Vector<>();
        objectSet.forEach(obj -> {
            data.add(extractObjData(obj, clazz));
        });
        return data;
    }

    public Vector<Object> extractObjData(Object obj, Class<?> clazz) {
        List<Field> fields = FileUtils.getAllFields(clazz);
        Vector<Object> entityObj = new Vector<>();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                if (FileUtils.isFieldPrimitiveDeserializable(field)) {
                    entityObj.add(field.get(obj));
                } else {
                    if (!field.getType().getPackage().getName().equals(DATA_OBJ_Pkg)) {
                        continue;
                    }
                    Class<?> fieldClass = field.getType();
                    Vector<Object> nestedData = extractObjData(field.get(obj), fieldClass);
                    entityObj.addAll(nestedData);
                }
            } catch (IllegalAccessException e) {
                System.out.println("Error returning all fields");
            }
        }
        return entityObj;
    }
}
