package com.example.app;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.StringJoiner;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;

@RestControllerAdvice(annotations = RestController.class)
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RestExceptionHandlerAdvise {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> handleException(Model model, Exception e) {
        return ImmutableMap.of("summary", "Internal Server Error", "message", e.getMessage());
    }

    @ExceptionHandler(HttpClientErrorException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleHttpClientErrorException(Model model, HttpClientErrorException e)
            throws JsonParseException, JsonMappingException, IOException {
        Map<String, Object> body = new ObjectMapper().readValue(e.getResponseBodyAsString(),
                new TypeReference<Map<String, Object>>() {
                });
        ImmutableMap.Builder<String, Object> builder = ImmutableMap.builder();
        builder.put("summary", "REST API Error");
        builder.put("message",
                new StringJoiner(" : ").add(e.getMessage()).add(body.get("message").toString()).toString());
        Optional.ofNullable(body.get("errors")).ifPresent(error -> builder.put("details", error));
        return builder.build();
    }
}
