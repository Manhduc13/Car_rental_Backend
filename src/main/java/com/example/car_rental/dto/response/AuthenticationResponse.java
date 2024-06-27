package com.example.car_rental.dto.response;

import com.example.car_rental.enums.UserRoles;
import lombok.Data;

@Data
public class AuthenticationResponse {
    private String jwt;
    private UserRoles userRoles;
    private Long userId;
}
