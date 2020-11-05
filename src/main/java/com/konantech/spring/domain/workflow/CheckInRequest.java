package com.konantech.spring.domain.workflow;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class CheckInRequest implements Serializable {

    @NotEmpty(message = "컨포넌트 서버이름을 입력하세요")
    private String compservername;

    @NotNull(message = "채널을 입력하세요")
    private int channel;

    @NotNull(message = "풀을 입력하세요")
    private int pool;

    @NotEmpty(message = "jab 이름들을 입력하세요")
    private List<String> jobnames;

    private String clientip;

    @NotNull(message = "포트를 입력하세요")
    private int port;

    private String clienturl;


    private int compserverid;
    private int sessionid;
    private int status;

    private Date checkintime;
    private Date timestamp;

    public CheckInRequest() { }


}