package com.ichamrong.filemanager.service;

public interface MinioService {

  void createBucket(String bucketName);

  void uploadFile(String bucketName, String fileName, String contentType, byte[] file);

  void downloadFile(String bucketName, String fileName, String contentType);

  void deleteFile(String bucketName, String fileName);

  void renameFile(String bucketName, String fileName, String newFileName);

  void moveFile(String bucketName, String fileName, String newBucketName);

  void createFolder(String bucketName, String folderName);

  void deleteFolder(String bucketName, String folderName);

  void renameFolder(String bucketName, String folderName, String newFolderName);

  void moveFolder(String bucketName, String folderName, String newBucketName);

  void listFiles(String bucketName);

  void listFolders(String bucketName);

  void getFolder(String bucketName, String folderName);

  void getFile(String bucketName, String fileName);

  void getAccessLevel(String bucketName, String fileName);

  void updateAccessLevel(String bucketName, String fileName, String accessLevel);

  void searchFiles(String bucketName, String query);

  void bulkDelete(String bucketName, String[] fileNames);
}
