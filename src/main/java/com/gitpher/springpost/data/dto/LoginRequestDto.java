package com.gitpher.springpost.data.dto;

import com.gitpher.springpost.data.entity.Authority;
import com.gitpher.springpost.data.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Setter
public class LoginRequestDto {
    private String nickname;
    private String password;

    public User toUser(PasswordEncoder passwordEncoder) {
        return User.builder()
                .nickname(nickname)
                .password(password)
                .authority(Authority.ROLE_USER)
                .build();
    }

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(nickname, password);
    }

}
