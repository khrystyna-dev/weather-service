package com.example.weatherapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SpecializedWeatherDto {
        @JsonProperty("temp")
        private Double temp;
        @JsonProperty("feels_like")
        private Double feelsLike;

}
