package com.gitpher.springpost.controller;

import com.gitpher.springpost.data.dto.ResponseDto;
import com.gitpher.springpost.data.dto.SignupRequestDto;
import com.gitpher.springpost.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping("/api/member/signup")
    public ResponseDto<?> registerUser(@RequestBody SignupRequestDto requestDto) {
        return userService.registerUser(requestDto);
    }


}
