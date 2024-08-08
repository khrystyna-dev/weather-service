package com.example.weatherapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CityDto {
    @JsonProperty("name")
    private String city;
    @JsonProperty("latitude")
    private Double lat;
    @JsonProperty("longitude")
    private Double lon;
    private String country;
}
