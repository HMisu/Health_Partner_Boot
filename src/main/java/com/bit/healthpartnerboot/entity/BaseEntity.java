package com.bit.healthpartnerboot.entity;

import com.bit.healthpartnerboot.listener.Auditable;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@MappedSuperclass
@EntityListeners(value = AuditingEntityListener.class)
public class BaseEntity implements Auditable {
    @CreatedDate
    // 현업에서 auto-ddl과 comment 속성은 잘 사용하지 않음.
    @Comment("생성시간")
    @Column(columnDefinition = "datetime(6) default now(6)", updatable = false, nullable = false)
    //@Column(columnDefinition = "datetime(6) default now(6) comment '생성시간'", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Comment("수정시간")
    @Column(columnDefinition = "datetime(6) default now(6)", nullable = false)
    // @Column(columnDefinition = "datetime(6) default now(6) comment '수정시간'", nullable = false)
    private LocalDateTime updatedAt;
}
