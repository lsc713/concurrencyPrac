//package com.service.concurrencyprac.common.image.service;
//
//import com.amazonaws.services.s3.AmazonS3Client;
//import com.amazonaws.services.s3.model.DeleteObjectRequest;
//import com.amazonaws.services.s3.model.ObjectMetadata;
//import com.service.concurrencyprac.common.image.dto.ImagesSaveDto;
//import com.service.concurrencyprac.common.image.dto.ImagesSaveDto.ItemImageResponseDto;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.multipart.MultipartFile;
//
//@Service
//@RequiredArgsConstructor
//public class S3Service {
//
//    @Value("${cloud.aws.s3.bucket}")
//    private String bucketName;
//    private final AmazonS3Client amazonS3Client;
//
//    @Transactional
//    public List<ItemImageResponseDto> saveImages(ImagesSaveDto saveDto) {
//        List<ItemImageResponseDto> resultList = new ArrayList<>();
//
//        for (MultipartFile multipartFile : saveDto.getImages()) {
//            ItemImageResponseDto itemImageResponseDto = saveImage(multipartFile);
//            resultList.add(itemImageResponseDto);
//        }
//        return resultList;
//    }
//
//    @Transactional
//    public ItemImageResponseDto saveImage(MultipartFile multipartFile) {
//
//        String originalName = multipartFile.getOriginalFilename();
//        String savedImage = getFileName(originalName);
//
//        try {
//            ObjectMetadata objectMetadata = new ObjectMetadata();
//            objectMetadata.setContentType(multipartFile.getContentType());
//            objectMetadata.setContentLength(multipartFile.getInputStream().available());
//
//            amazonS3Client.putObject(bucketName, savedImage, multipartFile.getInputStream(), objectMetadata);
//
//        } catch (IOException e) {
//
//        }
//        String accessUrl = amazonS3Client.getUrl(bucketName, savedImage).toString();
//
//        return new ItemImageResponseDto(originalName, savedImage, accessUrl);
//    }
//
//    @Transactional
//    public void deleteImage(String file) {
//        String splitStr = ".com/";
//        String filename = file.substring(file.lastIndexOf(splitStr) + splitStr.length());
//
//        amazonS3Client.deleteObject(new DeleteObjectRequest(bucketName,filename));
//    }
//
//    //파일 이름 저장을 위한 이름 변환 메서드
//    public String getFileName(String originName) {
//        return UUID.randomUUID() + extractExtension(originName);
//    }
//
//    //이미지에서 확장자 추출 메소드
//    public String extractExtension(String originName) {
//        int index = originName.lastIndexOf('.');
//        return originName.substring(index, originName.length());
//    }
//
//}
