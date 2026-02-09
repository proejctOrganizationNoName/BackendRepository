package com.project.demo.proceeding.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QProceedingLog is a Querydsl query type for ProceedingLog
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProceedingLog extends EntityPathBase<ProceedingLog> {

    private static final long serialVersionUID = 905805975L;

    public static final QProceedingLog proceedingLog = new QProceedingLog("proceedingLog");

    public final BooleanPath deleted = createBoolean("deleted");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> memberId = createNumber("memberId", Long.class);

    public final NumberPath<Long> proceedingId = createNumber("proceedingId", Long.class);

    public QProceedingLog(String variable) {
        super(ProceedingLog.class, forVariable(variable));
    }

    public QProceedingLog(Path<? extends ProceedingLog> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProceedingLog(PathMetadata metadata) {
        super(ProceedingLog.class, metadata);
    }

}

