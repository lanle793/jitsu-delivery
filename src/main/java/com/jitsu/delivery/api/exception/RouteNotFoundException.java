package com.jitsu.delivery.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RouteNotFoundException extends RuntimeException {
    public RouteNotFoundException(Long routeId) {
        super("Route not found with id: " + routeId);
    }

    public RouteNotFoundException(String message) {
        super(message);
    }
}
