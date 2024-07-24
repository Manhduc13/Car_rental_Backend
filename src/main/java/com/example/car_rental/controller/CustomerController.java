package com.example.car_rental.controller;

import com.example.car_rental.dto.request.BookCarRequest;
import com.example.car_rental.dto.request.SearchCarRequest;
import com.example.car_rental.dto.response.BookCarResponse;
import com.example.car_rental.dto.response.CarResponse;
import com.example.car_rental.service.customer.CustomerService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CustomerController {
    CustomerService customerService;

    @GetMapping("/cars")
    public ResponseEntity<List<CarResponse>> getAllCars(){
        List<CarResponse> carList = customerService.getAllCars();
        return ResponseEntity.ok(carList);
    }

    @PostMapping("/cars/book")
    public ResponseEntity<Void> bookACar(@RequestBody BookCarRequest request){
        boolean success = customerService.bookCar(request);
        if(success){
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/cars/{id}")
    public ResponseEntity<CarResponse> getCarById(@PathVariable Long id){
        CarResponse carResponse = customerService.getCarById(id);
        if(carResponse == null){
            return ResponseEntity.notFound().build();
        } else{
            return ResponseEntity.ok(carResponse);
        }
    }

    @GetMapping("/cars/bookings/{userId}")
    public ResponseEntity<List<BookCarResponse>> getBookingsByUserId(@PathVariable Long userId){
        return ResponseEntity.ok(customerService.getBookingsByUserId(userId));
    }

    @PostMapping("/cars/search")
    public ResponseEntity<List<CarResponse>> searchCar(@RequestBody SearchCarRequest request){

        return ResponseEntity.ok(customerService.searchCar(request));
    }
}
