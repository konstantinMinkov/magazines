package com.kpi.magazines.commands.pools;

import com.kpi.magazines.commands.UserProfileCommand;
import com.kpi.magazines.config.Commands;
import com.kpi.magazines.commands.*;

/**
 * Created by Konstantin Minkov on 17.07.2016.
 */
public class UserCommandPool extends AbstractCommandPool {

    public UserCommandPool() {
        addCommand(Commands.MAIN, new MainCommand());
        addCommand(Commands.LOGIN, new LogInCommand());
        addCommand(Commands.VK_AUTH, new VkAuthCommand());
        addCommand(Commands.SIGN_UP, new SignUpCommand());
        addCommand(Commands.LOGOUT, new LogOutCommand(Command.PROTECTED));
        addCommand(Commands.PROFILE, new UserProfileCommand(Command.PROTECTED));
        addCommand(Commands.EDITION, new EditionCommand());
        addCommand(Commands.CART, new CartCommand(Command.PROTECTED));
        addCommand(Commands.SEARCH, new SearchCommand());
        addCommand(Commands.CONFIRM_REGISTRATION, new ConfirmRegistrationCommand());
    }
}
