package com.sparta.domain.kakaopayment.entity;

import com.sparta.global.entity.TimeStamped;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
public class KakaoPayment extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //request
    private String cid; //가맹점 코드
    private String reservationId; //reservation 고유 id
    private String themeName; //테마이름
    private String userEmail;
    private String price;

    //response
    @Setter
    private String tid;


    @Builder
    public KakaoPayment(String cid,String tid,String reservationId,String price, String themeName, String userEmail){
        this.cid = cid;
        this.tid =tid;
        this.reservationId = reservationId;
        this.price = price;
        this.themeName = themeName;
        this.userEmail = userEmail;
    }


}
