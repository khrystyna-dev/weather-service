package com.example.weatherapp.service;

import com.example.weatherapp.dto.CityDto;

public interface CityService {
    CityDto getCity(String latitude, String longitude);

}
