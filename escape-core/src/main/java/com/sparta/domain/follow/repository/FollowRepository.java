package com.sparta.domain.follow.repository;

import com.sparta.domain.follow.entity.Follow;
import com.sparta.domain.store.entity.Store;
import com.sparta.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    Optional<Follow> findByUserAndStore(User user, Store store);

    default Follow findByUserAndStoreCheck(User user, Store store){
        Follow follow = findByUserAndStore(user, store).orElse(null);
        if(follow != null){
            throw new 
        }
    }
}
