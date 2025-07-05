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

@Tag(name = "Tenant", description = "Tenant Management")
@RestController
@RequestMapping("/api/v1/tenants")
public class TenantController {

  @GetMapping
  public ResponseEntity<String> getTenants() {
    return ResponseEntity.ok("List of tenants");
  }

  @GetMapping("/{id}")
  public ResponseEntity<String> getTenantById(@PathVariable("id") Long id) {
    return ResponseEntity.ok("Get tenant by ID: " + id);
  }

  @PostMapping
  public ResponseEntity<String> createTenant() {
    return ResponseEntity.ok("Tenant created");
  }

  @PutMapping("/{id}")
  public ResponseEntity<String> updateTenant(@PathVariable("id") Long id) {
    return ResponseEntity.ok("Tenant updated: " + id);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteTenant(@PathVariable("id") Long id) {
    return ResponseEntity.ok("Tenant deleted: " + id);
  }
}
