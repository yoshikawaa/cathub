package com.example.app;

import java.io.IOException;
import java.util.Map;
import java.util.StringJoiner;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ControllerAdvice
public class ExceptionHandlerAdvise {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(Model model, Exception e) {
        model.addAttribute("summary", "Internal Server Error");
        model.addAttribute("message", e.getMessage());
        return view();
    }

    @ExceptionHandler(HttpClientErrorException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleHttpClientErrorException(Model model, HttpClientErrorException e)
            throws JsonParseException, JsonMappingException, IOException {
        model.addAttribute("summary", "REST API Error");
        Map<String, Object> body = new ObjectMapper().readValue(e.getResponseBodyAsString(),
                new TypeReference<Map<String, Object>>() {
                });
        model.addAttribute("message",
                new StringJoiner(" : ").add(e.getMessage()).add(body.get("message").toString()).toString());
        model.addAttribute("details", body.get("errors"));
        return view();
    }

    private String view() {
        return "error";
    }
}
