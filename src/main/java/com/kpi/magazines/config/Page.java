package com.kpi.magazines.config;

/**
 * Created by Konstantin Minkov on 07.07.2016.
 */

/**
 * Class that provides constants of pages. Each page has default command, that is used for
 * redirects, and route to jsp page.
 */
public class Page {

    public static final Page MAIN = new Page(Commands.MAIN, Routes.MAIN_PAGE);
    public static final Page LOGIN = new Page(Commands.LOGIN, Routes.LOGIN_PAGE);
    public static final Page SIGN_UP = new Page(Commands.SIGN_UP, Routes.SIGN_UP_PAGE);
    public static final Page USER_PROFILE = new Page(Commands.PROFILE, Routes.USER_PROFILE_PAGE);
    public static final Page ADMIN_PANEL = new Page(Commands.PROFILE, Routes.ADMIN_PANEL_PAGE);
    public static final Page EDITION = new Page(Commands.EDITION, Routes.EDITION_PAGE);
    public static final Page CART = new Page(Commands.CART, Routes.CART_PAGE);
    public static final Page MODIFY_EDITION = new Page(Commands.MODIFY_EDITION, Routes.ADMIN_EDITION_MODIFICATION_PAGE);
    public static final Page ADMIN_EDITION = new Page(Commands.ADMIN_EDITION, Routes.ADMIN_EDITION_PAGE);
    public static final Page SEARCH = new Page(Commands.SEARCH, Routes.SEARCH);
    public static final Page CONFIRM_REGISTRATION = new Page(Commands.CONFIRM_REGISTRATION, Routes.REGISTRATION_CONFIRMATION);

    private String defaultCommand;
    private String pageRoute;
    private boolean isRedirect;

    private Page(String defaultCommand, String pageRoute) {
        this.defaultCommand = defaultCommand;
        this.pageRoute = pageRoute;
    }

    public Page redirect() {
        final Page route = new Page(defaultCommand, pageRoute);
        route.isRedirect = true;
        return route;
    }

    public String getCommand() {
        return defaultCommand;
    }

    public String getPageRoute() {
        return pageRoute;
    }

    public boolean isRedirect() {
        return isRedirect;
    }
}
