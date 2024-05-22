package com.bit.healthpartnerboot.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailAuthDTO {
    private String verifyCode;
    private String email;
}
