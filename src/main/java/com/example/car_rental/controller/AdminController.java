package com.example.car_rental.controller;

import com.example.car_rental.dto.CarDtoListDto;
import com.example.car_rental.dto.request.PostCarRequest;
import com.example.car_rental.dto.request.SearchCarRequest;
import com.example.car_rental.dto.response.BookCarResponse;
import com.example.car_rental.dto.response.CarResponse;
import com.example.car_rental.service.admin.AdminService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AdminController {
    AdminService adminService;

    @PostMapping("/cars/post")
    public ResponseEntity<?> postCar(@ModelAttribute PostCarRequest request) throws IOException {
        log.info("Received request: {}", request);
        boolean success = adminService.postCar(request);
        if(success) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/cars")
    public ResponseEntity<?> getAllCars(){
        return ResponseEntity.ok(adminService.getAllCars());
    }

    @GetMapping("/cars/{id}")
    public ResponseEntity<CarResponse> getCarById(@PathVariable Long id){
        CarResponse carResponse = adminService.getCarById(id);
        return ResponseEntity.ok(carResponse);
    }

    @DeleteMapping("/cars/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long id){
        adminService.deleteCar(id);
        return ResponseEntity.ok(null);
    }

    @PutMapping("/cars/{id}")
    public ResponseEntity<Void> updateCar(@PathVariable Long id, @ModelAttribute CarResponse carResponse) throws IOException {
        try{
            boolean success = adminService.updateCar(id, carResponse);
            if(success){
                return ResponseEntity.status(HttpStatus.OK).build();
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/cars/bookings")
    public ResponseEntity<List<BookCarResponse>> getBookings(){
        List<BookCarResponse> bookingCars = adminService.getBookings();
        return ResponseEntity.ok(bookingCars);
    }

    @GetMapping("/cars/bookings/{id}/{status}")
    public ResponseEntity<?> changeBookingStatus(@PathVariable Long id, @PathVariable String status){
        boolean success = adminService.changeBookingStatus(id,status);
        if(success) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/cars/search")
    public ResponseEntity<CarDtoListDto> searchCar(@RequestBody SearchCarRequest request){
        CarDtoListDto carDtoListDto = adminService.searchCar(request);
        return ResponseEntity.ok(carDtoListDto);
    }
}
