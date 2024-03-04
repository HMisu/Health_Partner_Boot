package com.bit.healthpartnerboot.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTodoFood is a Querydsl query type for TodoFood
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTodoFood extends EntityPathBase<TodoFood> {

    private static final long serialVersionUID = 888090161L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTodoFood todoFood = new QTodoFood("todoFood");

    public final StringPath foodCode = createString("foodCode");

    public final StringPath foodIdntCode = createString("foodIdntCode");

    public final EnumPath<com.bit.healthpartnerboot.dto.MealType> mealType = createEnum("mealType", com.bit.healthpartnerboot.dto.MealType.class);

    public final NumberPath<Long> seq = createNumber("seq", Long.class);

    public final QTodo todo;

    public final NumberPath<Float> weight = createNumber("weight", Float.class);

    public QTodoFood(String variable) {
        this(TodoFood.class, forVariable(variable), INITS);
    }

    public QTodoFood(Path<? extends TodoFood> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTodoFood(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTodoFood(PathMetadata metadata, PathInits inits) {
        this(TodoFood.class, metadata, inits);
    }

    public QTodoFood(Class<? extends TodoFood> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.todo = inits.isInitialized("todo") ? new QTodo(forProperty("todo"), inits.get("todo")) : null;
    }

}

