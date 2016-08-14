package com.kpi.magazines.database.migrations.utils;

/**
 * Created by Konstantin Minkov on 16.06.2016.
 */
public class Field {

    private String name;
    private StringBuilder sql;
    private StringBuilder sqlForeignKeys;

    public Field(String name) {
        this.name = name;
        sql = new StringBuilder();
        sqlForeignKeys = new StringBuilder();
        sql.append(name);
        sql.append(" ");
    }

    public Field notNull() {
        sql.append("NOT NULL ");
        return this;
    }

    public Field primaryKey() {
        sql.append("PRIMARY KEY ");
        return this;
    }

    public Field integer() {
        sql.append("INT ");
        return this;
    }

    public Field varchar(int size) {
        sql.append("VARCHAR(");
        sql.append(size);
        sql.append(") ");
        return this;
    }

    public Field autoIncrement() {
        sql.append("AUTO_INCREMENT ");
        return this;
    }

    public Field foreignKey() {
        integer();
        sqlForeignKeys.append("FOREIGN KEY (");
        sqlForeignKeys.append(name);
        sqlForeignKeys.append(") ");
        return this;
    }

    public Field references(String table, String keyField) {
        sqlForeignKeys.append("REFERENCES ");
        sqlForeignKeys.append(table);
        sqlForeignKeys.append("(");
        sqlForeignKeys.append(keyField);
        sqlForeignKeys.append(")");
        return this;
    }

    public String getSql() {
        if (sqlForeignKeys.length() != 0) {
            return sql.toString() + ", \n" + sqlForeignKeys.toString();
        }
        return sql.toString();
    }
}
