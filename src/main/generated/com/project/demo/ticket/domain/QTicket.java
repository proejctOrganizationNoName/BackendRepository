package com.project.demo.ticket.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QTicket is a Querydsl query type for Ticket
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTicket extends EntityPathBase<Ticket> {

    private static final long serialVersionUID = 606952349L;

    public static final QTicket ticket = new QTicket("ticket");

    public final com.project.demo.utility.QBaseTime _super = new com.project.demo.utility.QBaseTime(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final BooleanPath deleted = createBoolean("deleted");

    public final NumberPath<Long> memberId = createNumber("memberId", Long.class);

    public final NumberPath<Long> projectId = createNumber("projectId", Long.class);

    public final StringPath role = createString("role");

    public final EnumPath<TicketGrade> ticketGrade = createEnum("ticketGrade", TicketGrade.class);

    public final NumberPath<Long> ticketId = createNumber("ticketId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateDate = _super.updateDate;

    public QTicket(String variable) {
        super(Ticket.class, forVariable(variable));
    }

    public QTicket(Path<? extends Ticket> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTicket(PathMetadata metadata) {
        super(Ticket.class, metadata);
    }

}

