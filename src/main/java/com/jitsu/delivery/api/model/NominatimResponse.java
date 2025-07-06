package com.jitsu.delivery.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class NominatimResponse {
    @JsonProperty("display_name")
    private String displayName;
}
