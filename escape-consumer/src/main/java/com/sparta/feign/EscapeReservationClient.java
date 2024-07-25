package com.sparta.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "escape-reservation", url = "http://localhost:8085")
public interface EscapeReservationClient {

    @GetMapping("/reservations")
    String getReservation();

}
