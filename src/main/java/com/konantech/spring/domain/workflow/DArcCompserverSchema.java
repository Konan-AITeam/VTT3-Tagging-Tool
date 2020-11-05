package com.konantech.spring.domain.workflow;

import com.konantech.spring.mapper.WorkflowMapper;

public class DArcCompserverSchema {
    WorkflowMapper workflowMapper;
    String compserverName;

    public DArcCompserverSchema(WorkflowMapper workflowMapper, String compserverName) {
        //ToDo : check compserver & throw
        this.compserverName = compserverName;
        this.workflowMapper = workflowMapper;
    }

    public int checkout(int compserverID) {
        workflowMapper.delCompServerJob(compserverID);
        workflowMapper.delCompServer(compserverID);
        return compserverID;
    }

    public void setCompserverStatusStart(CompServerTB compserverDatTb) {
        compserverDatTb.setStatusEnum(STATUS.WORKING);
        workflowMapper.updateCompserverStatus(compserverDatTb);
    }

    public void setCompserverStatus(STATUS status, CompServerTB compserverDatTb) {

        if (status == STATUS.FAIL || status == STATUS.SUCCESS || status == STATUS.RETRY || status == STATUS.POSTPONE) {
            status = STATUS.NONE;
        }
        compserverDatTb.setStatusEnum(status);
        workflowMapper.updateCompserverStatus(compserverDatTb);
    }
}