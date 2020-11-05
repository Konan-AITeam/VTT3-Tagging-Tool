package com.konantech.spring.domain.workflow;

import lombok.Data;

@Data
public class CompJobTB {
    String jobName;
    String jobType;
    String paramProperty;
    String jobProperty;
    String startExName;
    String progressExName;
    String endExName;
}
