package com.example.car_rental.service.customer;

import com.example.car_rental.dto.request.BookCarRequest;
import com.example.car_rental.dto.response.CarResponse;
import com.example.car_rental.entity.BookCar;
import com.example.car_rental.entity.Car;
import com.example.car_rental.entity.User;
import com.example.car_rental.enums.BookCarStatus;
import com.example.car_rental.repository.BookCarRepository;
import com.example.car_rental.repository.CarRepository;
import com.example.car_rental.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomerServiceImpl implements CustomerService {
    CarRepository carRepository;
    UserRepository userRepository;
    BookCarRepository bookCarRepository;

    @Override
    public List<CarResponse> getAllCars() {
        return carRepository.findAll().stream().map(Car::getCarResponse).collect(Collectors.toList());
    }

    @Override
    public CarResponse getCarbyId(Long id) {
        Optional<Car> car = carRepository.findById(id);
        return car.map(Car::getCarResponse).orElse(null);
    }

    @Override
    public boolean bookCar(BookCarRequest request) {
        Optional<Car> car = carRepository.findById(request.getCarId());
        Optional<User> user = userRepository.findById(request.getUserId());
        if(car.isPresent() && user.isPresent()){
            BookCar bookCar = new BookCar();
            bookCar.setUser(user.get());
            bookCar.setCar(car.get());
            bookCar.setStatus(BookCarStatus.PENDING);
            long diffInMilliSeconds = bookCar.getEndDate().getTime() - bookCar.getStartDate().getTime();
            long days = TimeUnit.MILLISECONDS.toDays(diffInMilliSeconds);
            bookCar.setDays(days);
            bookCar.setPrice(car.get().getPrice() * days);

            bookCarRepository.save(bookCar);
            return true;
        }
        return false;
    }
}
