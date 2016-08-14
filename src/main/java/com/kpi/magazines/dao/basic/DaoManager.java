package com.kpi.magazines.dao.basic;

import com.kpi.magazines.dao.basic.classes.*;
import com.kpi.magazines.dao.basic.interfaces.*;

/**
 * Created by Konstantin Minkov on 27.06.2016.
 *
 * Provider of a concrete DAO-class, depending on the current database.
 */
public class DaoManager {

    public static UserDao getUserDao() {
        return new UserMySqlDao();
    }

    public static UserRoleDao getUserRoleDao() {
        return new UserRoleMySqlDao();
    }

    public static SubscriptionDao getSubscriptionDao() {
        return new SubscriptionMySqlDao();
    }

    public static PaymentDao getPaymentDao() {
        return new PaymentMySqlDao();
    }

    public static IssueDao getIssueDao() {
        return new IssueMySqlDao();
    }

    public static EditionDao getEditionDao() {
        return new EditionMySqlDao();
    }

    public static CategoryDao getCategoryDao() {
        return new CategoryMySqlDao();
    }
}
