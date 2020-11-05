package com.konantech.spring.domain.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@JsonIgnoreProperties({"headers", "statusCode", "statusCodeValue"})
public class ObjectResponse<T> extends ResponseEntity<T> {
    public ObjectResponse() {
        super(HttpStatus.OK);
    }

    public ObjectResponse(HttpStatus httpStatus) {
        super(httpStatus);
    }

    public ObjectResponse(T data) {
        this(data, HttpStatus.OK);
    }

    public ObjectResponse(T data, HttpStatus status) {
        super(data, status);
    }

    @JsonUnwrapped
    public T getBody() {
        return super.getBody();
    }
}