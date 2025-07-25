package com.jitsu.delivery.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CostCalculationException extends RuntimeException {
    public CostCalculationException(String message) {
        super(message);
    }
}
