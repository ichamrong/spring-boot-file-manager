package com.ichamrong.filemanager.service.impl;

import com.ichamrong.filemanager.service.MinioService;
import io.minio.CopyObjectArgs;
import io.minio.CopySource;
import io.minio.GetObjectArgs;
import io.minio.ListObjectsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.RemoveObjectsArgs;
import io.minio.Result;
import io.minio.StatObjectArgs;
import io.minio.StatObjectResponse;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;
import io.minio.messages.Item;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import com.ichamrong.filemanager.exception.StorageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MinioServiceImpl implements MinioService {

  private static final Logger logger = LoggerFactory.getLogger(MinioServiceImpl.class);
  private final MinioClient minioClient;

  public MinioServiceImpl(MinioClient minioClient) {
    this.minioClient = minioClient;
  }

  @Override
  public void createBucket(String bucketName) {
    try {
      logger.info("Creating bucket: {}", bucketName);
      final MakeBucketArgs args = MakeBucketArgs.builder().bucket(bucketName).build();
      this.minioClient.makeBucket(args);
      logger.info("Successfully created bucket: {}", bucketName);
    } catch (ErrorResponseException e) {
      String errorMessage = String.format("Failed to create bucket '%s' due to error response: %s", bucketName, e.getMessage());
      logger.error(errorMessage, e);
      throw new StorageException(errorMessage, e);
    } catch (InsufficientDataException e) {
      String errorMessage = String.format("Insufficient data received while creating bucket '%s'", bucketName);
      logger.error(errorMessage, e);
      throw new StorageException(errorMessage, e);
    } catch (InternalException e) {
      String errorMessage = String.format("Internal error occurred while creating bucket '%s'", bucketName);
      logger.error(errorMessage, e);
      throw new StorageException(errorMessage, e);
    } catch (InvalidKeyException | NoSuchAlgorithmException e) {
      String errorMessage = "Invalid credentials or configuration when creating bucket: " + bucketName;
      logger.error(errorMessage, e);
      throw new StorageException(errorMessage, e);
    } catch (InvalidResponseException e) {
      String errorMessage = String.format("Received invalid response from storage service while creating bucket '%s'", bucketName);
      logger.error(errorMessage, e);
      throw new StorageException(errorMessage, e);
    } catch (IOException e) {
      String errorMessage = String.format("I/O error occurred while creating bucket '%s'", bucketName);
      logger.error(errorMessage, e);
      throw new StorageException(errorMessage, e);
    } catch (ServerException e) {
      String errorMessage = String.format("Server error occurred while creating bucket '%s'", bucketName);
      logger.error(errorMessage, e);
      throw new StorageException(errorMessage, e);
    } catch (XmlParserException e) {
      String errorMessage = String.format("Failed to parse XML response when creating bucket '%s'", bucketName);
      logger.error(errorMessage, e);
      throw new StorageException(errorMessage, e);
    }
  }

  @Override
  public void uploadFile(String bucketName, String fileName, String contentType, byte[] file) {
    try {
      logger.info("Uploading file: {} to bucket: {} with content type: {}", fileName, bucketName,
          contentType);
      InputStream inputStream = new ByteArrayInputStream(file);
      PutObjectArgs args = PutObjectArgs.builder()
          .bucket(bucketName)
          .object(fileName)
          .stream(inputStream, file.length, -1)
          .contentType(contentType)
          .build();
      minioClient.putObject(args);
      logger.info("Successfully uploaded file: {} ({} bytes)", fileName, file.length);
    } catch (ErrorResponseException e) {
      String errorMsg = String.format("Failed to upload file '%s' to bucket '%s' - Server returned error: %s", 
          fileName, bucketName, e.getMessage());
      logger.error(errorMsg, e);
      throw new StorageException(errorMsg, e);
    } catch (InsufficientDataException | IOException e) {
      String errorMsg = String.format("I/O error while uploading file '%s' to bucket '%s'", fileName, bucketName);
      logger.error(errorMsg, e);
      throw new StorageException(errorMsg, e);
    } catch (InternalException | ServerException e) {
      String errorMsg = String.format("Internal server error while uploading file '%s' to bucket '%s'", fileName, bucketName);
      logger.error(errorMsg, e);
      throw new StorageException(errorMsg, e);
    } catch (InvalidKeyException | NoSuchAlgorithmException e) {
      String errorMsg = String.format("Authentication/encryption error while uploading file '%s' to bucket '%s'", 
          fileName, bucketName);
      logger.error(errorMsg, e);
      throw new StorageException(errorMsg, e);
    } catch (InvalidResponseException | XmlParserException e) {
      String errorMsg = String.format("Error parsing server response while uploading file '%s' to bucket '%s'", 
          fileName, bucketName);
      logger.error(errorMsg, e);
      throw new StorageException(errorMsg, e);
    }
  }

  @Override
  public void downloadFile(String bucketName, String fileName, String contentType) {
    try {
      logger.info("Downloading file: {} from bucket: {}", fileName, bucketName);
      GetObjectArgs args = GetObjectArgs.builder()
          .bucket(bucketName)
          .object(fileName)
          .build();
      minioClient.getObject(args);
      logger.info("Successfully downloaded file: {}", fileName);
    } catch (ErrorResponseException e) {
      String errorMsg = String.format("Failed to download file '%s' from bucket '%s' - Server returned error: %s", 
          fileName, bucketName, e.getMessage());
      logger.error(errorMsg, e);
      throw new StorageException(errorMsg, e);
    } catch (InsufficientDataException | IOException e) {
      String errorMsg = String.format("I/O error while downloading file '%s' from bucket '%s'", fileName, bucketName);
      logger.error(errorMsg, e);
      throw new StorageException(errorMsg, e);
    } catch (InternalException | ServerException e) {
      String errorMsg = String.format("Internal server error while downloading file '%s' from bucket '%s'", fileName, bucketName);
      logger.error(errorMsg, e);
      throw new StorageException(errorMsg, e);
    } catch (InvalidKeyException | NoSuchAlgorithmException e) {
      String errorMsg = String.format("Authentication/encryption error while downloading file '%s' from bucket '%s'", 
          fileName, bucketName);
      logger.error(errorMsg, e);
      throw new StorageException(errorMsg, e);
    } catch (InvalidResponseException | XmlParserException e) {
      String errorMsg = String.format("Error parsing server response while downloading file '%s' from bucket '%s'", 
          fileName, bucketName);
      logger.error(errorMsg, e);
      throw new StorageException(errorMsg, e);
    }
  }

  @Override
  public void deleteFile(String bucketName, String fileName) {
    try {
      logger.info("Deleting file: {} from bucket: {}", fileName, bucketName);
      RemoveObjectArgs args = RemoveObjectArgs.builder()
          .bucket(bucketName)
          .object(fileName)
          .build();
      minioClient.removeObject(args);
      logger.info("Successfully deleted file: {}", fileName);
    } catch (ErrorResponseException e) {
      String errorMsg = String.format("Failed to delete file '%s' from bucket '%s' - Server returned error: %s", 
          fileName, bucketName, e.getMessage());
      logger.error(errorMsg, e);
      throw new StorageException(errorMsg, e);
    } catch (InsufficientDataException | IOException e) {
      String errorMsg = String.format("I/O error while deleting file '%s' from bucket '%s'", fileName, bucketName);
      logger.error(errorMsg, e);
      throw new StorageException(errorMsg, e);
    } catch (InternalException | ServerException e) {
      String errorMsg = String.format("Internal server error while deleting file '%s' from bucket '%s'", fileName, bucketName);
      logger.error(errorMsg, e);
      throw new StorageException(errorMsg, e);
    } catch (InvalidKeyException | NoSuchAlgorithmException e) {
      String errorMsg = String.format("Authentication/encryption error while deleting file '%s' from bucket '%s'", 
          fileName, bucketName);
      logger.error(errorMsg, e);
      throw new StorageException(errorMsg, e);
    } catch (InvalidResponseException | XmlParserException e) {
      String errorMsg = String.format("Error parsing server response while deleting file '%s' from bucket '%s'", 
          fileName, bucketName);
      logger.error(errorMsg, e);
      throw new StorageException(errorMsg, e);
    }
  }

  @Override
  public void renameFile(String bucketName, String fileName, String newFileName) {
    try {
      logger.info("Renaming file from {} to {} in bucket: {}", fileName, newFileName, bucketName);
      // Copy the file with new name
      CopyObjectArgs copyArgs = CopyObjectArgs.builder()
          .bucket(bucketName)
          .object(newFileName)
          .source(CopySource.builder().bucket(bucketName).object(fileName).build())
          .build();
      minioClient.copyObject(copyArgs);
      logger.debug("File copied with new name: {}", newFileName);

      // Delete the original file
      deleteFile(bucketName, fileName);
      logger.info("Successfully renamed file from {} to {}", fileName, newFileName);
    } catch (ErrorResponseException e) {
      String errorMsg = String.format("Failed to rename file '%s' to '%s' in bucket '%s' - Server returned error: %s", 
          fileName, newFileName, bucketName, e.getMessage());
      logger.error(errorMsg, e);
      throw new StorageException(errorMsg, e);
    } catch (InsufficientDataException | IOException e) {
      String errorMsg = String.format("I/O error while renaming file '%s' to '%s' in bucket '%s'", fileName, newFileName, bucketName);
      logger.error(errorMsg, e);
      throw new StorageException(errorMsg, e);
    } catch (InternalException | ServerException e) {
      String errorMsg = String.format("Internal server error while renaming file '%s' to '%s' in bucket '%s'", fileName, newFileName, bucketName);
      logger.error(errorMsg, e);
      throw new StorageException(errorMsg, e);
    } catch (InvalidKeyException | NoSuchAlgorithmException e) {
      String errorMsg = String.format("Authentication/encryption error while renaming file '%s' to '%s' in bucket '%s'", 
          fileName, newFileName, bucketName);
      logger.error(errorMsg, e);
      throw new StorageException(errorMsg, e);
    } catch (InvalidResponseException | XmlParserException e) {
      String errorMsg = String.format("Error parsing server response while renaming file '%s' to '%s' in bucket '%s'", 
          fileName, newFileName, bucketName);
      logger.error(errorMsg, e);
      throw new StorageException(errorMsg, e);
    }
  }

  @Override
  public void moveFile(String bucketName, String fileName, String newBucketName) {
    try {
      logger.info("Moving file {} from bucket {} to bucket {}", fileName, bucketName,
          newBucketName);
      // Copy the file to new bucket
      CopyObjectArgs copyArgs = CopyObjectArgs.builder()
          .bucket(newBucketName)
          .object(fileName)
          .source(CopySource.builder().bucket(bucketName).object(fileName).build())
          .build();
      minioClient.copyObject(copyArgs);
      logger.debug("File copied to new bucket: {}", newBucketName);

      // Delete the original file
      deleteFile(bucketName, fileName);
      logger.info("Successfully moved file {} to bucket {}", fileName, newBucketName);
    } catch (ErrorResponseException e) {
      String errorMsg = String.format("Failed to move file '%s' from bucket '%s' to bucket '%s' - Server returned error: %s", 
          fileName, bucketName, newBucketName, e.getMessage());
      logger.error(errorMsg, e);
      throw new StorageException(errorMsg, e);
    } catch (InsufficientDataException | IOException e) {
      String errorMsg = String.format("I/O error while moving file '%s' from bucket '%s' to bucket '%s'", fileName, bucketName, newBucketName);
      logger.error(errorMsg, e);
      throw new StorageException(errorMsg, e);
    } catch (InternalException | ServerException e) {
      String errorMsg = String.format("Internal server error while moving file '%s' from bucket '%s' to bucket '%s'", fileName, bucketName, newBucketName);
      logger.error(errorMsg, e);
      throw new StorageException(errorMsg, e);
    } catch (InvalidKeyException | NoSuchAlgorithmException e) {
      String errorMsg = String.format("Authentication/encryption error while moving file '%s' from bucket '%s' to bucket '%s'", 
          fileName, bucketName, newBucketName);
      logger.error(errorMsg, e);
      throw new StorageException(errorMsg, e);
    } catch (InvalidResponseException | XmlParserException e) {
      String errorMsg = String.format("Error parsing server response while moving file '%s' from bucket '%s' to bucket '%s'", 
          fileName, bucketName, newBucketName);
      logger.error(errorMsg, e);
      throw new StorageException(errorMsg, e);
    }
  }

  @Override
  public void createFolder(String bucketName, String folderName) {
    try {
      logger.info("Creating folder: {} in bucket: {}", folderName, bucketName);
      // Create an empty object with folder name ending with "/"
      String folderPath = folderName.endsWith("/") ? folderName : folderName + "/";
      PutObjectArgs args = PutObjectArgs.builder()
          .bucket(bucketName)
          .object(folderPath)
          .stream(new ByteArrayInputStream(new byte[0]), 0, -1)
          .build();
      minioClient.putObject(args);
      logger.info("Successfully created folder: {}", folderName);
    } catch (ErrorResponseException e) {
      String errorMsg = String.format("Failed to create folder '%s' in bucket '%s' - Server returned error: %s", 
          folderName, bucketName, e.getMessage());
      logger.error(errorMsg, e);
      throw new StorageException(errorMsg, e);
    } catch (InsufficientDataException | IOException e) {
      String errorMsg = String.format("I/O error while creating folder '%s' in bucket '%s'", folderName, bucketName);
      logger.error(errorMsg, e);
      throw new StorageException(errorMsg, e);
    } catch (InternalException | ServerException e) {
      String errorMsg = String.format("Internal server error while creating folder '%s' in bucket '%s'", folderName, bucketName);
      logger.error(errorMsg, e);
      throw new StorageException(errorMsg, e);
    } catch (InvalidKeyException | NoSuchAlgorithmException e) {
      String errorMsg = String.format("Authentication/encryption error while creating folder '%s' in bucket '%s'", 
          folderName, bucketName);
      logger.error(errorMsg, e);
      throw new StorageException(errorMsg, e);
    } catch (InvalidResponseException | XmlParserException e) {
      String errorMsg = String.format("Error parsing server response while creating folder '%s' in bucket '%s'", 
          folderName, bucketName);
      logger.error(errorMsg, e);
      throw new StorageException(errorMsg, e);
    }
  }

  @Override
  public void deleteFolder(String bucketName, String folderName) {
    try {
      logger.info("Deleting folder: {} from bucket: {}", folderName, bucketName);
      String folderPath = folderName.endsWith("/") ? folderName : folderName + "/";
      ListObjectsArgs listArgs = ListObjectsArgs.builder()
          .bucket(bucketName)
          .prefix(folderPath)
          .recursive(true)
          .build();

      Iterable<Result<Item>> objects = minioClient.listObjects(listArgs);
      int fileCount = 0;
      for (Result<Item> result : objects) {
        Item item = result.get();
        deleteFile(bucketName, item.objectName());
        fileCount++;
      }
      logger.info("Successfully deleted folder: {} ({} files removed)", folderName, fileCount);
    } catch (ErrorResponseException e) {
      String errorMsg = String.format("Failed to delete folder '%s' from bucket '%s' - Server returned error: %s", 
          folderName, bucketName, e.getMessage());
      logger.error(errorMsg, e);
      throw new StorageException(errorMsg, e);
    } catch (InsufficientDataException | IOException e) {
      String errorMsg = String.format("I/O error while deleting folder '%s' from bucket '%s'", folderName, bucketName);
      logger.error(errorMsg, e);
      throw new StorageException(errorMsg, e);
    } catch (InternalException | ServerException e) {
      String errorMsg = String.format("Internal server error while deleting folder '%s' from bucket '%s'", folderName, bucketName);
      logger.error(errorMsg, e);
      throw new StorageException(errorMsg, e);
    } catch (InvalidKeyException | NoSuchAlgorithmException e) {
      String errorMsg = String.format("Authentication/encryption error while deleting folder '%s' from bucket '%s'", 
          folderName, bucketName);
      logger.error(errorMsg, e);
      throw new StorageException(errorMsg, e);
    } catch (InvalidResponseException | XmlParserException e) {
      String errorMsg = String.format("Error parsing server response while deleting folder '%s' from bucket '%s'", 
          folderName, bucketName);
      logger.error(errorMsg, e);
      throw new StorageException(errorMsg, e);
    }
  }

  @Override
  public void renameFolder(String bucketName, String folderName, String newFolderName) {
    try {
      logger.info("Renaming folder from {} to {} in bucket: {}", folderName, newFolderName,
          bucketName);
      String oldFolderPath = folderName.endsWith("/") ? folderName : folderName + "/";
      String newFolderPath = newFolderName.endsWith("/") ? newFolderName : newFolderName + "/";

      ListObjectsArgs listArgs = ListObjectsArgs.builder()
          .bucket(bucketName)
          .prefix(oldFolderPath)
          .recursive(true)
          .build();

      Iterable<Result<Item>> objects = minioClient.listObjects(listArgs);
      int fileCount = 0;
      for (Result<Item> result : objects) {
        Item item = result.get();
        String oldObjectName = item.objectName();
        String newObjectName = oldObjectName.replace(oldFolderPath, newFolderPath);

        // Copy to new location
        CopyObjectArgs copyArgs = CopyObjectArgs.builder()
            .bucket(bucketName)
            .object(newObjectName)
            .source(CopySource.builder().bucket(bucketName).object(oldObjectName).build())
            .build();
        minioClient.copyObject(copyArgs);
        logger.debug("Copied file from {} to {}", oldObjectName, newObjectName);

        // Delete from old location
        deleteFile(bucketName, oldObjectName);
        fileCount++;
      }
      logger.info("Successfully renamed folder from {} to {} ({} files moved)", folderName,
          newFolderName, fileCount);
    } catch (ErrorResponseException e) {
      String errorMsg = String.format("Failed to rename folder '%s' to '%s' in bucket '%s' - Server returned error: %s", 
          folderName, newFolderName, bucketName, e.getMessage());
      logger.error(errorMsg, e);
      throw new StorageException(errorMsg, e);
    } catch (InsufficientDataException | IOException e) {
      String errorMsg = String.format("I/O error while renaming folder '%s' to '%s' in bucket '%s'", folderName, newFolderName, bucketName);
      logger.error(errorMsg, e);
      throw new StorageException(errorMsg, e);
    } catch (InternalException | ServerException e) {
      String errorMsg = String.format("Internal server error while renaming folder '%s' to '%s' in bucket '%s'", folderName, newFolderName, bucketName);
      logger.error(errorMsg, e);
      throw new StorageException(errorMsg, e);
    } catch (InvalidKeyException | NoSuchAlgorithmException e) {
      String errorMsg = String.format("Authentication/encryption error while renaming folder '%s' to '%s' in bucket '%s'", 
          folderName, newFolderName, bucketName);
      logger.error(errorMsg, e);
      throw new StorageException(errorMsg, e);
    } catch (InvalidResponseException | XmlParserException e) {
      String errorMsg = String.format("Error parsing server response while renaming folder '%s' to '%s' in bucket '%s'", 
          folderName, newFolderName, bucketName);
      logger.error(errorMsg, e);
      throw new StorageException(errorMsg, e);
    }
  }

  @Override
  public void moveFolder(String bucketName, String folderName, String newBucketName) {
    try {
      logger.info("Moving folder {} from bucket {} to bucket {}", folderName, bucketName,
          newBucketName);
      String folderPath = folderName.endsWith("/") ? folderName : folderName + "/";

      ListObjectsArgs listArgs = ListObjectsArgs.builder()
          .bucket(bucketName)
          .prefix(folderPath)
          .recursive(true)
          .build();

      Iterable<Result<Item>> objects = minioClient.listObjects(listArgs);
      int fileCount = 0;
      for (Result<Item> result : objects) {
        Item item = result.get();
        String objectName = item.objectName();

        // Copy to new bucket
        CopyObjectArgs copyArgs = CopyObjectArgs.builder()
            .bucket(newBucketName)
            .object(objectName)
            .source(CopySource.builder().bucket(bucketName).object(objectName).build())
            .build();
        minioClient.copyObject(copyArgs);
        logger.debug("Copied file {} to new bucket: {}", objectName, newBucketName);

        // Delete from old bucket
        deleteFile(bucketName, objectName);
        fileCount++;
      }
      logger.info("Successfully moved folder {} to bucket {} ({} files moved)", folderName,
          newBucketName, fileCount);
    } catch (ErrorResponseException e) {
      String errorMsg = String.format("Failed to move folder '%s' from bucket '%s' to bucket '%s' - Server returned error: %s", 
          folderName, bucketName, newBucketName, e.getMessage());
      logger.error(errorMsg, e);
      throw new StorageException(errorMsg, e);
    } catch (InsufficientDataException | IOException e) {
      String errorMsg = String.format("I/O error while moving folder '%s' from bucket '%s' to bucket '%s'", folderName, bucketName, newBucketName);
      logger.error(errorMsg, e);
      throw new StorageException(errorMsg, e);
    } catch (InternalException | ServerException e) {
      String errorMsg = String.format("Internal server error while moving folder '%s' from bucket '%s' to bucket '%s'", folderName, bucketName, newBucketName);
      logger.error(errorMsg, e);
      throw new StorageException(errorMsg, e);
    } catch (InvalidKeyException | NoSuchAlgorithmException e) {
      String errorMsg = String.format("Authentication/encryption error while moving folder '%s' from bucket '%s' to bucket '%s'", 
          folderName, bucketName, newBucketName);
      logger.error(errorMsg, e);
      throw new StorageException(errorMsg, e);
    } catch (InvalidResponseException | XmlParserException e) {
      String errorMsg = String.format("Error parsing server response while moving folder '%s' from bucket '%s' to bucket '%s'", 
          folderName, bucketName, newBucketName);
      logger.error(errorMsg, e);
      throw new StorageException(errorMsg, e);
    }
  }

  @Override
  public void listFiles(String bucketName) {
    try {
      logger.info("Listing files in bucket: {}", bucketName);
      ListObjectsArgs args = ListObjectsArgs.builder()
          .bucket(bucketName)
          .recursive(true)
          .build();

      Iterable<Result<Item>> objects = minioClient.listObjects(args);
      for (Result<Item> result : objects) {
        Item item = result.get();
        if (!item.isDir()) {
          logger.info("File: {} (Size: {} bytes)", item.objectName(), item.size());
        }
      }
      logger.info("Finished listing files in bucket: {}", bucketName);
    } catch (ErrorResponseException e) {
      String errorMsg = String.format("Failed to list files in bucket '%s' - Server returned error: %s", 
          bucketName, e.getMessage());
      logger.error(errorMsg, e);
      throw new StorageException(errorMsg, e);
    } catch (InsufficientDataException | IOException e) {
      String errorMsg = String.format("I/O error while listing files in bucket '%s'", bucketName);
      logger.error(errorMsg, e);
      throw new StorageException(errorMsg, e);
    } catch (InternalException | ServerException e) {
      String errorMsg = String.format("Internal server error while listing files in bucket '%s'", bucketName);
      logger.error(errorMsg, e);
      throw new StorageException(errorMsg, e);
    } catch (InvalidKeyException | NoSuchAlgorithmException e) {
      String errorMsg = String.format("Authentication/encryption error while listing files in bucket '%s'", bucketName);
      logger.error(errorMsg, e);
      throw new StorageException(errorMsg, e);
    } catch (InvalidResponseException | XmlParserException e) {
      String errorMsg = String.format("Error parsing server response while listing files in bucket '%s'", bucketName);
      logger.error(errorMsg, e);
      throw new StorageException(errorMsg, e);
    }
  }

  @Override
  public void listFolders(String bucketName) {
    try {
      logger.info("Listing folders in bucket: {}", bucketName);
      ListObjectsArgs args = ListObjectsArgs.builder()
          .bucket(bucketName)
          .delimiter("/")
          .build();

      Iterable<Result<Item>> objects = minioClient.listObjects(args);
      for (Result<Item> result : objects) {
        Item item = result.get();
        if (item.isDir()) {
          logger.info("Folder: {}", item.objectName());
        }
      }
      logger.info("Finished listing folders in bucket: {}", bucketName);
    } catch (ErrorResponseException e) {
      String errorMsg = String.format("Failed to list folders in bucket '%s' - Server returned error: %s", 
          bucketName, e.getMessage());
      logger.error(errorMsg, e);
      throw new StorageException(errorMsg, e);
    } catch (InsufficientDataException | IOException e) {
      String errorMsg = String.format("I/O error while listing folders in bucket '%s'", bucketName);
      logger.error(errorMsg, e);
      throw new StorageException(errorMsg, e);
    } catch (InternalException | ServerException e) {
      String errorMsg = String.format("Internal server error while listing folders in bucket '%s'", bucketName);
      logger.error(errorMsg, e);
      throw new StorageException(errorMsg, e);
    } catch (InvalidKeyException | NoSuchAlgorithmException e) {
      String errorMsg = String.format("Authentication/encryption error while listing folders in bucket '%s'", bucketName);
      logger.error(errorMsg, e);
      throw new StorageException(errorMsg, e);
    } catch (InvalidResponseException | XmlParserException e) {
      String errorMsg = String.format("Error parsing server response while listing folders in bucket '%s'", bucketName);
      logger.error(errorMsg, e);
      throw new StorageException(errorMsg, e);
    }
  }

  @Override
  public void getFolder(String bucketName, String folderName) {
    try {
      logger.info("Getting contents of folder: {} in bucket: {}", folderName, bucketName);
      String folderPath = folderName.endsWith("/") ? folderName : folderName + "/";
      ListObjectsArgs args = ListObjectsArgs.builder()
          .bucket(bucketName)
          .prefix(folderPath)
          .recursive(false)
          .build();

      Iterable<Result<Item>> objects = minioClient.listObjects(args);
      logger.info("Contents of folder: {}", folderName);
      for (Result<Item> result : objects) {
        Item item = result.get();
        logger.info("- {} ({})", item.objectName(), item.isDir() ? "folder" : "file");
      }
    } catch (ErrorResponseException e) {
      String errorMsg = String.format("Failed to get folder '%s' from bucket '%s' - Server returned error: %s", 
          folderName, bucketName, e.getMessage());
      logger.error(errorMsg, e);
      throw new StorageException(errorMsg, e);
    } catch (InsufficientDataException | IOException e) {
      String errorMsg = String.format("I/O error while getting folder '%s' from bucket '%s'", folderName, bucketName);
      logger.error(errorMsg, e);
      throw new StorageException(errorMsg, e);
    } catch (InternalException | ServerException e) {
      String errorMsg = String.format("Internal server error while getting folder '%s' from bucket '%s'", folderName, bucketName);
      logger.error(errorMsg, e);
      throw new StorageException(errorMsg, e);
    } catch (InvalidKeyException | NoSuchAlgorithmException e) {
      String errorMsg = String.format("Authentication/encryption error while getting folder '%s' from bucket '%s'", 
          folderName, bucketName);
      logger.error(errorMsg, e);
      throw new StorageException(errorMsg, e);
    } catch (InvalidResponseException | XmlParserException e) {
      String errorMsg = String.format("Error parsing server response while getting folder '%s' from bucket '%s'", 
          folderName, bucketName);
      logger.error(errorMsg, e);
      throw new StorageException(errorMsg, e);
    }
  }

  @Override
  public void getFile(String bucketName, String fileName) {
    try {
      logger.info("Getting file info for: {} in bucket: {}", fileName, bucketName);
      StatObjectArgs args = StatObjectArgs.builder()
          .bucket(bucketName)
          .object(fileName)
          .build();

      StatObjectResponse stat = minioClient.statObject(args);
      logger.info("File: {}", fileName);
      logger.info("Size: {} bytes", stat.size());
      logger.info("Content-Type: {}", stat.contentType());
      logger.info("Last Modified: {}", stat.lastModified());
    } catch (ErrorResponseException e) {
      String errorMsg = String.format("Failed to get file info for '%s' in bucket '%s' - Server returned error: %s", 
          fileName, bucketName, e.getMessage());
      logger.error(errorMsg, e);
      throw new StorageException(errorMsg, e);
    } catch (InsufficientDataException | IOException e) {
      String errorMsg = String.format("I/O error while getting file info for '%s' in bucket '%s'", fileName, bucketName);
      logger.error(errorMsg, e);
      throw new StorageException(errorMsg, e);
    } catch (InternalException | ServerException e) {
      String errorMsg = String.format("Internal server error while getting file info for '%s' in bucket '%s'", fileName, bucketName);
      logger.error(errorMsg, e);
      throw new StorageException(errorMsg, e);
    } catch (InvalidKeyException | NoSuchAlgorithmException e) {
      String errorMsg = String.format("Authentication/encryption error while getting file info for '%s' in bucket '%s'", 
          fileName, bucketName);
      logger.error(errorMsg, e);
      throw new StorageException(errorMsg, e);
    } catch (InvalidResponseException | XmlParserException e) {
      String errorMsg = String.format("Error parsing server response while getting file info for '%s' in bucket '%s'", 
          fileName, bucketName);
      logger.error(errorMsg, e);
      throw new StorageException(errorMsg, e);
    }
  }

  @Override
  public void getAccessLevel(String bucketName, String fileName) {
    logger.info("Getting access level for file: {} in bucket: {}", fileName, bucketName);
    // Note: ObjectRetention functionality requires specific MinIO version and configuration
    logger.warn(
        "Access level functionality not implemented - requires MinIO version with retention support");
  }

  @Override
  public void updateAccessLevel(String bucketName, String fileName, String accessLevel) {
    logger.info("Updating access level for file: {} in bucket: {} to: {}", fileName, bucketName,
        accessLevel);
    // Note: ObjectRetention functionality requires specific MinIO version and configuration
    logger.warn(
        "Access level update functionality not implemented - requires MinIO version with retention support");
  }

  @Override
  public void searchFiles(String bucketName, String query) {
    try {
      logger.info("Searching files in bucket: {} with query: {}", bucketName, query);
      ListObjectsArgs args = ListObjectsArgs.builder()
          .bucket(bucketName)
          .recursive(true)
          .build();

      Iterable<Result<Item>> objects = minioClient.listObjects(args);
      logger.info("Search results for query: {}", query);
      for (Result<Item> result : objects) {
        Item item = result.get();
        if (!item.isDir() && item.objectName().toLowerCase().contains(query.toLowerCase())) {
          logger.info("Found: {} (Size: {} bytes)", item.objectName(), item.size());
        }
      }
    } catch (ErrorResponseException e) {
      String errorMsg = String.format("Failed to search files in bucket '%s' with query '%s' - Server returned error: %s", 
          bucketName, query, e.getMessage());
      logger.error(errorMsg, e);
      throw new StorageException(errorMsg, e);
    } catch (InsufficientDataException | IOException e) {
      String errorMsg = String.format("I/O error while searching files in bucket '%s' with query '%s'", bucketName, query);
      logger.error(errorMsg, e);
      throw new StorageException(errorMsg, e);
    } catch (InternalException | ServerException e) {
      String errorMsg = String.format("Internal server error while searching files in bucket '%s' with query '%s'", bucketName, query);
      logger.error(errorMsg, e);
      throw new StorageException(errorMsg, e);
    } catch (InvalidKeyException | NoSuchAlgorithmException e) {
      String errorMsg = String.format("Authentication/encryption error while searching files in bucket '%s' with query '%s'", 
          bucketName, query);
      logger.error(errorMsg, e);
      throw new StorageException(errorMsg, e);
    } catch (InvalidResponseException | XmlParserException e) {
      String errorMsg = String.format("Error parsing server response while searching files in bucket '%s' with query '%s'", 
          bucketName, query);
      logger.error(errorMsg, e);
      throw new StorageException(errorMsg, e);
    }
  }

  @Override
  public void bulkDelete(String bucketName, String[] fileNames) {
    try {
      logger.info("Bulk deleting {} files in bucket: {}", fileNames.length, bucketName);
      List<DeleteObject> objects = new ArrayList<>();
      for (String fileName : fileNames) {
        objects.add(new DeleteObject(fileName));
        logger.debug("Added file to delete list: {}", fileName);
      }

      RemoveObjectsArgs args = RemoveObjectsArgs.builder()
          .bucket(bucketName)
          .objects(objects)
          .build();

      Iterable<Result<DeleteError>> results = minioClient.removeObjects(args);
      for (Result<DeleteError> result : results) {
        DeleteError error = result.get();
        logger.error("Error deleting {}: {}", error.objectName(), error.message());
      }
      logger.info("Bulk delete operation completed for {} files", fileNames.length);
    } catch (ErrorResponseException e) {
      String errorMsg = String.format("Access denied or invalid request when deleting files in bucket %s: %s", bucketName, e.getMessage());
      logger.error(errorMsg, e);
      throw new StorageException(errorMsg, e);
    } catch (InsufficientDataException | IOException e) {
      String errorMsg = String.format("I/O error occurred while deleting files in bucket %s: %s", bucketName, e.getMessage());
      logger.error(errorMsg, e);
      throw new StorageException(errorMsg, e);
    } catch (InternalException | ServerException e) {
      String errorMsg = String.format("Server error while deleting files in bucket %s: %s", bucketName, e.getMessage());
      logger.error(errorMsg, e);
      throw new StorageException(errorMsg, e);
    } catch (InvalidKeyException | NoSuchAlgorithmException e) {
      String errorMsg = "Configuration error: " + e.getMessage();
      logger.error(errorMsg, e);
      throw new StorageException(errorMsg, e);
    } catch (InvalidResponseException | XmlParserException e) {
      String errorMsg = String.format("Invalid response from storage service when deleting files in bucket %s: %s", bucketName, e.getMessage());
      logger.error(errorMsg, e);
      throw new StorageException(errorMsg, e);
    }
  }
}
