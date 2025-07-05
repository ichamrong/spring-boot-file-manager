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

@Tag(name = "Role", description = "Role Management")
@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {

  @GetMapping
  public ResponseEntity<String> getRoles() {
    return ResponseEntity.ok("List of roles");
  }

  @GetMapping("/{id}")
  public ResponseEntity<String> getRoleById(@PathVariable("id") Long id) {
    return ResponseEntity.ok("Get role by ID: " + id);
  }

  @PostMapping
  public ResponseEntity<String> createRole() {
    return ResponseEntity.ok("Role created");
  }

  @PutMapping("/{id}")
  public ResponseEntity<String> updateRole(@PathVariable("id") Long id) {
    return ResponseEntity.ok("Role updated: " + id);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteRole(@PathVariable("id") Long id) {
    return ResponseEntity.ok("Role deleted: " + id);
  }
}
