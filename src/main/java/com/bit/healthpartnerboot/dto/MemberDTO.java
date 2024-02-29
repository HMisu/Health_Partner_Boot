package com.bit.healthpartnerboot.dto;

import com.bit.healthpartnerboot.entity.Member;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDTO {
    private long seq;
    private String username;
    private String password;
    private String role;
    private String token;

    public Member toEntity() {
        return Member.builder()
                .seq(this.seq)
                .username(this.username)
                .password(this.password)
                .role(this.role)
                .build();
    }

}
