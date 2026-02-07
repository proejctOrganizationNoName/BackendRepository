package com.project.demo.notify.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QNotify is a Querydsl query type for Notify
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNotify extends EntityPathBase<Notify> {

    private static final long serialVersionUID = -1917385385L;

    public static final QNotify notify = new QNotify("notify");

    public final com.project.demo.utility.QBaseTime _super = new com.project.demo.utility.QBaseTime(this);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final BooleanPath deleted = createBoolean("deleted");

    public final NumberPath<Long> notifyId = createNumber("notifyId", Long.class);

    public final NumberPath<Long> projectId = createNumber("projectId", Long.class);

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateDate = _super.updateDate;

    public QNotify(String variable) {
        super(Notify.class, forVariable(variable));
    }

    public QNotify(Path<? extends Notify> path) {
        super(path.getType(), path.getMetadata());
    }

    public QNotify(PathMetadata metadata) {
        super(Notify.class, metadata);
    }

}

