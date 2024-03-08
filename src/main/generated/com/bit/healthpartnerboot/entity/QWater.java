package com.bit.healthpartnerboot.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWater is a Querydsl query type for Water
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWater extends EntityPathBase<Water> {

    private static final long serialVersionUID = 463309770L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWater water = new QWater("water");

    public final QBaseTime _super = new QBaseTime(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Integer> intakeWater = createNumber("intakeWater", Integer.class);

    public final QMember member;

    public final NumberPath<Integer> seq = createNumber("seq", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QWater(String variable) {
        this(Water.class, forVariable(variable), INITS);
    }

    public QWater(Path<? extends Water> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWater(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWater(PathMetadata metadata, PathInits inits) {
        this(Water.class, metadata, inits);
    }

    public QWater(Class<? extends Water> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
    }

}

