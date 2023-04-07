package com.rest.procession.asyncapi.handler;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ValidationHandler extends ResponseEntityExceptionHandler {

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(
      HttpMessageNotReadableException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    Map<String, String> errors = new HashMap<>();
    errors.put("error", ex.getMessage());
    errors.put("code", "E001");
    return new ResponseEntity<>(errors, status);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {

    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult()
        .getAllErrors()
        .forEach(
            (error) -> {
              String fieldName = ((FieldError) error).getField();
              String message = error.getDefaultMessage();
              errors.put(fieldName, message);
              errors.put("code", "E001");
            });
    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ApplicationException.class)
  public ResponseEntity<Object> applicationExceptionHandler(Exception ex, WebRequest request) {
    Map<String, String> errors = new HashMap<>();
    errors.put("error", ex.getMessage());
    errors.put("code", "E003");
    return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(RecordNotFoundException.class)
  public ResponseEntity<Object> recordNotFoundExceptionHandler(Exception ex, WebRequest request) {
    Map<String, String> errors = new HashMap<>();
    errors.put("error", ex.getMessage());
    errors.put("code", "E002");
    return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(JobInProgressException.class)
  public ResponseEntity<Object> existJobExceptionHandler(Exception ex, WebRequest request) {
    Map<String, String> errors = new HashMap<>();
    errors.put("error", ex.getMessage());
    errors.put("code", "E004");
    return new ResponseEntity<>(errors, HttpStatus.CONFLICT);
  }
}
