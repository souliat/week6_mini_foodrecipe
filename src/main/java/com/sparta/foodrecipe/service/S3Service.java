package com.sparta.foodrecipe.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3Client amazonS3Client;
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    public String bucket;

    public String upload(MultipartFile file, String dirName){

        String fileName = createFileName(file.getOriginalFilename(), dirName);
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        objectMetadata.setContentType(file.getContentType());

        try(InputStream inputStream = file.getInputStream()) {
            amazonS3.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
            return amazonS3.getUrl(bucket, fileName).toString();

        }catch (IOException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드에 실패하셨습니다");
        }
    }

    private String createFileName(String fileName, String dirName) {
        // 먼저 파일 업로드 시, 파일명을 난수화하기 위해 random으로 돌립니다.
        return dirName + "/" + UUID.randomUUID().toString().concat(getFileExtension(fileName));
    }

    private String getFileExtension(String fileName) {
        // file 형식이 잘못된 경우를 확인하기 위해 만들어진 로직이며,
        // 파일 타입과 상관없이 업로드할 수 있게 하기 위해 .의 존재 유무만 판단
        try {
            return fileName.substring(fileName.lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 형식의 파일(" + fileName + ") 입니다.");
        }

    }

    public String updatePost(MultipartFile file, String currentFilePath, String dirName) {

        // 계속 403 Access Denied 됨...
        if(!"".equals(currentFilePath) && currentFilePath != null){
            boolean isExistObject = amazonS3.doesObjectExist(bucket, currentFilePath);
            if(isExistObject){
                amazonS3.deleteObject(bucket, currentFilePath);
            }
        }
        String fileName = createFileName(file.getOriginalFilename(), dirName);
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        objectMetadata.setContentType(file.getContentType());

        try(InputStream inputStream = file.getInputStream()) {
            amazonS3.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
            return amazonS3.getUrl(bucket, fileName).toString();

        }catch (IOException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드에 실패하셨습니다");
        }

    }



//    public Map<String, String> upload(MultipartFile multipartFile, String dirName) throws IOException{
//        File uploadFile = convert(multipartFile).orElseThrow(() -> new IllegalArgumentException("파일 전환 실패"));
//
//        return upload(uploadFile, dirName);
//    }
//    // S3로 파일 업로드하기
//    private Map<String, String> upload(File uploadFile, String dirName) {
//        Map<String, String> result = new HashMap<>();
//        String fileName = dirName + "/" + UUID.randomUUID() + uploadFile.getName();   // S3에 저장된 파일 이름
//
//        String uploadImageUrl = putS3(uploadFile, fileName); // s3로 업로드
//        removeNewFile(uploadFile);
//
//        result.put("imageUrl", uploadImageUrl);
//        result.put("fileName", fileName);
//
//        return result;
//    }
//
//    // S3로 업로드
//    private String putS3(File uploadFile, String fileName) {
//        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
//        return amazonS3Client.getUrl(bucket, fileName).toString();
//    }
//
//    // S3에서 이미지 삭제
//    public void deleteFileFromS3(String fileName) {
//        log.info("왜 문제가 될까?");
//        DeleteObjectRequest request = new DeleteObjectRequest(bucket, fileName);
//        amazonS3Client.deleteObject(request);
//    }
//
//    // 로컬에 저장된 이미지 지우기
//    private void removeNewFile(File targetFile) {
//        if (targetFile.delete()) {
//            log.info("File delete success");
//            return;
//        }
//        log.info("File delete fail");
//    }
//
//    private Optional<File> convert(MultipartFile multipartFile) throws IOException{
//        File convertFile = new File(System.getProperty("user.dir") + "/" + multipartFile.getOriginalFilename());
//        // 바로 위에서 지정한 경로에 File이 생성됨 (경로가 잘못되었다면 생성 불가능)
//        if (convertFile.createNewFile()) {
//            try (FileOutputStream fos = new FileOutputStream(convertFile)) { // FileOutputStream 데이터를 파일에 바이트 스트림으로 저장하기 위함
//                fos.write(multipartFile.getBytes());
//            }
//            return Optional.of(convertFile);
//        }
//
//        return Optional.empty();
//    }
}