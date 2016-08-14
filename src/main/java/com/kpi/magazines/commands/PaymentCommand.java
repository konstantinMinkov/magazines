package com.kpi.magazines.commands;

import com.kpi.magazines.beans.Edition;
import com.kpi.magazines.beans.Payment;
import com.kpi.magazines.beans.Subscription;
import com.kpi.magazines.beans.User;
import com.kpi.magazines.config.Page;
import com.kpi.magazines.dao.basic.DaoManager;
import com.kpi.magazines.dao.basic.interfaces.PaymentDao;
import com.kpi.magazines.dao.basic.interfaces.SubscriptionDao;
import com.kpi.magazines.utils.validation.validators.Validator;
import com.kpi.magazines.utils.validation.validators.ValidatorProvider;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Created by Konstantin Minkov on 14.08.2016.
 */
public class PaymentCommand extends AbstractCommand {

    public PaymentCommand() {
        super();
    }

    public PaymentCommand(boolean isProtected) {
        super(isProtected);
    }

    @Override
    public Page executeGet(HttpServletRequest request, HttpServletResponse response) {
        if (request.getSession().getAttribute("cart") == null) {
            return Page.MAIN.redirect();
        }
        return Page.PAYMENT;
    }

    @Override
    public Page executePost(HttpServletRequest request, HttpServletResponse response) throws UnsupportedOperationException {
        final Validator validator = ValidatorProvider.getCardValidator((String) request.getAttribute("locale"));
        final User user = (User) request.getSession().getAttribute("user");
        final Map<Edition, Integer> cart = (Map) request.getSession().getAttribute("cart");
        if (cart == null) {
            return Page.MAIN.redirect();
        }
        if ( !validator.isValid(request)) {
            request.setAttribute("errors", validator.getErrors());
            return Page.PAYMENT;
        }
        registerPayment(cart, user);
        updateSubscriptions(cart, user);
        request.getSession().removeAttribute("cart");
        return Page.USER_PROFILE.redirect();
    }

    @Override
    public Page executePut(HttpServletRequest request, HttpServletResponse response) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Page executeDelete(HttpServletRequest request, HttpServletResponse response) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
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
