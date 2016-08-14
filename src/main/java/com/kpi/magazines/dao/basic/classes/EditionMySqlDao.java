package com.kpi.magazines.dao.basic.classes;

import com.kpi.magazines.beans.Edition;
import com.kpi.magazines.dao.basic.interfaces.EditionDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Konstantin Minkov on 27.06.2016.
 */
public class EditionMySqlDao extends AbstractMySqlDao<Edition> implements EditionDao {

    private static final String TABLE = "edition";

    private static final int ID = 1;
    private static final int NAME = 2;
    private static final int DESCRIPTION = 3;
    private static final int PRICE = 4;
    private static final int EDITIONS_PER_MONTH = 5;
    private static final int TIME_CREATED = 6;
    private static final int TIME_UPDATED = 7;
    private static final int CATEGORY_ID = 8;
    private static final int DRIVE_ID = 9;

    private static final String SELECT_BY_ID = generateDynamicSelect(TABLE, "id");
    private static final String SELECT_BY_NAME = generateDynamicSelect(TABLE, "name");
    private static final String SELECT_ALL = generateDynamicSelect(TABLE);
    private static final String SELECT_ALL_PAGING = generateDynamicSelectWithPaging(TABLE);
    private static final String SELECT_ALL_PAGING_BY_UPDATE_TIME_DESC =
            generateDynamicSelectWithPagingOrderByDesc(TABLE, "time_updated");
    private static final String SELECT_LIKE_NAME_PAGING_BY_UPDATE_TIME_DESC =
            generateDynamicSelectWithPagingOrderByDesc(TABLE, "time_updated", "name");
    private static final String SELECT_LIKE_NAME_BY_CATEGORY_ID_PAGING_BY_UPDATE_TIME_DESC =
            generateDynamicSelectWithPagingOrderByDesc(TABLE, "time_updated", "name", "category_id");
    private static final String INSERT = generateDynamicInsert(TABLE, "name", "description", "price", "editions_per_month", "category_id", "drive_id");
    private static final String UPDATE = generateDynamicUpdate(TABLE, "name", "description", "price", "editions_per_month", "category_id", "drive_id");
    private static final String DELETE = generateDynamicDelete(TABLE, "id");
    private static final String ROWS_COUNT = generateDynamicRowsCount(TABLE);
    private static final String ROWS_COUNT_LIKE_NAME =
            generateDynamicRowsCount(TABLE, "name");
    private static final String ROWS_COUNT_LIKE_NAME_BY_CATEGORY_ID =
            generateDynamicRowsCount(TABLE, "name", "category_id");

    @Override
    public boolean update(Edition obj) {
        return executeUpdate(UPDATE, obj.getName(), obj.getDescription(), obj.getPrice(), obj.getEditionsPerMonth(),
                obj.getCategoryId(), obj.getDriveId(),
                obj.getId());
    }

    @Override
    public boolean delete(Edition obj) {
        return executeUpdate(DELETE, obj.getId());
    }

    @Override
    public boolean create(Edition obj) {
        return executeUpdate(INSERT, obj.getName(), obj.getDescription(), obj.getPrice(), obj.getEditionsPerMonth(),
                obj.getCategoryId(), obj.getDriveId());
    }

    @Override
    public Edition findById(int id) {
        final List<Edition> users = executeSelect(SELECT_BY_ID, id);
        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    public Edition findByName(String name) {
        final List<Edition> users = executeSelect(SELECT_BY_NAME, name);
        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    public List<Edition> findAll() {
        return executeSelect(SELECT_ALL);
    }

    @Override
    public List<Edition> findAll(int limit, int offset) {
        return executeSelect(SELECT_ALL_PAGING, limit, offset);
    }

    @Override
    public List<Edition> findAllByUpdateTimeDesc(int limit, int offset) {
        return executeSelect(SELECT_ALL_PAGING_BY_UPDATE_TIME_DESC, limit, offset);
    }

    @Override
    public List<Edition> findLikeNameByUpdateTimeDesc(String name, int limit, int offset) {
        return executeSelect(SELECT_LIKE_NAME_PAGING_BY_UPDATE_TIME_DESC,
                "%" + name + "%", limit, offset);
    }

    @Override
    public List<Edition> findLikeNameByCategoryIdByUpdateTimeDesc(String name, int editionId, int limit, int offset) {
        return executeSelect(SELECT_LIKE_NAME_BY_CATEGORY_ID_PAGING_BY_UPDATE_TIME_DESC,
                "%" + name + "%", editionId, limit, offset);
    }

    @Override
    public int rowsCountLikeName(String name) {
        return executeRowsCount(ROWS_COUNT_LIKE_NAME, "%" + name + "%");
    }

    @Override
    public int rowsCountLikeNameByCategoryId(String name, int categoryId) {
        return executeRowsCount(ROWS_COUNT_LIKE_NAME_BY_CATEGORY_ID,
                "%" + name + "%", categoryId);
    }

    @Override
    public int rowsCount() {
        return executeRowsCount(ROWS_COUNT);
    }

    @Override
    protected Edition transform(ResultSet resultSet) throws SQLException {
        final Edition edition = new Edition();
        edition.setId(resultSet.getInt(ID));
        edition.setName(resultSet.getString(NAME));
        edition.setDescription(resultSet.getString(DESCRIPTION));
        edition.setPrice(resultSet.getFloat(PRICE));
        edition.setEditionsPerMonth(resultSet.getInt(EDITIONS_PER_MONTH));
        edition.setTimeCreated(resultSet.getTimestamp(TIME_CREATED).toLocalDateTime());
        edition.setTimeUpdated(resultSet.getTimestamp(TIME_UPDATED).toLocalDateTime());
        edition.setCategoryId(resultSet.getInt(CATEGORY_ID));
        edition.setDriveId(resultSet.getString(DRIVE_ID));
        return edition;
    }
}
