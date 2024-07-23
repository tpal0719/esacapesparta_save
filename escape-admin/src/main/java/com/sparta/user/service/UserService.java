package com.sparta.user.service;

import com.sparta.domain.user.dto.UserResponseDto;
import com.sparta.domain.user.entity.User;
import com.sparta.domain.user.entity.UserType;
import com.sparta.domain.user.repository.UserRepository;
import com.sparta.global.exception.customException.UserException;
import com.sparta.global.exception.errorCode.UserErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     * TODO : 모든 Manager 조회
     *
     * @param user
     * @author SEMI
     */
    @Transactional(readOnly = true)
    public List<UserResponseDto> getAllManagers(User user) {

        List<User> managers = getUsers(UserType.MANAGER);

        return managers.stream()
                .map(manager -> new UserResponseDto(manager))
                .collect(Collectors.toList());
    }

    /**
     * TODO : 모든 Consumer 조회
     *
     * @param user
     * @author SEMI
     */
    @Transactional(readOnly = true)
    public List<UserResponseDto> getAllConsumers(User user) {
        List<User> consumers = getUsers(UserType.USER);

        return consumers.stream()
                .map(consumer -> new UserResponseDto(consumer))
                .collect(Collectors.toList());
    }



    /* Utils */

    /**
     * TODO : user type로 필터링 된 모든 user 구하기
     *
     * @param userType : ADMIN, MANAGER, USER
     * @author SEMI
     */
    private List<User> getUsers(UserType userType) {
        List<User> users = userRepository.findByUserType(userType);

        if (users.isEmpty()) {
            throw new UserException(UserErrorCode.USER_NO_ONE);
        }

        return users;
    }

}
