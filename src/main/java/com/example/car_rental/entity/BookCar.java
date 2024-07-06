package com.example.car_rental.entity;

import com.example.car_rental.dto.request.BookCarResponse;
import com.example.car_rental.enums.BookCarStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookCar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date startDate;
    private Date endDate;
    private Long days;
    private Long price;
    private BookCarStatus status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "car_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Car car;

    public BookCarResponse getBookCarResponse(){
        BookCarResponse bookCarResponse = new BookCarResponse();
        bookCarResponse.setId(id);
        bookCarResponse.setStartDate(startDate);
        bookCarResponse.setEndDate(endDate);
        bookCarResponse.setDays(days);
        bookCarResponse.setPrice(price);
        bookCarResponse.setStatus(status);
        bookCarResponse.setCarId(car.getId());
        bookCarResponse.setUserId(user.getId());
        bookCarResponse.setUserEmail(user.getEmail());
        return bookCarResponse;
    }
}
