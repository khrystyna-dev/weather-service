package com.example.weatherapp.impl;

import com.example.weatherapp.dto.CityDto;
import com.example.weatherapp.dto.SpecializedWeatherDto;
import com.example.weatherapp.exception.CustomException;
import com.example.weatherapp.model.Weather;
import com.example.weatherapp.service.CityService;
import com.example.weatherapp.service.SpecializedWeatherService;
import com.example.weatherapp.service.WeatherService;
import com.example.weatherapp.service.impl.WeatherServiceLatLonImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class WeatherServiceLatLonImplTest {
    private CityService cityService;
    private SpecializedWeatherService specializedWeatherService;
    private WeatherService weatherService;

    @BeforeEach
    void setUp() {
        cityService = Mockito.mock(CityService.class);
        specializedWeatherService = Mockito.mock(SpecializedWeatherService.class);
        weatherService = new WeatherServiceLatLonImpl(cityService, specializedWeatherService);
    }

    @Test
    public void getWeather_ok() {
        String param = "latitude-longitude";
        String latitude = "latitude";
        String longitude = "longitude";

        CityDto cityDto = new CityDto();
        SpecializedWeatherDto specializedWeatherDto = new SpecializedWeatherDto();

        when(cityService.getCity(latitude, longitude)).thenReturn(cityDto);
        when(specializedWeatherService.getSpecializedWeather(latitude, longitude)).thenReturn(specializedWeatherDto);

        Weather resultWeather = weatherService.getWeather(param);
        assertNotNull(resultWeather);
    }

    @Test
    public void getWeather_notOk() {
        String param = "longitude-latitude";
        String latitude = "latitude";
        String longitude = "longitude";

        when(cityService.getCity(latitude, longitude)).thenReturn(null);

        CustomException exception = assertThrows(CustomException.class, () ->
                weatherService.getWeather(param)
        );
        assertEquals("according to the longitude: " + longitude + " and latitude: " + latitude + " coordinates, the city was not found", exception.getMessage());
    }
}