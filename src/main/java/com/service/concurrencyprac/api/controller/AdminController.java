package com.service.concurrencyprac.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/admin")
    public String adminPage() {
        return null;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
