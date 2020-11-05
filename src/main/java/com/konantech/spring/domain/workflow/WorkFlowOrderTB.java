package com.konantech.spring.domain.workflow;

import lombok.Data;

@Data
public class WorkFlowOrderTB {


    private String workflowname;
    private String jobname;
    private int subtype;
    private int seq;
    private int pool;
    private int priority;
    private int expool;


}
