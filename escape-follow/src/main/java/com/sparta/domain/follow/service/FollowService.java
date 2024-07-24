package com.sparta.domain.follow.service;

import com.sparta.domain.follow.entity.Follow;
import com.sparta.domain.follow.repository.FollowRepository;
import com.sparta.domain.store.repository.StoreRepository;
import com.sparta.domain.user.entity.User;
import com.sparta.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class FollowService {

    private final FollowRepository followRepository;
    private final StoreRepository storeRepository;

    @Transactional
    public void follow(Long storeId, User user) {

        storeRepository.findByActiveStore(storeId);
        Follow follow = followRepository.

    }
}
