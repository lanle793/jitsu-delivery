package com.jitsu.delivery.api.service;

import com.jitsu.delivery.api.model.NominatimResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddressServiceTest {
    @Mock
    private RestTemplate restTemplate;
    @InjectMocks
    private AddressService addressService;

    @Test
    void isGeoCodingValid() {
        double latitude = 13.1;
        double longitude = 21.5;
        NominatimResponse response = new NominatimResponse("testAddress");

        when(restTemplate.getForObject(anyString(), eq(NominatimResponse.class))).thenReturn(response);

        assertTrue(addressService.isGeoCodingValid(latitude, longitude));

        when(restTemplate.getForObject(anyString(), eq(NominatimResponse.class))).thenReturn(null);

        assertFalse(addressService.isGeoCodingValid(latitude, longitude));
    }

    @Test
    void isValidRegionCode() {
        assertTrue(addressService.isValidRegionCode("US"));
        assertFalse(addressService.isValidRegionCode("DVD"));
    }
}