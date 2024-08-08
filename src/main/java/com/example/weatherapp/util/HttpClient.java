package com.example.weatherapp.util;

import com.example.weatherapp.dto.OpenWeatherDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
@RequiredArgsConstructor
public class HttpClient {
    private final ObjectMapper mapper;
    @Value("${api.header}")
    private String key;
    @Value("${api.key.value}")
    private String value;

    public <T> T get(String url, Class<T> clazz) {
        HttpGet request = new HttpGet(url);
        request.addHeader(key, value);
        if (OpenWeatherDto.class.isAssignableFrom(clazz)) {
            request.removeHeaders(key);
        }
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            InputStream content = client.execute(request).getEntity().getContent();
            return mapper.readValue(content, clazz);
        } catch (IOException e) {
            throw new RuntimeException("Can't findCity data by url: " + url);
        }
    }
}
