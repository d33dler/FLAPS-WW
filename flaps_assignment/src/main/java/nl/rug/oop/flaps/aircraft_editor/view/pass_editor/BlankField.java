package nl.rug.oop.flaps.aircraft_editor.view.pass_editor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used to define TravelerMember extending class objects' determining
 * attribute fields id and their constraints.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface BlankField {
    String  id() default "0";
    String pattern() default "[a-zA-Z0-9]{1,50}";
    int max() default -1;
    int min() default -1;
}
