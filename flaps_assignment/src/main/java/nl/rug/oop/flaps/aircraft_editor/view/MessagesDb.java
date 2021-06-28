package nl.rug.oop.flaps.aircraft_editor.view;

import lombok.NoArgsConstructor;

/**
 * Class responsible for storing all string messages employed in program-user interaction;
 */
@NoArgsConstructor
public class MessagesDb {//TODO transfer to yaml
    public static final String AIRPORT_CLOSED = "The selected airport does not accept incoming aircraft!";
    public static final String BP_CURSOR_OUT = "Your cursor is outside of the blueprint's coordinate area range.";
    public static final String AIRPORT_NIL = "Please select a destination airport before initializing the Aircraft Editor© !";
    public static final String LOG_TITLE = "■ COMMAND LOG HISTORY";
    public static final String FUEL_CONFIRM = "Fuel amount loaded successfully! ";
    public static final String FUEL_ERROR = "ERROR: Cannot loading fuel amount. Possible cause: total capacity was reached.";
    public static final String ADD_CARGO_POS = "Successfully added cargo.";
    public static final String ADD_CARGO_NEG = "Error adding new cargo freight.";
    public static final String R_CARGO_POS = "Cargo was removed successfully.";
    public static final String R_ALL_CARGO_POS = "All cargo was removed from the designated cargo area. ";
    public static final String UNDO = "Successful Undo: ";
    public static final String REDO = "Successful Redo: ";
    public static final String ADD_REV = "Cargo addition reverted. ";
    public static final String REM_REV = "Cargo removal reverted. ";
    public static final String ALLR_REV = "Cargo Addition reverted. ";
    public static final String REF_REV = "Refuel reverted";
    public static final String UNDO_ADD_C = UNDO + ADD_REV;
    public static final String UNDO_REM_C = UNDO + REM_REV;
    public static final String REDO_ADD_C = REDO + ADD_REV;
    public static final String REDO_REM_C = REDO + REM_REV;
    public static final String REDO_REMALL_C = REDO + ALLR_REV;
    public static final String UNDO_REMALL_C = UNDO + ALLR_REV;
    public static final String UNDO_FUEL = UNDO + REF_REV;
    public static final String REDO_FUEL = REDO + REF_REV;
    public static final String DEPART_1 = "Your plane is preparing for departure from ";
    public static final String ARRIVE_1 = "Your plane has arrived at ";
    public static final String DEPART_TIP_0 = "Departure is not possible due to unfulfilled requirements.";
    public static final String DEPART_TIP_1 = "Click to depart from the current airport";
    public static final String ARRIVE_CARGO_UN = "All cargo unloaded at: ";
    public static final String ARRIVE_FUEL_UN = "All fuel tanks were emptied.";
    public static final String END_MSG = "The weather is quite nice here!";
    public static final String TY_MSG = "Thank you for choosing F.L.A.P.S.© ";
    public static final String UNLOAD_TITLE = "The following cargo freights were unloaded at the destination airport: ";
    public static final String ADD_ERR = "No warehouse cargo selected";
    public static final String REM_ERR = "No aircraft cargo selected";
    public static final String OTHER_CONFIRM = "others";
}
