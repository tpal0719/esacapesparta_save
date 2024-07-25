package com.sparta.service;

import com.sparta.domain.user.dto.UserResponseDto;
import com.sparta.domain.user.entity.User;
import com.sparta.domain.user.repository.UserRepository;
import com.sparta.dto.request.EditProfileRequestDto;
import com.sparta.global.exception.customException.UserException;
import com.sparta.global.exception.errorCode.UserErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;


@Service
@RequiredArgsConstructor
public class ConsumerService {

    private final UserRepository userRepository;


   // TODO : pk값으로 유저 조회
    @Transactional(readOnly = true)
    public UserResponseDto inquiryUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

        return new UserResponseDto(user);
    }

    // TODO : 유저 프로필 수정
    @Transactional
    public UserResponseDto editProfile(Long userId, EditProfileRequestDto request, User user) {
        if(!Objects.equals(userId, user.getId())){
            throw new UserException(UserErrorCode.USER_NOT_FOUND);
        }

        user.editUser(request.getName());
        userRepository.save(user);

        return new UserResponseDto(user);
    }
}