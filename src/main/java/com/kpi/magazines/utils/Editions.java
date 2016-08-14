package com.kpi.magazines.utils;

import com.kpi.magazines.beans.Edition;
import com.kpi.magazines.dao.basic.DaoManager;
import com.kpi.magazines.dao.basic.interfaces.EditionDao;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.function.Supplier;

/**
 * Created by Konstantin Minkov on 11.07.2016.
 *
 * Logic class for working with Edition paging.
 */
public class Editions {

    public static void setPage(HttpServletRequest request, int editionsPerPage) {
        final EditionDao editionDao = DaoManager.getEditionDao();
        final int editionsCount = editionDao.rowsCount();
        int page;
        final int currentPage;
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch (Exception e) {
            page = 1;
        }
        currentPage = page;
        setPage(request, editionsCount, editionsPerPage, currentPage, () ->
            editionDao.findAllByUpdateTimeDesc(editionsPerPage, editionsPerPage * (currentPage - 1))
        );
    }

    public static void setPage(HttpServletRequest request, int editionsCount, int editionsPerPage, int currentPage,
                        Supplier<List<Edition>> editionsSupplier) {
        final List<Edition> editions = editionsSupplier.get();
        if (currentPage * editionsPerPage < editionsCount) {
            request.setAttribute("nextPage", currentPage + 1);
        }
        if (currentPage > 1) {
            request.setAttribute("previousPage", currentPage - 1);
        }
        request.setAttribute("editions", editions);
    }

}
