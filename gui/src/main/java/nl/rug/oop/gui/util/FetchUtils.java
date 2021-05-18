package nl.rug.oop.gui.util;

import org.apache.commons.lang3.ClassUtils;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This utility class provides methods for fetching entities using Java's
 * Reflection API, in combination with some sensible assumptions about the
 * structure of the SQL result set of queries.
 */
public class FetchUtils {
    /**
     * A mapping containing custom deserialization functions for certain common
     * field types. For example, {@link LocalDateTime} fields will be
     * deserialized by attempting to parse the column value as a string, using
     * the standard ISO8601 formatting rules.
     */
    public static final Map<Class<?>, Function<Object, Object>> customDeserializers = new HashMap<>();

    static {
        customDeserializers.put(LocalDateTime.class, o -> LocalDateTime.parse((String) o, DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        customDeserializers.put(boolean.class, o -> ((int) o) == 1);
        customDeserializers.put(Boolean.class, o -> ((int) o) == 1);
    }

    /**
     * Extracts an entity from the given result set.
     *
     * @param entityClass The class of entity to extract.
     * @param rs          The SQL result set to extract from.
     * @param <T>         The entity's type.
     * @return The entity that was extracted, or <code>null</code> if the entity
     * could not be properly extracted.
     * @throws Exception If any sort of reflection or SQL error occurs.
     */
    public static <T> T extractEntity(Class<T> entityClass, ResultSet rs) throws Exception {
        return extractEntity(entityClass, rs, null);
    }

    /**
     * Recursive implementation of {@link FetchUtils#extractEntity(Class, ResultSet)}.
     * Uses a column prefix to fetch nested entity data.
     *
     * @param entityClass  The class of the entity to extract.
     * @param rs           The SQL result set to extract from.
     * @param columnPrefix The prefix to use for all column names when fetching
     *                     values for all of the entity's fields.
     * @param <T>          The entity's type.
     * @return The entity that was extracted, or <code>null</code> if the entity
     * could not be properly extracted.
     * @throws Exception If any sort of reflection or SQL error occurs.
     */
    private static <T> T extractEntity(Class<T> entityClass, ResultSet rs, String columnPrefix) throws Exception {
        T entity = entityClass.getConstructor().newInstance();
        boolean allFieldsFound = true;
        boolean allFieldsMissing = true;
        for (var field : getAllFields(entityClass)) {
            try {
                field.setAccessible(true);
                String expectedColumnName = toSnakeCase(field.getName());
                if (columnPrefix != null && !columnPrefix.isBlank()) {
                    expectedColumnName = columnPrefix + "_" + expectedColumnName;
                }
                setFieldValue(field, expectedColumnName, rs, entity);
                allFieldsMissing = false;
            } catch (MissingFieldValueException e) {
                allFieldsFound = false;
            }
        }
        if (allFieldsMissing) {
            return null;
        }
        if (!allFieldsFound) {
            System.err.println("Not all fields were found while extracting entity: " + entityClass.getName());
        }
        return entity;
    }

    /**
     * Sets a field's value for a particular entity, taking into consideration
     * whether the field is a primitive, or a nested entity.
     *
     * @param field              The field to set.
     * @param expectedColumnName The name of the column containing the field's
     *                           value.
     * @param rs                 The result set.
     * @param entity             The entity that the field's value should be set on.
     * @throws MissingFieldValueException If value for the field could not be
     *                                    found.
     */
    private static void setFieldValue(Field field, String expectedColumnName, ResultSet rs, Object entity) throws MissingFieldValueException {
        try {
            if (isFieldPrimitiveDeserializable(field)) {
                Object value = getPrimitiveFieldValue(field, expectedColumnName, rs);
                if (field.getType().isPrimitive() && value == null) {
                    value = PrimitiveDefaults.getDefaultValue(field.getType());
                }
                field.set(entity, value);
            } else {
                field.set(entity, extractEntity(field.getType(), rs, expectedColumnName));
            }
        } catch (Exception e) {
            throw new MissingFieldValueException(field);
        }
    }

    /**
     * Gets all fields of a type, including fields inherited from parent classes.
     *
     * @param type The type to get fields of.
     * @return A list of all declared fields.
     */
    protected static List<Field> getAllFields(Class<?> type) {
        List<Field> fields = new ArrayList<>();
        for (Class<?> c = type; c != null; c = c.getSuperclass()) {
            fields.addAll(Arrays.asList(c.getDeclaredFields()));
        }
        return fields;
    }

    /**
     * Converts a string to snake case. So for example, <code>myVar</code> would
     * become <code>my_var</code>.
     *
     * @param camelCase The camel case styled string to convert.
     * @return The snake case form of the given string.
     */
    protected static String toSnakeCase(String camelCase) {
        Matcher m = Pattern.compile("(?<=[a-z])[A-Z]").matcher(camelCase);
        return m.replaceAll(match -> "_" + match.group().toLowerCase());
    }

    /**
     * Determines if a given field is able to be deserialized as a "primitive"
     * value, that is, without attempting to extract a nested entity object from
     * the field's type.
     *
     * @param field The field to check.
     * @return True if we should treat the field as a primitive.
     */
    protected static boolean isFieldPrimitiveDeserializable(Field field) {
        return ClassUtils.isPrimitiveOrWrapper(field.getType()) ||
                field.getType() == String.class ||
                customDeserializers.containsKey(field.getType());
    }

    /**
     * Obtains a value for a primitive field. This will search through the
     * result set for a column whose name matches the expected column name, and
     * then we try to deserialize that value. If a deserialization function is
     * defined in {@link FetchUtils#customDeserializers}, we use that, or fall
     * back to calling {@link ResultSet#getObject(int)}.
     *
     * @param field              The field to get a value for.
     * @param expectedColumnName The column name that we should look for when
     *                           finding a value.
     * @param rs                 The result set.
     * @return An object representing the value the field should have, or
     * <code>null</code> if none could be found.
     */
    protected static Object getPrimitiveFieldValue(Field field, String expectedColumnName, ResultSet rs) {
        try {
            var metaData = rs.getMetaData();
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                if (metaData.getColumnName(i).equalsIgnoreCase(expectedColumnName)) {
                    if (customDeserializers.containsKey(field.getType())) {
                        return customDeserializers.get(field.getType()).apply(rs.getObject(i));
                    }
                    return rs.getObject(i);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
