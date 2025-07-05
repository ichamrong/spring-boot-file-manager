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

@Tag(name = "Audit Log", description = "Audit Log Management")
@RestController
@RequestMapping("/api/v1/audit-logs")
public class AuditLogController {

  @GetMapping
  public ResponseEntity<String> getAuditLogs() {
    return ResponseEntity.ok("List of audit logs");
  }

  @GetMapping("/{id}")
  public ResponseEntity<String> getAuditLogById(@PathVariable("id") Long id) {
    return ResponseEntity.ok("Get audit log by ID: " + id);
  }

  @PostMapping
  public ResponseEntity<String> createAuditLog() {
    return ResponseEntity.ok("Audit log created");
  }

  @PutMapping("/{id}")
  public ResponseEntity<String> updateAuditLog(@PathVariable("id") Long id) {
    return ResponseEntity.ok("Audit log updated: " + id);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteAuditLog(@PathVariable("id") Long id) {
    return ResponseEntity.ok("Audit log deleted: " + id);
  }
}
