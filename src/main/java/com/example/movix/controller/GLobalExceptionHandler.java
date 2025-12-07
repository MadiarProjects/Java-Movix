package com.example.movix.controller;

import com.example.movix.exceptions.ErrorResponce;
import com.example.movix.exceptions.InvalidParamException;
import com.example.movix.exceptions.NotFoundedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GLobalExceptionHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponce notFounded(NotFoundedException e){
        return new ErrorResponce(e.getMessage());
    }
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponce invalidParam(InvalidParamException e){
        return new ErrorResponce(e.getMessage());
    }
}
