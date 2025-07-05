package com.ichamrong.filemanager.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/files")
public class FileController {

  // 🔍 List files
  @GetMapping
  public String listFiles() {
    return "🔍 GET /api/v1/files";
  }

  // 📄 Get file metadata
  @GetMapping("/{id}")
  public String getFile(@PathVariable String id) {
    return "📄 GET /api/v1/files/" + id;
  }

  // ⬆️ Upload a new file
  @PostMapping("/upload")
  public String uploadFile() {
    return "⬆️ POST /api/v1/files/upload";
  }

  // ❌ Delete a file
  @DeleteMapping("/{id}")
  public String deleteFile(@PathVariable String id) {
    return "❌ DELETE /api/v1/files/" + id;
  }

  // ✏️ Rename file
  @PutMapping("/{id}/rename")
  public String renameFile(@PathVariable String id) {
    return "✏️ PUT /api/v1/files/" + id + "/rename";
  }

  // 📂 Move file
  @PostMapping("/{id}/move")
  public String moveFile(@PathVariable String id) {
    return "📂 POST /api/v1/files/" + id + "/move";
  }

  // 📥 Download file
  @GetMapping("/{id}/download")
  public String downloadFile(@PathVariable String id) {
    return "📥 GET /api/v1/files/" + id + "/download";
  }

  // 👀 Preview file
  @GetMapping("/{id}/preview")
  public String previewFile(@PathVariable String id) {
    return "👀 GET /api/v1/files/" + id + "/preview";
  }

  // 🔐 Update access level
  @PutMapping("/{id}/access")
  public String updateAccess(@PathVariable String id) {
    return "🔐 PUT /api/v1/files/" + id + "/access";
  }

  // 🚮 Bulk delete
  @PostMapping("/bulk-delete")
  public String bulkDelete() {
    return "🚮 POST /api/v1/files/bulk-delete";
  }

  // 🔎 Search files
  @GetMapping("/search")
  public String searchFiles(@RequestParam String q) {
    return "🔎 GET /api/v1/files/search?q=" + q;
  }

  // ✅ Pre-upload validation
  @PostMapping("/validate-upload")
  public String validateUpload() {
    return "✅ POST /api/v1/files/validate-upload";
  }

  // 🔍 Check access level (public/private, owner, etc.)
  @GetMapping("/{id}/access")
  public String getAccessLevel(@PathVariable String id) {
    return "🔍 GET /api/v1/files/" + id + "/access";
  }
  
  // 🔗 Generate a public/private shareable link
  @PostMapping("/{id}/share")
  public String generateShareLink(@PathVariable String id) {
    return "🔗 POST /api/v1/files/" + id + "/share";
  }
}
