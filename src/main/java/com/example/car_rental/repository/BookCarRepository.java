package com.example.car_rental.repository;

import com.example.car_rental.entity.BookCar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookCarRepository extends JpaRepository<BookCar, Long> {
}
