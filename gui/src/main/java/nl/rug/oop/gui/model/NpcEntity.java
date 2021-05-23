package nl.rug.oop.gui.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * The entity that represents an NPC as it's stored in the database.
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
public class NpcEntity {
	private long id;
	private LocalDateTime createdAt;
	private String name;
	private String profileImagePath;
	private NpcTypeEntity type;
	private String description;
	private double health;
	private double attackStrength;
	private double defenseStrength;
}
