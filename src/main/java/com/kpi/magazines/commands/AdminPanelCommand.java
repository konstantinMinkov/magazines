package com.kpi.magazines.commands;

import com.kpi.magazines.beans.Edition;
import com.kpi.magazines.beans.Payment;
import com.kpi.magazines.beans.User;
import com.kpi.magazines.config.Page;
import com.kpi.magazines.dao.basic.DaoManager;
import com.kpi.magazines.dao.basic.interfaces.EditionDao;
import com.kpi.magazines.utils.validation.errors.Errors;
import com.kpi.magazines.utils.validation.errors.ValidationErrors;
import lombok.extern.log4j.Log4j2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.ErrorData;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Konstantin Minkov on 26.07.2016.
 */

@Log4j2
public class AdminPanelCommand extends AbstractCommand {

    public AdminPanelCommand() {
        super();
    }

    public AdminPanelCommand(boolean isProtected) {
        super(isProtected);
    }

    @Override
    public Page executeGet(HttpServletRequest request, HttpServletResponse response) {
        final int paymentsPerPage = 10;
        final List<Payment> payments;
        final int paymentsCount = DaoManager.getPaymentDao().rowsCount();
        final Map<Payment, User> paymentUserMap;
        int currentPage;
        try {
            currentPage = Integer.parseInt(request.getParameter("page"));
        } catch (Exception e) {
            currentPage = 1;
        }
        if (currentPage * paymentsPerPage < paymentsCount) {
            request.setAttribute("nextPage", currentPage + 1);
        }
        if (currentPage > 1) {
            request.setAttribute("previousPage", currentPage - 1);
        }
        payments = DaoManager.getPaymentDao().findAllByCreateTime(paymentsPerPage, paymentsPerPage * (currentPage - 1));
        paymentUserMap = new HashMap<>();
        for (Payment payment : payments) {
            paymentUserMap.put(payment, DaoManager.getUserDao().findById(payment.getUserId()));
        }
        request.setAttribute("paymentUserMap", paymentUserMap);
        return Page.ADMIN_PANEL;
    }

    @Override
    public Page executePost(HttpServletRequest request, HttpServletResponse response) throws UnsupportedOperationException {
        final String fromDateString = request.getParameter("from");
        final String toDateString = request.getParameter("to");
        final Errors errors = new ValidationErrors();
        final LocalDateTime fromDate;
        final LocalDateTime toDate;
        final List<Payment> payments;
        final Map<Payment, User> paymentUserMap;
        if (fromDateString == null || toDateString == null
                || fromDateString.length() == 0 || toDateString.length() == 0) {
            errors.setError("general", "Please, select dates.");
            request.setAttribute("errors", errors);
            return Page.ADMIN_PANEL;
        }
        try {
            fromDate = LocalDateTime.parse(fromDateString);
            toDate = LocalDateTime.parse(toDateString);
            if (fromDate.isAfter(toDate)
                    || fromDate.isAfter(LocalDateTime.now())
                    || toDate.isAfter(LocalDateTime.now())) {
                errors.setError("general", "Invalid dates.");
                request.setAttribute("errors", errors);
                return Page.ADMIN_PANEL;
            }
        } catch (Exception e) {
            log.catching(e);
            errors.setError("general", "Invalid dates, cannot parse.");
            request.setAttribute("errors", errors);
            return Page.ADMIN_PANEL;
        }
        payments = DaoManager.getPaymentDao()
                .findAll()
                .stream()
                .filter(payment -> {
                            final LocalDateTime timeUpdated = payment.getTimeCreated();
                            return timeUpdated.isAfter(fromDate) && timeUpdated.isBefore(toDate);
                        }).collect(Collectors.toList());
//        request.setAttribute();
        paymentUserMap = new HashMap<>();
        for (Payment payment : payments) {
            paymentUserMap.put(payment, DaoManager.getUserDao().findById(payment.getUserId()));
        }
        request.setAttribute("paymentUserMap", paymentUserMap);
        return Page.ADMIN_PANEL;
//        throw new UnsupportedOperationException();
    }

    @Override
    public Page executePut(HttpServletRequest request, HttpServletResponse response) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Page executeDelete(HttpServletRequest request, HttpServletResponse response) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }
}
