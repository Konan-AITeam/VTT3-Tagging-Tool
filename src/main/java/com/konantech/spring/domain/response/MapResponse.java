package com.konantech.spring.domain.response;

import lombok.Data;

@Data
public class MapResponse {
    String result;
    int status;
    Object message;
    long timestamp;

    public MapResponse() {
        this.status = 200;
        timestamp = System.currentTimeMillis();
    }
}
