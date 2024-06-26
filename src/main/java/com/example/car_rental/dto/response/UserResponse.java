package com.example.car_rental.dto.response;

import com.example.car_rental.enums.UserRoles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private long id;
    private String username;
    private String password;
    private String email;
    private UserRoles userRoles;

}
