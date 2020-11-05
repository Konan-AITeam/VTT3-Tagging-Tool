package com.konantech.spring.domain.workflow;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class CompJobJobPropertyUpdate {

    static final int UpdateStatusNone = 0;
    static final int UpdateStatusReady = 1000;
    static final int UpdateStatusWorking = 2000;
    static final int UpdateStatusSuccess = 3000;
    static final int UpdateStatusFail = 4000;
    static final int UpdateStatusRetry = 5000;
    static final int UpdateStatusPostpone = 6000;
    static final int UpdateStatusCancel = 7000;

    String table;
    List<String> where;
    String update;

    public String getUpdateStatusQuery(STATUS status, int progress, Map<String, Object> paramMap) {
        String whereCond = getWhereCond(paramMap);
        int value = getUpdateValue(status, progress);

        String query = String.format("update %s set %s=%d where %s", table, update, value, whereCond);

        return query;
    }

    public String getUpdateTimeQuery(Map<String, Object> paramMap) {
        String whereCond = getWhereCond(paramMap);

        // ToDo DMBS 분기
        String query = String.format("update %s set %s=now() where %s", table, update, whereCond);
        return query;
    }

    public String getUpdateTimeInitQuery(Map<String, Object> paramMap) {
        String whereCond = getWhereCond(paramMap);

        // ToDo DMBS 분기
        String query = String.format("update %s set %s=null where %s", table, update, whereCond);
        return query;
    }

    String getWhereCond(Map<String, Object> paramMap) {
        String whereCond = "";
        for (int i = 0; i < where.size(); i++) {
            String whereField = where.get(i);
            if (i > 0 ) {
                whereCond += " and";
            }
            whereCond += String.format(" %s=%s", whereField, paramMap.get(whereField).toString());
        }

        return whereCond;
    }

    int getUpdateValue(STATUS status, int progress) {
        int value = 0;

        switch (status) {
            case READY:
                value = UpdateStatusReady;
                break;
            case WORKING:
                value = UpdateStatusWorking + (progress % 100);
                break;
            case SUCCESS:
                value = UpdateStatusSuccess;
                break;
            case FAIL:
                value = UpdateStatusFail;
                break;
            case RETRY:
                value = UpdateStatusRetry;
                break;
            case POSTPONE:
                value = UpdateStatusPostpone;
                break;
            case CANCEL:
                value = UpdateStatusCancel;
                break;
        }

        return value;
    }

}
