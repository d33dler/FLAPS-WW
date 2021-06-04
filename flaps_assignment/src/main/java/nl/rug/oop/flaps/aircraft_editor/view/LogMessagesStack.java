package nl.rug.oop.flaps.aircraft_editor.view;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class LogMessagesStack {
    public static final String FUEL_CONFIRM = "Fuel amount loaded successfully! ";
    public static final String FUEL_ERROR = "ERROR: Cannot loading fuel amount. Possible cause: total capacity was reached.";
    public static final String OTHER_CONFIRM = "others";
}
