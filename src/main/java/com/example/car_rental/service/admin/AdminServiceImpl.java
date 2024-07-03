package com.example.car_rental.service.admin;

import com.example.car_rental.dto.request.PostCarRequest;
import com.example.car_rental.dto.response.CarResponse;
import com.example.car_rental.entity.Car;
import com.example.car_rental.repository.CarRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AdminServiceImpl implements  AdminService{
    CarRepository carRepository;

    @Override
    public List<CarResponse> getAllCars() {
        return carRepository.findAll().stream().map(Car::getCarResponse).collect(Collectors.toList());
    }

    @Override
    public boolean updateCar(Long id, CarResponse carResponse) throws IOException {
        Optional<Car> car = carRepository.findById(id);
        if(car.isPresent()){
            Car existingCar = car.get();
            if(carResponse.getImage() != null){
                existingCar.setImage(carResponse.getImage().getBytes());
            }
            existingCar.setName(carResponse.getName());
            existingCar.setBrand(carResponse.getBrand());
            existingCar.setType(carResponse.getType());
            existingCar.setYear(carResponse.getYear());
            existingCar.setPrice(carResponse.getPrice());
            existingCar.setColor(carResponse.getColor());
            existingCar.setTransmission(carResponse.getTransmission());
            existingCar.setDescription(carResponse.getDescription());

            carRepository.save(existingCar);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void  deleteCar(Long id) {
        carRepository.deleteById(id);
    }

    @Override
    public CarResponse getCarbyId(Long id) {
        Optional<Car> car = carRepository.findById(id);
        return car.map(Car::getCarResponse).orElse(null);
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
