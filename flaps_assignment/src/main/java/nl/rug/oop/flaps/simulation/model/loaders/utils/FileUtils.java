package nl.rug.oop.flaps.simulation.model.loaders.utils;

import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.apache.commons.lang3.ClassUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author T.O.W.E.R.
 */
@Log
public class FileUtils {

    public static final Map<Class<?>, Function<Object, Object>> customDeserializers = new HashMap<>();

    static {
        customDeserializers.put(LocalDateTime.class,
                o -> LocalDateTime.parse((String) o, DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        customDeserializers.put(boolean.class, o -> ((int) o) == 1);
        customDeserializers.put(Boolean.class, o -> ((int) o) == 1);
    }

    public static List<Path> findMatches(Path directory, String pattern) throws IOException {
        var matcher = FileSystems.getDefault().getPathMatcher(pattern);
        try (var paths = Files.walk(directory)) {
            return paths.filter(matcher::matches).collect(Collectors.toList());
        }
    }

    public static Optional<Path> findMatch(Path directory, String pattern) throws IOException {
        var matcher = FileSystems.getDefault().getPathMatcher(pattern);
        try (var paths = Files.walk(directory)) {
            return paths.filter(matcher::matches).findAny();
        } catch (NullPointerException e) {
            return Optional.empty();
        }
    }

    public static Path findMatchOrThrow(Path directory, String pattern) throws IOException {
        return findMatch(directory, pattern)
                .orElseThrow(() -> new IOException("No match found for pattern " + pattern + " in directory " + directory + "."));
    }

    public static boolean isFieldPrimitiveDeserializable(Field field) {
        return ClassUtils.isPrimitiveOrWrapper(field.getType()) ||
                field.getType() == String.class ||
                customDeserializers.containsKey(field.getType());
    }

    public static List<Field> getAllFields(Class<?> type) {
        List<Field> fields = new ArrayList<>();
        for (Class<?> c = type; c != null; c = c.getSuperclass()) {
            fields.addAll(Arrays.asList(c.getDeclaredFields()));
        }
        return fields;
    }

    public static List<Field> getAllFieldsFiltered(Class<?> type) {
        List<Field> list = new ArrayList<>();
        List<Field> fieldsUnfiltered = getAllFields(type);
        fieldsUnfiltered.forEach(field -> {
            if (FileUtils.isFieldPrimitiveDeserializable(field)
                    && !Modifier.isStatic(field.getModifiers())) {
                list.add(field);
            }
        });
        return list;
    }

    /**
     * @param clazz some class
     * @return all declared fields (except static and non-primitive fields);
     */
    public static List<Field> getLocalFields(Class<?> clazz) {
        List<Field> newFieldList = new ArrayList<>();
        List<Field> list = Arrays.asList(clazz.getDeclaredFields());
        list.forEach(field -> {
            if (FileUtils.isFieldPrimitiveDeserializable(field)
                    && !Modifier.isStatic(field.getModifiers())) {
                newFieldList.add(field);
            }
        });
        return newFieldList;
    }

    public static List<Field> getFields(Class<?> clazz) {
        return Arrays.asList(clazz.getDeclaredFields());
    }

    public static <K, V> Set<K> addUnitSet(V area, Map<V, Set<K>> setMap) {
        return setMap.computeIfAbsent(area, k -> new HashSet<>());
    }

    @SneakyThrows
    public static void cloneFields(Object obj_1, Object obj_2, List<Field> obj_1_fields) {
        obj_1_fields.forEach(field -> {
            field.setAccessible(true);
            try {
                field.set(obj_1, field.get(obj_2));
            } catch (IllegalAccessException ignored) {
            }
        });
    }

}
