package com.example.car_rental.repository;

import com.example.car_rental.entity.BookCar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookCarRepository extends JpaRepository<BookCar, Long> {
    List<BookCar> findAllByUserId(Long userId);
}
