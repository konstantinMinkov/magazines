package com.kpi.magazines.dao.basic.interfaces;

import com.kpi.magazines.beans.Payment;

import java.util.List;

/**
 * Created by Konstantin Minkov on 27.06.2016.
 */
public interface PaymentDao extends GenericDao<Payment> {

    List<Payment> findByUserId(int userId);
    List<Payment> findAllByCreateTime(int limit, int offset);
    int rowsCount();

}
