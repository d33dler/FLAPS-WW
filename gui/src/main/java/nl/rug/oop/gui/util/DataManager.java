package nl.rug.oop.gui.util;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import java.util.Vector;

import lombok.SneakyThrows;
import nl.rug.oop.gui.model.AppCore;


/**
 * This class is responsible for interacting with the database.
 * <p>
 * This class implements {@link AutoCloseable}, meaning that it is intended
 * to be used with a <em>try-with-resources</em> construct, so that the
 * internal connection to the database is closed automatically when exiting
 * the try block.
 * </p>
 * <p>
 * The following is an example usage where we use the data manager to fetch
 * all entities using a query, and map the results to a list of objects that
 * we've defined. We then print each object:
 * </p>
 * <pre><code>
 *     try (DataManager dm = new DataManager()) {
 *         List&lt;MyNpcEntity&gt; entities = dm.findAll(
 *             MyNpcEntity.class,
 *             QueryCache.getOrThrow("sql/my_query.sql")
 *         );
 *         entities.forEach(System.out::println);
 *     } catch (Exception e) {
 *         e.printStackTrace();
 *     }
 * </code></pre>
 * <p>
 * <strong>Warning: Failure to close this object will result in memory leaks
 * and potential strain/failure of the database, and resources will be held
 * until JVM termination.</strong>
 * </p>
 * <p>
 * The methods in this class offer data operations for arbitrary entities
 * and arbitrary SQL queries. These functionalities rely on a set of
 * assumptions about the entities and their structure.
 * </p>
 * <ul>
 *     <li>All entity classes <strong>must have a public no-args constructor.</strong></li>
 *     <li>
 *         A query for one or more entities should return a result set in which
 *         all attributes of the entity are available in columns with names
 *         in the snake-case equivalent of the entity's attribute names. If we
 *         have an entity <code>Pet</code> with attribute <code>dogBreed</code>,
 *         then this fetcher expects the SQL to return a column named
 *         <code>dog_breed</code>. If the pet entity has a nested attribute of
 *         type <code>NameData</code> named <code>name</code>, where NameData
 *         has <code>firstName</code> and <code>lastName</code> attributes, we
 *         expect the SQL to return a column <code>name_first_name</code> and
 *         <code>name_last_name</code>.
 *         <p>
 *             Take a look at the existing queries available in the resources
 *             directory for some examples.
 *         </p>
 *     </li>
 * </ul>
 *
 * @author Andrew Lalis
 */
public class DataManager implements AutoCloseable {

    private static final String dbUrl = "jdbc:sqlite:npcs.sqlite";
    private AppCore model;
    public static final String SEARCH_NPCS = "src/main/resources/sql/search_npcs.sql";
    public static final String SELECT_DISTINCT_ALL = "src/main/resources/sql/select_npcs_full.sql";

    private final Connection connection;

    /**
     * Constructs a new fetcher using the default url.
     *
     * @throws SQLException If the connection could not be initialized.
     */
    public DataManager(AppCore model) throws SQLException {
        this(dbUrl);
        this.model = model;
    }

    /**
     * Constructs a new fetcher using the given JDBC url.
     *
     * @param jdbcUrl The JDBC url to connect to.
     * @throws SQLException If the connection could not be initialized.
     */
    public DataManager(String jdbcUrl) throws SQLException {
        this.connection = DriverManager.getConnection(jdbcUrl);
    }

    /**
     * Fetches a list of entities of the given class.
     *
     * @param entityClass The class of entities to fetch.
     * @param sql         The SQL string to use as a query. This query should take no
     *                    parameters, and should produce one row for each entity in the
     *                    system, thus no duplicate rows for groupings.
     * @param <T>         The type of entity that is fetched.
     * @return A list of entities.
     */
    public <T> List<T> findAll(Class<T> entityClass, String sql) {
        try (Statement stmt = this.connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            List<T> entities = new ArrayList<>();
            while (rs.next()) {
                entities.add(FetchUtils.extractEntity(entityClass, rs, model));
            }
            return entities;
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    /**
     * Executes an update SQL script (INSERT, UPDATE, DELETE) on the database.
     *
     * @param sql The script to run.
     * @return The number of affected rows, if applicable.
     */
    public int executeUpdate(String sql) {
        try {
            Statement stmt = this.connection.createStatement();
            return stmt.executeUpdate(sql);
        } catch (SQLException e) {
            return 0;
        }
    }

    /**
     * Closes the underlying connection to the database.
     *
     * @throws Exception If an SQL error occurs while closing the connection.
     */
    @Override
    public void close() throws Exception {
        this.connection.close();
    }

    /**
     *
     * @param path - of the SQL query file
     * @return - query in String value type
     * getQuery() : reads the file data and writes it to a byteArray buffer,
     * afterwards, parsing it to String type with UTF_8 standard.
     */

    @SneakyThrows
    public String getQuery(String path) {
        try {
            InputStream iStream = new FileInputStream(path);
            ByteArrayOutputStream query = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            for (int length; (length = iStream.read(buffer)) != -1; ) {
                query.write(buffer, 0, length);
            }
            System.out.println("query was loaded successfully");
            return query.toString(StandardCharsets.UTF_8);
        } catch (Exception e) {
            System.out.println("Error loading the  query.");
        }
        return null;
    }

    /**
     *
     * @param sql - simple path from source dir of the project
     * @return absolute path of the sql file holding the query.
     */
    private String getQueryPath(String sql) {
        File queryFile = new File(sql);
        File queryExtract = queryFile.getAbsoluteFile();
        return queryExtract.getAbsolutePath();
    }

    /**
     *
     * @param sql - path to the SQL command query file
     * @return resultSet created from the statement of the supplied SQL query file.
     */
    @SneakyThrows
    public ResultSet getResultSet(AppCore model, String sql) {
        try {
            String query = getQuery(getQueryPath(sql));
            PreparedStatement stmt = this.connection.prepareStatement(query);
            stmt = insertQuery(model, stmt);
            return stmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("Error executing the query.");
            return null;
        }
    }

    /**
     *
     * @param stmt - statement to be altered if the query is related to searching
     *             based on string matching;
     * @return - prepared statement -> modified/unaltered;
     */
    @SneakyThrows
    public PreparedStatement insertQuery(AppCore model, PreparedStatement stmt) {
        String query = model.getSearchField();
        if (query != null && !model.isExportQuery()) {
            stmt.setString(1, query);
        }
        return stmt;
    }

    /**
     *
     * @param metaData - to obtain column count and column names for the DefaultTableModel
     * @return columnIds Strings vector list
     */
    @SneakyThrows
    public Vector<String> getColumns(ResultSetMetaData metaData) {
        Vector<String> columnIds = new Vector<>();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            columnIds.add(metaData.getColumnName(i));
        }
        return columnIds;
    }

    /**
     * @return all fields data from all entities in the current resultSet.
     */
    @SneakyThrows
    public Vector<Vector<Object>> getData(ResultSet resultSet, ResultSetMetaData metaData) {
        Vector<Vector<Object>> data = new Vector<>();
        while (resultSet.next()) {
            Vector<Object> entityObj = new Vector<>();
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                entityObj.add(resultSet.getObject(i));
            }
            data.add(entityObj);
        }
        return data;
    }

}
