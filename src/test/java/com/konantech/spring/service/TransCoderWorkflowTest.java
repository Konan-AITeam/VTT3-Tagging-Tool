package com.konantech.spring.service;

import com.konantech.spring.CommonTests;
import com.konantech.spring.domain.workflow.*;
import com.konantech.spring.mapper.CustomQueryMapper;
import com.konantech.spring.mapper.WorkflowMapper;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;

import java.util.Arrays;
import java.util.List;

public class TransCoderWorkflowTest extends CommonTests {

    private static Logger log = LoggerFactory.getLogger(TransCoderWorkflowTest.class);
    private int delay = 3 * 1000; // 3 sec

    private int assetid = 1;
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
        request.setCompservername("transcoder");
        request.setClientip("10.10.18.128");
        request.setPort(8080);
        request.setJobnames(Arrays.asList("transcoding"));
        request.setClienturl("http://127.0.0.1:8080/darc4/responseTest");
        int result = workflowService.checkin(req, request);
        System.out.println("compserverId = " + result);
    }


    /* DELETE FROM KWD_WORKFLOW_TB;   DELETE FROM KWD_COMPJOB_TB;   update kmu_video_tb set transcodingstatus=0 where idx=1; */
    @Test
    public void workflowsRun_02() throws Exception {

        customQueryMapper.selectItem("UPDATE DEMO_VIDEO_TB SET TRANSCODINGSTATUS=0 WHERE IDX=1");
//        customQueryMapper.selectItem("DELETE FROM DEMO_COMPJOBQUEUE_TB");
//        customQueryMapper.selectItem("DELETE FROM DEMO_WORKFLOWHIS_TB");

        MockHttpServletRequest req = new MockHttpServletRequest();
        WorkflowRequest request = new WorkflowRequest();
        request.setWorkflowname("tc");
        request.setAssetname("demo_video_tb");
        request.setAssetid(assetid);
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

            customQueryMapper.selectItem("delete from kwd_compjob_tb where jobname != 'transcoding'");
            customQueryMapper.selectItem("delete from kwd_workflow_tb where currjob != 'transcoding'");
            customQueryMapper.selectItem("update kwd_workflow_tb set status = 0");
            customQueryMapper.selectItem("update ksd_compserver_tb set status = 0");
            customQueryMapper.selectItem("update kwd_compjob_tb set status = 0");


            // ToDo 서버간 동기화 구간 시작
            CompJobQueueTB compjobDatTb = workflowMapper.selectAllocJob();
            CompServerTB compserverDatTb = null;
            if (compjobDatTb != null) {
                compserverDatTb = workflowMapper.selectAllocServer(compjobDatTb);
            }
            if (compjobDatTb != null && compserverDatTb != null) {
                workflowService.updateStatusProc(compjobDatTb, compserverDatTb);
            }

            if (compjobDatTb == null) {
                Thread.sleep(delay);
            }
            else {
                workflowService.allocJob(compjobDatTb, compserverDatTb);
            }
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