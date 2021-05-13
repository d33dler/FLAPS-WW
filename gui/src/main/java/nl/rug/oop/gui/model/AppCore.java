package nl.rug.oop.gui.model;

import lombok.Getter;
import nl.rug.oop.gui.util.DataManager;
import nl.rug.oop.gui.util.DetailsUpdater;
import nl.rug.oop.gui.util.TableUpdater;
import nl.rug.oop.gui.view.MainFrame;

import java.sql.SQLException;

@Getter
public class AppCore {
    private DataManager dm;
    private Table database;
    private String searchQuery;
    private String searchField;
    private MainFrame gui;
    private DetailsUpdater detailsUpdater = new DetailsUpdater();
    private TableUpdater tableUpdater = new TableUpdater();

    public AppCore() throws SQLException {
        this.dm = new DataManager();
        this.searchField = "%%";
        this.searchQuery = DataManager.SEARCH_NPCS;
        this.database = new Table(this);
        this.gui = new MainFrame(this);
    }

    public void setSearchField(String searchField) {
        this.searchField = ("%" + searchField + "%");
        System.out.println("i update sF" + searchField);
        this.database = new Table(this);
        tableUpdater.fireUpdate(this);
    }

    public void setGui(MainFrame gui) {
        this.gui = gui;
    }

    public void setRequestData() {
        detailsUpdater.fireUpdate(this);
    }
}
