package com.example.car_rental.repository;

import com.example.car_rental.entity.User;
import com.example.car_rental.enums.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    User findByUserRoles (UserRoles userRoles);
}
