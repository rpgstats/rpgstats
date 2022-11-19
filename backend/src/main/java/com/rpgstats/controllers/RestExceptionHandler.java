package com.rpgstats.controllers;

import com.rpgstats.exceptions.ConflictDataException;
import com.rpgstats.exceptions.ItemNotFoundException;
import com.rpgstats.exceptions.ModelException;
import com.rpgstats.messages.ErrorResponse;
import io.swagger.v3.oas.annotations.Hidden;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Hidden
public class RestExceptionHandler {

  @ExceptionHandler(ModelException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<ErrorResponse> handleModelException(Exception exception) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(new ErrorResponse(exception.getMessage()));
  }

  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<ErrorResponse> handleConstraintViolatation(Exception exception) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(new ErrorResponse(exception.getMessage()));
  }

  @ExceptionHandler(ConflictDataException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  public ResponseEntity<ErrorResponse> handleConflictData(Exception exception) {
    return ResponseEntity.status(HttpStatus.CONFLICT)
        .body(new ErrorResponse(exception.getMessage()));
  }

  @ExceptionHandler(ItemNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<ErrorResponse> handleNotFound(Exception exception) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(new ErrorResponse(exception.getMessage()));
  }
}
