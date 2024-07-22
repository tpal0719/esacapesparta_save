package com.sparta.domain.reaction.repository;

import com.sparta.domain.reaction.entity.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReactionRepository extends JpaRepository<Reaction,Long> {
}
