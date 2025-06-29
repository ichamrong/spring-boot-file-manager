package com.ichamrong.filemanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

  @GetMapping("/")
  public String home(Model model) {
    model.addAttribute("appName", "Spring Boot File Manager");
    model.addAttribute("author", "Chamrong");
    model.addAttribute("version", "1.0.0");
    model.addAttribute("contactEmail", "info@ichamrong.com");
    return "home";
  }
}
