package com.kpi.magazines.database.migrations.utils;

import java.util.ArrayList;

/**
 * Created by Konstantin Minkov on 16.06.2016.
 */
public class Table {

    private static final String HEADER = "CREATE TABLE ";
    private final String NAME;
    private static final String FOOTER = ") ENGINE=INNODB;";
    private StringBuilder builder = new StringBuilder();
    private ArrayList<Field> fields = new ArrayList<>();

    public Table(String name) {
        this.NAME = name;
    }

    public Field createField(String name) {
        final Field field = new Field(name);
        fields.add(field);
        return field;
    }

    public String getSql() {
        if (builder.length() == 0) {
            builder.append(HEADER);
            builder.append(NAME);
            builder.append(" (");
            for (Field field : fields) {
                builder.append(field.getSql());
                builder.append(", \n");
            }
            builder.delete(builder.length() - 3, builder.length());
            builder.append(FOOTER);
        }
        return builder.toString();
    }
}
