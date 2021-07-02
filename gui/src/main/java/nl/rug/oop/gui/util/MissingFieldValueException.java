package nl.rug.oop.gui.util;

import lombok.Getter;

import java.lang.reflect.Field;

/**
 * Exception that's thrown when we're unable to deduce the value of an entity's
 * field, during result set deserialization.
 */
public class MissingFieldValueException extends Exception {
	/**
	 * The field whose value was missing.
	 * <p>
	 *     Note that we use the {@link Getter} annotation here from Lombok. This
	 *     will generate a simple getter method in the bytecode when the code
	 *     compiles.
	 * </p>
	 */
	@Getter
	private final Field field;

	/**
	 * Constructs the exception using the given field.
	 * @param field The field whose value is missing.
	 */
	public MissingFieldValueException(Field field) {
		super("Missing value for field " + field.getName());
		this.field = field;
	}
}
