package nl.rug.oop.gui.util;

import lombok.SneakyThrows;
import nl.rug.oop.gui.model.AppCore;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.List;


public class JsonConverter {

    private final StringBuilder dataConverted;
    private StringBuilder objectData;
    private int indent;

    public JsonConverter() {
        this.dataConverted = new StringBuilder();
        this.objectData = new StringBuilder();
        this.indent = 1;
    }

    public String convertSyntax(List<?> data) {
        this.dataConverted
                .append("{\n\t\"")
                .append(data.get(0).getClass().getSimpleName()).append("\": [");
        data.forEach(object -> {
            passObject(object);
            dataConverted.append(objectData);
            objectData = new StringBuilder();
        });
        this.dataConverted.setLength(dataConverted.length() - 1);
        this.dataConverted.append("\n\t]\n}\n");
        return String.valueOf(this.dataConverted);
    }

    public void passObject(Object object) {
        addIndent(objectData);
        objectData.append("{");
        Field[] objData = object.getClass().getDeclaredFields();
        Arrays.stream(objData).sequential().forEach(info -> convertField(object, info));
        this.objectData.setLength(objectData.length() - 1);
        addIndent(objectData);
        objectData.append("},");
    }

    @SneakyThrows
    public void convertField(Object entity, Field field) {
        field.setAccessible(true);
        getFieldValue(field, entity);
    }

    @SneakyThrows
    protected void getFieldValue(Field field, Object entity) {
        String columnName = FetchUtils.toSnakeCase(field.getName());
        Object value;
        addIndent(objectData);
        objectData.append("\"")
                .append(columnName)
                .append("\": ");
        if (FetchUtils.isFieldPrimitiveDeserializable(field)) {
            value = field.get(entity);
            if (value != null) {
                objectData.append(electType(value));
            } else {
                objectData.append("null,");
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

    protected void addIndent(StringBuilder objectData) {
        objectData.append("\n");
        objectData.append("\t".repeat(Math.max(-1, this.indent)));
    }

}
