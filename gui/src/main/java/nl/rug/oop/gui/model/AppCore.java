package nl.rug.oop.gui.model;

import nl.rug.oop.gui.util.DataManager;

import java.sql.SQLException;

public class AppCore {
    private DataManager dm;
    private Table database;
    private String searchQuery;

    public AppCore() throws SQLException {
        this.dm = new DataManager();
        this.searchQuery = DataManager.SELECT_NPCS;
        this.database = new Table(this);
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
}
