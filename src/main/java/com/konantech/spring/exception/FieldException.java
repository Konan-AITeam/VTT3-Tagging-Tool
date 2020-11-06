package com.konantech.spring.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FieldException extends ApiException {
    @JsonProperty("fields")
    private List<ErrorInfo> errorList;

    public FieldException(BindingResult result) {
        super(601);
        errorList = new ArrayList<>();
        List<ObjectError> objectErrorList = result.getAllErrors();
        if (objectErrorList != null && !objectErrorList.isEmpty()) {
            errorList.addAll(objectErrorList.stream().map(error -> new ErrorInfo(((FieldError) error).getField(), error.getDefaultMessage())).collect(Collectors.toList()));
        }
    }

    public class ErrorInfo {
        private String field;
        private String message;

        public ErrorInfo(String field, String message) {
            this.field = field;
            this.message = message;
        }

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public String getMessage() {
            return message;
        }

        
        public void setMessage(String message) {
            this.message = message;
        }
    }
}
