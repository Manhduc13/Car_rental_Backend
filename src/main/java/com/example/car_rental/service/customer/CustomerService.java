package com.example.car_rental.service.customer;

import com.example.car_rental.dto.request.BookCarRequest;
import com.example.car_rental.dto.response.CarResponse;

import java.util.List;

public interface CustomerService {
    List<CarResponse> getAllCars();
    boolean bookCar(BookCarRequest request);
    CarResponse getCarbyId(Long id);
}
