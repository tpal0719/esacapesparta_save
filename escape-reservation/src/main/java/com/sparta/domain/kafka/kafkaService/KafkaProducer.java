package com.sparta.domain.kafka.kafkaService;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducer {

    private final KafkaTemplate<String, String> kafkaEmailTemplate;

    private final ObjectMapper objectMapper;

    public void sendEmail(String topic, String email){
        try{
            log.error("{} 1", email);
            kafkaEmailTemplate.send(topic, email);
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
