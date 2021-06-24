package nl.rug.oop.flaps.aircraft_editor.view.maineditor.main_panels;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import nl.rug.oop.flaps.aircraft_editor.controller.AircraftDataTracker;
import nl.rug.oop.flaps.aircraft_editor.controller.CommercialDataTracker;
import nl.rug.oop.flaps.aircraft_editor.model.listeners.interfaces.BlueprintSelectionListener;
import nl.rug.oop.flaps.aircraft_editor.model.listeners.interfaces.CargoUnitsListener;
import nl.rug.oop.flaps.aircraft_editor.model.EditorCore;
import nl.rug.oop.flaps.aircraft_editor.model.listeners.interfaces.FuelSupplyListener;
import nl.rug.oop.flaps.simulation.model.aircraft.Aircraft;
import nl.rug.oop.flaps.simulation.model.aircraft.areas.Compartment;
import nl.rug.oop.flaps.simulation.model.loaders.FileUtils;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static nl.rug.oop.flaps.simulation.model.loaders.FileUtils.toNiceCase;

/**
 * InfoPanel class - panel on the western part of the Configurer frame. It displays all data concerning the aircrafts
 * real-time state , travel details, commercial data;
 * All the data is obtained from the AircraftDataTracker and CommercialDataTracker;
 */

@Getter
@Setter
public class InfoPanel extends JPanel implements CargoUnitsListener, BlueprintSelectionListener, FuelSupplyListener {
    private EditorCore editorCore;
    private Aircraft aircraft;
    private AircraftDataTracker aircraftDataTracker;
    private CommercialDataTracker commercialDataTracker;
    private List<JLabel> dataSetADT, dataSetCDT;
    private List<Field> fieldListADT, fieldListCDT;
    private JPanel aircraftDataReg, commercialDataReg;
    private static final String
            PANEL1_TITLE = "AIRCRAFT DATA",
            PANEL2_TITLE = "Commercial DATA",
            PAD = ":";
    private final static int MAX_SPACE = 50;
    private final static int F_WIDTH = 80, F_HEIGHT = 20, WIDTH = 300, HEIGHT = 550;
    private static final Color PANEL_BG = new Color(78, 86, 108, 67);


    public InfoPanel(EditorCore editorCore) {
        this.editorCore = editorCore;
        this.aircraft = editorCore.getAircraft();
        this.aircraftDataTracker = editorCore.getDataTracker();
        this.commercialDataTracker = aircraftDataTracker.getCommercialData();
        init();
    }

    @SneakyThrows
    private void init() {
        editorCore.getAircraftLoadingModel().addListener((CargoUnitsListener) this);
        editorCore.getAircraftLoadingModel().addListener((FuelSupplyListener) this);
        editorCore.getBpSelectionModel().addListener(EditorCore.generalListenerID, this);
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setSize(new Dimension(WIDTH, HEIGHT));
        this.fieldListADT = extractFields(aircraftDataTracker.getClass());
        this.fieldListCDT = extractFields(commercialDataTracker.getClass());
        this.dataSetADT = new ArrayList<>();
        this.dataSetCDT = new ArrayList<>();
        addAircraftDataPanel();
        addCommercialDataPanel();
    }

    /**
     * Init and configure the aircraft data panel;
     */
    private void addAircraftDataPanel() {
        this.aircraftDataReg = new JPanel();
        configureRegisterPanel(aircraftDataReg, PANEL1_TITLE, fieldListADT,
                dataSetADT, aircraftDataTracker);
        add(new JScrollPane(aircraftDataReg));
    }

    /**
     * Init and configure the commercial data panel;
     */
    private void addCommercialDataPanel() {
        this.commercialDataReg = new JPanel();
        configureRegisterPanel(commercialDataReg, PANEL2_TITLE, fieldListCDT,
                dataSetCDT, commercialDataTracker);
        add(new JScrollPane(commercialDataReg));
    }

    /**
     * @param panel   panel to be configured;
     * @param title   title of the panel that is configured;
     * @param list    list of fields the values of which is displayed in the panel (if not null);
     * @param dataSet set of JLabels which contain the changing field variables;
     * @param object  object supplying the data variables according to the list (of fields)
     *                Extracts all data from the object and creates a panel;
     */

    private void configureRegisterPanel(JPanel panel, String title, List<Field> list,
                                        List<JLabel> dataSet, Object object) {
        panel.setBorder
                (BorderFactory.createTitledBorder
                        (BorderFactory.createRaisedSoftBevelBorder(),
                                title, TitledBorder.LEFT, TitledBorder.ABOVE_TOP));
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        for (Field field : list) {
            field.setAccessible(true);
            if (FileUtils.isFieldPrimitiveDeserializable(field)
                    && !Modifier.isStatic(field.getModifiers())) {
                JPanel fieldPanel = setupFieldPanel(field, dataSet, object);
                panel.add(new SpecialComponent(fieldPanel));
            }
        }
    }

    /**
     * @param field   - member of the new JPanel;
     * @param dataSet - set of JLabels which contain the changing field variables of an object;
     * @param object  - the specific object supplying the data;
     * @return panel containing the labels with strings : *Field name* :  *variable*
     */
    private JPanel setupFieldPanel(Field field, List<JLabel> dataSet, Object object) {
        JLabel fieldName = getFieldName(field);
        JLabel data = getFieldData(field, object);
        JPanel fieldPanel = new JPanel(new GridLayout(1, 2));
        fieldPanel.setBackground(PANEL_BG);
        fieldPanel.add(fieldName);
        fieldPanel.add(data);
        dataSet.add(data);
        return fieldPanel;
    }

    /**
     * @param field field used to collect the name
     * @return formatted field name
     */
    private JLabel getFieldName(Field field) {
        JLabel fieldName = new JLabel();
        String format = StringUtils.capitalize(toNiceCase(field.getName())) + PAD;
        fieldName.setText(format);
        fieldName.setFont(new Font(Font.DIALOG, Font.BOLD, this.getFont().getSize() - 1));
        return fieldName;
    }

    /**
     * @param field  field used to collect variable value
     * @param object object supplying the variable value
     * @return formatted string label;
     */
    @SneakyThrows
    private JLabel getFieldData(Field field, Object object) {
        JLabel dataField = new JLabel(field.get(object).toString());
        dataField.setBorder(BorderFactory.createEtchedBorder());
        return dataField;
    }

    /**
     * Updates the data in all internal Data panels
     */
    private void updateAllData() {
        updateFields(fieldListADT, dataSetADT, aircraftDataTracker);
        updateFields(fieldListCDT, dataSetCDT, commercialDataTracker);
    }

    /**
     * @param fieldList list of fields used to extract new data;
     * @param dataList  list of JLabels to be updated with the new data;
     * @param object    supplies the updated data;
     */
    @SneakyThrows
    private void updateFields(List<Field> fieldList, List<JLabel> dataList, Object object) {
        Iterator<Field> fieldIterator = fieldList.iterator();
        int i = 0;
        while (i < dataList.size() && fieldIterator.hasNext()) {
            JLabel label = dataList.get(i);
            Field field = fieldIterator.next();
            label.setText(field.get(object).toString());
            i++;
        }
    }

    /**
     * @param clazz some class
     * @return all declared fields (except static and non-primitive fields);
     */
    private List<Field> extractFields(Class<?> clazz) {
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

    @Override
    public void compartmentSelected(Compartment area, AircraftDataTracker dataTracker) {
        updateAllData();
    }

    @Override
    public void fireCargoTradeUpdate(AircraftDataTracker dataTracker) {
        updateAllData();
    }

    @Override
    public void fireFuelSupplyUpdate(AircraftDataTracker dataTracker) {
        updateAllData();
    }
}
