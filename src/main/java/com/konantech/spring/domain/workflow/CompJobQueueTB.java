package com.konantech.spring.domain.workflow;

import lombok.Data;

import java.util.Date;

@Data
public class CompJobQueueTB {
    int jobid;
    int workflowid;
    String jobname;
    int compserverid;
    String compserverip;
    int channel;
    int pool;
    int priority;
    int status;
    int progress;
    String progressex;
    Date starttime;
    Date modifytime;
    Date endtime;
    String jobcontents;
    String paramlist;
    int failcount;
    String failreason;
    int joborder;

    public void setStatusEnum(STATUS status) {
        this.status = status.getValue();
    }

    public STATUS getStatusEnum() {
        for(STATUS status : STATUS.values()) {
            if (status.getValue() == this.status) return status;
        }

        return STATUS.NONE;
    }

    /*
    public enum STATUS {
        NONE(0), ALLOC(1), WORKING(2), SUCCESS(3), FAIL(4), RETRY(5), POSTPONE(6);

        private final int value;
        STATUS(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
*/
}


