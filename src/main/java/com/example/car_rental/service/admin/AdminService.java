package com.example.car_rental.service.admin;

import com.example.car_rental.dto.CarDtoListDto;
import com.example.car_rental.dto.request.PostCarRequest;
import com.example.car_rental.dto.request.SearchCarRequest;
import com.example.car_rental.dto.response.BookCarResponse;
import com.example.car_rental.dto.response.CarResponse;

import java.io.IOException;
import java.util.List;

public interface AdminService {
    boolean postCar(PostCarRequest request) throws IOException;
    List<CarResponse> getAllCars();
    void deleteCar(Long id);
    CarResponse getCarById(Long id);
    boolean updateCar(Long id, CarResponse carResponse) throws IOException;
    List<BookCarResponse> getBookings();
    boolean changeBookingStatus(Long bookingId, String status);
    CarDtoListDto searchCar(SearchCarRequest request);
}
