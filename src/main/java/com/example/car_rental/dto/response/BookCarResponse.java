package com.example.car_rental.dto.request;

import com.example.car_rental.enums.BookCarStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookCarResponse {
    private Long id;
    private Date startDate;
    private Date endDate;
    private Long days;
    private Long price;
    private BookCarStatus status;
    private Long carId;
    private Long userId;
    private String userEmail;
}
