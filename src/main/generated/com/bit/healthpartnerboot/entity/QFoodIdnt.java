package com.bit.healthpartnerboot.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QFoodIdnt is a Querydsl query type for FoodIdnt
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFoodIdnt extends EntityPathBase<FoodIdnt> {

    private static final long serialVersionUID = -1731577332L;

    public static final QFoodIdnt foodIdnt = new QFoodIdnt("foodIdnt");

    public final NumberPath<Float> carbohydrate = createNumber("carbohydrate", Float.class);

    public final StringPath code = createString("code");

    public final NumberPath<Float> energy = createNumber("energy", Float.class);

    public final NumberPath<Float> faessf = createNumber("faessf", Float.class);

    public final NumberPath<Float> fafref = createNumber("fafref", Float.class);

    public final NumberPath<Float> famsf = createNumber("famsf", Float.class);

    public final NumberPath<Float> fapuf = createNumber("fapuf", Float.class);

    public final NumberPath<Float> fibtg = createNumber("fibtg", Float.class);

    public final StringPath name = createString("name");

    public final NumberPath<Float> prot = createNumber("prot", Float.class);

    public final NumberPath<Float> sugar = createNumber("sugar", Float.class);

    public final NumberPath<Float> water = createNumber("water", Float.class);

    public final NumberPath<Float> weight = createNumber("weight", Float.class);

    public QFoodIdnt(String variable) {
        super(FoodIdnt.class, forVariable(variable));
    }

    public QFoodIdnt(Path<? extends FoodIdnt> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFoodIdnt(PathMetadata metadata) {
        super(FoodIdnt.class, metadata);
    }

}

