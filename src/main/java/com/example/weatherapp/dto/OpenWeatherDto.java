package com.example.weatherapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

@Data
public class OpenWeatherDto {
    @JsonProperty("coord")
    private Map<String, Object> coordinate;
    @JsonProperty("main")
    private Map<String, Object> mainProperties;
    @JsonProperty("sys")
    private Map<String, Object> countryProperties;
    @JsonProperty("name")
    private String city;

    public Double getLat(){
        return coordinate != null ? (Double) coordinate.get("lat") : null;
    }
    public Double getLon(){
        return coordinate != null ? (Double) coordinate.get("lon") : null;
    }
    public String getCountry() {
        return countryProperties != null ? (String) countryProperties.get("country") : null;
    }

    public Double getFeelsLike() {
        return mainProperties != null ? (Double) mainProperties.get("feels_like") : null;
    }

    public Double getTemp() {
        return mainProperties != null ? (Double) mainProperties.get("temp") : null;
    }
}
