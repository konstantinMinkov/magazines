package com.kpi.magazines.dao.orm;

/**
 * Created by Konstantin Minkov on 23.06.2016.
 */
public class Runner {

    public static void main(String[] args) {
        final UserDao userDao = new UserDao();
        System.out.println(userDao.findByLogin("root"));
        final UserRoleDao userRoleDao = new UserRoleDao();
        System.out.println(userRoleDao.findById(1));
//        final User user = new User();
//        user.setLogin("login");
//        user.setPassword("password");
//        user.setUserRoleId(1);
//        userDao.create(user);
//        userDao.delete(user);
    }
}
