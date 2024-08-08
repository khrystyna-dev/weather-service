package com.example.weatherapp.service.impl;

import com.example.weatherapp.dto.CityDto;
import com.example.weatherapp.exception.CustomException;
import com.example.weatherapp.service.CityService;
import com.example.weatherapp.util.HttpClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {
    private final static Double OVERSIGHT = 0.01;
    private final HttpClient client;
    @Value("${api.city.url}")
    private String urlCity;
    @Value("${api.default.lat}")
    private String defaultLat;
    @Value("${api.default.lon}")
    private String defaultLon;
    @Value("${api.default.lat-min}")
    private String defaultLatMin;
    @Value("${api.default.lon-min}")
    private String defaultLonMin;

    public CityDto getCity(String latitude, String longitude) {
        return Arrays.stream(client.get(formatUrl(urlCity, latitude, longitude), CityDto[].class)).findFirst().orElseThrow(
                () -> new CustomException("Can`t find city")
        );
    }

    public String formatUrl(String urlCity, String latitude, String longitude) {
        return urlCity.replaceFirst(defaultLonMin, String.valueOf(Double.parseDouble(longitude) - OVERSIGHT))
                .replaceFirst(defaultLatMin, String.valueOf(Double.parseDouble(latitude) - OVERSIGHT))
                .replaceFirst(defaultLon, String.valueOf(Double.parseDouble(longitude) + OVERSIGHT))
                .replaceFirst(defaultLat, String.valueOf(Double.parseDouble(latitude) + OVERSIGHT));
    }
}
