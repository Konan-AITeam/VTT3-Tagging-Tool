package com.konantech.spring.domain.workflow;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class ReportResultRequest implements Serializable {

    @NotNull(message = "jobid 를 입력하세요")
    private int jobid;

    @NotEmpty(message = "result 를 입력하세요")
    private String result;

    private String message;

    public ReportResultRequest() { }
}