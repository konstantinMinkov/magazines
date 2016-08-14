package com.kpi.magazines.dao.orm.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Konstantin Minkov on 23.06.2016.
 */
public class StatementsHolder {

    private Map<String, String> queries = new HashMap<>();

    public StatementsHolder() { }

    public void addStatement(String name, String sqlPrepared) {
        queries.put(name, sqlPrepared);
    }

    public String getStatement(String name) {
        return queries.get(name);
    }

    @Override
    public String toString() {
        return "StatementsHolder{" +
                "queries=" + queries +
                '}';
    }
}
