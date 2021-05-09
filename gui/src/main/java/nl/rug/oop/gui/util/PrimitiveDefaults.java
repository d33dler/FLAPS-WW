package nl.rug.oop.gui.util;

/**
 * Helper class that provides a lookup of default values for primitive types, as
 * a fallback for when fetching the value of an entity's field fails.
 *
 * Taken from here: https://stackoverflow.com/a/2892067/4288259
 */
public class PrimitiveDefaults {
	/**
	 * Gets the default value for a primitive class type.
	 * @param clazz The class to get a default value for.
	 * @return The default value that an instance of that class should have.
	 * @throws IllegalArgumentException If the class is not a primitive type.
	 */
	public static Object getDefaultValue(Class<?> clazz) {
		if (clazz.equals(boolean.class)) {
			return false;
		} else if (clazz.equals(byte.class)) {
			return 0;
		} else if (clazz.equals(short.class)) {
			return 0;
		} else if (clazz.equals(int.class)) {
			return 0;
		} else if (clazz.equals(long.class)) {
			return 0;
		} else if (clazz.equals(float.class)) {
			return 0;
		} else if (clazz.equals(double.class)) {
			return 0;
		} else {
			throw new IllegalArgumentException("Class type " + clazz + " not supported");
		}
	}
}
