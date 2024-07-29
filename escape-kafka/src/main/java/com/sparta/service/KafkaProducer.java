package com.sparta.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.domain.reservation.entity.Reservation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducer {

    public static final String PAYMENT_TOPIC= "payment_topic";

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void sendMessage(String topic, String email){
        try{
            kafkaTemplate.send(topic, email);
        } catch (Exception e){
            log.error("error");
        }
    }

//    public void sendMessage(String topic, Reservation reservation) {
//        try {
//            String message = objectMapper.writeValueAsString(reservation);
//            kafkaTemplate.send(topic, message);
//        } catch (Exception e) {
//            log.error("Error serializing Reservation object: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }
}
