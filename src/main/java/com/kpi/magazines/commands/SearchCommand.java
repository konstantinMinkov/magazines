package com.kpi.magazines.commands;

import com.kpi.magazines.beans.Category;
import com.kpi.magazines.config.Page;
import com.kpi.magazines.dao.basic.DaoManager;
import com.kpi.magazines.dao.basic.interfaces.EditionDao;
import com.kpi.magazines.utils.Editions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Konstantin Minkov on 26.07.2016.
 */
public class SearchCommand extends AbstractCommand {

    public SearchCommand() {
        super();
    }

    public SearchCommand(boolean isProtected) {
        super(isProtected);
    }

    @Override
    public Page executeGet(HttpServletRequest request, HttpServletResponse response) {
        //// TODO: 26.07.2016 add content
        return Page.SEARCH;
    }

    @Override
    public Page executePost(HttpServletRequest request, HttpServletResponse response) throws UnsupportedOperationException {
        final int editionsPerPage = 8;
        final int editionsCount = DaoManager.getEditionDao().rowsCount();
        final EditionDao editionDao = DaoManager.getEditionDao();
        final List<Category> categories = DaoManager.getCategoryDao().findAll();
        final Editions editions = new Editions();
        final Category category;
        final String magazine = request.getParameter("magazine");
        final int currentPage;
        int page;
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch (Exception e) {
            page = 1;
        }
        currentPage = page;
        request.setAttribute("editionsCount", editionsCount);
        request.setAttribute("categories", categories);
        if (magazine == null || magazine.length() == 0) {
            editions.setPage(request, editionsPerPage);
        } else {
            category = getCategory(request);
            request.setAttribute("magazine", magazine);
            if (category != null) {
                request.setAttribute("category", category);
                editions.setPage(request,
                        editionDao.rowsCountLikeNameByCategoryId(magazine, category.getId()),
                        editionsPerPage,
                        currentPage,
                        () -> editionDao.findLikeNameByCategoryIdByUpdateTimeDesc(magazine, category.getId(), editionsPerPage,
                                editionsPerPage * (currentPage - 1))
                );
            } else {
                editions.setPage(request,
                        editionDao.rowsCountLikeName(magazine),
                        editionsPerPage,
                        currentPage,
                        () -> editionDao.findLikeNameByUpdateTimeDesc(magazine, editionsPerPage,
                                editionsPerPage * (currentPage - 1))
                );
            }
        }
        return Page.SEARCH;
    }

    @Override
    public Page executePut(HttpServletRequest request, HttpServletResponse response) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Page executeDelete(HttpServletRequest request, HttpServletResponse response) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    private Category getCategory(HttpServletRequest request) {
        final String category = request.getParameter("category");
        if (category != null && category.length() > 0) {
            return DaoManager.getCategoryDao().findByName(category);
        }
        return null;
    }
}
