package com.konantech.spring.core;

import com.konantech.spring.domain.workflow.CompJobQueueTB;
import com.konantech.spring.domain.workflow.CompServerTB;
import com.konantech.spring.mapper.WorkflowMapper;
import com.konantech.spring.service.WorkflowService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class WorkflowInitializing {

    private static Logger log = LoggerFactory.getLogger(WorkflowInitializing.class);

    @Value("${jobs.schedule.enable}")
    private boolean scheduleJobEnable;

    @Autowired
    WorkflowService workflowService;

    @Autowired
    WorkflowMapper workflowMapper;

    /*@Scheduled(fixedDelay=3000)*/
    public void workflowJob() throws Exception {
        log.debug("workflowJob");
        if(scheduleJobEnable) {
            try {
                System.out.print(".");
                // ToDo 서버간 동기화 구간 시작
                CompJobQueueTB compJobQueueTB = workflowMapper.selectAllocJob();
                CompServerTB compServerTB = null;
                if (compJobQueueTB != null) {
                    compServerTB = workflowMapper.selectAllocServer(compJobQueueTB);
                    if (compServerTB != null) {
                        workflowService.updateStatusProc(compJobQueueTB, compServerTB);
                    }
                    workflowService.allocJob(compJobQueueTB, compServerTB);
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
    }

}