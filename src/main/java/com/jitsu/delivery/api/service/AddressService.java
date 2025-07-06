package com.jitsu.delivery.api.service;

import com.jitsu.delivery.api.model.NominatimResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.Locale;

@Service
public class AddressService {
    public boolean isGeoCodingValid(double latitude, double longitude) {
        // use Nominatim OpenStreetMap API to validate geocoding
        final RestTemplate restTemplate = new RestTemplate();
        String url = UriComponentsBuilder
                .newInstance()
                .scheme("https")
                .host("nominatim.openstreetmap.org")
                .path("/reverse")
                .queryParam("lat", latitude)
                .queryParam("lon", longitude)
                .queryParam("format", "json")
                .build()
                .toUriString();
        try {
            NominatimResponse response = restTemplate.getForObject(url, NominatimResponse.class);
            return response != null && response.getDisplayName() != null;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isValidRegionCode(String code) {
        String upperCode = code.toUpperCase();
        return Arrays.asList(Locale.getISOCountries()).contains(upperCode);
    }

}
