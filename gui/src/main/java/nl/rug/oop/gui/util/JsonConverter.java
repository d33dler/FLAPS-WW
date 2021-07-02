package nl.rug.oop.gui.util;

import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

/**
 * JsonConverter - reads the list of entities and converts all the data
 * according to the JSON syntax to a string.
 */
public class JsonConverter {

    private final StringBuilder dataConverted;
    private StringBuilder objectData;
    private int indent;

    /**
     * Constructor initiates string builders and indentation to 1;
     */
    public JsonConverter() {
        this.dataConverted = new StringBuilder();
        this.objectData = new StringBuilder();
        this.indent = 1;
    }

    /**
     * Loops through the list of objects -> converts each object's values and appends
     * it's formatted structure to the main output ('dataConverted' StringBuilder)
     *
     * @param data - to be converted and reorganized to JSON representation syntax
     * @return processed data list written in JSON syntax.
     */
    public String convertSyntax(List<?> data) {
        addHeader(data);
        data.forEach(object -> {
            convertObject(object);
            dataConverted.append(objectData);
            objectData = new StringBuilder();
        });
        addTermination();
        return String.valueOf(this.dataConverted);
    }

    /**
     * Create output header;
     */
    public void addHeader(List<?> data) {
        this.dataConverted
                .append("{\n\t\"")
                .append(data.get(0).getClass().getSimpleName()).append("\": [");
    }

    /**
     * Create output termination;
     */
    public void addTermination() {
        this.dataConverted.setLength(dataConverted.length() - 1);
        this.dataConverted.append("\n\t]\n}\n");
    }

    /**
     * @param object - to be read and its values copied and formatted;
     *               convertObject() : extract object's array of declared fields;
     *               forEach field -  convert field value and format & indent;
     */
    public void convertObject(Object object) {
        addIndent(objectData);
        objectData.append("{");
        Field[] objData = object.getClass().getDeclaredFields();
        Arrays.stream(objData).sequential().forEach(info -> convertField(object, info));
        this.objectData.setLength(objectData.length() - 1);
        addIndent(objectData);
        objectData.append("},");
    }

    /**
     * @param entity holding the field to be converted;
     * @param field  requiring reading and conversion to JSON;
     */
    @SneakyThrows
    public void convertField(Object entity, Field field) {
        field.setAccessible(true);
        getFieldValue(field, entity);
    }

    /**
     * @param field  - holding the value to be extracted;
     * @param entity - object with the proprietary field value;
     *               getFieldValue() : formats the column name,
     *               extracts value and verifies integrity & primitivity;
     *               if value isn't primitive -> convertObject(value)
     */
    @SneakyThrows
    protected void getFieldValue(Field field, Object entity) {
        getColumnName(field);
        Object value;
        if (FetchUtils.isFieldPrimitiveDeserializable(field)) {
            value = field.get(entity);
            if (value != null) {
                objectData.append(electType(value));
            } else {
                objectData.append("null,");
            }
        } else {
            indent++;
            convertObject(field.get(entity));
            indent--;
        }
    }

    /**
     * @param field to extract the name of the field and format it;
     */
    protected void getColumnName(Field field) {
        String columnName = FetchUtils.toSnakeCase(field.getName());
        addIndent(objectData);
        objectData.append("\"")
                .append(columnName)
                .append("\": ");
    }

    /**
     * @param value - value to be formatted;
     * @return value as String, formatted in dependence to value type;
     */
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

    /**
     * @param objectData - to be formatted by appending newlines and indentation;
     */
    protected void addIndent(StringBuilder objectData) {
        objectData.append("\n");
        objectData.append("\t".repeat(Math.max(-1, this.indent)));
    }
}
