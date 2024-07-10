package com.example.car_rental.dto;

import com.example.car_rental.dto.response.CarResponse;
import lombok.Data;

import java.util.List;

@Data
public class CarDtoListDto {
    private List<CarResponse> carResponseList;
}
