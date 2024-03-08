package com.bit.healthpartnerboot.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMainFood is a Querydsl query type for MainFood
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMainFood extends EntityPathBase<MainFood> {

    private static final long serialVersionUID = -2119575004L;

    public static final QMainFood mainFood = new QMainFood("mainFood");

    public final StringPath code = createString("code");

    public final StringPath fdGrupp = createString("fdGrupp");

    public final NumberPath<Integer> foodCnt = createNumber("foodCnt", Integer.class);

    public final StringPath imgAddress = createString("imgAddress");

    public final StringPath name = createString("name");

    public final StringPath upperFdGrupp = createString("upperFdGrupp");

    public final NumberPath<Float> weight = createNumber("weight", Float.class);

    public QMainFood(String variable) {
        super(MainFood.class, forVariable(variable));
    }

    public QMainFood(Path<? extends MainFood> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMainFood(PathMetadata metadata) {
        super(MainFood.class, metadata);
    }

}

