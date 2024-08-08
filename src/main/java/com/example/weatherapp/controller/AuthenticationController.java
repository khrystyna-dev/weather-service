package com.example.weatherapp.controller;

import com.example.weatherapp.dto.UserLoginDto;
import com.example.weatherapp.dto.UserRegisterDto;
import com.example.weatherapp.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthenticationController {
    private final String mainPageUrl;
    private final String mailUrl;
    private final AuthenticationService authService;

    public AuthenticationController(@Value("${api.default.url}") String mainPageUrl,
                                    @Value("${api.mail.url}") String mailUrl,
                                    AuthenticationService authService) {
        this.mainPageUrl = mainPageUrl;
        this.mailUrl = mailUrl;
        this.authService = authService;
    }

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("userLoginDto", new UserLoginDto());
        return "login";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("userRegisterDto", new UserRegisterDto());
        return "register";
    }

    @PostMapping("/register")
    public String registerProcessForm(@Valid UserRegisterDto dto,
                                      BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("userRegisterDto", dto);
            return "register";
        }
        authService.register(dto);
        model.addAttribute("url", String.format(
                mailUrl,dto.getEmail().substring(dto.getEmail().indexOf("@"))));
        model.addAttribute("mainPage", mainPageUrl);
        return "verification";
    }

    @GetMapping("/")
    public String getHome() {
        return "redirect:/weather";
    }

    @GetMapping("/verified")
    public String verifiedUser(@RequestParam String code) {
        authService.verification(code);
        return "redirect:/login";
    }
}
