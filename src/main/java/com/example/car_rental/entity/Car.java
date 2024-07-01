package com.example.car_rental.entity;

import com.example.car_rental.dto.response.CarResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.web.WebProperties;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String brand;
    private String name;
    private String type;
    private String transmission;
    private String color;
    private String description;
    private Long price;
    private Date year;
    @Column(columnDefinition = "longblob")
    private byte[] image;

    public CarResponse getCarResponse(){
        CarResponse carResponse = new CarResponse();

        carResponse.setId(id);
        carResponse.setName(name);
        carResponse.setColor(color);
        carResponse.setPrice(price);
        carResponse.setBrand(brand);
        carResponse.setType(type);
        carResponse.setYear(year);
        carResponse.setDescription(description);
        carResponse.setTransmission(transmission);
        carResponse.setReturnedImage(image);

        return carResponse;
    }
}
