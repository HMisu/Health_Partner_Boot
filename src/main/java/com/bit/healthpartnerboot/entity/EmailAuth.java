package com.bit.healthpartnerboot.entity;

import com.bit.healthpartnerboot.converter.AuthTypeConverter;
import com.bit.healthpartnerboot.dto.AuthType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "TB_EMAIL_AUTH")
public class EmailAuth {
    private static final Long MAX_EXPIRE_TIME = 5L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "email_auth_seq")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_seq")
    private Member member;

    @Convert(converter = AuthTypeConverter.class)
    private AuthType authType;

    private String authToken;

    private Boolean expired;

    private LocalDateTime expireDate;

    public void useToken() {
        this.expired = true;
    }
}
