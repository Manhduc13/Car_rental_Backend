package com.example.car_rental.service.auth;

import com.example.car_rental.dto.request.SignupRequest;
import com.example.car_rental.dto.response.UserResponse;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    UserResponse createUser (SignupRequest request);
    boolean emailExisted(String email);
}
