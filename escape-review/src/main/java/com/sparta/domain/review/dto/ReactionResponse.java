package com.sparta.domain.review.dto;

import com.sparta.domain.reaction.entity.ReactionType;
import lombok.Getter;

@Getter
public class ReactionResponse {
    private ReactionType reactionType;
    private Boolean reactionStatus;

    public ReactionResponse(ReactionType reactionType, Boolean reactionStatus){
        this.reactionType = reactionType;
        this.reactionStatus = reactionStatus;
    }
}
