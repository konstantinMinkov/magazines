package com.kpi.magazines.dao.basic.classes;

import com.kpi.magazines.beans.Payment;
import com.kpi.magazines.dao.basic.interfaces.PaymentDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Konstantin Minkov on 27.06.2016.
 */
public class PaymentMySqlDao extends AbstractMySqlDao<Payment> implements PaymentDao {

    private static final String TABLE = "payment";

    private static final int ID = 1;
    private static final int USER_ID = 2;
    private static final int PRICE = 3;
    private static final int TIME_CREATED = 4;

    private static final String SELECT_BY_ID = generateDynamicSelect(TABLE, "id");
    private static final String SELECT_BY_USER_ID = generateDynamicSelect(TABLE, "user_id");
    private static final String SELECT_ALL = generateDynamicSelect(TABLE);
    private static final String SELECT_ALL_PAGING = generateDynamicSelectWithPaging(TABLE);
    private static final String SELECT_ALL_PAGING_BY_CREATE_TIME_DESC =
            generateDynamicSelectWithPagingOrderByDesc(TABLE, "time_created");
    private static final String INSERT = generateDynamicInsert(TABLE, "user_id", "price");
    private static final String UPDATE = generateDynamicUpdate(TABLE, "user_id", "price");
    private static final String DELETE = generateDynamicDelete(TABLE, "id");
    private static final String ROWS_COUNT = generateDynamicRowsCount(TABLE);

    @Override
    public boolean update(Payment obj) {
        return executeUpdate(UPDATE, obj.getUserId(), obj.getPrice(), obj.getId());
    }

    @Override
    public boolean delete(Payment obj) {
        return executeUpdate(DELETE, obj.getId());
    }

    @Override
    public boolean create(Payment obj) {
        return executeUpdate(INSERT, obj.getUserId(), obj.getPrice());
    }

    @Override
    public Payment findById(int id) {
        final List<Payment> users = executeSelect(SELECT_BY_ID, id);
        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    public List<Payment> findByUserId(int userId) {
        return executeSelect(SELECT_BY_USER_ID, userId);
    }

    @Override
    public List<Payment> findAll() {
        return executeSelect(SELECT_ALL);
    }

    @Override
    public List<Payment> findAll(int limit, int offset) {
        return executeSelect(SELECT_ALL_PAGING, limit, offset);
    }

    @Override
    public List<Payment> findAllByCreateTime(int limit, int offset) {
        return executeSelect(SELECT_ALL_PAGING_BY_CREATE_TIME_DESC, limit, offset);
    }

    @Override
    public int rowsCount() {
        return executeRowsCount(ROWS_COUNT);
    }

    @Override
    protected Payment transform(ResultSet resultSet) throws SQLException {
        final Payment payment = new Payment();
        payment.setId(resultSet.getInt(ID));
        payment.setUserId(resultSet.getInt(USER_ID));
        payment.setPrice(resultSet.getFloat(PRICE));
        payment.setTimeCreated(resultSet.getTimestamp(TIME_CREATED).toLocalDateTime());
        return payment;
    }
}
