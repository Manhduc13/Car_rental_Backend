package com.example.car_rental.service.admin;

import com.example.car_rental.dto.request.PostCarRequest;
import com.example.car_rental.dto.response.CarResponse;
import com.example.car_rental.entity.Car;
import com.example.car_rental.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements  AdminService{
    private final CarRepository carRepository;

    @Override
    public List<CarResponse> getAllCars() {
        return carRepository.findAll().stream().map(Car::getCarResponse).collect(Collectors.toList());
    }

    @Override
    public void  deleteCar(Long id) {
        carRepository.deleteById(id);
    }

    @Override
    public boolean postCar(PostCarRequest request) throws IOException {
        try{
            Car car = new Car();

            car.setName(request.getName());
            car.setBrand(request.getBrand());
            car.setColor(request.getColor());
            car.setDescription(request.getDescription());
            car.setPrice(request.getPrice());
            car.setYear(request.getYear());
            car.setType(request.getType());
            car.setTransmission(request.getTransmission());
            car.setImage(request.getImage().getBytes());

            carRepository.save(car);

            return true;
        } catch(Exception e){
            return false;
        }
    }
}
