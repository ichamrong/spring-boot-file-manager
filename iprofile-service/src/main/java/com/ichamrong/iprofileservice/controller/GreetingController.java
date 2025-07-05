package com.ichamrong.iprofileservice.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Greeting", description = "Greeting Management")
@RestController
@RequestMapping("/api/v1/greeting")
public class GreetingController {

  @GetMapping
  public String greeting() {
    return "Hello from IProfile API";
  }
}
