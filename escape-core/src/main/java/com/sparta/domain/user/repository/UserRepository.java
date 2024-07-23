package com.sparta.domain.user.repository;

import com.sparta.domain.user.entity.User;
import com.sparta.domain.user.entity.UserType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByUserType(UserType userType);
}
