package com.sparta.domain.user.repository;

import com.sparta.domain.user.entity.User;
import com.sparta.domain.user.entity.UserType;
import com.sparta.global.exception.customException.UserException;
import com.sparta.global.exception.errorCode.UserErrorCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    default User findByIdOrElseThrow(Long userId) {
        return findById(userId).orElseThrow(()->new UserException(UserErrorCode.USER_NOT_FOUND));
    }
    List<User> findByUserType(UserType userType);

    Optional<User> findByEmail(String email); // 고민중
}
