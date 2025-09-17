package com.shumair99.shorty.core;

import com.shumair99.shorty.dto.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiErrorHandler {

  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<ApiError> handleBad(BadRequestException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(new ApiError("BadRequest", ex.getMessage()));
  }

  @ExceptionHandler(ConflictException.class)
  public ResponseEntity<ApiError> handleConflict(ConflictException ex) {
    return ResponseEntity.status(HttpStatus.CONFLICT)
        .body(new ApiError("Conflict", ex.getMessage()));
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ApiError> handleNotFound(NotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(new ApiError("NotFound", ex.getMessage()));
  }

  @ExceptionHandler(GoneException.class)
  public ResponseEntity<ApiError> handleGone(GoneException ex) {
    return ResponseEntity.status(HttpStatus.GONE)
        .body(new ApiError("Gone", ex.getMessage()));
  }
}
