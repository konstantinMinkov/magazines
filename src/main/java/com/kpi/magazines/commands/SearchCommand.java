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

    private static final int EDITIONS_PER_PAGE = 8;

    public SearchCommand() {
        super();
    }

    public SearchCommand(boolean isProtected) {
        super(isProtected);
    }

    @Override
    public Page executeGet(HttpServletRequest request, HttpServletResponse response) {
        final EditionDao editionDao = DaoManager.getEditionDao();
        final int EDITIONS_COUNT = editionDao.rowsCount();
        final int CURRENT_PAGE = 1;
        final int OFFSET = 0;
        Editions.setPage(request, EDITIONS_COUNT, EDITIONS_PER_PAGE, CURRENT_PAGE,
                () -> editionDao.findAllByUpdateTimeDesc(EDITIONS_PER_PAGE, OFFSET));
        return Page.SEARCH;
    }

    @Override
    public Page executePost(HttpServletRequest request, HttpServletResponse response) throws UnsupportedOperationException {
        final EditionDao editionDao = DaoManager.getEditionDao();
        final int EDITIONS_COUNT = editionDao.rowsCount();
        final List<Category> categories = DaoManager.getCategoryDao().findAll();
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
        request.setAttribute("editionsCount", EDITIONS_COUNT);
        request.setAttribute("categories", categories);
        if (magazine == null || magazine.length() == 0) {
            Editions.setPage(request, EDITIONS_PER_PAGE);
        } else {
            category = getCategory(request);
            request.setAttribute("magazine", magazine);
            if (category != null) {
                request.setAttribute("category", category);
                Editions.setPage(request,
                        editionDao.rowsCountLikeNameByCategoryId(magazine, category.getId()),
                        EDITIONS_PER_PAGE,
                        currentPage,
                        () -> editionDao.findLikeNameByCategoryIdByUpdateTimeDesc(magazine, category.getId(), EDITIONS_PER_PAGE,
                                EDITIONS_PER_PAGE * (currentPage - 1))
                );
            } else {
                Editions.setPage(request,
                        editionDao.rowsCountLikeName(magazine),
                        EDITIONS_PER_PAGE,
                        currentPage,
                        () -> editionDao.findLikeNameByUpdateTimeDesc(magazine, EDITIONS_PER_PAGE,
                                EDITIONS_PER_PAGE * (currentPage - 1))
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
