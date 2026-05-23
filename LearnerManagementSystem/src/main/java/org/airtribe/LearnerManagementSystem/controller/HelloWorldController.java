package org.airtribe.LearnerManagementSystem.controller;

import org.airtribe.LearnerManagementSystem.service.HelloWorldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HelloWorldController {
  // field / setter based injection
  @Autowired
  private HelloWorldService _helloWorldService;

  // constructor based jectio

  @GetMapping("/")
  public String helloWorld() {
    _helloWorldService.helloWorld();
    return "hello world";
  }

  @GetMapping("/test")
  public String testWorld() {
    return "Test endpoint";
  }
}


/// DTO
// POJO
// Entities