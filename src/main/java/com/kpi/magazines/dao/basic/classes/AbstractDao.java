package com.kpi.magazines.dao.basic.classes;

import com.kpi.magazines.dao.basic.interfaces.GenericDao;
import com.kpi.magazines.database.ConnectionManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Konstantin Minkov on 26.06.2016.
 */
@SuppressWarnings("Duplicates")
public abstract class AbstractDao<T> implements GenericDao<T> {

    private static final Logger LOGGER = LogManager.getLogger(AbstractDao.class);

    protected abstract T transform(ResultSet resultSet) throws SQLException;

    protected List<T> executeSelect(String sqlPrepared, Object ... params) {
        final ResultSet resultSet = executeQuery(sqlPrepared, params);
        final List<T> objects = new ArrayList<>(1);
        try {
            while (resultSet.next()) {
                objects.add(transform(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.catching(e);
        } finally {
            closeResultSet(resultSet);
        }
        return objects;
    }

    protected boolean executeUpdate(String sqlPrepared, Object ... params) {
        final Connection connection = ConnectionManager.getConnection();
        PreparedStatement statement;
        boolean isSuccessful = false;
        try {
            statement = connection.prepareStatement(sqlPrepared);
            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }
            isSuccessful = statement.execute();
        } catch (SQLException e) {
            LOGGER.catching(e);
        } finally {
            ConnectionManager.returnConnection(connection);
        }
        return isSuccessful;
    }

    protected int executeRowsCount(String sqlPrepared, Object ... params) {
        final ResultSet resultSet = executeQuery(sqlPrepared, params);
        int count = 0;
        try {
            resultSet.next();
            count = resultSet.getInt(1);
        } catch (SQLException e) {
            LOGGER.catching(e);
        } finally {
            closeResultSet(resultSet);
        }
        return count;
    }

    private ResultSet executeQuery(String sqlPrepared, Object ... params) {
        final Connection connection = ConnectionManager.getConnection();
        ResultSet resultSet = null;
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(sqlPrepared);
            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }
            resultSet = statement.executeQuery();
        } catch (SQLException e) {
            LOGGER.catching(e);
        } finally {
            ConnectionManager.returnConnection(connection);
        }
        return resultSet;
    }

    protected void closeResultSet(ResultSet resultSet) {
        try {
            resultSet.close();
        } catch (SQLException e) {
            LOGGER.catching(e);
        }
    }
}
