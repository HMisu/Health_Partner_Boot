package com.bit.healthpartnerboot.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMemberHistory is a Querydsl query type for MemberHistory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberHistory extends EntityPathBase<MemberHistory> {

    private static final long serialVersionUID = -2001595315L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMemberHistory memberHistory = new QMemberHistory("memberHistory");

    public final NumberPath<Float> bmi = createNumber("bmi", Float.class);

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final NumberPath<Integer> height = createNumber("height", Integer.class);

    public final QMember member;

    public final NumberPath<Long> seq = createNumber("seq", Long.class);

    public final NumberPath<Integer> weight = createNumber("weight", Integer.class);

    public QMemberHistory(String variable) {
        this(MemberHistory.class, forVariable(variable), INITS);
    }

    public QMemberHistory(Path<? extends MemberHistory> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMemberHistory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMemberHistory(PathMetadata metadata, PathInits inits) {
        this(MemberHistory.class, metadata, inits);
    }

    public QMemberHistory(Class<? extends MemberHistory> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
    }

}

