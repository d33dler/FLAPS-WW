package nl.rug.oop.gui.control;

import nl.rug.oop.gui.model.AppCore;
import nl.rug.oop.gui.model.NpcEntity;
import nl.rug.oop.gui.util.DataManager;
import nl.rug.oop.gui.util.JsonConverter;
import nl.rug.oop.gui.view.FileChooser;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class DatabaseExport {
    File exportDir;
    JsonConverter jsonConverter;

    public DatabaseExport(AppCore model) {
        this.exportDir = new File("export");
        this.exportDir.mkdirs();
    }

    public void exportFile(AppCore model, FileChooser fileChooser) {
        model.setExport(true);
        this.jsonConverter = new JsonConverter(model);
        List<NpcEntity> entities = getExportData(model);
        String dataJson = jsonConverter.convertSyntax(entities);
        try {
            FileOutputStream fstream = new FileOutputStream("export/" + fileChooser.getSelectedFile().getName());
            ByteArrayOutputStream store = new ByteArrayOutputStream();
            byte[] data = dataJson.getBytes(StandardCharsets.UTF_8);
            store.write(data);
            store.writeTo(fstream);
            store.flush();
            store.close();
            fstream.close();
            System.out.println("Data exported succesfully");
        } catch (Exception e) {
            System.out.println("Error exporting data");
        }
    }

    public List<NpcEntity> getExportData(AppCore model) {
        DataManager dataManager = model.getDm();
        String query = dataManager.getQuery(DataManager.SELECT_DISTINCT_ALL);
        List<NpcEntity> list = dataManager.findAll(NpcEntity.class, query);
        model.setExport(false);
        return list;
    }


}
