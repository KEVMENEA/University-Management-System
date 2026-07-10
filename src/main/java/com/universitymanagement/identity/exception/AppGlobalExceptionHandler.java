package com.universitymanagement.identity.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class AppGlobalExceptionHandler {
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<?> handleService(ResponseStatusException e){
       RestErrorResponse restErrorResponse = RestErrorResponse.builder()
                .message("Buisness Logic Error, Please Check!")
                .code(e.getStatusCode().value())
                .status(e.getStatusCode().toString())
                .timestamp(Instant.now())
                .errors(e.getReason()).build();
        return new ResponseEntity<>(restErrorResponse,e.getStatusCode());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    RestErrorResponse<?> handleValidation(MethodArgumentNotValidException e){
        List<FieldResponse> fields = new ArrayList<>();
        e.getFieldErrors().forEach(fieldError -> {
            fields.add(new FieldResponse(fieldError.getField(),fieldError.getDefaultMessage()));
        });

        return RestErrorResponse.builder()
                .message("Requested data is invalid")
                .code(HttpStatus.BAD_REQUEST.value())
                .status(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .timestamp(Instant.now())
                .errors(fields).build();
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException e){
        RestErrorResponse<?> restErrorResponse = RestErrorResponse.builder()
                .message(e.getMessage())
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .timestamp(Instant.now())
                .errors(null)
                .build();
        return new ResponseEntity<>(restErrorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({
            UserNotFoundException.class,
            StudentNotFoundException.class,
            DepartmentNotFoundException.class
    })
    public ResponseEntity<RestErrorResponse<?>> handleNotFound(RuntimeException ex) {

        RestErrorResponse<?> response = RestErrorResponse.builder()
                .status(String.valueOf(HttpStatus.NOT_FOUND.value()))
                .message(ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

    }
}