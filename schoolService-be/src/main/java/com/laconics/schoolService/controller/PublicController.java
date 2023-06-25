package com.laconics.schoolService.controller;

import com.laconics.schoolService.DTO.Authentification.AuthenticationDTO;
import com.laconics.schoolService.entity.User;
import com.laconics.schoolService.exception.CustomExceptions;
import com.laconics.schoolService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.RoleNotFoundException;

@RestController
@RequestMapping("/public")
public class PublicController {

    private final UserService userService;


    @Autowired
    public PublicController(UserService userService) {
        this.userService = userService;

    }
    @PostMapping("/login")
    public ResponseEntity<String> authenticate(@RequestBody AuthenticationDTO user) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", userService.authenticate(user));
        return new ResponseEntity<>("OK", headers, HttpStatus.OK);
    }
}