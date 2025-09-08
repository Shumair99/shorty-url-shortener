package com.shumair99.shorty;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShortyController {
    @RequestMapping("/home")
    public String home() {
        return "Welcome to Shorty URL Shortener!";
    }
}
