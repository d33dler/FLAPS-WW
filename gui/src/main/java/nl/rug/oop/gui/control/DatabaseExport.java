package nl.rug.oop.gui.control;

import nl.rug.oop.gui.model.AppCore;
import nl.rug.oop.gui.model.NpcEntity;
import nl.rug.oop.gui.util.DataManager;
import nl.rug.oop.gui.util.JsonConverter;

import javax.swing.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * DataBaseExport is responsible for initializing the conversion to a file format syntax of the
 * database contents that are quarried to be exported.
 */

public class DatabaseExport {
    File exportDir;
    JsonConverter jsonConverter;

    /**
     * Creates default directory for exporting
     */
    public DatabaseExport() {
        this.exportDir = new File("export");
        this.exportDir.mkdirs();
    }

    /**
     *
     * @param fileChooser is initiated for file overwrite;
     *                    exportFile() :
     *                    writes data from the string holding all converted data to a byteArray,
     *                    overwrites the selected file;
     *                    sends the boolean result to the model;
     *
     */
    public void exportFile(AppCore model, JFileChooser fileChooser) {
        String dataJson = initExport(model);
        String outputPath = fileChooser.getCurrentDirectory() + "/" + fileChooser.getSelectedFile().getName();
        try {
            FileOutputStream fStream =
                    new FileOutputStream(outputPath);
            ByteArrayOutputStream data = new ByteArrayOutputStream();
            byte[] byteData = dataJson.getBytes(StandardCharsets.UTF_8);
            data.write(byteData);
            data.writeTo(fStream);
            data.flush();
            data.close();
            fStream.close();
            model.confirmExport(true);
        } catch (Exception e) {
            model.confirmExport(false);
        }
    }

    /**
     *
     * @param model - has the boolean setting for exportQuery -> change to true;
     * @return converted String from the List of entities;
     */
    public String initExport(AppCore model) {
        model.setExportQuery(true);
        this.jsonConverter = new JsonConverter();
        List<NpcEntity> entities = getExportData(model);
        return jsonConverter.convertSyntax(entities);
    }

    /**
     *
     * @return List of all entities in the remote database;
     */
    public List<NpcEntity> getExportData(AppCore model) {
        DataManager dataManager = model.getDm();
        String query = dataManager.getQuery(DataManager.SELECT_DISTINCT_ALL);
        List<NpcEntity> list = dataManager.findAll(NpcEntity.class, query);
        model.setExportQuery(false);
        return list;
    }

}
