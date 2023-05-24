package com.developers.uzchat.service.service_impl;

import com.amazonaws.services.s3.AmazonS3;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayDeque;
import java.util.Queue;

@Service
public class S3Service {
    private AmazonS3 s3client;

    //@Value('s3.bucket-name')
    private String bucketName;

}
