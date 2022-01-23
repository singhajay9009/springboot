package com.sample.s3Ops.s3FileOperations.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class AwsS3Client {

    private final S3ConfigProperties s3ConfigProperties;

    @Autowired
    public AwsS3Client(final S3ConfigProperties s3ConfigProperties) {
        this.s3ConfigProperties = s3ConfigProperties;
    }


    @Bean
    private AmazonS3 s3Client() {
        AWSCredentials credentials = new BasicAWSCredentials(s3ConfigProperties.getAccessKey(), s3ConfigProperties.getAccessSecret());
        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(s3ConfigProperties.getRegion()).build();
    }
}
