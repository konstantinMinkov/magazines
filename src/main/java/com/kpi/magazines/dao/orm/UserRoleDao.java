package com.kpi.magazines.dao.orm;

import com.epam.minkov.beans.UserRole;

/**
 * Created by Konstantin Minkov on 24.06.2016.
 */
public class UserRoleDao extends AbstractDao<com.epam.minkov.beans.UserRole> {

    static {
        registerDao(com.epam.minkov.beans.UserRole.class);
    }

    @Override
    protected Class<com.epam.minkov.beans.UserRole> getEntityClass() {
        return UserRole.class;
    }
}
