package com.kpi.magazines.dao.orm;

import com.kpi.magazines.dao.orm.utils.EntityMetadata;
import com.kpi.magazines.dao.orm.utils.StatementsHolder;
import com.kpi.magazines.database.ConnectionManager;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Konstantin Minkov on 18.06.2016.
 */
public abstract class AbstractDao<T> {

    private static final Map<Class, StatementsHolder> statementsForEntityMap = new HashMap<>();
    private static final Map<Class, EntityMetadata> entitiesMetadataMap = new HashMap<>();

    public boolean create(T obj) {
        final StatementsHolder statementsHolder = statementsForEntityMap.get(obj.getClass());
        final String createStatement = statementsHolder.getStatement("INSERT");
        return executeUpdate(createStatement, getFieldsValues(obj).toArray());
    }

    public boolean update(T obj) {
        final StatementsHolder statementsHolder = statementsForEntityMap.get(obj.getClass());
        final String updateStatement = statementsHolder.getStatement("UPDATE");
        return executeUpdate(updateStatement, getFieldsValues(obj).toArray(), getIdValue(obj));
    }

    public boolean delete(T obj) {
        final StatementsHolder statementsHolder = statementsForEntityMap.get(obj.getClass());
        final String deleteStatement = statementsHolder.getStatement("DELETE");
        return executeUpdate(deleteStatement, getIdValue(obj));
    }

    public T findById(int id) {
        final StatementsHolder statementsHolder = statementsForEntityMap.get(getEntityClass());
        final String selectStatement = statementsHolder.getStatement("SELECT_BY_ID");
        final List<T> list = select(selectStatement, id);
        return list.isEmpty() ? null : list.get(0);
    }

    public List<T> findAll() {
        final StatementsHolder statementsHolder = statementsForEntityMap.get(getEntityClass());
        final String selectStatement = statementsHolder.getStatement("SELECT_BY_ID");
        return select(selectStatement);
    }

    public List<T> findByDynamicSelect(Map<String, Object> columnsAndValues) {
        final StringBuilder builder = new StringBuilder();
        final List<Object> values = new ArrayList<>();
        final String selectStatement;
        builder.append("SELECT_BY_");
        columnsAndValues.forEach((columnName, columnValue) -> {
            builder.append(columnName.toUpperCase());
            values.add(columnValue);
        });
        selectStatement  = statementsForEntityMap
                .get(getEntityClass())
                .getStatement(builder.toString());
        if (selectStatement == null) {
            throw new IllegalArgumentException("Unregistered select.");
        }
        return select(selectStatement, values);
    }

    public List<T> findByDynamicSelect(String column, Object value) {
        final String selectStatement;
        selectStatement  = statementsForEntityMap
                .get(getEntityClass())
                .getStatement("SELECT_BY_" + column.toUpperCase());
        if (selectStatement == null) {
            throw new IllegalArgumentException("Unregistered select.");
        }
        return select(selectStatement, value);
    }

    //глупая заглушка для find
    protected abstract Class<T> getEntityClass();

    protected static void registerDao(Class entityClass) {
        final EntityMetadata metadata = new EntityMetadata();
        metadata.parseEntity(entityClass);
        entitiesMetadataMap.put(entityClass, metadata);
        generateBasicCRUD(entityClass, metadata);
    }

    protected static void registerSelect(Class entityClass, String column) {
        final String selectName = "SELECT_BY_" + column.toUpperCase();
        final StatementsHolder statementsHolder = statementsForEntityMap.get(entityClass);
        final EntityMetadata entityMetadata = entitiesMetadataMap.get(entityClass);
        if (entityMetadata == null || statementsHolder == null) {
            throw new IllegalArgumentException("No DAO for this entity was registered.");
        }
        statementsHolder.addStatement(selectName, generateDynamicSelect(entityMetadata, column));
        //System.out.println(statementsHolder.toString());
    }

    private static void generateBasicCRUD(Class entityClass, EntityMetadata metadata) {
        final StatementsHolder statementsHolder = new StatementsHolder();
        statementsHolder.addStatement("INSERT", generateDynamicInsert(metadata));
        statementsHolder.addStatement("SELECT_ALL", generateDynamicSelect(metadata));
        statementsHolder.addStatement("SELECT_BY_ID", generateDynamicSelect(metadata, "id"));
        statementsHolder.addStatement("UPDATE", generateDynamicUpdate(metadata));
        statementsHolder.addStatement("DELETE", generateDynamicDelete(metadata, "id"));
        statementsForEntityMap.put(entityClass, statementsHolder);
        //System.out.println(statementsHolder);
    }

    private static String generateDynamicInsert(EntityMetadata metadata) {
        final StringBuilder builder = new StringBuilder();
        builder.append("INSERT INTO ");
        builder.append(metadata.getTable());
        builder.append(" (");
        metadata.forEachField(field -> {
            if ( !field.isAutogenerated()) {
                builder.append(field.getColumn());
                builder.append(", ");
            }
        });
        builder.replace(builder.length() - 2, builder.length(), ") ");
        builder.append("VALUES (");
        metadata.forEachField(field -> {
            if ( !field.isAutogenerated()) {
                builder.append("?, ");
            }
        });
        builder.replace(builder.length() - 2, builder.length(), ");");
        return builder.toString();
    }

    private static String generateDynamicSelect(EntityMetadata metadata, String ... columns) {
        final StringBuilder builder = new StringBuilder();
        builder.append("SELECT * FROM ");
        builder.append(metadata.getTable());
        appendColumns(builder, columns);
        builder.append(";");
        return builder.toString();
    }

    private static String generateDynamicUpdate(EntityMetadata metadata) {
        final StringBuilder builder = new StringBuilder();
        builder.append("UPDATE ");
        builder.append(metadata.getTable());
        builder.append(" SET ");
        metadata.forEachField(fieldMetadata -> {
            if ( !fieldMetadata.isAutogenerated()) {
                builder.append(fieldMetadata.getColumn());
                builder.append(" = ?, ");
            }
        });
        builder.replace(builder.length() - 2, builder.length(), " WHERE id = ?;");
        return builder.toString();
    }

    private static String generateDynamicDelete(EntityMetadata metadata, String ... columns) {
        final StringBuilder builder = new StringBuilder();
        builder.append("DELETE * FROM ");
        builder.append(metadata.getTable());
        appendColumns(builder, columns);
        builder.append(";");
        return builder.toString();
    }

    private static void appendColumns(StringBuilder builder, String ... columns) {
        if (columns.length > 0) {
            builder.append(" WHERE ");
            for (String column : columns) {
                builder.append(column);
                builder.append(" = ?, ");
            }
            builder.replace(builder.length() - 2, builder.length(), "");
        }
    }

    private T transform(ResultSet resultSet) throws SQLException {
        T entity = null;
        final Class<T> entityClass = getEntityClass();
        final EntityMetadata metadata = entitiesMetadataMap.get(entityClass);
        try {
            entity = getEntityClass().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        final T finalEntity = entity;
        metadata.forEachField(fieldMetadata -> {
            Field field = null;
            try {
                field = entityClass.getDeclaredField(fieldMetadata.getField());
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            field.setAccessible(true);
            try {
                switch (field.getGenericType().getTypeName()) {
                    case "int" :
                        field.set(finalEntity, resultSet.getInt(fieldMetadata.getColumn()));
                        break;
                    case "java.time.LocalDateTime" :
                        field.set(finalEntity, resultSet.getTimestamp(fieldMetadata.getColumn()).toLocalDateTime());
                        break;
                    case "java.lang.String" :
                        field.set(finalEntity, resultSet.getString(fieldMetadata.getColumn()));
                        break;
                    default:
                        System.out.println(field);
                }
            } catch (IllegalAccessException | SQLException e) {
                e.printStackTrace();
            }
            field.setAccessible(false);
        });
        return entity;
    }

    private List<T> select(String select, Object ... params) {
        final ResultSet resultSet = executeQuery(select, params);
        final List<T> objects = new ArrayList<>(1);
        try {
            while (resultSet.next()) {
                objects.add(transform(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResultSet(resultSet);
        }
        return objects;
    }

    private boolean executeUpdate(String sqlPrepared, Object ... params) {
        final Connection connection = ConnectionManager.getConnection();
        PreparedStatement statement;
        boolean isSuccessful = false;
        try {
            statement = connection.prepareStatement(sqlPrepared);
            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }
            return isSuccessful = statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.returnConnection(connection);
        }
        return isSuccessful;
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
            e.printStackTrace();
        } finally {
            ConnectionManager.returnConnection(connection);
        }
        return resultSet;
    }

    @Deprecated
    private Map<String, Object> mapValuesToColumns(T obj) {
        final Class entityClazz = obj.getClass();
        final Map<String, Object> map = new HashMap<>();
        final EntityMetadata entityMetadata = entitiesMetadataMap.get(entityClazz);
        entityMetadata.forEachField(fieldMetadata -> {
            if ( !fieldMetadata.isAutogenerated()) {
                Field field = null;
                try {
                    field = entityClazz.getDeclaredField(fieldMetadata.getField());
                    field.setAccessible(true);
                    map.put(fieldMetadata.getColumn(), field.get(obj));
                    field.setAccessible(false);
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
        System.out.println(map);
        return map;
    }

    private List<Object> getFieldsValues(T obj) {
        final Class entityClazz = obj.getClass();
        final List<Object> values = new ArrayList<>();
        final EntityMetadata entityMetadata = entitiesMetadataMap.get(entityClazz);
        entityMetadata.forEachField(fieldMetadata -> {
            if ( !fieldMetadata.isAutogenerated()) {
                Field field = null;
                try {
                    field = entityClazz.getDeclaredField(fieldMetadata.getField());
                    field.setAccessible(true);
                    values.add(field.get(obj));
                    field.setAccessible(false);
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
        return values;
    }

    private int getIdValue(T obj) {
        int id = -1;
        final Class entityClazz = obj.getClass();
        final EntityMetadata.FieldMetadata idFieldMetadata =
                entitiesMetadataMap.get(entityClazz).getIdFieldMetadata();
        final Field field;
        try {
            field = entityClazz.getDeclaredField(idFieldMetadata.getColumn());
            field.setAccessible(true);
            id = field.getInt(obj);
            field.setAccessible(false);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return id;
    }

    private void closeResultSet(ResultSet resultSet) {
        try {
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
