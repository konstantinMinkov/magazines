package com.kpi.magazines.dao.basic.interfaces;

import com.kpi.magazines.beans.Category;

/**
 * Created by Konstantin Minkov on 12.07.2016.
 */
public interface CategoryDao extends GenericDao<Category> {

    Category findByName(String name);
}
