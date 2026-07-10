//package com.universitymanagement.minio;
//
//
//import io.minio.*;
//import io.minio.messages.Item;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//=======
//import com.universitymanagement.minio.MinioConfig.MinioProperties;
//import io.minio.*;
//import io.minio.http.Method;
//import io.minio.messages.Item;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//>>>>>>> origin/main
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//
//@Service
//@RequiredArgsConstructor
//<<<<<<< HEAD
//public class MinioServiceImpl implements MinioService {
//    private final MinioClient minioClient;
//
//    @Value("${minio.bucket}")
//    private String bucket;
//
//    @Override
//    public String getPreviewUrl(String objectName) {
//        try {
//            return minioClient.getPresignedObjectUrl(
//                    GetPresignedObjectUrlArgs.builder()
//                            .method(Http.Method.GET)
//                            .bucket(bucket)
//                            .object(objectName)
//                            .expiry(60 * 60)
//                            .build()
//            );
//=======
//public class MinioServiceImpl implements MinioService{
//
//    private final MinioClient minioClient;
//    private final MinioProperties properties;
//
//    @Override
//    public String getPreviewUrl(String objectName) {
//
//        try {
//
//            return minioClient.getPresignedObjectUrl(
//                    GetPresignedObjectUrlArgs.builder()
//                            .method(Method.GET)
//                            .bucket(properties.getBucket())
//                            .object(objectName)
//                            .expiry(60 * 60)
//                            .build());
//
//>>>>>>> origin/main
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//<<<<<<< HEAD
//
//    @Override
//    public String uploadFile(MultipartFile file) {
//        try {
//            String fileName = UUID.randomUUID() + "-" + file.getOriginalFilename();
//
//            minioClient.putObject(
//                    PutObjectArgs.builder()
//                            .bucket(bucket)
//                            .object(fileName)
//                            .stream(file.getInputStream(), file.getSize(), -1L)
//=======
//    @Override
//    public String uploadFile(MultipartFile file) {
//
//        try {
//
//            String fileName = UUID.randomUUID()
//                    + "-"
//                    + file.getOriginalFilename();
//
//            minioClient.putObject(
//                    PutObjectArgs.builder()
//                            .bucket(properties.getBucket())
//                            .object(fileName)
//                            .stream(
//                                    file.getInputStream(),
//                                    file.getSize(),
//                                    -1
//                            )
//>>>>>>> origin/main
//                            .contentType(file.getContentType())
//                            .build()
//            );
//
//            return fileName;
//
//        } catch (Exception e) {
//<<<<<<< HEAD
//            throw new RuntimeException("Upload failed", e);
//=======
//            throw new RuntimeException("Failed to upload file", e);
//>>>>>>> origin/main
//        }
//    }
//
//    @Override
//<<<<<<< HEAD
//    public List<String> getAllFileByMinio() {
//        List<String> files = new ArrayList<>();
//
//        Iterable<Result<Item>> results = minioClient.listObjects(
//                ListObjectsArgs.builder()
//                        .bucket("products")
//                        .build()
//        );
//
//        for (Result<Item> result : results){
//            try{
//                Item item = result.get();
//                files.add(item.objectName());
//            }catch (Exception e){
//                throw new RuntimeException(e);
//            }
//        }
//=======
//    public List<String> getAllFIleByMinio() {
//
//        List<String> files = new ArrayList<>();
//
//        Iterable<Result<Item>> results =
//                minioClient.listObjects(
//                        ListObjectsArgs.builder()
//                                .bucket(properties.getBucket())
//                                .build());
//
//        for (Result<Item> result : results) {
//
//            try {
//                files.add(result.get().objectName());
//
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        }
//
//>>>>>>> origin/main
//        return files;
//    }
//}
