package com.skrbomb.eCommerce.service;


import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
@Slf4j
public class AwsS3Service {

    private final String bucketName="skrbomb-ecommerce";

    @Value("${aws.s3.access}")
    private  String awsS3Access;

    @Value("${aws.s3.secrete}")
    private String awsS3Secret;

    public String saveImageToS3(MultipartFile image){

        try{
            String s3FileName=image.getOriginalFilename();
            //create AWS credentials using the access and secret key
            BasicAWSCredentials awsCredentials=new BasicAWSCredentials(awsS3Access,awsS3Secret);

            //create a s3 client with config credentials and region
            AmazonS3 s3Client= AmazonS3ClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                    .withRegion(Regions.AP_SOUTHEAST_2)
                    .build();

            //get input stream from image
            InputStream inputStream=image.getInputStream();

            //set metadata for the object
            ObjectMetadata metadata=new ObjectMetadata();
            metadata.setContentType("image/jpeg");

            //create a put request to upload the image to s3
            PutObjectRequest putObjectRequest=new PutObjectRequest(bucketName,s3FileName,inputStream,metadata);
            s3Client.putObject(putObjectRequest);

        //  ex: https://skrbomb-ecommerce.s3.ap-southeast-2.amazonaws.com/shoes_converse_01.jpeg
            return "https://"+bucketName+".s3.ap-southeast-2.amazonaws.com/"+s3FileName;



        }catch (IOException e){
            e.printStackTrace();
            throw new RuntimeException("Unable to save image to AWS S3 bucket: "+e.getMessage());
        }
    }
}
