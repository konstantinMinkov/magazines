package com.kpi.magazines.dao.orm;

import com.kpi.magazines.beans.UserRole;

/**
 * Created by Konstantin Minkov on 24.06.2016.
 */
public class UserRoleDao extends AbstractDao<UserRole> {

    static {
        registerDao(UserRole.class);
    }

    @Override
    protected Class<UserRole> getEntityClass() {
        return UserRole.class;
    }
}
