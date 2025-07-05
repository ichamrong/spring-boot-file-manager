package com.ichamrong.filemanager.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.proxy.HibernateProxy;

@Entity
@Table(name = "file_metadata")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileMetadata {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "original_filename", nullable = false)
  private String originalFilename;

  @Column(name = "stored_filename", nullable = false, unique = true)
  private String storedFilename;

  @Column(name = "file_path")
  private String filePath;

  @Column(name = "content_type")
  private String contentType;

  @Column(name = "file_size")
  private Long fileSize;

  @Column(name = "bucket_name", nullable = false)
  private String bucketName;

  @Column(name = "uploaded_by", nullable = false)
  private String uploadedBy;

  @Column(name = "description")
  private String description;

  @Enumerated(EnumType.STRING)
  @Column(name = "access_level")
  private AccessLevel accessLevel = AccessLevel.PRIVATE;

  @Enumerated(EnumType.STRING)
  @Column(name = "file_status")
  private FileStatus fileStatus = FileStatus.ACTIVE;

  @Column(name = "folder_id")
  private Long folderId;

  @Column(name = "checksum")
  private String checksum;

  @Column(name = "tags")
  private String tags; // JSON string or comma-separated

  @CreationTimestamp
  @Column(name = "created_at", updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @Column(name = "last_accessed")
  private LocalDateTime lastAccessed;

  @Column(name = "download_count")
  private Long downloadCount = 0L;

  @Column(name = "share_token")
  private String shareToken;

  @Column(name = "share_expires_at")
  private LocalDateTime shareExpiresAt;

  public enum AccessLevel {
    PUBLIC, PRIVATE, SHARED
  }

  public enum FileStatus {
    ACTIVE, DELETED, ARCHIVED, PROCESSING
  }

  @Override
  public final boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null) {
      return false;
    }
    Class<?> oEffectiveClass = o instanceof HibernateProxy
        ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass()
        : o.getClass();
    Class<?> thisEffectiveClass = this instanceof HibernateProxy
        ? ((HibernateProxy) this).getHibernateLazyInitializer()
        .getPersistentClass() : this.getClass();
    if (thisEffectiveClass != oEffectiveClass) {
      return false;
    }
    FileMetadata that = (FileMetadata) o;
    return getId() != null && Objects.equals(getId(), that.getId());
  }

  @Override
  public final int hashCode() {
    return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer()
        .getPersistentClass().hashCode() : getClass().hashCode();
  }
}