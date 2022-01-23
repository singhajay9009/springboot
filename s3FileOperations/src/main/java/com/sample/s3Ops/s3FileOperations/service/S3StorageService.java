package com.sample.s3Ops.s3FileOperations.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.sample.s3Ops.s3FileOperations.config.S3ConfigProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class S3StorageService {

    private final S3ConfigProperties awsConfigProperties;

    private final AmazonS3 s3Client;

    public String uploadFile(MultipartFile multipartFile) throws Exception {
        Optional.ofNullable(multipartFile).orElseThrow(() ->
                new IllegalArgumentException("Multipart File can not be null"));

        File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(file);
        String fileName = multipartFile.getOriginalFilename();
        try{
            fos.write(multipartFile.getBytes());
            s3Client.putObject(new PutObjectRequest(awsConfigProperties.getBucket(), fileName,file));
            file.delete();
        }catch(IOException e){
            throw new Exception("Error in uploading file: " + file.getName(), e);
        }finally{
            fos.close();
        }
        return "File: " + fileName + "uploaded successfully...";
    }

    public byte[] downloadFile(String fileName) throws Exception {
        try{
            S3Object s3Object = s3Client.getObject(awsConfigProperties.getBucket(),fileName);
            S3ObjectInputStream inputStream = s3Object.getObjectContent();
            return inputStream.readAllBytes();
        }catch(IOException e){
            throw new Exception("Exception while downloading file: " + fileName);
        }
    }

    public String deleteFile(String fileName){
        String bucketName = awsConfigProperties.getBucket();
        s3Client.deleteObject(bucketName, fileName);
        return "File: " + fileName + " deleted successfully";
    }
}
