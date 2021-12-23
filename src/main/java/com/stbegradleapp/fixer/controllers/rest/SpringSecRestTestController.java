package com.stbegradleapp.fixer.controllers.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app")
public class SpringSecRestTestController {

    @GetMapping("/admin")
    public String availableOnAuthUsers() {
        return "User is auth";
    }
}
