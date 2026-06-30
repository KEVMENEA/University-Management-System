package com.universitymanagement.minio;

import com.universitymanagement.minio.MinioConfig.MinioProperties;
import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MinioServiceImpl implements MinioService{

    private final MinioClient minioClient;
    private final MinioProperties properties;

    @Override
    public String getPreviewUrl(String objectName) {

        try {

            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(properties.getBucket())
                            .object(objectName)
                            .expiry(60 * 60)
                            .build());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String uploadFile(MultipartFile file) {

        try {

            String fileName = UUID.randomUUID()
                    + "-"
                    + file.getOriginalFilename();

            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(properties.getBucket())
                            .object(fileName)
                            .stream(
                                    file.getInputStream(),
                                    file.getSize(),
                                    -1
                            )
                            .contentType(file.getContentType())
                            .build()
            );

            return fileName;

        } catch (Exception e) {
            throw new RuntimeException("Failed to upload file", e);
        }
    }

    @Override
    public List<String> getAllFIleByMinio() {

        List<String> files = new ArrayList<>();

        Iterable<Result<Item>> results =
                minioClient.listObjects(
                        ListObjectsArgs.builder()
                                .bucket(properties.getBucket())
                                .build());

        for (Result<Item> result : results) {

            try {
                files.add(result.get().objectName());

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return files;
    }
}
