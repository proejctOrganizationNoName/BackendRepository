package com.project.demo.material.repository;


import com.amazonaws.services.s3.AmazonS3;
import com.project.demo.excpetion.CustomError;
import com.project.demo.material.domain.Material;
import com.project.demo.material.domain.QMaterial;
import com.project.demo.material.domain.RequestMaterialDto;
import com.project.demo.material.domain.ResponseMaterialDto;
import com.project.demo.utility.ClassCheck;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.project.demo.material.domain.QMaterial.*;
import static com.project.demo.material.domain.RequestMaterialDto.*;
import static com.project.demo.material.domain.ResponseMaterialDto.*;

@Repository

@RequiredArgsConstructor
public class AdvanceMaterialRepository {
    private final MaterialRepository materialRepository;
    private final JPAQueryFactory jpaQueryFactory;



    public Material saveMaterial(Material material){
       return materialRepository.save(material);
    }

    public Material findById(Long materialId){
        Optional<Material> material=materialRepository.findById(materialId);
        if(material.isEmpty()||material.get().getDeleted()){
            throw new CustomError("없는 자료입니다");
        }
        return material.get();
    }

    public Page<MaterialDto> MaterialConditionSearch(RequestConditionSearchMaterial requestConditionSearchMaterial
            , PageRequest pageRequest, String bucket, AmazonS3 amazonS3){
        List<MaterialDto> materialDtoList=jpaQueryFactory.select(
                        Projections.constructor(
                                MaterialDto.class,
                                material.id,
                                material.key,
                                material.materialType,
                                material.createDate.stringValue()
                        )
                )
                .from(material)
                .where(conditionSearch(requestConditionSearchMaterial).and(material.classCheck.eq(ClassCheck.TASK)))
                .offset(pageRequest.getOffset())
                .limit(pageRequest.getPageSize())
                .orderBy(material.createDate.desc())
                .fetch();
        Long count=jpaQueryFactory.select(
                    material.count()
                )
                .from(material)
                .where(conditionSearch(requestConditionSearchMaterial).and(material.classCheck.eq(ClassCheck.TASK)))
                .orderBy(material.createDate.desc())
                .fetch().getFirst();

        materialDtoList.stream().forEach(x->{
            x.makePreSignGetUrl(bucket,amazonS3);
        });
        return new PageImpl<>(materialDtoList,pageRequest,count);
    }

    private BooleanBuilder conditionSearch(RequestConditionSearchMaterial requestConditionSearchMaterial){
        BooleanBuilder booleanBuilder=new BooleanBuilder();

        if(requestConditionSearchMaterial.getMaterialType()!=null){
            booleanBuilder.and(material.materialType.eq(requestConditionSearchMaterial.getMaterialType()));
        }
        if(requestConditionSearchMaterial.getTaskId()!=null){
            booleanBuilder.and(material.refId.eq(requestConditionSearchMaterial.getTaskId()));
        }
        return booleanBuilder;
    }

}