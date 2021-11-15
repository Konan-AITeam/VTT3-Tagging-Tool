package com.konantech.spring.response;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Data
public class BasicResponse implements Serializable {
    String result;
    int status;
    Object data;
    long timestamp = System.currentTimeMillis();

    public BasicResponse() {
        this.status = HttpStatus.OK.value();
    }

    public BasicResponse(Exception e) {
        this.status = HttpStatus.BAD_REQUEST.value();
        this.setResult("error");
        this.setData(e.getMessage());
    }
}

