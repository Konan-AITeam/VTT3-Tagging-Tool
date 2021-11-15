package com.konantech.spring.exception;

import com.konantech.spring.response.ObjectResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice(annotations = {RestController.class})
public class GlobalExceptionController {
    @ExceptionHandler(value = FieldException.class)
    @ResponseBody
    public ObjectResponse<FieldException> handleFieldException(FieldException ex) {
        return new ObjectResponse<>(ex, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ApiException.class)
    @ResponseBody
    public ObjectResponse<ApiException> handleKonanException(ApiException ex) {
        return new ObjectResponse<>(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = NotFoundException.class)
    @ResponseBody
    public ObjectResponse<NotFoundException> handleKonanException(NotFoundException ex) {
        return new ObjectResponse<>(ex, HttpStatus.NOT_FOUND);
    }
}
