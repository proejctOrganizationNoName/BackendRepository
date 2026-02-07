package com.project.demo.rule.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAgreement is a Querydsl query type for Agreement
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAgreement extends EntityPathBase<Agreement> {

    private static final long serialVersionUID = 1836650409L;

    public static final QAgreement agreement = new QAgreement("agreement");

    public final BooleanPath agree = createBoolean("agree");

    public final NumberPath<Long> agreementId = createNumber("agreementId", Long.class);

    public final BooleanPath deleted = createBoolean("deleted");

    public final NumberPath<Long> memberId = createNumber("memberId", Long.class);

    public final NumberPath<Long> projectId = createNumber("projectId", Long.class);

    public final NumberPath<Long> ruleId = createNumber("ruleId", Long.class);

    public QAgreement(String variable) {
        super(Agreement.class, forVariable(variable));
    }

    public QAgreement(Path<? extends Agreement> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAgreement(PathMetadata metadata) {
        super(Agreement.class, metadata);
    }

}

