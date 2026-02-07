package com.project.demo.material.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMaterial is a Querydsl query type for Material
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMaterial extends EntityPathBase<Material> {

    private static final long serialVersionUID = -1441516717L;

    public static final QMaterial material = new QMaterial("material");

    public final com.project.demo.utility.QBaseTime _super = new com.project.demo.utility.QBaseTime(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final BooleanPath deleted = createBoolean("deleted");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath key = createString("key");

    public final EnumPath<MaterialType> materialType = createEnum("materialType", MaterialType.class);

    public final NumberPath<Long> memberId = createNumber("memberId", Long.class);

    public final NumberPath<Long> taskId = createNumber("taskId", Long.class);

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateDate = _super.updateDate;

    public QMaterial(String variable) {
        super(Material.class, forVariable(variable));
    }

    public QMaterial(Path<? extends Material> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMaterial(PathMetadata metadata) {
        super(Material.class, metadata);
    }

}

