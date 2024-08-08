package com.example.weatherapp.model;

import lombok.Data;

@Data
public class Weather {
        private String city;
        private String country;
        private Double temp;
        private Double lat;
        private Double lon;
        private Double feelsLike;
}
