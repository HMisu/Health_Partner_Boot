package com.bit.healthpartnerboot.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMainFoodIngredients is a Querydsl query type for MainFoodIngredients
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMainFoodIngredients extends EntityPathBase<MainFoodIngredients> {

    private static final long serialVersionUID = 1284151294L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMainFoodIngredients mainFoodIngredients = new QMainFoodIngredients("mainFoodIngredients");

    public final QFoodIdnt foodIdnt;

    public final QMainFood mainFood;

    public QMainFoodIngredients(String variable) {
        this(MainFoodIngredients.class, forVariable(variable), INITS);
    }

    public QMainFoodIngredients(Path<? extends MainFoodIngredients> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMainFoodIngredients(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMainFoodIngredients(PathMetadata metadata, PathInits inits) {
        this(MainFoodIngredients.class, metadata, inits);
    }

    public QMainFoodIngredients(Class<? extends MainFoodIngredients> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.foodIdnt = inits.isInitialized("foodIdnt") ? new QFoodIdnt(forProperty("foodIdnt")) : null;
        this.mainFood = inits.isInitialized("mainFood") ? new QMainFood(forProperty("mainFood")) : null;
    }

}

