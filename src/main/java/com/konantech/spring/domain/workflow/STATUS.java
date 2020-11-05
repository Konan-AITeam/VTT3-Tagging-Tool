package com.konantech.spring.domain.workflow;

/**
 * Created by seheung on 2017. 4. 13..
 */
public enum STATUS {
    NONE(0), READY(1), WORKING(2), SUCCESS(3), FAIL(4), RETRY(5), POSTPONE(6), CANCEL(7), CANCELREQUEST(8), PROC(998), ERROR(999);

    private final int value;
    STATUS(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
