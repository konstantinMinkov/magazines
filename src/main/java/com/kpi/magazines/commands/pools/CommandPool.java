package com.kpi.magazines.commands.pools;

import com.kpi.magazines.commands.Command;

/**
 * Created by Konstantin Minkov on 17.07.2016.
 */
public interface CommandPool {

    Command getCommand(String name);
}
