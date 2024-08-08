package com.example.weatherapp.service.impl;

import com.example.weatherapp.dto.CityDto;
import com.example.weatherapp.dto.SpecializedWeatherDto;
import com.example.weatherapp.exception.CustomException;
import com.example.weatherapp.mapper.WeatherMapper;
import com.example.weatherapp.model.Weather;
import com.example.weatherapp.service.CityService;
import com.example.weatherapp.service.SpecializedWeatherService;
import com.example.weatherapp.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
@Primary
public class WeatherServiceLatLonImpl implements WeatherService {
    private final static String SEPARATOR = "-";
    private final CityService cityService;
    private final SpecializedWeatherService service;
    @Override
    public Weather getWeather(String param) {
        CityDto city = cityService.getCity(param.split(SEPARATOR)[0], param.split(SEPARATOR)[1]);
        if (isNull(city)) {
            throw new CustomException("according to the longitude: " + param.split(SEPARATOR)[0] + " and latitude: "
                    + param.split(SEPARATOR)[1] + " coordinates, the city was not found");
        }
        SpecializedWeatherDto specialized = service.getSpecializedWeather(param.split(SEPARATOR)[0], param.split(SEPARATOR)[1]);
        return WeatherMapper.INSTANCE.toWeather(specialized, city);
    }
}
