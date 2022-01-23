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


import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class S3StorageService {

    private final S3ConfigProperties awsConfigProperties;

    private final AmazonS3 s3Client;

    public String uploadFile(File file, String fileName) {
        s3Client.putObject(new PutObjectRequest(awsConfigProperties.getBucket(), fileName, file));
        file.delete();
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

    public String deleteFile(String bucketName, String fileName){
        s3Client.deleteObject(bucketName, fileName);
        return "File: " + fileName + " deleted successfully";
    }
}
