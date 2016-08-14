package com.kpi.magazines.commands;

import com.kpi.magazines.beans.Edition;
import com.kpi.magazines.config.Page;
import com.kpi.magazines.dao.basic.DaoManager;
import com.kpi.magazines.dao.basic.interfaces.EditionDao;
import lombok.extern.log4j.Log4j2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Konstantin Minkov on 26.07.2016.
 */

@Log4j2
public class AdminEditionCommand extends AbstractCommand {

    public AdminEditionCommand() {
        super();
    }

    public AdminEditionCommand(boolean isProtected) {
        super(isProtected);
    }

    @Override
    public Page executeGet(HttpServletRequest request, HttpServletResponse response) {
        final EditionDao editionDao;
        final Edition edition;
        int id;
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
        return Page.ADMIN_EDITION;
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
