package com.example.car_rental.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarResponse {
    private Long id;
    private String brand;
    private String name;
    private String type;
    private String transmission;
    private String color;
    private String description;
    private Long price;
    private Date year;
    private byte[] img;
    private MultipartFile returnedImage;
}
