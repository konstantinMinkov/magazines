package com.kpi.magazines.commands;

/**
 * Created by Konstantin Minkov on 29.07.2016.
 */
public abstract class AbstractCommand implements Command {

    private boolean isProtected;

    public AbstractCommand() {
        this(false);
    }

    public AbstractCommand(boolean isProtected) {
        this.isProtected = isProtected;
    }

    @Override
    public boolean isProtected() {
        return isProtected;
    }
}
