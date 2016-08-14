package com.kpi.magazines.dao.basic.classes;

import com.kpi.magazines.beans.User;
import com.kpi.magazines.dao.basic.interfaces.UserDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Konstantin Minkov on 26.06.2016.
 */
public class UserMySqlDao extends AbstractMySqlDao<User> implements UserDao {

    private static final String TABLE = "user";

    private static final int ID = 1;
    private static final int LOGIN = 2;
    private static final int EMAIL = 3;
    private static final int PASSWORD = 4;
    private static final int USER_ROLE_ID = 5;
    private static final int TIME_CREATED = 6;
    private static final int TIME_UPDATED = 7;

    private static final String SELECT_BY_ID = generateDynamicSelect(TABLE, "id");
    private static final String SELECT_BY_LOGIN = generateDynamicSelect(TABLE, "login");
    private static final String SELECT_BY_LOGIN_AND_PASSWORD = generateDynamicSelect(TABLE, "login", "password");
    private static final String SELECT_BY_EMAIL = generateDynamicSelect(TABLE, "email");
    private static final String SELECT_ALL = generateDynamicSelect(TABLE);
    private static final String SELECT_ALL_PAGING = generateDynamicSelectWithPaging(TABLE);
    private static final String INSERT = generateDynamicInsert(TABLE, "login", "email", "password", "user_role_id");
    private static final String UPDATE = generateDynamicUpdate(TABLE, "login", "email", "password", "user_role_id");
    private static final String DELETE = generateDynamicDelete(TABLE, "id");

    @Override
    public boolean create(User obj) {
        return executeUpdate(INSERT, obj.getLogin(), obj.getEmail(), obj.getPassword(), obj.getUserRoleId());
    }

    @Override
    public boolean update(User obj) {
        return executeUpdate(UPDATE, obj.getLogin(), obj.getEmail(), obj.getPassword(), obj.getUserRoleId(),
                obj.getId());
    }

    @Override
    public boolean delete(User obj) {
        return executeUpdate(DELETE, obj.getId());
    }

    @Override
    public User findById(int id) {
        final List<User> users = executeSelect(SELECT_BY_ID, id);
        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    public User findByLogin(String login) {
        final List<User> users = executeSelect(SELECT_BY_LOGIN, login);
        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    public User findByLoginAndPassword(String login, String password) {
        final List<User> users = executeSelect(SELECT_BY_LOGIN_AND_PASSWORD, login, password);
        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    public User findByEmail(String email) {
        final List<User> users = executeSelect(SELECT_BY_EMAIL, email);
        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    public List<User> findAll() {
        return executeSelect(SELECT_ALL);
    }

    @Override
    public List<User> findAll(int limit, int offset) {
        return executeSelect(SELECT_ALL_PAGING, limit, offset);
    }

    @Override
    protected User transform(ResultSet resultSet) throws SQLException {
        final User user = new User();
        user.setId(resultSet.getInt(ID));
        user.setLogin(resultSet.getString(LOGIN));
        user.setEmail(resultSet.getString(EMAIL));
        user.setPassword(resultSet.getString(PASSWORD));
        user.setUserRoleId(resultSet.getInt(USER_ROLE_ID));
        user.setTimeCreated(resultSet.getTimestamp(TIME_CREATED).toLocalDateTime());
        user.setTimeUpdated(resultSet.getTimestamp(TIME_UPDATED).toLocalDateTime());
        return user;
    }
}
