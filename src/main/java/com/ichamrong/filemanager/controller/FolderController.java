package com.ichamrong.filemanager.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/folders")
public class FolderController {

  // 🗂️ List all folders (flat or nested)
  @GetMapping
  public String listFolders() {
    return "🗂️ GET /api/v1/folders";
  }

  // 📁 Get folder info and contents
  @GetMapping("/{id}")
  public String getFolder(@PathVariable String id) {
    return "📁 GET /api/v1/folders/" + id;
  }

  // ➕ Create a new folder
  @PostMapping
  public String createFolder() {
    return "➕ POST /api/v1/folders";
  }

  // ✏️ Rename a folder
  @PutMapping("/{id}/rename")
  public String renameFolder(@PathVariable String id) {
    return "✏️ PUT /api/v1/folders/" + id + "/rename";
  }

  // ❌ Delete a folder (soft delete or if empty)
  @DeleteMapping("/{id}")
  public String deleteFolder(@PathVariable String id) {
    return "❌ DELETE /api/v1/folders/" + id;
  }
}
