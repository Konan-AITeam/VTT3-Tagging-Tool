package com.konantech.spring.service;

import com.konantech.spring.CommonTests;
import com.konantech.spring.domain.workflow.CheckInRequest;
import com.konantech.spring.domain.workflow.CompJobQueueTB;
import com.konantech.spring.domain.workflow.CompServerTB;
import com.konantech.spring.domain.workflow.WorkflowRequest;
import com.konantech.spring.mapper.CustomQueryMapper;
import com.konantech.spring.mapper.WorkflowMapper;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

public class CatalogerWorkflowTest extends CommonTests {

    private static Logger log = LoggerFactory.getLogger(CatalogerWorkflowTest.class);
    private int delay = 3 * 1000; // 3 sec

    @Autowired
    WorkflowService workflowService;

    @Autowired
    WorkflowMapper workflowMapper;

    @Autowired
    CustomQueryMapper customQueryMapper;


    @Test
    public void reset() throws Exception {
        customQueryMapper.selectItem("DELETE FROM DEMO_COMPJOBQUEUE_TB");
        customQueryMapper.selectItem("DELETE FROM DEMO_COMPSERVER_TB");
        customQueryMapper.selectItem("DELETE FROM DEMO_COMPSERVERJOB_TB");
        customQueryMapper.selectItem("DELETE FROM DEMO_WORKFLOWHIS_TB");


    }

    /* ksd_compserver_tb, ksd_compserverjob_tb */
    @Test
    public void compserversCheckout_01() throws Exception {
        int result = workflowService.checkout(1);
        System.out.println("compserverId = " + result);
    }

    /* ksd_compserver_tb, ksd_compserverjob_tb */
    @Test
    public void compserversCheckin_01() throws Exception {
        CheckInRequest request = new CheckInRequest();
        request.setChannel(1);
        request.setPool(0);
        request.setCompservername("cataloger");
        request.setClientip("10.10.18.128");
        request.setPort(8080);
        request.setJobnames(Arrays.asList("cataloging"));
        request.setClienturl("http://127.0.0.1:8080/darc4/responseTest");
        int result = workflowService.checkin(req, request);
        System.out.println("compserverId = " + result);
    }

    /* DELETE FROM KWD_WORKFLOW_TB;   DELETE FROM KWD_COMPJOB_TB;   update kmu_video_tb set catalogstatus=0 where idx=1; */
    @Test
    public void workflowsRun_02() throws Exception {

        customQueryMapper.selectItem("UPDATE DEMO_VIDEO_TB SET CATALOGSTATUS=0 WHERE IDX=1");
        customQueryMapper.selectItem("DELETE FROM DEMO_COMPJOBQUEUE_TB");
        customQueryMapper.selectItem("DELETE FROM DEMO_WORKFLOWHIS_TB");

        WorkflowRequest request = new WorkflowRequest();
        request.setWorkflowname("ca");
        request.setAssetname("demo_video_tb");
        request.setAssetid(1);
        request.setSubtype(0);
        List<Integer> workflowIds = workflowService.createWorkflowHis(
                req.getSession(),
                request.getWorkflowname(),
                request.getAssetname(),
                request.getAssetid(),
                request.getSubtype());
        System.out.println("workflowIds = " + workflowIds);
    }

    @Test
    public void workflowJob_03() throws Exception {
        try {

            customQueryMapper.selectItem("DELETE FROM DEMO_COMPJOBQUEUE_TB WHERE JOBNAME != 'cataloging'"); //불필요 큐제거
            customQueryMapper.selectItem("DELETE FROM DEMO_WORKFLOWHIS_TB WHERE CURRJOB != 'cataloging'"); //불필요 워크플로우 제거
            customQueryMapper.selectItem("UPDATE DEMO_WORKFLOWHIS_TB SET STATUS = 0"); //워크플로우 상태 초기화
            customQueryMapper.selectItem("UPDATE DEMO_COMPSERVER_TB SET STATUS = 0"); //SET 콤포넌트 상태 초기화
            customQueryMapper.selectItem("UPDATE DEMO_COMPJOBQUEUE_TB SET STATUS = 0"); //큐 작업 초기화

            // ToDo 서버간 동기화 구간 시작
            CompJobQueueTB compJobQueueTB = workflowMapper.selectAllocJob();
            CompServerTB compServerTB = null;
            if (compJobQueueTB != null) {
                compServerTB = workflowMapper.selectAllocServer(compJobQueueTB);
            }

            if (compJobQueueTB != null && compServerTB != null) {
                workflowService.updateStatusProc(compJobQueueTB, compServerTB);
            }

            Thread.sleep(delay);
            workflowService.allocJob(compJobQueueTB, compServerTB);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Test
    public void workflowJob_all() throws Exception {
        workflowsRun_02();
        workflowJob_03();
    }

}