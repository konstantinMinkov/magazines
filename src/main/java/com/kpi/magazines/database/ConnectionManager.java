package com.kpi.magazines.database;

import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Queue;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Konstantin Minkov on 15.06.2016.
 */

/**
 * @author Konstantin Minkov
 * Class, that provides Connection instances to data source.
 */
@Log4j2
public class ConnectionManager {

    /**
     * Resource bundle, that contains all essential config data for establishing Connection
     * to database.
     */
    private static final ResourceBundle resources = ResourceBundle.getBundle("database");

    /**
     * Pool of Connection instances.
     */
    private static final Queue<Connection> pool = new ConcurrentLinkedQueue<>();

    /**
     * Initial pool size
     */
    private static final int INITIAL_POOL_SIZE = 10;

    private ConnectionManager() { }

    /**
     * Static section for loading database driver and filling pool with Connection
     * instances.
     */
    static {
        Connection connection;
        try {
            Class.forName(resources.getString("driver")).newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            log.catching(Level.FATAL, e);
        }
        for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
            connection = createConnection();
            if (connection != null) {
                pool.offer(connection);
            } else {
                log.log(Level.ERROR, "Trying to init connection pool with NULL.");
            }
        }
        log.log(Level.INFO, "Connection pool size on init is:   " + pool.size());
        log.log(Level.INFO, "Estimated connection pool size is: " + INITIAL_POOL_SIZE);
    }

    /**
     *
     * @return Connection instance, otherwise null, if something goes wrong with DriverManager.
     */
    private static Connection createConnection() {
        final String host = resources.getString("host");
        final String user = resources.getString("user");
        final String password = resources.getString("password");
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(host, user, password);
        } catch (SQLException e) {
            log.catching(Level.ERROR, e);
        }
        return connection;
    }

    /**
     *
     * @return Connection from pool, or new instance if pool is empty.
     */
    public static Connection getConnection() {
        if (pool.size() != 0) {
            return pool.poll();
        }
        synchronized (ConnectionManager.class) {
            return createConnection();
        }
    }

    /**
     * Put Connection to pool after usage.
     * @param connection
     */
    public static void returnConnection(Connection connection) {
        if ( !pool.contains(connection)) {
            pool.offer(connection);
        }
    }
}
