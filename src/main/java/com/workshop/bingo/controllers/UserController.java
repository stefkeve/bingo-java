package com.workshop.bingo.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
public class UserController {
    @GetMapping("/user")
    public Map<String,String> getUser(@Parameter(hidden = true) 
        @AuthenticationPrincipal OAuth2User principal) throws Exception {
        Map<String, String> response = new HashMap<String,String>();
        response.put("name", principal.getAttribute("name"));
        return response;
    }
}