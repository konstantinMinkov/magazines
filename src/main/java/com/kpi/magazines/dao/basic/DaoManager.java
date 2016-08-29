package com.kpi.magazines.dao.basic;

import com.kpi.magazines.dao.basic.classes.*;
import com.kpi.magazines.dao.basic.interfaces.*;

/**
 * Created by Konstantin Minkov on 27.06.2016.
 *
 * Provider of a concrete DAO-class, depending on the current database.
 */
public class DaoManager {

    private static final UserDao USER_DAO = new UserMySqlDao();
    private static final UserRoleDao USER_ROLE_DAO = new UserRoleMySqlDao();
    private static final SubscriptionDao SUBSCRIPTION_DAO = new SubscriptionMySqlDao();
    private static final PaymentDao PAYMENT_DAO = new PaymentMySqlDao();
    private static final IssueDao ISSUE_DAO = new IssueMySqlDao();
    private static final EditionDao EDITION_DAO = new EditionMySqlDao();
    private static final CategoryDao CATEGORY_DAO = new CategoryMySqlDao();

    public static UserDao getUserDao() {
        return USER_DAO;
    }

    public static UserRoleDao getUserRoleDao() {
        return USER_ROLE_DAO;
    }

    public static SubscriptionDao getSubscriptionDao() {
        return SUBSCRIPTION_DAO;
    }

    public static PaymentDao getPaymentDao() {
        return PAYMENT_DAO;
    }

    public static IssueDao getIssueDao() {
        return ISSUE_DAO;
    }

    public static EditionDao getEditionDao() {
        return EDITION_DAO;
    }

    public static CategoryDao getCategoryDao() {
        return CATEGORY_DAO;
    }
}
