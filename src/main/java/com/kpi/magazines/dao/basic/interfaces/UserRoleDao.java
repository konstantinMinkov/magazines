package com.kpi.magazines.dao.basic.interfaces;

import com.kpi.magazines.beans.UserRole;

/**
 * Created by Konstantin Minkov on 27.06.2016.
 */
public interface UserRoleDao extends GenericDao<UserRole> {

    UserRole findByRole(String role);
}
