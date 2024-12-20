package com.skrbomb.eCommerce.controller;


import com.skrbomb.eCommerce.dto.LoginRequest;
import com.skrbomb.eCommerce.dto.Response;
import com.skrbomb.eCommerce.dto.UserDto;
import com.skrbomb.eCommerce.service.interf.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Response> registerUser(@RequestBody UserDto registerRequest){
        Response response=userService.registerUser(registerRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Response> loginUser(@RequestBody LoginRequest loginRequest){
        Response response=userService.loginUser(loginRequest);
        return ResponseEntity.ok(response);
    }

}
