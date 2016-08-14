package com.kpi.magazines.dao.basic.classes;

import com.kpi.magazines.beans.Category;
import com.kpi.magazines.dao.basic.interfaces.CategoryDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Konstantin Minkov on 12.07.2016.
 */
public class CategoryMySqlDao extends AbstractMySqlDao<Category> implements CategoryDao {

    private static final String TABLE = "category";

    private static final int ID = 1;
    private static final int NAME = 2;

    private static final String SELECT_BY_ID = generateDynamicSelect(TABLE, "id");
    private static final String SELECT_BY_NAME = generateDynamicSelect(TABLE, "name");
    private static final String SELECT_ALL = generateDynamicSelect(TABLE);
    private static final String SELECT_ALL_PAGING = generateDynamicSelectWithPaging(TABLE);
    private static final String INSERT = generateDynamicInsert(TABLE, "name");
    private static final String UPDATE = generateDynamicUpdate(TABLE, "name");
    private static final String DELETE = generateDynamicDelete(TABLE, "id");

    @Override
    public boolean create(Category obj) {
        return executeUpdate(INSERT, obj.getName());
    }

    @Override
    public boolean update(Category obj) {
        return executeUpdate(UPDATE, obj.getName(), obj.getId());
    }

    @Override
    public boolean delete(Category obj) {
        return executeUpdate(DELETE, obj.getId());
    }

    @Override
    public Category findById(int id) {
        final List<Category> categories = executeSelect(SELECT_BY_ID, id);
        return categories.isEmpty() ? null : categories.get(0);
    }

    @Override
    public Category findByName(String name) {
        final List<Category> categories = executeSelect(SELECT_BY_NAME, name);
        return categories.isEmpty() ? null : categories.get(0);
    }

    @Override
    public List<Category> findAll() {
        return executeSelect(SELECT_ALL);
    }

    @Override
    public List<Category> findAll(int limit, int offset) {
        return executeSelect(SELECT_ALL_PAGING, limit, offset);
    }

    @Override
    protected Category transform(ResultSet resultSet) throws SQLException {
        final Category category = new Category();
        category.setId(resultSet.getInt(ID));
        category.setName(resultSet.getString(NAME));
        return category;
    }
}
