package com.project.demo.rule.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRule is a Querydsl query type for Rule
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRule extends EntityPathBase<Rule> {

    private static final long serialVersionUID = -123760131L;

    public static final QRule rule = new QRule("rule");

    public final com.project.demo.utility.QBaseTime _super = new com.project.demo.utility.QBaseTime(this);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final BooleanPath deleted = createBoolean("deleted");

    public final NumberPath<Long> projectId = createNumber("projectId", Long.class);

    public final NumberPath<Long> ruleId = createNumber("ruleId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateDate = _super.updateDate;

    public QRule(String variable) {
        super(Rule.class, forVariable(variable));
    }

    public QRule(Path<? extends Rule> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRule(PathMetadata metadata) {
        super(Rule.class, metadata);
    }

}

