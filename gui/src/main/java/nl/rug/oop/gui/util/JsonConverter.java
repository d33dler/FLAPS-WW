package nl.rug.oop.gui.util;

import lombok.SneakyThrows;
import nl.rug.oop.gui.model.AppCore;
import nl.rug.oop.gui.model.NpcTypeEntity;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.List;


public class JsonConverter {
    AppCore model;
    ResultSet resultSet;
    StringBuilder dataConverted;
    StringBuilder objectData;

    public JsonConverter(AppCore model) {
        this.model = model;
        this.resultSet = model.getDm().getResultSet(this.model, DataManager.SELECT_DISTINCT_ALL);
        this.dataConverted = new StringBuilder();
        this.objectData = new StringBuilder();
    }

    public String convertSyntax(List<?> data) {
        this.dataConverted.append("{\n\"").append(data.getClass().getName()).append("\": [\n");
        data.forEach(object -> {
            passObject(object);
            dataConverted.append(objectData);
            objectData = new StringBuilder();
        });
        this.dataConverted.append("\n]\n}");
        System.out.println(this.dataConverted);
        return String.valueOf(this.dataConverted);
    }

    public void passObject(Object object) {
        objectData.append("{\n");
        Field[] objData = object.getClass().getDeclaredFields();
        Arrays.stream(objData).sequential().forEach(info -> convertField(object, info));
        objectData.append("\n}");
        System.out.println(objectData);
    }

    @SneakyThrows
    public void convertField(Object entity, Field field) {
        field.setAccessible(true);
        FetchUtils.extractRefactor(field, resultSet, entity, null, 0);
        System.out.println("new field");
        getFieldValue(field, entity);
    }

    @SneakyThrows
    protected void getFieldValue(Field field, Object entity) {
        String columnName = FetchUtils.toSnakeCase(field.getName());
        Object value = null;
        objectData.append("\"").append(columnName).append("\" : ");
        if (FetchUtils.isFieldPrimitiveDeserializable(field)) {
             value = FetchUtils.getPrimitiveFieldValue(field, columnName, resultSet);
            if (field.getType().isPrimitive() && value == null) {
                value = PrimitiveDefaults.getDefaultValue(field.getType());
                objectData.append(electType(value));
            } else if (value == null) {
                objectData.append("null,\n");
            }
        } else {
            //System.out.println("Fielddd : " + field.getType().getSimpleName());
            field.set(entity, FetchUtils.extractEntity(NpcTypeEntity.class, resultSet, columnName));
            passObject(field);
        }
    }

    protected String electType(Object value) {
        String output = value.toString();
        if (value instanceof String) {
            output = "\"" + output + "\",\n";
        }
        return output;
    }
}
