package com.konantech.spring.mapper;


import com.konantech.spring.domain.workflow.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Mapper
@Repository
public interface WorkflowMapper {

    int getCompServerCount(CheckInRequest checkInRequest);

    void putCompServer(CheckInRequest checkInRequest);

    void putCompServerJob(@Param("compserverid") int compserverID, @Param("jobname") String jobName);

    CompJobTB getCompJob(String compjobName);

    CompServerTB getCompServerByID(int compserverid);

    void delCompServerJob(int compserverid);

    void delCompServer(int compserverid);

    List<WorkFlowOrderTB> getWorkFlowOrder(WorkFlowOrderTB params);

    void putWorkflowHis(WorkflowHisTB workflowHisTB);

    void putCompJobQueue(CompJobQueueTB compJobQueueTB);

    int selectCustomInt(String query);

    CompJobQueueTB selectAllocJob();

    CompServerTB selectAllocServer(CompJobQueueTB compJobQueueTB);

    void updateCompjobAllocStatus(CompJobQueueTB compJobQueueTB);

    void updateCompserverAllocStatus(CompServerTB compServerTB);

    WorkflowHisTB selectWorkflowByID(int workflowid);

    CompJobQueueTB selectJobByID(int jobid);

    void updateCompjobProgress(CompJobQueueTB compJobQueueTB);

    void updateCustom(String query);


    void insertToDelWorkflow(int workflowID);

    void insertToDelCompjob(int jobID);


    void updateWorkflowStatus(WorkflowHisTB workflowDataTb);

    void updateWorkflowStatusAndCurJob(WorkflowHisTB workflowDataTb);

    void updateWorkflowStatusAndDeleteTime(WorkflowHisTB workflowDataTb);

    void updateCompjobStart(CompJobQueueTB compjobDatTb);

    void updateCompjobResult(CompJobQueueTB compjobDatTb);

    void updateCompserverStatus(CompServerTB compserverDatTb);

    int updateCompserverTimeStamp(int compserverID);


    void deleteWorkflow(int workflowID);

    void deleteCompjob(int compjobID);

    List<Integer> selectSubtypeByName(String workflowName);


    Map<String, Object> selectCompjobOrder(Map<String, Object> params);


    List<Map<String, Object>> selectCustomObjectList(String query);

    Map<String, Object> selectCustomObject(String query);

    int putCatalogQue(Map param);
}
