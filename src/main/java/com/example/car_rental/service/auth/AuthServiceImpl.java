package com.example.car_rental.service.auth;

import com.example.car_rental.dto.request.SignUpRequest;
import com.example.car_rental.dto.response.UserResponse;
import com.example.car_rental.entity.User;
import com.example.car_rental.enums.UserRoles;
import com.example.car_rental.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final UserRepository userRepository;

    @PostConstruct
    public void createAdminAccount(){
        User admin = userRepository.findByUserRoles(UserRoles.ADMIN);
        if(admin == null){
            User newAdmin = new User();
            newAdmin.setUsername("admin");
            newAdmin.setEmail("admin@gmail.com");
            newAdmin.setPassword(new BCryptPasswordEncoder().encode("admin"));
            newAdmin.setUserRoles(UserRoles.ADMIN);
            userRepository.save(newAdmin);
            System.out.println("Admin account has been created.");
        }
    }

    @Override
    public boolean emailExisted(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    @Override
    public UserResponse createUser(SignUpRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(new BCryptPasswordEncoder().encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setUserRoles(UserRoles.CUSTOMER);

        User createdUser = userRepository.save(user);
        UserResponse userResponse = new UserResponse();
        userResponse.setId(createdUser.getId());
        userResponse.setUsername(createdUser.getUsername());
        userResponse.setPassword(createdUser.getPassword());
        userResponse.setEmail(createdUser.getEmail());
        userResponse.setUserRoles(createdUser.getUserRoles());

        return userResponse;
    }
}
