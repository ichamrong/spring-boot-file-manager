package com.ichamrong.filemanager.repository;

import com.ichamrong.filemanager.model.entity.FileMetadata;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileMetadataRepository extends JpaRepository<FileMetadata, Long> {

  List<FileMetadata> findByUploadedBy(String uploadedBy);
}