package com.example.car_rental.service.customer;

import com.example.car_rental.dto.request.BookCarRequest;
import com.example.car_rental.dto.request.SearchCarRequest;
import com.example.car_rental.dto.response.BookCarResponse;
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
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
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
    public List<CarResponse> searchCar(SearchCarRequest request) {
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

        return carList.stream().map(Car::getCarResponse).collect(Collectors.toList());
    }

    @Override
    public List<CarResponse> getAllCars() {
        return carRepository.findAll().stream().map(Car::getCarResponse).collect(Collectors.toList());
    }

    @Override
    public List<BookCarResponse> getBookingsByUserId(Long userId) {
        return bookCarRepository.findAllByUserId(userId).stream().map(BookCar::getBookCarResponse).collect(Collectors.toList());
    }

    @Override
    public CarResponse getCarById(Long id) {
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
            bookCar.setEndDate((request.getEndDate()));
            bookCar.setStartDate(request.getStartDate());
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
