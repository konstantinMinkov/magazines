package com.kpi.magazines.commands.pools;

import com.kpi.magazines.beans.User;
import com.kpi.magazines.beans.UserRole;
import com.kpi.magazines.commands.Command;
import com.kpi.magazines.config.Commands;
import com.kpi.magazines.config.Routes;
import com.kpi.magazines.dao.basic.DaoManager;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Konstantin Minkov on 30.06.2016.
 */
public class CommandProvider {

    private static final Map<String, CommandPool> commandPools = new HashMap<>();

    static {
        commandPools.put(Routes.USER, new UserCommandPool());
        commandPools.put(Routes.ADMIN, new AdminCommandPool());
    }

    public static Command getCommand(HttpServletRequest request) {
        final CommandPool commandMap;
        final String commandName;
        commandMap = commandPools.get(request.getAttribute("userURI"));
        commandName = (String) request.getAttribute("command");
        if (commandName == null) {
            return commandMap.getCommand(Commands.MAIN);
        }
        return protection(request, commandName);
//        return commandMap.getCommand(commandName);
    }

    public static Command protection(HttpServletRequest request, String commandName) {
        final User user = (User) request.getSession().getAttribute("user");
        final String userURI = (String) request.getAttribute("userURI");
        final UserRole role;
        final String sessionUserURI;
        if (user == null) {
            final Command command = commandPools.get(userURI).getCommand(commandName);
            if (command.isProtected()) {
                return commandPools.get(userURI).getCommand(Commands.LOGIN);
            }
        } else {
            role = DaoManager.getUserRoleDao().findById(user.getUserRoleId());
            sessionUserURI = "/" + role.getRole();
            if ( !sessionUserURI.equals(request.getAttribute("userURI"))) {
                return commandPools.get(sessionUserURI).getCommand(Commands.MAIN);
            }
        }
        return commandPools.get(userURI).getCommand(commandName);
    }
}
