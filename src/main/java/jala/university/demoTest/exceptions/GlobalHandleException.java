package jala.university.demoTest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.Timestamp;
import java.time.Instant;

@ControllerAdvice
public class GlobalHandleException {

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException e) {
        ErrorResponse error = buildErrorResponse(HttpStatus.NOT_FOUND, e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = UserAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExistException(
            UserAlreadyExistException e) {
        ErrorResponse error = buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e) {
        ErrorResponse error = new ErrorResponse();
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        for (FieldError fieldError : e.getFieldErrors()) {
            error.add(fieldError.getDefaultMessage());
        }
        error.setTimestamp(Timestamp.from(Instant.now()));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    private ErrorResponse buildErrorResponse(HttpStatus httpStatus, String message) {
        ErrorResponse error = new ErrorResponse();
        error.setStatus(httpStatus.value());
        error.add(message);
        error.setTimestamp(Timestamp.from(Instant.now()));
        return error;
    }
}
