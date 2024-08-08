package com.example.weatherapp;

import com.example.weatherapp.controller.AuthenticationController;
import com.example.weatherapp.dto.UserRegisterDto;
import com.example.weatherapp.service.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@WebMvcTest(AuthenticationController.class)
public class AuthenticationControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AuthenticationService service;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(
                new AuthenticationController("http://localhost:8091/", "http://%s", service))
                .build();
    }

    @Test
    public void testRegisterProcessForm_Success() throws Exception {
        UserRegisterDto userRegisterDto = new UserRegisterDto();
        userRegisterDto.setEmail("test@example.com");
        userRegisterDto.setFirstName("First");
        userRegisterDto.setLastName("Last");
        userRegisterDto.setPassword("12345678");

        mockMvc.perform(MockMvcRequestBuilders.post("/register")
               .flashAttr("userRegisterDto", userRegisterDto))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.view().name("verification"));
    }


    @Test
    public void testVerifiedUser() throws Exception {
        String verificationCode = "12345678";
        mockMvc.perform(MockMvcRequestBuilders.get("/verified")
               .param("code", verificationCode))
               .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
               .andExpect(MockMvcResultMatchers.redirectedUrl("/login"));
    }
}
