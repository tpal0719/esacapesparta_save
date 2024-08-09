package com.sparta.domain.consumer.service;

import com.sparta.domain.consumer.dto.request.EditPasswordRequestDto;
import com.sparta.domain.consumer.dto.request.EditProfileRequestDto;
import com.sparta.domain.user.dto.response.UserResponseDto;
import com.sparta.domain.user.entity.User;
import com.sparta.domain.user.repository.UserRepository;
import com.sparta.global.exception.customException.UserException;
import com.sparta.global.exception.errorCode.UserErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class ConsumerService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;


    // pk값으로 유저 조회
    @Transactional(readOnly = true)
    public UserResponseDto inquiryUser(User user) {

        return new UserResponseDto(user);
    }

    // 유저 프로필 수정
    @Transactional
    public UserResponseDto modifyProfile(EditProfileRequestDto request, User user) {

        user.editUser(request.getName());
        userRepository.save(user);

        return new UserResponseDto(user);
    }

    //유저 비밀번호 변경
    @Transactional
    public UserResponseDto editPassword(EditPasswordRequestDto requestDTO, User user) {

        if (!passwordEncoder.matches(requestDTO.getCurrentPassword(), user.getPassword())) {
            throw new UserException(UserErrorCode.PASSWORD_NOT_MATCH);
        }

        if (passwordEncoder.matches(requestDTO.getNewPassword(), user.getPassword())) {
            throw new UserException(UserErrorCode.PASSWORD_NOT_MIXMATCH);
        }

        String editPassword = passwordEncoder.encode(requestDTO.getNewPassword());
        user.changePassword(editPassword);
        userRepository.save(user);

        return new UserResponseDto(user);
    }
}
