package nl.rug.oop.gui.control;
import nl.rug.oop.gui.model.AppCore;
import nl.rug.oop.gui.model.NpcEntity;
import nl.rug.oop.gui.util.DataManager;
import nl.rug.oop.gui.util.JsonConverter;
import javax.swing.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class DatabaseExport {
    File exportDir;
    JsonConverter jsonConverter;

    public DatabaseExport() {
        this.exportDir = new File("export");
        this.exportDir.mkdirs();
    }

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
            model.setConfirmExport(true);
        } catch (Exception e) {
            model.setConfirmExport(false);
        }
    }

    public String initExport(AppCore model) {
        model.setExportQuery(true);
        this.jsonConverter = new JsonConverter();
        List<NpcEntity> entities = getExportData(model);
        return jsonConverter.convertSyntax(entities);
    }

    public List<NpcEntity> getExportData(AppCore model) {
        DataManager dataManager = model.getDm();
        String query = dataManager.getQuery(DataManager.SELECT_DISTINCT_ALL);
        List<NpcEntity> list = dataManager.findAll(NpcEntity.class, query);
        model.setExportQuery(false);
        return list;
    }

}
