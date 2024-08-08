package com.example.weatherapp.mapper;

import com.example.weatherapp.dto.CityDto;
import com.example.weatherapp.dto.OpenWeatherDto;
import com.example.weatherapp.dto.SpecializedWeatherDto;
import com.example.weatherapp.dto.WeatherDto;
import com.example.weatherapp.model.Weather;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface WeatherMapper {
    WeatherMapper INSTANCE = Mappers.getMapper(WeatherMapper.class);

    @Mapping(source = "specialized.temp", target = "temp")
    @Mapping(source = "specialized.feelsLike", target = "feelsLike")
    @Mapping(source = "city.city", target = "city")
    @Mapping(source = "city.country", target = "country")
    @Mapping(source = "city.lat", target = "lat")
    @Mapping(source = "city.lon", target = "lon")
    Weather toWeather(SpecializedWeatherDto specialized, CityDto city);

    Weather toWeather(WeatherDto weatherDto);

    Weather toWeather(OpenWeatherDto openWeatherDto);
}