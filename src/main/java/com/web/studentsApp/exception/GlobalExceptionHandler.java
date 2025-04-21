package com.web.studentsApp.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler{

    @ExceptionHandler(ResourceNotFoundException.class)
    public ModelAndView handleResourceNotFoundException(ResourceNotFoundException exception, 
                                                      WebRequest webRequest) {
        return buildErrorModelAndView(
            exception.getMessage(),
            HttpStatus.NOT_FOUND,
            "Resource not found",
            webRequest
        );
    }

    @ExceptionHandler(StudentAlreadyExistsException.class)
    public ModelAndView handleStudentAlreadyExistsException(StudentAlreadyExistsException exception,
                                                          WebRequest webRequest) {
        return buildErrorModelAndView(
            exception.getMessage(),
            HttpStatus.BAD_REQUEST,
            "Student already created",
            webRequest
        );
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleGlobalException(Exception exception, WebRequest webRequest) {
        //exception.printStackTrace();
        return buildErrorModelAndView(
            "Unexpected error",
            HttpStatus.INTERNAL_SERVER_ERROR,
            "Internal server error",
            webRequest
        );
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ModelAndView handleNotFound(NoHandlerFoundException ex, WebRequest request) {
        return buildErrorModelAndView(
            "Resource not found",
            HttpStatus.NOT_FOUND,
            "Requested page not exist: " + ex.getRequestURL(),
            request
        );
    }

    private ModelAndView buildErrorModelAndView(String message, HttpStatus status, 
                                              String errorType, WebRequest webRequest) {
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("timestamp", LocalDateTime.now());
        mav.addObject("status", status.value());
        mav.addObject("error", errorType);
        mav.addObject("message", message);
        mav.addObject("path", webRequest.getDescription(false).replace("uri=", ""));
        return mav;
    }
}
