package com.web.studentsApp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping({"/","/students"})
    public String index() {
        return "index";
    }
}
