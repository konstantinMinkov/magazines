package com.kpi.magazines.commands.pools;

import com.kpi.magazines.commands.Command;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Konstantin Minkov on 17.07.2016.
 */
public abstract class AbstractCommandPool implements CommandPool {

    private final Map<String, Command> commands;

    protected AbstractCommandPool() {
        commands = new HashMap<>();
    }

    protected void addCommand(String name, Command command) {
        commands.put(name, command);
    }

    protected Command removeCommand(String name) {
        return commands.remove(name);
    }

    @Override
    public Command getCommand(String name) {
        return commands.get(name);
    }
}
