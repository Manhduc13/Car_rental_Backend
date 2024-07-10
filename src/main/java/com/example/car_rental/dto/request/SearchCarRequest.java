package com.example.car_rental.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchCarRequest {
    private String brand;
    private String type;
    private String transmission;
    private String color;
}
