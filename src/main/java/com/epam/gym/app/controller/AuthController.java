package com.epam.gym.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

//@RestController
public class AuthController {

//    Login (GET method)
//    a. Request
//    I. Username (required)
//   II. Password (required)
//    b. Response
//    I. 200 OK
    @GetMapping()
    void login() {

    }

//    Change Login (PUT method)
//    a. Request
//    I. Username (required)
//    II. Old Password (required)
//    III. New Password (required)
//    b. Response
//    I. 200 OK
    @PutMapping()
    void changePassword() {

    }
}
