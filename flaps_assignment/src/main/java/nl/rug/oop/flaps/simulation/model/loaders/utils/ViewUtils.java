package nl.rug.oop.flaps.simulation.model.loaders.utils;

import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ViewUtils {

    public static String toSnakeCase(String camelCase) {
        Matcher m = Pattern.compile("(?<=[a-z])[A-Z]").matcher(camelCase);
        return m.replaceAll(match -> "_" + match.group().toLowerCase());
    }

    public static String toNiceCase(String camelCase) {
        Matcher m = Pattern.compile("(?<=[a-z])[A-Z]").matcher(camelCase);
        return m.replaceAll(match -> " " + match.group().toLowerCase());
    }

    /**
     * @param field field used to collect the name
     * @return formatted field name
     */
    public static JLabel getFormattedName(Field field) {
        JLabel fieldName = new JLabel();
        String format = StringUtils.capitalize(toNiceCase(field.getName())) + " : ";
        fieldName.setText(format);
        fieldName.setFont(new Font(Font.DIALOG, Font.BOLD, 14));
        return fieldName;
    }

    public static void setCustomBorder(JComponent jC, String title, int type, int just, int pos) {
        jC.setBorder(BorderFactory.createTitledBorder
                (BorderFactory.createEtchedBorder(1),
                        title, just, pos));
    }
}
