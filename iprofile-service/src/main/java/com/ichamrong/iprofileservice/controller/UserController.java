package com.ichamrong.iprofileservice.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Users", description = "User Management")
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

  @GetMapping
  public ResponseEntity<String> getUsers() {
    return ResponseEntity.ok("List of users");
  }

  @GetMapping("/me")
  public ResponseEntity<String> getCurrentUser() {
    return ResponseEntity.ok("Current user");
  }

  @GetMapping("/{id}")
  public ResponseEntity<String> getUserById(@PathVariable("id") Long id) {
    return ResponseEntity.ok("Get user by ID: " + id);
  }

  @PostMapping
  public ResponseEntity<String> createUser() {
    return ResponseEntity.ok("User created");
  }

  @PutMapping("/{id}")
  public ResponseEntity<String> updateUser(@PathVariable("id") Long id) {
    return ResponseEntity.ok("User updated: " + id);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteUser(@PathVariable("id") Long id) {
    return ResponseEntity.ok("User deleted: " + id);
  }
}
