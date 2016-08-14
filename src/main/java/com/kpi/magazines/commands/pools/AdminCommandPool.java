package com.kpi.magazines.commands.pools;

import com.kpi.magazines.commands.*;
import com.kpi.magazines.config.Commands;

/**
 * Created by Konstantin Minkov on 17.07.2016.
 */
public class AdminCommandPool extends AbstractCommandPool {

    protected AdminCommandPool() {
        addCommand(Commands.MAIN, new MainCommand());
        addCommand(Commands.PROFILE, new AdminPanelCommand(Command.PROTECTED));
        addCommand(Commands.MODIFY_EDITION, new AdminEditionModificationCommand(Command.PROTECTED));
        addCommand(Commands.LOGIN, new LogInCommand());
        addCommand(Commands.LOGOUT, new LogOutCommand(Command.PROTECTED));
        addCommand(Commands.EDITION, new AdminEditionCommand(Command.PROTECTED));
        addCommand(Commands.SEARCH, new SearchCommand());
    }
}
