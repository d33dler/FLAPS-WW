package nl.rug.oop.gui.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Represents an entity that stores information about a particular type of NPC.
 * <p>
 *     Note the {@link Getter} and {@link NoArgsConstructor} annotations. These
 *     are from the Lombok library, and signal that during compilation, bytecode
 *     should be generated to create getter methods for all fields in this class
 *     and a no-argument constructor should be generated. We use this to reduce
 *     boilerplate code for plain objects that are just glorified data containers.
 * </p>
 */
@Getter
@NoArgsConstructor
public class NpcTypeEntity {
	private String name;
	private boolean hostile;
	private String race;
}
