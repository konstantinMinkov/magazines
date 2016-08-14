package com.kpi.magazines.commands;

import com.kpi.magazines.beans.Edition;
import com.kpi.magazines.beans.Payment;
import com.kpi.magazines.beans.Subscription;
import com.kpi.magazines.beans.User;
import com.kpi.magazines.config.Page;
import com.kpi.magazines.dao.basic.DaoManager;
import com.kpi.magazines.dao.basic.interfaces.EditionDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Konstantin Minkov on 26.07.2016.
 */
public class UserProfileCommand extends AbstractCommand {

    public UserProfileCommand() {
        super();
    }

    public UserProfileCommand(boolean isProtected) {
        super(isProtected);
    }

    @Override
    public Page executeGet(HttpServletRequest request, HttpServletResponse response) {
        final User user = (User) request.getSession().getAttribute("user");
        final List<Subscription> subscriptions = DaoManager.getSubscriptionDao().findByUserId(user.getId());
        final EditionDao editionDao = DaoManager.getEditionDao();
        final List<Payment> payments = DaoManager.getPaymentDao().findByUserId(user.getId());
        final Map<Subscription, Edition> subscriptionEditionMap = new HashMap<>();
        for (Subscription subscription : subscriptions) {
            subscriptionEditionMap.put(subscription, editionDao.findById(subscription.getEditionId()));
        }
        request.setAttribute("subscriptionEditionMap", subscriptionEditionMap);
        request.setAttribute("payments", payments);return Page.USER_PROFILE;
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
