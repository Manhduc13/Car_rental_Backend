package com.example.car_rental.controller;

import com.example.car_rental.dto.request.SignupRequest;
import com.example.car_rental.dto.response.UserResponse;
import com.example.car_rental.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<?> createUser(@RequestBody SignupRequest request){

        if(authService.emailExisted(request.getEmail()))
            return new ResponseEntity<>("Customer with this email is already existed", HttpStatus.NOT_ACCEPTABLE);

        UserResponse userResponse = authService.createUser(request);
        if(userResponse == null) return new ResponseEntity<>("Customer can not created", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(userResponse,HttpStatus.CREATED);
    }
}
