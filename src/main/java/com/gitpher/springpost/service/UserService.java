package com.gitpher.springpost.service;

import com.gitpher.springpost.config.security.jwt.JwtFilter;
import com.gitpher.springpost.config.security.jwt.TokenProvider;
import com.gitpher.springpost.data.dto.LoginRequestDto;
import com.gitpher.springpost.data.dto.ResponseDto;
import com.gitpher.springpost.data.dto.SignupRequestDto;
import com.gitpher.springpost.data.dto.TokenDto;
import com.gitpher.springpost.data.entity.Authority;
import com.gitpher.springpost.data.entity.RefreshToken;
import com.gitpher.springpost.data.entity.User;
import com.gitpher.springpost.data.repository.RefreshTokenRepository;
import com.gitpher.springpost.data.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;


    public ResponseDto<?> registerUser(SignupRequestDto requestDto) {
        String pattern = "^[a-zA-Z0-9]*$";
        String nickname = requestDto.getNickname();
        String password1 = requestDto.getPassword1();
        String password2 = requestDto.getPassword2();
        Authority authority = Authority.ROLE_USER;

        // 회원 ID 중복 확인
        Optional<User> found = userRepository.findByNickname(nickname);
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자 ID 가 존재합니다.");
        }
        // 회원가입 조건
        if (nickname.length() < 4) {
            throw new RuntimeException("닉네임은 4자 이상이어야 합니다.");
        }
        if (!Pattern.matches(pattern, nickname)) {
            throw new RuntimeException("닉네임과 비밀번호 형식을 확인해주세요.");
        }
        if (!password1.equals(password2)) {
            throw new RuntimeException("비밀번호가 서로 일치하지 않습니다.");
        }
        if (password1.length() < 4) {
            throw new RuntimeException("비밀번호는 4자 이상이어야 합니다.");
        }
        if (nickname.equals("")) {
            throw new RuntimeException("닉네임을 입력해주세요");
        }
        if (nickname.equals(null)) {
            throw new RuntimeException("닉네임을 입력해주세요");
        }

        // 패스워드 암호화
        String password = passwordEncoder.encode(requestDto.getPassword1());

        // 회원 저장
        User user = new User(nickname, password, authority);
        userRepository.save(user);

        // 가입한 회원 반환
        Optional<User> userSaved = userRepository.findByNickname(nickname);
        return ResponseDto.success(userSaved);
    }

    public ResponseEntity<?> loginUser(LoginRequestDto requestDto) {
        // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = requestDto.toAuthentication();

        // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        // authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        // 4. RefreshToken 저장
        RefreshToken refreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .value(tokenDto.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshToken);

        // 5. 토큰 발급 및 회원 정보도 함께 헤더에 추가
        Optional<User> user = userRepository.findByNickname(requestDto.getNickname());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer "+ tokenDto.getAccessToken());

        return new ResponseEntity<>(ResponseDto.success(user), httpHeaders, HttpStatus.OK);

    }
}
