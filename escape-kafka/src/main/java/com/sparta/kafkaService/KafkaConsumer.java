package com.sparta.kafkaService;

import com.sparta.global.KafkaTopic;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumer {

    private final AmazonSESService emailService;

    @KafkaListener(topics = KafkaTopic.PAYMENT_TOPIC, groupId = "${GROUP_ID}")
    public void consume(String message) {
//        Reservation reservation = parseMessage(message);

        if (message != null) {
            emailService.sendEmail(message, "예약 확인", buildEmailContent(message));
        }
    }

//    private Reservation parseMessage(String message) {
//        ObjectMapper objectMapper = new ObjectMapper();
//        try {
//            return objectMapper.readValue(message, Reservation.class);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

    private String buildEmailContent(String message) {
        return "<h1>예약 확인</h1>"
                + "<p>감사합니다. 예약이 확인되었습니다.</p>"
                + "<p>예약 ID: " + message + "</p>";
//                + "<p>예약 날짜: " + reservation.getDate() + "</p>";
    }
}
