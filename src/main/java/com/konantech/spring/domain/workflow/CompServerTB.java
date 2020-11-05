package com.konantech.spring.domain.workflow;

import lombok.Data;

import java.util.Date;

@Data
public class CompServerTB {
    private int compserverid;
    private String compservername;
    private String jobname;
    private int sessionid;
    private int channel;
    private int status;
    private Date checkintime;
    private int pool;
    private String clientip;
    private Date timestamp;
    private int port;
    private String clienturl;
    private int pingstamp;

    public void setStatusEnum(STATUS status) {
        this.status = status.getValue();
    }

    public STATUS getStatusEnum() {
        for(STATUS status : STATUS.values()) {
            if (status.getValue() == this.status) return status;
        }
        return STATUS.NONE;
    }

}
