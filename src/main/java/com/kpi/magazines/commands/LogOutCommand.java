package com.kpi.magazines.commands;

import com.kpi.magazines.config.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Konstantin Minkov on 26.07.2016.
 */
public class LogOutCommand extends AbstractCommand {

    public LogOutCommand() {
        super();
    }

    public LogOutCommand(boolean isProtected) {
        super(isProtected);
    }

    @Override
    public Page executeGet(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().invalidate();
        return Page.MAIN.redirect();
    }

    @Override
    public Page executePost(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Page executePut(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Page executeDelete(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException();
    }
}
