package com.kpi.magazines.database.migrations.utils;

import com.kpi.magazines.database.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Konstantin Minkov on 16.06.2016.
 */
public class Database {

    public static void createTable(String name, TableBuilder builder) {
        final Table table = new Table(name);
        builder.build(table);
        executeStatement(table.getSql());
    }

    public static void dropTables(String ... names) {
        final StringBuilder sql = new StringBuilder();
        sql.append("DROP TABLE IF EXISTS ");
        for (String name : names) {
            sql.append(name);
            sql.append(", ");
        }
        sql.replace(sql.length() - 2, sql.length(), ";");
        executeStatement(sql.toString());
    }

    private static void executeStatement(String sql) {
        final Connection connection = ConnectionManager.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
                ConnectionManager.returnConnection(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        System.out.println(sql);
    }
}
