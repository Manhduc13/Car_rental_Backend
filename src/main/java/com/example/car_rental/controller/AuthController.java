package com.example.car_rental.controller;

import com.example.car_rental.dto.request.SignInRequest;
import com.example.car_rental.dto.request.SignUpRequest;
import com.example.car_rental.dto.response.AuthenticationResponse;
import com.example.car_rental.dto.response.UserResponse;
import com.example.car_rental.entity.User;
import com.example.car_rental.repository.UserRepository;
import com.example.car_rental.service.auth.AuthService;
import com.example.car_rental.service.jwt.UserService;
import com.example.car_rental.utils.JWTUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {

    AuthService authService;
    AuthenticationManager authenticationManager;
    UserService userService;
    JWTUtil jwtUtil;
    UserRepository userRepository;

    @PostMapping("/signup")
    public ResponseEntity<?> createUser(@RequestBody SignUpRequest request){

        if(authService.emailExisted(request.getEmail()))
            return new ResponseEntity<>("Customer with this email is already existed", HttpStatus.NOT_ACCEPTABLE);

        UserResponse userResponse = authService.createUser(request);
        if(userResponse == null) return new ResponseEntity<>("Customer can not created", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(userResponse,HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public AuthenticationResponse createAuthenticationToken(@RequestBody SignInRequest request) throws BadCredentialsException, DisabledException, UsernameNotFoundException {

        // Check user email and password
        //**************************************************************************************************************
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),
                    request.getPassword()));
        } catch (BadCredentialsException e){
            throw new BadCredentialsException("Incorrect username or password");
        }
        //**************************************************************************************************************

        // Get user info and generate token
        //**************************************************************************************************************
        final UserDetails userDetails = userService.userDetailsService().loadUserByUsername(request.getEmail());
        Optional<User> optionalUser = userRepository.findByEmail(userDetails.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);
        //**************************************************************************************************************

        // Build authentication response
        //**************************************************************************************************************
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        if(optionalUser.isPresent()){
            authenticationResponse.setJwt(jwt);
            authenticationResponse.setUserId(optionalUser.get().getId());
            authenticationResponse.setUserRoles(optionalUser.get().getUserRoles());
        }
        //**************************************************************************************************************
        return authenticationResponse;
    }
}
