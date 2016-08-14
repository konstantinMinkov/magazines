package com.kpi.magazines.dao.basic.interfaces;

import com.kpi.magazines.beans.Subscription;

import java.util.List;

/**
 * Created by Konstantin Minkov on 27.06.2016.
 */
public interface SubscriptionDao extends GenericDao<Subscription> {

    List<Subscription> findByUserId(int userId);
    List<Subscription> findByEditionId(int editionId);

}
