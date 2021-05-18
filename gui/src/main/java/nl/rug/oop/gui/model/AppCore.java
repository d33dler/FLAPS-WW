package nl.rug.oop.gui.model;

import lombok.Getter;
import lombok.SneakyThrows;
import nl.rug.oop.gui.control.DatabaseExport;
import nl.rug.oop.gui.util.DataManager;
import nl.rug.oop.gui.util.DetailsUpdater;
import nl.rug.oop.gui.util.TableUpdater;
import nl.rug.oop.gui.view.MainFrame;

import java.sql.SQLException;

@Getter
public class AppCore {

    private final DataManager dm;
    private Database database;
    private String queryCommand, searchField;
    private MainFrame gui;
    private final DetailsUpdater detailsUpdater = new DetailsUpdater();
    private final TableUpdater tableUpdater = new TableUpdater();
    private int logQuery;
    private boolean exportQuery;
    private boolean confirmExport;
    private final String DEFAULT = "%%";
    private final DatabaseExport databaseExport;

    public AppCore() throws SQLException {
        this.dm = new DataManager();
        this.searchField = DEFAULT;
        this.queryCommand = DataManager.SEARCH_NPCS;
        this.database = new Database(this);
        this.gui = new MainFrame(this);
        this.databaseExport = new DatabaseExport(this);
        this.exportQuery = false;
        this.confirmExport = false;
    }

    @SneakyThrows
    public void executeSearchQuery(String searchField) {
        this.searchField = ("%" + searchField + "%");
        this.database = new Database(this);
        tableUpdater.fireUpdate(this);
    }

    public void fetchEntityDetails() {
        detailsUpdater.fireUpdate(this);
    }

    public void updateDatabase() {
        logQuery = dm.executeUpdate(queryCommand);
        reportLogUpdate();
        setQueryCommand(DataManager.SEARCH_NPCS);
        executeSearchQuery(DEFAULT);
    }

    public void setQueryCommand(String queryCommand) {
        this.queryCommand = queryCommand;
    }

    public void reportLogUpdate() {
        gui.getQueryPanel().updateLog(this);
    }

    public void setGui(MainFrame gui) {
        this.gui = gui;
    }

    public void setExportQuery(boolean exportQuery) {
        this.exportQuery = exportQuery;
    }

    public void setConfirmExport(boolean confirmExport) {
        this.confirmExport = confirmExport;
        gui.getQueryPanel().exportUpdateLog(this);
    }
}
