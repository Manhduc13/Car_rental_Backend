package com.example.car_rental.service.admin;

import com.example.car_rental.dto.request.PostCarRequest;

import java.io.IOException;

public interface AdminService {
    boolean postCar(PostCarRequest request) throws IOException;
}
