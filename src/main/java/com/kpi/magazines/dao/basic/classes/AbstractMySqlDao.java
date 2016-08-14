package com.kpi.magazines.dao.basic.classes;

/**
 * Created by Konstantin Minkov on 26.06.2016.
 */
@SuppressWarnings("Duplicates")
public abstract class AbstractMySqlDao<T> extends AbstractDao<T> {

    protected static String generateDynamicSelect(String table, String ... fields) {
        final StringBuilder builder = new StringBuilder();
        builder.append("SELECT * FROM ");
        builder.append(table);
        if (fields.length > 0) {
            builder.append(" WHERE ");
            insertFields(builder, fields);
            builder.replace(builder.length() - 5, builder.length(), "");
        }
        builder.append(";");
        return builder.toString();
    }

    protected static String generateDynamicSelectLike(String table, String ... fields) {
        final StringBuilder builder = new StringBuilder();
        builder.append("SELECT * FROM ");
        builder.append(table);
        if (fields.length > 0) {
            builder.append(" WHERE ");
            insertFieldsLike(builder, fields);
            builder.replace(builder.length() - 5, builder.length(), "");
        }
        builder.append(";");
        return builder.toString();
    }

    protected static String generateDynamicSelectWithPaging(String table, String ... fields) {
        final StringBuilder builder = new StringBuilder();
        builder.append(generateDynamicSelect(table, fields));
        builder.replace(builder.length() - 1, builder.length(), " LIMIT ? OFFSET ?;");
        return builder.toString();
    }

    protected static String generateDynamicInsert(String table, String ... fields) {
        final StringBuilder builder = new StringBuilder();
        builder.append("INSERT INTO ");
        builder.append(table);
        builder.append(" (");
        for (String field : fields) {
            builder.append(field);
            builder.append(", ");
        }
        builder.replace(builder.length() - 2, builder.length(), ") ");
        builder.append("VALUES (");
        for (int i = 0; i < fields.length; i++) {
            builder.append("?, ");
        }
        builder.replace(builder.length() - 2, builder.length(), ");");
        return builder.toString();
    }

    protected static String generateDynamicUpdate(String table, String ... fields) {
        final StringBuilder builder = new StringBuilder();
        builder.append("UPDATE ");
        builder.append(table);
        builder.append(" SET ");
        //insertFields(builder, fields);
        for (String field : fields) {
            builder.append(field);
            builder.append(" = ?, ");
        }

        builder.replace(builder.length() - 2, builder.length(), " WHERE id = ?;");
        return builder.toString();
    }

    protected static String generateDynamicDelete(String table, String ... fields) {
        final StringBuilder builder = new StringBuilder();
        builder.append("DELETE FROM ");
        builder.append(table);
        if (fields.length > 0) {
            builder.append(" WHERE ");
            insertFields(builder, fields);
            builder.replace(builder.length() - 5, builder.length(), "");
        }
        builder.append(";");
        return builder.toString();
    }

    protected static String generateDynamicRowsCount(String table, String ... columns) {
        final StringBuilder builder = new StringBuilder();
        builder.append("SELECT COUNT(*) FROM ");
        builder.append(table);
        if (columns.length > 0) {
            builder.append(" WHERE ");
            insertFieldsLike(builder, columns);
            builder.replace(builder.length() - 5, builder.length(), "");
        }
        builder.append(";");
        return builder.toString();
    }

    protected static String generateDynamicSelectWithPagingOrderByDesc(String table, String column, String ... columns) {
        final StringBuilder builder = new StringBuilder();
        builder.append(generateDynamicSelectLike(table, columns));
        builder.replace(builder.length() - 1, builder.length(), " ORDER BY ");
        builder.append(column);
        builder.append(" DESC LIMIT ? OFFSET ?;");
        return builder.toString();
    }

    private static void insertFieldsLike(StringBuilder builder, String ... fields) {
        for (String field : fields) {
            builder.append(field);
            builder.append(" LIKE ? AND ");
        }
    }

    private static void insertFields(StringBuilder builder, String ... fields) {
        for (String field : fields) {
            builder.append(field);
            builder.append(" = ? AND ");
        }
    }
}
