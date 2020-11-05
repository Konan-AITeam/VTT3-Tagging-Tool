package com.konantech.spring.domain.workflow;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class ReportProgressRequest implements Serializable {

    @NotNull(message = "jobid 를 입력하세요")
    private int jobid;

    @NotNull(message = "progress 를 입력하세요")
    private int progress;

    private String exvalue;

    public ReportProgressRequest() { }
}