package com.project.demo.material.service;



import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.project.demo.material.domain.Material;
import com.project.demo.material.domain.MaterialType;
import com.project.demo.material.domain.ResponseMaterialDto;
import com.project.demo.material.repository.AdvanceMaterialRepository;
import lombok.RequiredArgsConstructor;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import static com.project.demo.material.domain.RequestMaterialDto.*;
import static com.project.demo.material.domain.ResponseMaterialDto.*;

@Service
@RequiredArgsConstructor
public class MaterialService {
    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucket;
    private final Tika tika;
    private final AmazonS3 amazonS3;
    private final AdvanceMaterialRepository advanceMaterialRepository;

   public MaterialDto checkFile(MultipartFile file, RequestSaveMaterial requestSaveMaterial){

       MaterialType fileType;

       try (InputStream is = file.getInputStream()) {
           String mimeType = tika.detect(is);
           fileType=MaterialType.findByMimeType(mimeType);
       } catch (IOException e) {
           throw new RuntimeException(e);
       }

       ObjectMetadata metadata = new ObjectMetadata();
       metadata.setContentType(fileType.getMimeType());
       metadata.setContentLength(file.getSize());
       String key=fileType.getMimeType()+"/"+UUID.randomUUID().toString()+"-"+file.getOriginalFilename();

       try (InputStream uploadStream = file.getInputStream()) {
          amazonS3.putObject(bucket, key, uploadStream, metadata);
       } catch (IOException e) {
           throw new RuntimeException("업로드 실패", e);
       }

       Material material=Material.builder()
               .materialType(fileType)
               .key(key)
               .title(file.getOriginalFilename())
               .memberId(requestSaveMaterial.getMemberId())
               .taskId(requestSaveMaterial.getTaskId())
               .build();

       material=advanceMaterialRepository.saveMaterial(material);

       MaterialDto materialDto= MaterialDto.builder()
               .materialType(material.getMaterialType())
               .materialId(material.getId())
               .getUrl(material.getKey())
               .build();

       materialDto.makePreSignGetUrl(bucket,amazonS3);

       return materialDto;
   }

   public MaterialDto requestNewGetUrl(Long materialId){
       Material material=advanceMaterialRepository.findById(materialId);
       MaterialDto materialDto=MaterialDto.builder()
               .materialId(materialId)
               .getUrl(material.getKey())
               .build();
       materialDto.makePreSignGetUrl(bucket,amazonS3);
       return materialDto;
   }

   public void deleteMaterial(Long materialId){
       Material material=advanceMaterialRepository.findById(materialId);
       material.updateDeleted();
   }

   public Page<MaterialDto> requestMaterialConditionSearch(RequestConditionSearchMaterial requestConditionSearchMaterial){
        return advanceMaterialRepository.MaterialConditionSearch(
                requestConditionSearchMaterial,
                PageRequest.of(
                        requestConditionSearchMaterial.provideOffset(),10)
        ,bucket,amazonS3);
   }


}
