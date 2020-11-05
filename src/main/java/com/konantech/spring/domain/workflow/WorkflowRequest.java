package com.konantech.spring.domain.workflow;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class WorkflowRequest implements Serializable {

    @NotEmpty(message = "workflowname 을 입력하세요")
    String workflowname;

    @NotEmpty(message = "assetname 을 입력하세요")
    String assetname;

    @NotNull(message = "assetid 를 입력하세요")
    int assetid;

    @NotNull(message = "subtype 을 입력하세요")
    int subtype;
}