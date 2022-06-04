package com.epam.filmrating.model.pool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Singleton that organizes pool of database connections.
 */
public class ConnectionPool {
    /**
     * Logger.
     */
    private static final Logger LOGGER = LogManager.getLogger();
    /**
     * Blocking deque of available proxy connections.
     */
    private final BlockingDeque<ProxyConnection> availableConnections;
    /**
     * Blocking deque of proxy connections in use.
     */
    private final BlockingDeque<ProxyConnection> connectionsInUse;
    /**
     * Reentrant lock for synchronization.
     */
    private static final ReentrantLock lock = new ReentrantLock();
    /**
     * Connection pool instance.
     */
    private static ConnectionPool instance;
    /**
     * Amount of connections in pool.
     */
    private static final int CONNECTION_POOL_SIZE = 32;

    /**
     * Private constructor.
     */
    private ConnectionPool() {
        availableConnections = new LinkedBlockingDeque<>(CONNECTION_POOL_SIZE);
        connectionsInUse = new LinkedBlockingDeque<>(CONNECTION_POOL_SIZE);
        ConnectionFactory connectionFactory = new ConnectionFactory();
        createConnections(connectionFactory);

        if (availableConnections.isEmpty()) {
            LOGGER.fatal("Connections weren't created.");
            throw new RuntimeException("Connections weren't created.");
        } else {
            if (availableConnections.size() < CONNECTION_POOL_SIZE) {
                LOGGER.warn("Connection pool is not full. Trying to fill...");
                createConnections(connectionFactory);
            }
        }
    }

    /**
     * Method to get pool of connections.
     *
     * @return ConnectionPool
     */
    public static ConnectionPool getInstance() {
        ConnectionPool localInstance = instance;
        if (localInstance == null) {
            lock.lock();
            localInstance = instance;
            try {
                if (localInstance == null) {
                    localInstance = new ConnectionPool();
                    instance = localInstance;
                }
            } finally {
                lock.unlock();
            }
        }
        return localInstance;
    }

    /**
     * Creates available connections.
     *
     * @param connectionFactory
     */
    private void createConnections(ConnectionFactory connectionFactory) {
        Connection connection;
        try {
            for (int i = 0; i < CONNECTION_POOL_SIZE; i++) {
                connection = connectionFactory.create();
                ProxyConnection proxyConnection = new ProxyConnection(connection);
                boolean isAdded = availableConnections.add(proxyConnection);
            }
        } catch (SQLException e) {
            LOGGER.error("Connection wasn't created caused database error.", e);
        }
    }

    /**
     * Gets available connection.
     *
     * @return
     */
    public Connection getConnection() {
        ProxyConnection connection = null;
        try {
            connection = availableConnections.take();
            connectionsInUse.offer(connection);
        } catch (InterruptedException e) {
            LOGGER.error("Current connection thread was interrupted", e);
            Thread.currentThread().interrupt();
        }
        return connection;
    }

    /**
     * Returns connection in use.
     *
     * @param connection
     */
    public void returnConnection(Connection connection) {
        if (connection.getClass() == ProxyConnection.class) {
            try {
                availableConnections.put((ProxyConnection) connection);
                connectionsInUse.remove(connection);
            } catch (InterruptedException e) {
                LOGGER.error("Current connection thread was interrupted", e);
                Thread.currentThread().interrupt();
            }
        } else {
            LOGGER.error("Given connection is not ProxyConnection.");
        }
    }

    /**
     * Destroys connection pool.
     */
    public void destroyPool() {
        try {
            for (int i = 0; i < CONNECTION_POOL_SIZE; i++) {
                availableConnections.take().reallyClose();
            }
        } catch (SQLException | InterruptedException throwables) {
            LOGGER.error("Connection destroying error.");
        }
        deregisterDrivers();
    }

    /**
     * Deregister JDBC drivers.
     */
    private void deregisterDrivers() {
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        try {
            while (drivers.hasMoreElements()) {
                Driver driver = drivers.nextElement();
                DriverManager.deregisterDriver(driver);
            }
        } catch (SQLException e) {
            LOGGER.fatal("Driver wasn't deregistered caused database error.", e);
        }

    }
}
