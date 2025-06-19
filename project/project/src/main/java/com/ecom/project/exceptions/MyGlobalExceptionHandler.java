package com.ecom.project.exceptions;

import com.ecom.project.payload.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class MyGlobalExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String , String>> myMethodArgumentNotValidException(MethodArgumentNotValidException e){
        Map<String,String> response = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach(err -> {
            String filedName = ((FieldError)err).getField();
            String message = err.getDefaultMessage();
            response.put(filedName,message);
        });
        return  new ResponseEntity<Map<String , String>>(response, HttpStatus.BAD_REQUEST);
    }
// Custom Exception
    @ExceptionHandler(ResourceNotFoundException.class)
    public  ResponseEntity<ApiResponse> myResourceNotFoundException (ResourceNotFoundException e){
    String message = e.getMessage();
    ApiResponse apiResponse = new ApiResponse(message,false);
    return new ResponseEntity<>(apiResponse ,HttpStatus.NOT_FOUND);
    }

// Custom Exception
    @ExceptionHandler(APIException.class)
    public  ResponseEntity<ApiResponse> myRAPIException (APIException e){
        String message = e.getMessage();
        ApiResponse apiResponse = new ApiResponse(message,false);
        return new ResponseEntity<>(apiResponse ,HttpStatus.BAD_REQUEST);
    }
}
