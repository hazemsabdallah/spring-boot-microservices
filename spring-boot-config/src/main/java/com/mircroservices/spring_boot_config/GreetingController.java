package com.mircroservices.spring_boot_config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class GreetingController {

    @Value("${my.greeting: default value}")
    private String greeting;

    @Value("${my.greeting.list: default value}")
    private List<String> greetingsList;

    @Value("#{${my.map}}")
    private Map<String, String> map;

    @GetMapping(value = "/greeting")
    public String greeting() {
        return greeting + " " + greetingsList + " " + map;
    }
}
