package nl.rug.oop.gui.model;

import nl.rug.oop.gui.util.DataManager;
import nl.rug.oop.gui.util.Updater;
import nl.rug.oop.gui.view.MainFrame;

import java.sql.SQLException;

public class AppCore extends Updater {
    private DataManager dm;
    private Table database;
    private String searchQuery;
    private String searchField;
    private MainFrame gui;

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
        fireUpdate(this);
    }

    public Table getDatabase() {
        return database;
    }

    public void setDatabase(Table database) {
        this.database = database;
    }

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public DataManager getDm() {
        return dm;
    }

    public void setDm(DataManager dm) {
        this.dm = dm;
    }

    public String getSearchField() {
        return searchField;
    }


    public MainFrame getGui() {
        return gui;
    }

    public void setGui(MainFrame gui) {
        this.gui = gui;
    }
}
