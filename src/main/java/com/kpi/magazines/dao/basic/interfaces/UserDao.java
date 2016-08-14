package com.kpi.magazines.dao.basic.interfaces;

import com.kpi.magazines.beans.User;

/**
 * Created by Konstantin Minkov on 26.06.2016.
 */
public interface UserDao extends GenericDao<User> {

    User findByLogin(String login);
    User findByLoginAndPassword(String login, String password);
    User findByEmail(String email);

}
