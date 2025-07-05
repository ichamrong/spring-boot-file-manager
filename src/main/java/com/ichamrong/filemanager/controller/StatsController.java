package com.ichamrong.filemanager.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/stats")
public class StatsController {

  // 📊 File & storage usage summary
  @GetMapping
  public String getGlobalStats() {
    return "📊 GET /api/v1/stats";
  }

  // 👤 Usage stats for current user
  @GetMapping("/user")
  public String getUserStats() {
    return "👤 GET /api/v1/stats/user";
  }
}
