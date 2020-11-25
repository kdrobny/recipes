package com.example.recipes.rest;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/user")
public class UserController {

    @GetMapping
    public Principal user(Principal user) {
        return user;
    }

}
