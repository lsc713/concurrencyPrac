package com.service.concurrencyprac.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Health {

    @GetMapping("/health")
    public ResponseEntity health() {
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
