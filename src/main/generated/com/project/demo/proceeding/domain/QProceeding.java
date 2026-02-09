package com.project.demo.proceeding.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QProceeding is a Querydsl query type for Proceeding
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProceeding extends EntityPathBase<Proceeding> {

    private static final long serialVersionUID = 1045550957L;

    public static final QProceeding proceeding = new QProceeding("proceeding");

    public final com.project.demo.utility.QBaseTime _super = new com.project.demo.utility.QBaseTime(this);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final BooleanPath deleted = createBoolean("deleted");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> projectId = createNumber("projectId", Long.class);

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateDate = _super.updateDate;

    public QProceeding(String variable) {
        super(Proceeding.class, forVariable(variable));
    }

    public QProceeding(Path<? extends Proceeding> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProceeding(PathMetadata metadata) {
        super(Proceeding.class, metadata);
    }

}

