package com.bikkadit.electronic.store.ElectronicStore.exceptions;

import com.bikkadit.electronic.store.ElectronicStore.dtos.ApiResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {

private Logger logger= LoggerFactory.getLogger(GlobalExceptionHandler.class);
    // handle resource not found exception

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponseMessage> resourceNotFoundExceptionHandler(ResourceNotFoundException res) {


        logger.info("Exception Handler Invoked");
        ApiResponseMessage response = ApiResponseMessage.builder().message(res.getMessage()).status(HttpStatus.NOT_FOUND)
                .success(true).build();

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

    }


    // method argument not valid exception


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleMethodNotValidException(MethodArgumentNotValidException ex) {
        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();

        Map<String, Object> response = new HashMap<>();
        allErrors.stream().forEach(objectError -> {
            String message = objectError.getDefaultMessage();
            String field = ((FieldError) objectError).getField();
            response.put(field, message);
        });
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);

    }

    // to Handle Bad Api request
    @ExceptionHandler(BadApiRequest.class)
    public ResponseEntity<ApiResponseMessage> handleBadApiRequest(BadApiRequest exp) {


        logger.info("Exception Handler Invoked");
        ApiResponseMessage response = ApiResponseMessage.builder().message(exp.getMessage()).status(HttpStatus.BAD_REQUEST)
                .success(false).build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

    }


}