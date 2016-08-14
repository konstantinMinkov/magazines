package com.kpi.magazines.dao.basic.classes;

import com.kpi.magazines.beans.Subscription;
import com.kpi.magazines.dao.basic.interfaces.SubscriptionDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Konstantin Minkov on 27.06.2016.
 */
public class SubscriptionMySqlDao extends AbstractMySqlDao<Subscription> implements SubscriptionDao {

    private static final String TABLE = "subscription";

    private static final int ID = 1;
    private static final int USER_ID = 2;
    private static final int EDITION_ID = 3;
    private static final int DATE_CREATED = 4;
    private static final int DATE_ENDING = 5;

    private static final String SELECT_BY_ID = generateDynamicSelect(TABLE, "id");
    private static final String SELECT_BY_USER_ID = generateDynamicSelect(TABLE, "user_id");
    private static final String SELECT_BY_EDITION_ID = generateDynamicSelect(TABLE, "edition_id");
    private static final String SELECT_ALL = generateDynamicSelect(TABLE);
    private static final String SELECT_ALL_PAGING = generateDynamicSelectWithPaging(TABLE);
    private static final String INSERT = generateDynamicInsert(TABLE, "user_id", "edition_id", "date_created", "date_ending");
    private static final String UPDATE = generateDynamicUpdate(TABLE, "user_id", "edition_id", "date_created", "date_ending");
    private static final String DELETE = generateDynamicDelete(TABLE, "id");

    @Override
    public boolean update(Subscription obj) {
        return executeUpdate(UPDATE, obj.getUserId(), obj.getEditionId(), obj.getDateCreated(),
                obj.getDateEnding(), obj.getId());
    }

    @Override
    public boolean delete(Subscription obj) {
        return executeUpdate(DELETE, obj.getId());
    }

    @Override
    public boolean create(Subscription obj) {
        return executeUpdate(INSERT, obj.getUserId(), obj.getEditionId(), obj.getDateCreated(),
                obj.getDateEnding());
    }

    @Override
    public Subscription findById(int id) {
        final List<Subscription> users = executeSelect(SELECT_BY_ID, id);
        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    public List<Subscription> findByUserId(int userId) {
        return executeSelect(SELECT_BY_USER_ID, userId);
    }

    @Override
    public List<Subscription> findByEditionId(int editionId) {
        return executeSelect(SELECT_BY_EDITION_ID, editionId);
    }

    @Override
    public List<Subscription> findAll() {
        return executeSelect(SELECT_ALL);
    }

    @Override
    public List<Subscription> findAll(int limit, int offset) {
        return executeSelect(SELECT_ALL_PAGING, limit, offset);
    }

    @Override
    protected Subscription transform(ResultSet resultSet) throws SQLException {
        final Subscription subscription = new Subscription();
        subscription.setId(resultSet.getInt(ID));
        subscription.setUserId(resultSet.getInt(USER_ID));
        subscription.setEditionId(resultSet.getInt(EDITION_ID));
        subscription.setDateCreated(resultSet.getDate(DATE_CREATED).toLocalDate());
        subscription.setDateEnding(resultSet.getDate(DATE_ENDING).toLocalDate());
        return subscription;
    }
}
