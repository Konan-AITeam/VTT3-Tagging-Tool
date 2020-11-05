package com.konantech.spring.domain.workflow;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * Created by seheung on 2017. 4. 4..
 */

@Data
public class DArcCompjobPropertySelect {

    String table;
    String alias;
    List<String> where;
    //String whereField;
    String select;

    public boolean isSingleObject() {
        if (alias != null && alias.matches(".*src.*")) {
            return true;
        }

        return false;
    }

    public String getSelectQuery(Map<String, Object> paramMap) {
        String whereCond = "";
        for (int i = 0; i < where.size(); i++) {
            String whereField = where.get(i);
            if (i > 0 ) {
                whereCond += " and";
            }
            whereCond += String.format(" %s=%s", whereField, paramMap.get(whereField).toString());
        }

        String query = String.format("select %s from %s where %s", select, table, whereCond);
        return query;
    }


}
