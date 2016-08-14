package com.kpi.magazines.config;

import com.kpi.magazines.beans.UserRole;

/**
 * Created by Konstantin Minkov on 04.07.2016.
 *
 * Class, that provides all routes to JSP pages and relative URLs.
 */
public class Routes {

    private static final String PARTIALS_FOLDER = "/WEB-INF/jsp/partials/";
    public static final String USER = "/" + UserRole.USER.getRole();
    public static final String ADMIN = "/" + UserRole.ADMIN.getRole();
    public static final String HOLDER_PAGE = "/WEB-INF/jsp/holder.jsp";
    public static final String MAIN_PAGE = PARTIALS_FOLDER + "main.jsp";
    public static final String LOGIN_PAGE = PARTIALS_FOLDER + "login.jsp";
    public static final String SIGN_UP_PAGE = PARTIALS_FOLDER + "signup.jsp";
    public static final String USER_PROFILE_PAGE = PARTIALS_FOLDER + "profile.jsp";
    public static final String EDITION_PAGE = PARTIALS_FOLDER + "edition.jsp";
    public static final String CART_PAGE = PARTIALS_FOLDER + "cart.jsp";
    public static final String ADMIN_PANEL_PAGE = PARTIALS_FOLDER + "admin-panel.jsp";
    public static final String ADMIN_EDITION_MODIFICATION_PAGE = PARTIALS_FOLDER + "admin-edition-modify.jsp";
    public static final String ADMIN_EDITION_PAGE = PARTIALS_FOLDER + "admin-edition.jsp";
    public static final String SEARCH = PARTIALS_FOLDER + "search.jsp";
    public static final String REGISTRATION_CONFIRMATION = PARTIALS_FOLDER + "registration-confirmation.jsp";
}
