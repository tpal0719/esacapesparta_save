package com.sparta.domain.recommendation.entity;

import com.sparta.domain.escapeRoom.entity.EscapeRoom;
import com.sparta.domain.user.entity.User;
import com.sparta.global.entity.TimeStamped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Recommendation extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "escape_room_id", nullable = false)
    private EscapeRoom escapeRoom;
}
