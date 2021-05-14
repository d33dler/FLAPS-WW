package nl.rug.oop.gui.model;

import lombok.Getter;
import lombok.SneakyThrows;
import nl.rug.oop.gui.util.DataManager;
import nl.rug.oop.gui.util.DetailsUpdater;
import nl.rug.oop.gui.util.TableUpdater;
import nl.rug.oop.gui.view.MainFrame;

import java.sql.SQLException;

@Getter
public class AppCore {

    private DataManager dm;
    private Table database;
    private String queryCommand, searchField;
    private MainFrame gui;
    private DetailsUpdater detailsUpdater = new DetailsUpdater();
    private TableUpdater tableUpdater = new TableUpdater();
    private final String DEFAULT = "%%";

    public AppCore() throws SQLException {
        this.dm = new DataManager();
        this.searchField = "%%";
        this.queryCommand = DataManager.SEARCH_NPCS;
        this.database = new Table(this);
        this.gui = new MainFrame(this);
    }

    @SneakyThrows
    public void setSearchField(String searchField) {
        this.searchField = ("%" + searchField + "%");
        // System.out.println("Search field update: " + searchField);
        this.database = new Table(this);
        tableUpdater.fireUpdate(this);
    }

    public void fetchEntityDetails() {
        detailsUpdater.fireUpdate(this);
    }

    public void updateDatabase() {
        dm.executeUpdate(queryCommand);
        setQueryCommand(DataManager.SEARCH_NPCS);
        setSearchField(DEFAULT);
    }

    public void setQueryCommand(String queryCommand) {
        this.queryCommand = queryCommand;
    }

    public void setGui(MainFrame gui) {
        this.gui = gui;
    }
}
