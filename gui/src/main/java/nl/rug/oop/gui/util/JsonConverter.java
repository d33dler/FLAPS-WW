package nl.rug.oop.gui.util;

import lombok.SneakyThrows;
import nl.rug.oop.gui.model.AppCore;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.List;


public class JsonConverter {
    private AppCore model;
    private ResultSet resultSet;
    private StringBuilder dataConverted;
    private StringBuilder objectData;
    private int indent;

    public JsonConverter(AppCore model) {
        this.model = model;
        this.resultSet = model.getDm().getResultSet(this.model, DataManager.SELECT_DISTINCT_ALL);
        this.dataConverted = new StringBuilder();
        this.objectData = new StringBuilder();
        this.indent = 1;
    }

    public String convertSyntax(List<?> data) {
        this.dataConverted.append("{\n\t\"").append(data.get(0).getClass().getSimpleName()).append("\": [");
        data.forEach(object -> {
            passObject(object);
            dataConverted.append(objectData);
            objectData = new StringBuilder();
        });
        this.dataConverted.setLength(dataConverted.length() - 1);
        this.dataConverted.append("\n\t]\n}\n");
        System.out.println(this.dataConverted);
        return String.valueOf(this.dataConverted);
    }

    public void passObject(Object object) {
        appendIndent(objectData);
        objectData.append("{");
        Field[] objData = object.getClass().getDeclaredFields();
        Arrays.stream(objData).sequential().forEach(info -> convertField(object, info));
        this.objectData.setLength(objectData.length() - 1);
        appendIndent(objectData);
        objectData.append("},");
        // System.out.println(objectData);
    }

    @SneakyThrows
    public void convertField(Object entity, Field field) {
        field.setAccessible(true);
        FetchUtils.extractRefactor(field, resultSet, entity, null, 0);
        // System.out.println("new field:  " + field.getName());
        getFieldValue(field, entity);
    }

    @SneakyThrows
    protected void getFieldValue(Field field, Object entity) {
        String columnName = FetchUtils.toSnakeCase(field.getName());
        Object value;
        appendIndent(objectData);
        objectData.append("\"").append(columnName).append("\": ");
        if (FetchUtils.isFieldPrimitiveDeserializable(field)) {
            // System.out.println("Primitive field: " + field.getName());
            value = field.get(entity);
            if (field.getType().isPrimitive() && value != null) {
                objectData.append(electType(value));
            } else if (value == null) {
                objectData.append("null,");
            } else {
                objectData.append(electType(value));
            }
        } else {
            indent++;
            passObject(field.get(entity));
            indent--;
        }
    }

    protected String electType(Object value) {
        String output = value.toString();
        if (value instanceof Integer
                || value instanceof Boolean
                || value instanceof Long) {
            output = "" + output + ",";
        } else {
            output = "\"" + output + "\",";
        }
        return output;
    }

    protected void appendIndent(StringBuilder objectData) {
        objectData.append("\n");
        objectData.append("\t".repeat(Math.max(-1, this.indent)));
    }
}
