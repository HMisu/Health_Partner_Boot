package com.bit.healthpartnerboot.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QFood is a Querydsl query type for Food
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFood extends EntityPathBase<MainFood> {

    private static final long serialVersionUID = -816831669L;

    public static final QFood food = new QFood("food");

    public final StringPath code = createString("code");

    public final StringPath fdGrupp = createString("fdGrupp");

    public final NumberPath<Integer> foodCnt = createNumber("foodCnt", Integer.class);

    public final StringPath imgAddress = createString("imgAddress");

    public final StringPath name = createString("name");

    public final StringPath upperFdGrupp = createString("upperFdGrupp");

    public final NumberPath<Float> weight = createNumber("weight", Float.class);

    public QFood(String variable) {
        super(MainFood.class, forVariable(variable));
    }

    public QFood(Path<? extends MainFood> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFood(PathMetadata metadata) {
        super(MainFood.class, metadata);
    }

}

