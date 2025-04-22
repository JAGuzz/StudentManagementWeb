package com.web.studentsApp.controller;

import org.apache.coyote.BadRequestException;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.web.studentsApp.exception.ResourceNotFoundException;

@Controller
public class CustomErrController implements ErrorController{

    @GetMapping("/not-found/{id}")
    public String getResource(@PathVariable Long id) {
        if (id == 0) {
            throw new ResourceNotFoundException("Student ", "Id", id+"");
        }
        return "Resource not found: " + id;
    }

    @GetMapping("/bad-request")
    public String badRequestExample() throws BadRequestException {
        throw new BadRequestException("Invalid request values.");
    }

    @GetMapping("/server-error")
    public String serverErrorExample() {
        throw new RuntimeException("Unexpected internal server error.");
    }

}
