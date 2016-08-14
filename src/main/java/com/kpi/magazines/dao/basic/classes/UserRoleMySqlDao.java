package com.kpi.magazines.dao.basic.classes;

import com.kpi.magazines.dao.basic.interfaces.UserRoleDao;
import com.kpi.magazines.beans.UserRole;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Konstantin Minkov on 27.06.2016.
 */
public class UserRoleMySqlDao extends AbstractMySqlDao<UserRole> implements UserRoleDao {

    private static final String TABLE = "user_role";

    private static final int ID = 1;
    private static final int ROLE = 2;

    private static final String SELECT_BY_ID = generateDynamicSelect(TABLE, "id");
    private static final String SELECT_BY_ROLE = generateDynamicSelect(TABLE, "role");
    private static final String SELECT_ALL = generateDynamicSelect(TABLE);
    private static final String SELECT_ALL_PAGING = generateDynamicSelectWithPaging(TABLE);
    private static final String INSERT = generateDynamicInsert(TABLE, "role");
    private static final String UPDATE = generateDynamicUpdate(TABLE, "role");
    private static final String DELETE = generateDynamicDelete(TABLE, "id");

    @Override
    public boolean update(UserRole obj) {
        return executeUpdate(UPDATE, obj.getRole(), obj.getId());
    }

    @Override
    public boolean delete(UserRole obj) {
        return executeUpdate(DELETE, obj.getId());
    }

    @Override
    public boolean create(UserRole obj) {
        return executeUpdate(INSERT, obj.getRole());
    }

    @Override
    public UserRole findById(int id) {
        final List<UserRole> roles = executeSelect(SELECT_BY_ID, id);
        return roles.isEmpty() ? null : roles.get(0);
    }

    @Override
    public UserRole findByRole(String role) {
        final List<UserRole> roles = executeSelect(SELECT_BY_ROLE, role);
        return roles.isEmpty() ? null : roles.get(0);
    }

    @Override
    public List<UserRole> findAll() {
        return executeSelect(SELECT_ALL);
    }

    @Override
    public List<UserRole> findAll(int limit, int offset) {
        return executeSelect(SELECT_ALL_PAGING, limit, offset);
    }

    @Override
    protected UserRole transform(ResultSet resultSet) throws SQLException {
        final UserRole userRole = new UserRole();
        userRole.setId(resultSet.getInt(ID));
        userRole.setRole(resultSet.getString(ROLE));
        return userRole;
    }
}
