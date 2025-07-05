package com.example.library_management_utspbold.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/login")
    public String login() {
        return "login"; 
    }
    @GetMapping("/")
    public String home() {
        return "index";
    }
    
    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login?logout";
    }
}