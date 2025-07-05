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

@Tag(name = "Tenant Settings", description = "Tenant Settings Management")
@RestController
@RequestMapping("/api/v1/tenant-settings")
public class TenantSettingController {

  @GetMapping
  public ResponseEntity<String> getTenantSettings() {
    return ResponseEntity.ok("List of tenant settings");
  }

  @GetMapping("/{id}")
  public ResponseEntity<String> getTenantSettingById(@PathVariable("id") Long id) {
    return ResponseEntity.ok("Get tenant setting by ID: " + id);
  }

  @PostMapping
  public ResponseEntity<String> createTenantSetting() {
    return ResponseEntity.ok("Tenant setting created");
  }

  @PutMapping("/{id}")
  public ResponseEntity<String> updateTenantSetting(@PathVariable("id") Long id) {
    return ResponseEntity.ok("Tenant setting updated: " + id);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteTenantSetting(@PathVariable("id") Long id) {
    return ResponseEntity.ok("Tenant setting deleted: " + id);
  }
}
