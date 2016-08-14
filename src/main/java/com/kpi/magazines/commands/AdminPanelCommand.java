package com.kpi.magazines.commands;

import com.kpi.magazines.beans.Payment;
import com.kpi.magazines.beans.User;
import com.kpi.magazines.config.Page;
import com.kpi.magazines.dao.basic.DaoManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Konstantin Minkov on 26.07.2016.
 */
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
        throw new UnsupportedOperationException();
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
