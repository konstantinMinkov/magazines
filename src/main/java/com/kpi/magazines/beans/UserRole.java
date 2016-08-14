package com.kpi.magazines.beans;

import com.kpi.magazines.dao.basic.DaoManager;
import lombok.*;

/**
 * Created by Konstantin Minkov on 22.06.2016.
 */

@Data
public final class UserRole {

    private int id;
    private String role;

    public static final UserRole ADMIN = DaoManager.getUserRoleDao().findByRole("admin");
    public static final UserRole USER = DaoManager.getUserRoleDao().findByRole("user");

//    private static final LinkedList<UserRole> priorityList = new LinkedList<>();
//
//    static {
//        priorityList.add(UserRole.USER);
//        priorityList.add(UserRole.ADMIN);
//    }
//
//    public boolean sameOrHigher(UserRole role) {
//        if ( !priorityList.contains(role)) {
//            return false;
//        }
//        for (int i = priorityList.indexOf(role); i < priorityList.size(); i++) {
//            if (priorityList.get(i).equals(this)) {
//                return true;
//            }
//        }
//        return false;
//    }
}
