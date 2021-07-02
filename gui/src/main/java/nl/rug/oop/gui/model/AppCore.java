package nl.rug.oop.gui.model;

import lombok.Getter;
import lombok.SneakyThrows;
import nl.rug.oop.gui.control.DatabaseExport;
import nl.rug.oop.gui.util.DataManager;
import nl.rug.oop.gui.util.DetailsUpdater;
import nl.rug.oop.gui.util.TableUpdater;
import nl.rug.oop.gui.view.MainFrame;

import java.sql.SQLException;


/**
 * AppCore - represent the model (in the MVC architecture)
 * It hold all program main system components - DataManager, Database, GUI frame, Updaters;
 */

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
    private final static String DEFAULT = "%%";
    private final DatabaseExport databaseExport;

    /**
     * @throws SQLException - if initialization of the database based on the current set
     *                      queryCommand fails.
     */
    public AppCore() throws SQLException {
        this.dm = new DataManager(this);
        this.searchField = DEFAULT;
        this.queryCommand = DataManager.SEARCH_NPCS;
        this.database = new Database(this);
        this.gui = new MainFrame(this);
        this.databaseExport = new DatabaseExport();
        this.exportQuery = false;
        this.confirmExport = false;
    }

    /**
     * @param searchField text supplied by the DocumentListener of searchbar JTextField (SearchBarPanel),
     *                    updates the local app database and fires update to Table component class listeners list.
     */
    @SneakyThrows
    public void executeSearchQuery(String searchField) {
        this.searchField = ("%" + searchField + "%");
        this.database = new Database(this);
        tableUpdater.fireUpdate(this);
    }

    /**
     * Fires update to Details component class listeners (Details & Description text, Image) list;
     */
    public void fetchEntityDetails() {
        detailsUpdater.fireUpdate(this);
    }

    /**
     * Executes an update query on the remote database and updates the local database and the view;
     * Also, reports result to the log component;
     */
    public void updateDatabase() {
        logQuery = dm.executeUpdate(queryCommand);
        reportLogUpdate();
        setQueryCommand(DataManager.SEARCH_NPCS);
        executeSearchQuery(DEFAULT);
    }

    public void reportLogUpdate() {
        gui.getQueryPanel().updateLog(this);
    }

    /**
     * @param error - text supplied by FetchUtils/DataManager during extraction of field values
     *              during export process - which is outputted to the log;
     */
    public void reportLogCustomError(String error) {
        gui.getQueryPanel().entityExtractionError(error);
    }

    /**
     * @param confirmExport is true if the database export went successfully,
     *                      -> report to the log
     */
    public void confirmExport(boolean confirmExport) {
        this.confirmExport = confirmExport;
        gui.getQueryPanel().exportUpdateLog(this);
    }

    public void setGui(MainFrame gui) {
        this.gui = gui;
    }

    public void setExportQuery(boolean exportQuery) {
        this.exportQuery = exportQuery;
    }


    public void setQueryCommand(String queryCommand) {
        this.queryCommand = queryCommand;
    }
}
