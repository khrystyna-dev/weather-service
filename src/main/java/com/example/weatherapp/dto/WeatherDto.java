package com.example.weatherapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

@Data
public class WeatherDto {
    @JsonProperty("location")
    private Map<String, Object> location;

    @JsonProperty("current")
    private Map<String, Object> current;

    public String getCity() {
        return location != null ? (String) location.get("name") : null;
    }

    public String getCountry() {
        return location != null ? (String) location.get("country") : null;
    }

    public Double getTemp() {
        return current != null ? (Double) current.get("temp_c") : null;
    }

    public Double getLat() {
        return location != null ? (Double) location.get("lat") : null;
    }

    public Double getLon() {
        return location != null ? (Double) location.get("lon") : null;
    }

    public Double getFeelsLike() {
        return current != null ? (Double) current.get("feelslike_c") : null;
    }
}