package org.airtribe.LearnerManagementSystem.controller;

import org.airtribe.LearnerManagementSystem.dto.DummyResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class DummyController {

  @GetMapping("/dummy")
  public String dummyEndpoint() {
    return "Some dummy data";
  }

  @GetMapping("/mydummy")
  public DummyResponse myDummyEndpoint() {
    return new DummyResponse("test", "test");
  }
}
