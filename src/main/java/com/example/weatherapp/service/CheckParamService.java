package com.example.weatherapp.service;

import com.example.weatherapp.dto.CityDto;
import com.example.weatherapp.dto.InputDataDto;
import com.example.weatherapp.exception.CustomException;
import com.example.weatherapp.model.Weather;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;


@Service
@RequiredArgsConstructor
public class CheckParamService {
    private final static String SEPARATOR = "-";
    private final WeatherStrategy strategy;
    private final CityService cityService;
    private final WeatherService weatherService;

    public Weather getWeather(InputDataDto dto) {
        try {
            if (!dto.getCity().isEmpty()) {
                if (dto.getLat().isEmpty() && dto.getLon().isEmpty()) {
                    return getWeatherByCity(dto.getCity());
                } else if (!dto.getLat().isEmpty() && !dto.getLon().isEmpty()) {
                    return getWeatherByCityAndLatLon(dto);
                }
            } else if (!dto.getLat().isEmpty() && !dto.getLon().isEmpty()) {
                return getWeatherByLatLon(dto);
            }
            throw new CustomException("Please enter the required information: the name of the city, or its coordinates");
        } catch (NumberFormatException e) {
            throw new CustomException("The data entered is invalid! latitude: " + dto.getLat() + " or longitude: " + dto.getLon());
        }
    }

    private Weather getWeatherByCityAndLatLon(InputDataDto dto) {
        CityDto cityDto = cityService.getCity(dto.getLat(), dto.getLon());
        if (dto.getCity().equalsIgnoreCase(cityDto.getCity())) {
            return getWeatherByCity(dto.getCity());
        } else {
            throw new CustomException("The entered coordinates do not correspond to the name of the city");
        }
    }

    private Weather getWeatherByLatLon(InputDataDto dto) {
        return weatherService.getWeather(dto.getLat() + SEPARATOR + dto.getLon());
    }

    private Weather getWeatherByCity(String city) {
        Weather weather = strategy.getService(city).getWeather(city);
        if (isNull(weather.getCity())) {
            throw new CustomException("No weather found for this city: " + city + ", try to check the correct spelling");
        }
        return weather;
    }
}
