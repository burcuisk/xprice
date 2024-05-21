package com.moneypay.xprice.exception;

import com.moneypay.xprice.exception.data.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler  {

    private static final String HTTP_BODY_NOT_READABLE = "Http body not readable. Check if content of body is valid.";
    private static final String HTTP_INVALID_MEDIA_TYPE = "Media type is invalid";

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProductNotFoundException(ProductNotFoundException ex) {
        return new ResponseEntity<>(ErrorResponse.builder()
                                        .date(LocalDateTime.now())
                                        .message(ex.getMessage())
                                        .build(),
                                    HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = BindException.class)
    public ResponseEntity<?> handleValidationException(BindException e) {
        log.info("Error on validation: ", e.getMessage());
        String message = getErrorMessage(e.getBindingResult().getAllErrors());

        return new ResponseEntity<>(ErrorResponse.builder()
                                        .date(LocalDateTime.now())
                                        .message(message)
                                        .build(),
                                    HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException e) {
        log.info("Error on validation: {}", e.getMessage());
        String message = getErrorMessage(e.getBindingResult().getAllErrors());;
        return new ResponseEntity<>(ErrorResponse.builder()
                                        .date(LocalDateTime.now())
                                        .message(message)
                                        .build(),
                                    HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleInvalidBodyException(HttpMessageNotReadableException e) {
        log.info("HTTP body not readable: {}", e.getMessage());
        return new ResponseEntity<>(ErrorResponse.builder()
                                        .date(LocalDateTime.now())
                                        .message(HTTP_BODY_NOT_READABLE)
                                        .build(),
                                    HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<?> handleContentTypeException(HttpMediaTypeNotSupportedException e) {
        log.info("Invalid media type:", e.getMessage());
        return new ResponseEntity<>(ErrorResponse.builder()
                                        .date(LocalDateTime.now())
                                        .message(HTTP_INVALID_MEDIA_TYPE)
                                        .build(),
                                    HttpStatus.BAD_REQUEST);
    }

    private String getErrorMessage(List<ObjectError> errorList) {
        String message = "";
        for (ObjectError error : errorList) {
            String fieldName = error instanceof FieldError ? ((FieldError) error).getField() : "";

            String errorMessage = error.getDefaultMessage();
            message += fieldName.isEmpty() ? errorMessage : fieldName + ":" + errorMessage + '\n';
        }
        return message;
    }


}