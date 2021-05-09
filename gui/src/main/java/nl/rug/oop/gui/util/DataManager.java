package nl.rug.oop.gui.util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * This class is responsible for interacting with the database.
 * <p>
 *     This class implements {@link AutoCloseable}, meaning that it is intended
 *     to be used with a <em>try-with-resources</em> construct, so that the
 *     internal connection to the database is closed automatically when exiting
 *     the try block.
 * </p>
 * <p>
 *     The following is an example usage where we use the data manager to fetch
 *     all entities using a query, and map the results to a list of objects that
 *     we've defined. We then print each object:
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
 *     <strong>Warning: Failure to close this object will result in memory leaks
 *     and potential strain/failure of the database, and resources will be held
 *     until JVM termination.</strong>
 * </p>
 * <p>
 *     The methods in this class offer data operations for arbitrary entities
 *     and arbitrary SQL queries. These functionalities rely on a set of
 *     assumptions about the entities and their structure.
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

	// The following constants are defined for convenience.
	public static final String SELECT_NPC = "sql/select_npc.sql";
	public static final String SELECT_NPCS = "sql/select_npcs.sql";
	public static final String SELECT_NPC_TYPES = "sql/select_npc_types.sql";

	private final Connection connection;

	/**
	 * Constructs a new fetcher using the default url.
	 * @throws SQLException If the connection could not be initialized.
	 */
	public DataManager() throws SQLException {
		this(dbUrl);
	}

	/**
	 * Constructs a new fetcher using the given JDBC url.
	 * @param jdbcUrl The JDBC url to connect to.
	 * @throws SQLException If the connection could not be initialized.
	 */
	public DataManager(String jdbcUrl) throws SQLException {
		this.connection = DriverManager.getConnection(jdbcUrl);
	}

	/**
	 * Fetches a single entity by their identifying attribute.
	 * @param entityClass The class of the entity to fetch.
	 * @param id The id of the entity.
	 * @param sql The SQL query. This <strong>must</strong> have exactly one
	 *            placeholder parameter, which is used to identify a single
	 *            entity by its primary key.
	 * @param <T> The entity type.
	 * @return An optional container that will contain the found entity, or it
	 * will be empty if none was found.
	 */
	public <T> Optional<T> findById(Class<T> entityClass, Object id, String sql) {
		try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
			stmt.setObject(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return Optional.of(FetchUtils.extractEntity(entityClass, rs));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}

	/**
	 * Fetches a list of entities of the given class.
	 * @param entityClass The class of entities to fetch.
	 * @param sql The SQL string to use as a query. This query should take no
	 *            parameters, and should produce one row for each entity in the
	 *            system, thus no duplicate rows for groupings.
	 * @param <T> The type of entity that is fetched.
	 * @return A list of entities.
	 */
	public <T> List<T> findAll(Class<T> entityClass, String sql) {
		try (Statement stmt = this.connection.createStatement()) {
			ResultSet rs = stmt.executeQuery(sql);
			List<T> entities = new ArrayList<>();
			while (rs.next()) {
				entities.add(FetchUtils.extractEntity(entityClass, rs));
			}
			return entities;
		} catch (Exception e) {
			e.printStackTrace();
			return List.of();
		}
	}

	/**
	 * Fetches a list of entities of the given class, which match a given search
	 * query string. It is up to the SQL script to determine how to use the
	 * provided search query parameter.
	 * @param entityClass The class of entities to fetch.
	 * @param searchQuery A string to supply to the query for searching. Note
	 *                    that the string is supplied as-is to the query, so for
	 *                    things like wildcard support in a <code>LIKE</code>
	 *                    clause, please ensure that you add the necessary "%"
	 *                    before passing the string to this method.
	 * @param sql The SQL string to use as a query. The query should take a
	 *            single string parameter to use to filter results. It is up to
	 *            the query to decide how to do filtering, but using a pattern
	 *            such as <code>WHERE name LIKE ?;</code> works in most cases.
	 * @param <T> The type of entity that is fetched.
	 * @return A list of entities.
	 */
	public <T> List<T> searchAll(Class<T> entityClass, String searchQuery, String sql) {
		try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
			stmt.setString(1, searchQuery);
			ResultSet rs = stmt.executeQuery();
			List<T> entities = new ArrayList<>();
			while (rs.next()) {
				entities.add(FetchUtils.extractEntity(entityClass, rs));
			}
			return entities;
		} catch (Exception e) {
			e.printStackTrace();
			return List.of();
		}
	}

	/**
	 * Executes an update SQL script (INSERT, UPDATE, DELETE) on the database.
	 * @param sql The script to run.
	 * @return The number of affected rows, if applicable.
	 */
	public int executeUpdate(String sql) {
		try {
			Statement stmt = this.connection.createStatement();
			return stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * Closes the underlying connection to the database.
	 * @throws Exception If an SQL error occurs while closing the connection.
	 */
	@Override
	public void close() throws Exception {
		this.connection.close();
	}
}
