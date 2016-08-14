package com.kpi.magazines.dao.basic.classes;

import com.kpi.magazines.beans.Issue;
import com.kpi.magazines.dao.basic.interfaces.IssueDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Konstantin Minkov on 27.06.2016.
 */
public class IssueMySqlDao extends AbstractMySqlDao<Issue> implements IssueDao {

    private static final String TABLE = "issue";

    private static final int ID = 1;
    private static final int EDITION_ID = 2;
    private static final int RELEASE_DATE = 3;
    private static final int NUMBER = 4;

    private static final String SELECT_BY_ID = generateDynamicSelect(TABLE, "id");
    private static final String SELECT_BY_EDITION_ID = generateDynamicSelect(TABLE, "edition_id");
    private static final String SELECT_ALL = generateDynamicSelect(TABLE);
    private static final String SELECT_ALL_PAGING = generateDynamicSelectWithPaging(TABLE);
    private static final String INSERT = generateDynamicInsert(TABLE, "edition_id", "release_date", "number");
    private static final String UPDATE = generateDynamicUpdate(TABLE, "edition_id", "release_date", "number");
    private static final String DELETE = generateDynamicDelete(TABLE, "id");

    @Override
    public boolean update(Issue obj) {
        return executeUpdate(UPDATE, obj.getEditionId(), obj.getReleaseDate(), obj.getNumber(),
                obj.getId());
    }

    @Override
    public boolean delete(Issue obj) {
        return executeUpdate(DELETE, obj.getId());
    }

    @Override
    public boolean create(Issue obj) {
        return executeUpdate(INSERT, obj.getEditionId(), obj.getReleaseDate(), obj.getNumber(),
                obj.getId());
    }

    @Override
    public Issue findById(int id) {
        final List<Issue> users = executeSelect(SELECT_BY_ID, id);
        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    public Issue findByEditionId(int editionId) {
        final List<Issue> users = executeSelect(SELECT_BY_EDITION_ID, editionId);
        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    public List<Issue> findAll() {
        return executeSelect(SELECT_ALL);
    }

    @Override
    public List<Issue> findAll(int limit, int offset) {
        return executeSelect(SELECT_ALL_PAGING, limit, offset);
    }

    @Override
    protected Issue transform(ResultSet resultSet) throws SQLException {
        final Issue issue = new Issue();
        issue.setId(resultSet.getInt(ID));
        issue.setEditionId(resultSet.getInt(EDITION_ID));
        issue.setNumber(resultSet.getInt(NUMBER));
        issue.setReleaseDate(resultSet.getDate(RELEASE_DATE).toLocalDate());
        return issue;
    }
}
