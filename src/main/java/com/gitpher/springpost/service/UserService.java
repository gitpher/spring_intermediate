package com.gitpher.springpost.service;

import com.gitpher.springpost.data.dto.ResponseDto;
import com.gitpher.springpost.data.dto.SignupRequestDto;
import com.gitpher.springpost.data.entity.User;
import com.gitpher.springpost.data.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public ResponseDto<?> registerUser(SignupRequestDto requestDto) {
        String pattern = "^[a-zA-Z0-9]*$";
        String nickname = requestDto.getNickname();
        String password1 = requestDto.getPassword1();
        String password2 = requestDto.getPassword2();

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
        User user = new User(nickname, password);
        userRepository.save(user);

        // 가입한 회원 반환
        Optional<User> userSaved = userRepository.findByNickname(nickname);
        return ResponseDto.success(userSaved);
    }
}
