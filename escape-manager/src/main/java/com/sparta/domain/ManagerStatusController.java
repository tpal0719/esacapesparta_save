package com.sparta.domain;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ManagerStatusController {

  @GetMapping("/manager/status")
  public ResponseEntity<String> getStatus() {
    String statusMessage = "Service is running";
    return new ResponseEntity<>(statusMessage, HttpStatus.OK);
  }
}