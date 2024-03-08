package com.bit.healthpartnerboot.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStep is a Querydsl query type for Step
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStep extends EntityPathBase<Step> {

    private static final long serialVersionUID = -816439879L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStep step = new QStep("step");

    public final QBaseTime _super = new QBaseTime(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final QMember member;

    public final NumberPath<Integer> seq = createNumber("seq", Integer.class);

    public final NumberPath<Integer> stepNum = createNumber("stepNum", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QStep(String variable) {
        this(Step.class, forVariable(variable), INITS);
    }

    public QStep(Path<? extends Step> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStep(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStep(PathMetadata metadata, PathInits inits) {
        this(Step.class, metadata, inits);
    }

    public QStep(Class<? extends Step> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
    }

}

