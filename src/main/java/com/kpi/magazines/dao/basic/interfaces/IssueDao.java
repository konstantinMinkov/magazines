package com.kpi.magazines.dao.basic.interfaces;

import com.kpi.magazines.beans.Issue;

/**
 * Created by Konstantin Minkov on 27.06.2016.
 */
public interface IssueDao extends GenericDao<Issue> {

    Issue findByEditionId(int editionId);

}
