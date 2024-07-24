package com.example.car_rental.service.auth;

import com.example.car_rental.dto.request.SignUpRequest;
import com.example.car_rental.dto.response.UserResponse;

public interface AuthService {
    UserResponse createUser (SignUpRequest request);
    boolean emailExisted(String email);
}
