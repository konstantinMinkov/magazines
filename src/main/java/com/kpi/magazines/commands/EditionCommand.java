package com.kpi.magazines.commands;

import com.kpi.magazines.beans.Edition;
import com.kpi.magazines.config.Page;
import com.kpi.magazines.dao.basic.DaoManager;
import com.kpi.magazines.dao.basic.interfaces.EditionDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Konstantin Minkov on 26.07.2016.
 */
public class EditionCommand extends AbstractCommand {

    public EditionCommand() {
        super();
    }

    public EditionCommand(boolean isProtected) {
        super(isProtected);
    }

    @Override
    public Page executeGet(HttpServletRequest request, HttpServletResponse response) {
        final EditionDao editionDao;
        final Edition edition;
        int id = 0;
        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch (Exception e) {
            return Page.MAIN.redirect();
        }
        editionDao = DaoManager.getEditionDao();
        edition = editionDao.findById(id);
        if (edition == null) {
            return Page.MAIN.redirect();
        }
        request.setAttribute("edition", edition);
        request.setAttribute("category", DaoManager.getCategoryDao().findById(edition.getCategoryId()));
        request.setAttribute("totalIssues", 1);
        return Page.EDITION;
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
