package com.kpi.magazines.dao.basic.interfaces;

import com.kpi.magazines.beans.Edition;

import java.util.List;

/**
 * Created by Konstantin Minkov on 27.06.2016.
 */
public interface EditionDao extends GenericDao<Edition> {

    Edition findByName(String name);
    List<Edition> findAllByUpdateTimeDesc(int limit, int offset);
    List<Edition> findLikeNameByUpdateTimeDesc(String name, int limit, int offset);
    List<Edition> findLikeNameByCategoryIdByUpdateTimeDesc(String name, int editionId, int limit, int offset);
    int rowsCount();
    int rowsCountLikeName(String name);
    int rowsCountLikeNameByCategoryId(String name, int categoryId);
}
