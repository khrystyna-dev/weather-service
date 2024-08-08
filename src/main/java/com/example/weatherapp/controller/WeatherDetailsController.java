package com.example.weatherapp.controller;

import com.example.weatherapp.dto.InputDataDto;
import com.example.weatherapp.model.Weather;
import com.example.weatherapp.service.CheckParamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class WeatherDetailsController {
    private final CheckParamService service;
    private Weather weather;

    @GetMapping("/weather")
    public ModelAndView inputFrom() {
        ModelAndView modelAndView = new ModelAndView("input");
        modelAndView.addObject("inputDataDto", new InputDataDto());
        return modelAndView;
    }

    @PostMapping("/processForm")
    public ModelAndView processForm(@Valid @ModelAttribute("inputDataDto") InputDataDto dto) {
        weather = service.getWeather(dto);
        ModelAndView modelAndView = new ModelAndView("response_short");
        modelAndView.addObject("weather", weather);

        return modelAndView;
    }

    @PostMapping("/specializedFrom")
    public ModelAndView specializedFrom() {
        ModelAndView modelAndView = new ModelAndView("response");
        modelAndView.addObject("weather", weather);

        return modelAndView;
    }

}
