package com.example.weatherapp;

import com.example.weatherapp.controller.WeatherDetailsController;
import com.example.weatherapp.service.CheckParamService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Validator;
import org.springframework.web.servlet.ModelAndView;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WeatherDetailsController.class)
public class WeatherDetailsControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CheckParamService checkParamService;

    @Autowired
    private Validator validator;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new WeatherDetailsController(checkParamService))
                .setValidator(validator)
                .build();
    }

    @Test
    public void testInputForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/weather"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("input"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("inputDataDto"));
    }

    @Test
    public void testProcessForm() throws Exception {
//        InputDataDto mockDto = new InputDataDto();
//        Weather mockWeather = new Weather();
//        Mockito.when(checkParamService.getWeather(Mockito.any(InputDataDto.class))).thenReturn(mockWeather);
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/processForm")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(mockDto)))
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.view().name("response_short"))
//                .andExpect(MockMvcResultMatchers.model().attributeExists("weather"))
//                .andExpect(MockMvcResultMatchers.model().attribute("weather", mockWeather));
    }

    @Test
    public void testSpecializedForm() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/specializedFrom"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("response"))
                .andReturn();
        ModelAndView modelAndView = result.getModelAndView();
        Assertions.assertNotNull(modelAndView);
        Assertions.assertTrue(modelAndView.getModel().containsKey("weather"));
    }
}