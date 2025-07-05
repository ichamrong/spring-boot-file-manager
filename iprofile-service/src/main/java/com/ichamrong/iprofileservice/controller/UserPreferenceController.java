package com.ichamrong.iprofileservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user-preferences")
public class UserPreferenceController {

  @GetMapping
  public ResponseEntity<String> getAllUserPreferences() {
    return ResponseEntity.ok("All user preferences");
  }

  @GetMapping("/{id}")
  public ResponseEntity<String> getUserPreference(@PathVariable("id") Long id) {
    return ResponseEntity.ok("User preference: " + id);
  }

  @PostMapping
  public ResponseEntity<String> createUserPreference() {
    return ResponseEntity.ok("Create user preference");
  }

  @PutMapping("/{id}")
  public ResponseEntity<String> updateUserPreference(@PathVariable("id") Long id) {
    return ResponseEntity.ok("Update user preference: " + id);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteUserPreference(@PathVariable("id") Long id) {
    return ResponseEntity.ok("Delete user preference: " + id);
  }
}
