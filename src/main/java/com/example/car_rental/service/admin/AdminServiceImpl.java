package com.example.car_rental.service.admin;

import com.example.car_rental.dto.CarDtoListDto;
import com.example.car_rental.dto.request.PostCarRequest;
import com.example.car_rental.dto.request.SearchCarRequest;
import com.example.car_rental.dto.response.BookCarResponse;
import com.example.car_rental.dto.response.CarResponse;
import com.example.car_rental.entity.BookCar;
import com.example.car_rental.entity.Car;
import com.example.car_rental.enums.BookCarStatus;
import com.example.car_rental.repository.BookCarRepository;
import com.example.car_rental.repository.CarRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AdminServiceImpl implements  AdminService{
    CarRepository carRepository;
    private final BookCarRepository bookCarRepository;

    @Override
    public boolean changeBookingStatus(Long bookingId, String status) {
        Optional<BookCar> bookCar = bookCarRepository.findById(bookingId);
        if(bookCar.isPresent()){
            BookCar existedBookCar = bookCar.get();
            if(Objects.equals(status, "Approve")){
                existedBookCar.setStatus(BookCarStatus.APPROVED);
            } else {
                existedBookCar.setStatus(BookCarStatus.REJECTED);
            }
            bookCarRepository.save(existedBookCar);
            return true;
        }
        return false;
    }

    @Override
    public List<BookCarResponse> getBookings() {
        return bookCarRepository.findAll().stream().map(BookCar::getBookCarResponse).collect(Collectors.toList());
    }

    @Override
    public CarDtoListDto searchCar(SearchCarRequest request) {
        Car car = new Car();

        car.setBrand(request.getBrand());
        car.setType(request.getType());
        car.setTransmission(request.getTransmission());
        car.setColor(request.getColor());

        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll()
                .withMatcher("brand", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("type", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("transmission", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("color", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());

        Example<Car> carExample = Example.of(car, exampleMatcher);
        List<Car> carList = carRepository.findAll(carExample);
        CarDtoListDto carDtoListDto = new CarDtoListDto();
        carDtoListDto.setCarResponseList(carList.stream().map(Car::getCarResponse).collect(Collectors.toList()));
        return carDtoListDto;
    }

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
    public CarResponse getCarById(Long id) {
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
