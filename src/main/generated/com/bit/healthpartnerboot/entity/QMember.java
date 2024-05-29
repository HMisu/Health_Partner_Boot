package com.bit.healthpartnerboot.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = 1194891847L;

    public static final QMember member = new QMember("member1");

    public final NumberPath<Integer> age = createNumber("age", Integer.class);

    public final NumberPath<Float> bmi = createNumber("bmi", Float.class);

    public final StringPath email = createString("email");

    public final NumberPath<Integer> goalPedometer = createNumber("goalPedometer", Integer.class);

    public final NumberPath<Integer> goalWater = createNumber("goalWater", Integer.class);

    public final NumberPath<Float> height = createNumber("height", Float.class);

    public final StringPath imgAddress = createString("imgAddress");

    public final BooleanPath isActive = createBoolean("isActive");

    public final DateTimePath<java.time.LocalDateTime> lastLoginDate = createDateTime("lastLoginDate", java.time.LocalDateTime.class);

    public final StringPath name = createString("name");

    public final StringPath password = createString("password");

    public final StringPath provider = createString("provider");

    public final EnumPath<com.bit.healthpartnerboot.dto.Role> role = createEnum("role", com.bit.healthpartnerboot.dto.Role.class);

    public final NumberPath<Integer> seq = createNumber("seq", Integer.class);

    public final NumberPath<Float> weight = createNumber("weight", Float.class);

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}

