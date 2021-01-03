package com.mvp.exception.handler;

import com.mvp.error.APIError;
import com.mvp.exception.InvalidDataException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class PatientDirectorySearchExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(InvalidDataException.class)
  public ResponseEntity<Object> handleIllegalArgumentException(InvalidDataException ex) {
    return new ResponseEntity<>(
        new APIError("INVALID_DATA", ex.getMessage()), HttpStatus.BAD_REQUEST);
  }
}
