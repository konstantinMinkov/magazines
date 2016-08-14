package com.kpi.magazines.commands;

import com.kpi.magazines.beans.Edition;
import com.kpi.magazines.beans.Payment;
import com.kpi.magazines.beans.Subscription;
import com.kpi.magazines.beans.User;
import com.kpi.magazines.config.Page;
import com.kpi.magazines.dao.basic.DaoManager;
import com.kpi.magazines.dao.basic.interfaces.EditionDao;
import com.kpi.magazines.dao.basic.interfaces.PaymentDao;
import com.kpi.magazines.dao.basic.interfaces.SubscriptionDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Konstantin Minkov on 26.07.2016.
 */
public class CartCommand extends AbstractCommand {

    public CartCommand() {
        super();
    }

    public CartCommand(boolean isProtected) {
        super(isProtected);
    }

    @Override
    public Page executeGet(HttpServletRequest request, HttpServletResponse response) {
        return Page.CART;
    }

    @Override
    public Page executePost(HttpServletRequest request, HttpServletResponse response) {
        final User user = (User) request.getSession().getAttribute("user");
        final Map<Edition, Integer> cart = (Map) request.getSession().getAttribute("cart");
        if (cart == null) {
            return Page.MAIN.redirect();
        }
        registerPayment(cart, user);
        updateSubscriptions(cart, user);
        request.getSession().removeAttribute("cart");
        return Page.USER_PROFILE.redirect();
    }

    @Override
    public Page executePut(HttpServletRequest request, HttpServletResponse response) {
        final HttpSession session = request.getSession();
        final Map<Edition, Integer> cart;
        Edition edition;
        int issues;
        int editionId;
        if (session.getAttribute("user") == null) {
            return Page.LOGIN;
        }
        try {
            editionId = Integer.parseInt(request.getParameter("editionId"));
            edition = DaoManager.getEditionDao().findById(editionId);
            if (edition == null) {
                return Page.MAIN.redirect();
            }
            issues = Integer.parseInt(request.getParameter("issues"));
            if (1 > issues || issues > 12) {
                return Page.EDITION.redirect();
            }
        } catch (Exception e) {
            return Page.MAIN.redirect();
        }
        if (session.getAttribute("cart") == null) {
            cart = new HashMap<>();
            session.setAttribute("cart", cart);
        } else {
            cart = (Map) session.getAttribute("cart");
        }
        cart.put(edition, issues);
        request.setAttribute("edition", edition);
        request.setAttribute("totalIssues", issues);
        return Page.EDITION;
    }

    @Override
    public Page executeDelete(HttpServletRequest request, HttpServletResponse response) {
        final EditionDao editionDao;
        final Edition edition;
        final Map<Edition, Integer> cart;
        int id = 0;
        try {
            id = Integer.parseInt(request.getParameter("editionId"));
        } catch (Exception e) {
            return Page.MAIN.redirect();
        }
        editionDao = DaoManager.getEditionDao();
        edition = editionDao.findById(id);
        if (edition == null) {
            return Page.MAIN.redirect();
        }
        cart = (Map<Edition, Integer>) request.getSession().getAttribute("cart");
        if (cart == null) {
            return Page.MAIN.redirect();
        }
        cart.remove(edition);
        return Page.CART.redirect();
    }

    private boolean registerPayment(Map<Edition, Integer> cart, User user) {
        final PaymentDao paymentDao = DaoManager.getPaymentDao();
        final Payment payment = new Payment();
        float totalPrice = 0;
        for (Map.Entry<Edition, Integer> entry : cart.entrySet()) {
            totalPrice += entry.getKey().getPrice() * entry.getValue();
        }
        payment.setUserId(user.getId());
        payment.setPrice(totalPrice);
        return paymentDao.create(payment);
    }

    private boolean updateSubscriptions(Map<Edition, Integer> cart, User user) {
        final SubscriptionDao subscriptionDao = DaoManager.getSubscriptionDao();
        final List<Subscription> subscriptions = subscriptionDao.findByUserId(user.getId());
        Subscription subscription;
        for (Map.Entry<Edition, Integer> entry : cart.entrySet()) {
            subscription = checkSubscriptionExists(subscriptions, entry.getKey());
            if (subscription == null) {
                subscription = new Subscription();
                subscription.setUserId(user.getId());
                subscription.setEditionId(entry.getKey().getId());
                subscription.setDateCreated(LocalDate.now());
                //subscription.setDateEnding(LocalDate.now().plusMonths(entry.getValue()));
                subscription.setDateEnding(LocalDate.now().plusWeeks(4 / entry.getKey().getEditionsPerMonth() * entry.getValue()));
                subscriptionDao.create(subscription);
            } else {
                subscription.setDateEnding(LocalDate.now().plusWeeks(4 / entry.getKey().getEditionsPerMonth() * entry.getValue()));
                subscriptionDao.update(subscription);
            }
        }
        return true;
    }

    private Subscription checkSubscriptionExists(List<Subscription> subscriptions, Edition edition) {
        for (Subscription subscription : subscriptions) {
            if (edition.getId() == subscription.getEditionId()) {
                return subscription;
            }
        };
        return null;
    }
}
