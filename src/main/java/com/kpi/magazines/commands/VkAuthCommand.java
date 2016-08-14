package com.kpi.magazines.commands;

import com.kpi.magazines.beans.User;
import com.kpi.magazines.beans.UserRole;
import com.kpi.magazines.config.Page;
import com.kpi.magazines.dao.basic.DaoManager;
import com.kpi.magazines.dao.basic.interfaces.UserDao;
import com.kpi.magazines.utils.http.Http;
import com.google.gson.JsonObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by Konstantin Minkov on 27.07.2016.
 */
public class VkAuthCommand extends AbstractCommand {

    private static final String URL;

    static {
        final ResourceBundle vkConfigs = ResourceBundle.getBundle("vk");
        URL = "https://oauth.vk.com/access_token?"
                + "client_id=" + vkConfigs.getString("clientId")
                + "&redirect_uri=" + vkConfigs.getString("redirectURI")
                + "&client_secret=" + vkConfigs.getString("clientSecret")
                + "&v=" + vkConfigs.getString("v")
                + "&code=";
    }

    public VkAuthCommand() {
        super();
    }

    public VkAuthCommand(boolean isProtected) {
        super(isProtected);
    }

    @Override
    public Page executeGet(HttpServletRequest request, HttpServletResponse response) {
        final String code = request.getParameter("code");
        final JsonObject respParams;
//        final Map<String, Object> reqParams = new LinkedHashMap<>();
        if (code == null || code.length() == 0) {
            return Page.LOGIN;
        }
        respParams = Http.get(URL + code);
        if (respParams != null && respParams.has("access_token")) {
            final String userRequestUrl = "https://api.vk.com/method/users.get?"
                    + "user_ids=" + respParams.get("user_id")
                    + "&name_case=nom";
            final JsonObject usersInfoArray = Http.get(userRequestUrl);
            request.getSession().setAttribute("user",
                    getUser(usersInfoArray.get("response").getAsJsonArray().get(0).getAsJsonObject(), respParams));
            return Page.USER_PROFILE.redirect();
        }
        return Page.LOGIN;
    }

    private User getUser(JsonObject userInfo, JsonObject authInfo) {
        final UserDao userDao = DaoManager.getUserDao();
        final String email = authInfo.get("email").getAsString();
        User user = userDao.findByEmail(email);
        if (user == null) {
            user = createUser(userInfo, authInfo);
        }
        user.setLogin(userInfo.get("first_name").getAsString());
        return user;
    }

    private User createUser(JsonObject userInfo, JsonObject authInfo) {
        final UserDao userDao = DaoManager.getUserDao();
        final User user = new User();
        final String email = authInfo.get("email").getAsString();
        user.setLogin(userInfo.get("uid").getAsString());
        user.setEmail(email);
        user.setPassword(authInfo.get("access_token").getAsString().substring(0, 40));
        user.setUserRoleId(UserRole.USER.getId());
        userDao.create(user);
//        if (userDao.create(user)) {
            return userDao.findByEmail(email);
//        }
//        return null;
    }

    @Override
    public Page executePost(HttpServletRequest request, HttpServletResponse response) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Page executePut(HttpServletRequest request, HttpServletResponse response) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Page executeDelete(HttpServletRequest request, HttpServletResponse response) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }
}
