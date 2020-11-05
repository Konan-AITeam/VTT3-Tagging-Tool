package com.konantech.spring.domain.workflow;

import com.konantech.spring.mapper.WorkflowMapper;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DArcWorkflowSchema {

    String workflowName;
    int subtype;
    List<DArcCompjobSchema> jobList;
    WorkflowMapper workflowMapper;


    public DArcWorkflowSchema(WorkflowMapper workflowMapper, String workflowName, int subtype) {
        jobList = null;
        this.workflowMapper = workflowMapper;
        this.workflowName = workflowName;
        this.subtype = subtype;

        initWorklfow();
    }

    public List<Integer> CreateWorkflow(HttpSession httpSession, String mainTable, int mainAssetID, String refTable, int refAssetID) {

        List<Integer> intList = new ArrayList<Integer>();
        for(DArcCompjobSchema dArcCompjobSchema : this.jobList) {
            WorkflowHisTB workflowDataTb = new WorkflowHisTB();
            workflowDataTb.setWorkflowname(workflowName);
            workflowDataTb.setSubtype(subtype);
            workflowDataTb.setCurrjob(dArcCompjobSchema.getJobName());
            workflowDataTb.setMaintable(mainTable);
            workflowDataTb.setMainassetid(mainAssetID);
            workflowDataTb.setReftable(refTable);
            workflowDataTb.setRefassetid(refAssetID);
//            workflowDataTb.setSessionid(session.getSessionID());
//            workflowDataTb.setUserid(session.getUserID());

            workflowMapper.putWorkflowHis(workflowDataTb);
            int workflowID = workflowDataTb.getWorkflowid();
            dArcCompjobSchema.CreateCompjob(workflowID);
            intList.add(workflowID);
        }
        return intList;
    }

    public void setCompjobStatus(WorkflowHisTB workflowDataTb, STATUS status, CompJobQueueTB compjobDatTb, String failReason) {
        DArcCompjobSchema compjobSchema = jobList.get(compjobDatTb.getJoborder());
        if (compjobSchema == null) {
            //ToDo throw error
        }
        compjobSchema.setCompjobStatus(status, compjobDatTb, failReason);

        if (status == STATUS.FAIL) {

        }
    }

    public void setCompjobStatusStart(WorkflowHisTB workflowDataTb, CompJobQueueTB compjobDatTb, CompServerTB compserverDatTb) {
        DArcCompjobSchema compjobSchema = jobList.get(compjobDatTb.getJoborder());
        if (compjobSchema == null) {
            //ToDo throw error
        }
        compjobSchema.setCompjobStatusStart(compjobDatTb, compserverDatTb);

        workflowDataTb.setStatusEnum(STATUS.WORKING);
        workflowDataTb.setCurrjob(compjobDatTb.getJobname());
        workflowMapper.updateWorkflowStatusAndCurJob(workflowDataTb);
    }

    public void setCompjobIdle(CompJobQueueTB compjobDatTb) {
        DArcCompjobSchema compjobSchema = jobList.get(compjobDatTb.getJoborder());
        if (compjobSchema == null) {
            //ToDo throw error
        }

    }

    public void reportProgress(WorkflowHisTB workflowDataTb, CompJobQueueTB compjobDatTb, int progress, String progressExValue) {
        DArcCompjobSchema compjobSchema = jobList.get(compjobDatTb.getJoborder());
        if (compjobSchema == null) {
            //ToDo throw error
        }
        compjobSchema.reportProgress(compjobDatTb, progress, progressExValue);
    }

    public void reportResult(HttpSession httpSession, CompJobQueueTB compjobDatTb, WorkflowHisTB workflowData, STATUS status, String message) {
        if (status == STATUS.RETRY || status == STATUS.POSTPONE) {
            // 임시 하드코딩
            if (compjobDatTb.getFailcount() > 3) {
                status = STATUS.FAIL;
            }
        }
        DArcCompjobSchema compjobSchema = jobList.get(compjobDatTb.getJoborder());
        if (compjobSchema == null) {
            //ToDo throw error
        }
        compjobSchema.reportResult(compjobDatTb, status, message);

        DArcCompjobSchema nextJob = null;

        if (status == STATUS.SUCCESS) {
            nextJob = getNextJob(compjobDatTb);
            if (nextJob != null){
                nextJob.CreateCompjob(workflowData.getWorkflowid());

                workflowData.setCurrjob(nextJob.getJobName());
                workflowData.setStatusEnum(STATUS.WORKING);
                workflowMapper.updateWorkflowStatusAndCurJob(workflowData);
            }
        }

        if (status == STATUS.FAIL || status == STATUS.CANCEL || (status == STATUS.SUCCESS && nextJob == null)) {
            workflowData.setStatusEnum(status);
            workflowMapper.updateWorkflowStatusAndDeleteTime(workflowData);
//            workflowMapper.insertToDelWorkflow(workflowData.getWorkflowid());
            workflowMapper.deleteWorkflow(workflowData.getWorkflowid());
        }

    }

    DArcCompjobSchema getNextJob(CompJobQueueTB compjobDatTb) {
        if (compjobDatTb.getJoborder() + 1 < jobList.size()) {
            return jobList.get(compjobDatTb.getJoborder() + 1);
        }

        return null;
    }

    void initWorklfow() {
        WorkFlowOrderTB param = new WorkFlowOrderTB();
        param.setWorkflowname(workflowName);
        param.setSubtype(subtype);

        List<WorkFlowOrderTB> results = workflowMapper.getWorkFlowOrder(param);
        if (results == null || results.isEmpty()) {
            //ToDo throw error
            return;
        }
        jobList = new ArrayList<>();
        int i = 0;
        for(WorkFlowOrderTB result : results) {
            jobList.add(
                new DArcCompjobSchema(
                    workflowMapper,
                    result.getJobname(),
                    result.getPool(),
                    result.getPriority(),
                    i++
                )
            );
        }
    }
}
