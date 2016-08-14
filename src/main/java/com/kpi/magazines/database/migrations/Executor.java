package com.kpi.magazines.database.migrations;

import com.kpi.magazines.database.migrations.utils.Database;

/**
 * Created by Konstantin Minkov on 16.06.2016.
 */
public class Executor {

    public static void main(String[] args) {
        //Database.dropTables("user", "user_role");

        Database.createTable("user_role", table -> {
            table.createField("id")
                    .integer()
                    .primaryKey()
                    .notNull()
                    .autoIncrement();
            table.createField("role").varchar(45).notNull();
        });

        Database.createTable("user", table -> {
            table.createField("id")
                    .integer()
                    .notNull()
                    .primaryKey()
                    .autoIncrement();
            table.createField("name").varchar(45).notNull();
            table.createField("surname").varchar(45).notNull();
            table.createField("user_role_id").
                    foreignKey().
                    notNull().
                    references("user_role", "id");
        });
    }

}
