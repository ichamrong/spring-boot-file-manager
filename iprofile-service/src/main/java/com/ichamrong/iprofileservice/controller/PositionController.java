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

@Tag(name = "Position", description = "Position Management")
@RestController
@RequestMapping("/api/v1/positions")
public class PositionController {

  @GetMapping
  public ResponseEntity<String> getPositions() {
    return ResponseEntity.ok("List of positions");
  }

  @GetMapping("/{id}")
  public ResponseEntity<String> getPositionById(@PathVariable("id") Long id) {
    return ResponseEntity.ok("Get position by ID: " + id);
  }

  @PostMapping
  public ResponseEntity<String> createPosition() {
    return ResponseEntity.ok("Position created");
  }

  @PutMapping("/{id}")
  public ResponseEntity<String> updatePosition(@PathVariable("id") Long id) {
    return ResponseEntity.ok("Position updated: " + id);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deletePosition(@PathVariable("id") Long id) {
    return ResponseEntity.ok("Position deleted: " + id);
  }
}
