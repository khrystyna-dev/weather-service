package com.example.weatherapp.service;

import com.example.weatherapp.dto.SpecializedWeatherDto;

public interface SpecializedWeatherService {
    SpecializedWeatherDto getSpecializedWeather(String latitude, String longitude);

}
