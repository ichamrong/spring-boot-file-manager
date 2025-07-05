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

@Tag(name = "Department", description = "Department Management")
@RestController
@RequestMapping("/api/v1/departments")
public class DepartmentController {

  @GetMapping
  public ResponseEntity<String> getDepartments() {
    return ResponseEntity.ok("List of departments");
  }

  @GetMapping("/{id}")
  public ResponseEntity<String> getDepartmentById(@PathVariable("id") Long id) {
    return ResponseEntity.ok("Get department by ID: " + id);
  }

  @PostMapping
  public ResponseEntity<String> createDepartment() {
    return ResponseEntity.ok("Department created");
  }

  @PutMapping("/{id}")
  public ResponseEntity<String> updateDepartment(@PathVariable("id") Long id) {
    return ResponseEntity.ok("Department updated: " + id);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteDepartment(@PathVariable("id") Long id) {
    return ResponseEntity.ok("Department deleted: " + id);
  }
}
