package com.konantech.spring.domain.workflow;

import lombok.Data;

import java.util.Date;

@Data
public class WorkflowHisTB {
    int workflowid;
    String workflowname;
    int subtype;
    String currjob;
    String maintable;
    int mainassetid;
    String reftable;
    int refassetid;
    int sessionid;
    int userid;
    int status;
    Date createTime;
    Date deleteTime;

    public void setStatusEnum(STATUS status) {
        this.status = status.getValue();
    }

    public STATUS getStatusEnum() {
        for(STATUS status : STATUS.values()) {
            if (status.getValue() == this.status) return status;
        }

        return STATUS.NONE;
    }
}
