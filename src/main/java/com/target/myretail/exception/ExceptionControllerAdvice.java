package com.target.myretail.exception;

import com.target.myretail.models.ErrorData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorData> handleResponseStatusException(ResponseStatusException ex, WebRequest request) {
        return new ResponseEntity<>(
                new ErrorData(ex.getStatus().toString(), ex.getReason()), ex.getStatus()
        );
    }
}
