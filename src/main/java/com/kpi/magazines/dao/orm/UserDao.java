package com.kpi.magazines.dao.orm;

import java.util.List;

/**
 * Created by Konstantin Minkov on 23.06.2016.
 */
public class UserDao extends AbstractDao<User> {

    static {
        registerDao(User.class);
        registerSelect(User.class, "login");
    }

    @Override
    protected Class<User> getEntityClass() {
        return User.class;
    }

    public User findByLogin(String login) {
        final List<User> users = findByDynamicSelect("login", login);
        return users.isEmpty() ? null : users.get(0);
    }
}
