package com.project.demo.material.domain;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.project.demo.utility.CustomDateTimeFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.Date;

public class ResponseMaterialDto {


    @NoArgsConstructor
    @Getter
    public final static class MaterialDto{
        private Long materialId;
        private String getUrl;
        private MaterialType materialType;
        private String createDate;

        @Builder
        public MaterialDto(Long materialId, String getUrl,
                           MaterialType materialType,String createDate) {
            this.materialId = materialId;
            this.getUrl = getUrl;
            this.materialType = materialType;
            this.createDate=createDate;
        }
        public void makePreSignGetUrl(String bucket, AmazonS3 amazonS3){
            Date expiration = new Date();
            long expTimeMillis = expiration.getTime();
            expTimeMillis += 1000 * 60 * 60; // 1시간 유효
            expiration.setTime(expTimeMillis);

            GeneratePresignedUrlRequest generatePresignedUrlRequest =
                    new GeneratePresignedUrlRequest(bucket, getUrl)
                            .withMethod(HttpMethod.GET)
                            .withExpiration(expiration);
            URL url = amazonS3.generatePresignedUrl(generatePresignedUrlRequest);
            this.getUrl=url.toString();
        }
        public void parseDateTime(){
            this.createDate= CustomDateTimeFormat.parseServerTimeToClientFormat(
                    LocalDateTime.parse(this.createDate));
        }
    }
}
