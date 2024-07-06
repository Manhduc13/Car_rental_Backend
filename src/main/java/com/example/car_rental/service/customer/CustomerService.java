package com.example.car_rental.service.customer;

import com.example.car_rental.dto.request.BookCarRequest;
import com.example.car_rental.dto.request.BookCarResponse;
import com.example.car_rental.dto.response.CarResponse;

import java.util.List;

public interface CustomerService {
    List<CarResponse> getAllCars();
    boolean bookCar(BookCarRequest request);
    CarResponse getCarById(Long id);
    List<BookCarResponse> getBookingsByUserId(Long userId);
}
