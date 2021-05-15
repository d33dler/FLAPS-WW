package nl.rug.oop.gui.model;

import lombok.Getter;
import lombok.SneakyThrows;
import nl.rug.oop.gui.util.DataManager;
import nl.rug.oop.gui.util.DetailsUpdater;
import nl.rug.oop.gui.util.LogUpdater;
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
    private final LogUpdater logUpdater = new LogUpdater();
    private int logQuery;

    private final String DEFAULT = "%%";
    private final String Q_ERROR = "Error executing the query";
    private final String Q_POS = "Your query was executed successfully.";
    public AppCore() throws SQLException {
        this.dm = new DataManager();
        this.searchField = "%%";
        this.queryCommand = DataManager.SEARCH_NPCS;
        this.database = new Database(this);
        this.gui = new MainFrame(this);
    }

    @SneakyThrows
    public void executeSearchQuery(String searchField) {
        this.searchField = ("%" + searchField + "%");
        System.out.println("Search field update: " + searchField);
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
}
