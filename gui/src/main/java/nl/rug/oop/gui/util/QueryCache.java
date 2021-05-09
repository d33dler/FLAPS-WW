package nl.rug.oop.gui.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class defines a singleton that acts as a runtime cache for queries that
 * have been loaded from files. Once a query is loaded, it is saved in the cache
 * so that it can be used elsewhere.
 * <p>
 *     This cache is thread-safe, meaning that concurrently attempting to load
 *     new queries should not cause any issues.
 * </p>
 */
public class QueryCache {
	private static final QueryCache instance = new QueryCache();

	/**
	 * The internal map which holds a cached SQL query string for each resource
	 * name key.
	 */
	private final Map<String, String> queryCacheMap = new ConcurrentHashMap<>();

	/**
	 * Gets a query from the cache, loading it from a resource file if necessary.
	 * @param resourceName The name of the query file.
	 * @return An optional that contains the SQL query if successful, or empty
	 * if it could not be found.
	 */
	public static Optional<String> get(String resourceName) {
		return instance.getQuery(resourceName);
	}

	/**
	 * Convenience method for getting a query or throwing an unchecked exception
	 * if the query couldn't be found.
	 * @param resourceName The name of the query file.
	 * @return An optional that contains the SQL query if successful, or empty
	 * if it could not be found.
	 */
	public static String getOrThrow(String resourceName) {
		return get(resourceName).orElseThrow(() -> new IllegalArgumentException("Could not find resource at " + resourceName));
	}

	/**
	 * Fetches the SQL query for a certain resource name.
	 * @param resourceName The name of the resource.
	 * @return An optional that contains the SQL query if successful, or empty
	 * if it could not be found.
	 */
	private Optional<String> getQuery(String resourceName) {
		String sql = queryCacheMap.get(resourceName);
		if (sql == null) {
			try {
				sql = this.readSql(resourceName);
				queryCacheMap.put(resourceName, sql);
			} catch (IOException e) {
				e.printStackTrace();
				return Optional.empty();
			}
		}
		return Optional.of(sql);
	}

	/**
	 * Reads an SQL script from a resource file on the classpath.
	 * @param resourceName The name of the resource.
	 * @return The SQL text that was read from the file.
	 * @throws IOException If the file could not be read.
	 */
	private String readSql(String resourceName) throws IOException {
		try (InputStream is = getClass().getClassLoader().getResourceAsStream(resourceName)) {
			if (is == null) throw new IOException("Could not obtain input stream for resource: " + resourceName);
			return new String(is.readAllBytes());
		}
	}
}
