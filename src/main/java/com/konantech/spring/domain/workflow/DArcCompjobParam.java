package com.konantech.spring.domain.workflow;

import java.util.Map;

/**
 * Created by seheung on 2017. 4. 3..
 */
public class DArcCompjobParam {
    String type;
    String field;
    String value;

    public String getType() {
        return type;
    }

    public String getField() {
        return field;
    }

    public String getValue() {
        return value;
    }

    public DArcCompjobParam(Map<String, Object> params) {
        type = params.get("type").toString();
        field = params.get("field").toString();

        if ("other".equals(type) || "direct".equals(type)) {
            value = params.get("value").toString();
        }
    }

    public String getOtherQuery(WorkflowHisTB workflowDataTb) {
        String query = value.replaceAll("#MAINKEY", Integer.toString(workflowDataTb.getMainassetid()));
        query = query.replaceAll("#REFKEY", Integer.toBinaryString(workflowDataTb.getRefassetid()));

        return query;
    }


}
