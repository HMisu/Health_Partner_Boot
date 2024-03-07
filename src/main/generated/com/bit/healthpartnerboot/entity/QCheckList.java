package com.bit.healthpartnerboot.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCheckList is a Querydsl query type for CheckList
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCheckList extends EntityPathBase<CheckList> {

    private static final long serialVersionUID = -609715463L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCheckList checkList = new QCheckList("checkList");

    public final QBaseTime _super = new QBaseTime(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final BooleanPath isCheck = createBoolean("isCheck");

    public final NumberPath<Integer> seq = createNumber("seq", Integer.class);

    public final StringPath text = createString("text");

    public final QTodo todo;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QCheckList(String variable) {
        this(CheckList.class, forVariable(variable), INITS);
    }

    public QCheckList(Path<? extends CheckList> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCheckList(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCheckList(PathMetadata metadata, PathInits inits) {
        this(CheckList.class, metadata, inits);
    }

    public QCheckList(Class<? extends CheckList> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.todo = inits.isInitialized("todo") ? new QTodo(forProperty("todo"), inits.get("todo")) : null;
    }

}

