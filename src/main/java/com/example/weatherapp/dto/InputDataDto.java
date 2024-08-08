package com.example.weatherapp.dto;

import com.example.weatherapp.lib.ValidDataLocation;
import lombok.Data;

@Data
public class InputDataDto {
        private String city;
        @ValidDataLocation
        private String lat;
        @ValidDataLocation
        private String lon;
}
