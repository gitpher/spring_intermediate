package com.gitpher.springpost.data.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {
    private String nickname;
    private String password1;
    private String password2;
}
