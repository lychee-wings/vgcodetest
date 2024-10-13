package com.example.vgcodetest.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  public GlobalExceptionHandler() {
    super();
  }

  @ExceptionHandler({ApplicativeException.class, InvalidDateFormatException.class,
      InvalidCSVFileException.class})
  public ResponseEntity<Object> handleApplicativeException(ApplicativeException e) {
    ErrorMessage errorMessage;
    HttpStatus status;

    // Build the response based on the exception.
    status = e.getClass().getDeclaredAnnotation(ResponseStatus.class).code();
    errorMessage = ErrorMessage.builder() //
        .reason(e.getClass().getDeclaredAnnotation(ResponseStatus.class)
            .reason()) //
        .statusCode(status.value())//
        .message(e.getMessage()).timeStamp(LocalDateTime.now()).build();

    return ResponseEntity.status(status).body(errorMessage);
  }

  @ExceptionHandler({ConstraintViolationException.class})
  public ResponseEntity<Object> handleValidationException(ConstraintViolationException e) {
    ErrorMessage errorMessage;
    HttpStatus status;

    List<String> reason = e.getConstraintViolations()
        .stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());

    // Build the response based on the exception.
    status = HttpStatus.BAD_REQUEST;
    errorMessage = ErrorMessage.builder() //
        .reason(reason) //
        .statusCode(status.value())//
        //
        .timeStamp(LocalDateTime.now()).build();

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
  }


  @ExceptionHandler({MultipartException.class, IOException.class})
  public ResponseEntity<Object> handleMissingParamsException(Exception e) {
    ErrorMessage errorMessage;
    HttpStatus status;

    // Build the response based on the exception.
    status = HttpStatus.BAD_REQUEST;
    errorMessage = ErrorMessage.builder() //
        .reason(e.getMessage()) //
        .statusCode(status.value())//
        //
        .timeStamp(LocalDateTime.now()).build();

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
  }

  @Builder
  record ErrorMessage(int statusCode, Object reason,
                      @JsonInclude(Include.NON_NULL) String message,
                      LocalDateTime timeStamp) {

  }

}
