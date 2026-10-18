package com.carbontrack.carbontrack;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // Open CarbonTrack Dashboard
    @GetMapping("/")
    public String home() {
        return "index";
    }

    // Open Login Page
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // Open Registration Page
    @GetMapping("/register")
    public String register() {
        return "register";
    }
}