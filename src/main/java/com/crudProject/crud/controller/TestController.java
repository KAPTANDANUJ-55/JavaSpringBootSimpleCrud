package com.crudProject.crud.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/")
public class TestController {
    @GetMapping("/public")
    public String publicEndpoint(HttpServletRequest request) {
        return "Public endpoint: Koi bhi access kar sakta hai!"+request.getSession().getId();
    }

    // Login ke baad normal user ya admin dono access kar sakein
    @GetMapping("/user")
    public String userEndpoint(HttpServletRequest request) {
        return "User endpoint: Welcome logged-in user!"+request.getSession().getId();
    }

    // Sirf ADMIN access kar sake
    @GetMapping("/admin")
    public String adminEndpoint(HttpServletRequest request) {
        return "Admin endpoint: Welcome BOSS!"+ request.getSession().getId();
    }
}
