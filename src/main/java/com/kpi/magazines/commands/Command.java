package com.kpi.magazines.commands;

import com.kpi.magazines.config.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Konstantin Minkov on 26.07.2016.
 */

/**
 * Basic command interface provides four methods depending on http method.
 */
public interface Command {

    boolean PROTECTED = true;

    /**
     * Shows if user should be logged in for using this command.
     * @return true, if protected.
     */
    boolean isProtected();

    Page executeGet(HttpServletRequest request, HttpServletResponse response);
    Page executePost(HttpServletRequest request, HttpServletResponse response) throws UnsupportedOperationException;
    Page executePut(HttpServletRequest request, HttpServletResponse response) throws UnsupportedOperationException;
    Page executeDelete(HttpServletRequest request, HttpServletResponse response) throws UnsupportedOperationException;
}
