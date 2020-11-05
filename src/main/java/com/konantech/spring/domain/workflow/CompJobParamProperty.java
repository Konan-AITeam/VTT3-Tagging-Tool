package com.konantech.spring.domain.workflow;

import lombok.Data;

@Data
public class CompJobParamProperty {
    String type;
    String field;
    String value;

    public String getOtherQuery(WorkflowHisTB workflowHisTB) {
        String query = value.replaceAll("#MAINKEY", Integer.toString(workflowHisTB.getMainassetid()));
        query = query.replaceAll("#REFKEY", Integer.toBinaryString(workflowHisTB.getRefassetid()));

        return query;
    }

}
