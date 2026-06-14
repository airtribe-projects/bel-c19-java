package org.airtribe.AsyncApiApplicationC19.controller;

import org.airtribe.AsyncApiApplicationC19.dto.ProductResult;
import org.airtribe.AsyncApiApplicationC19.service.AsyncApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;


@RestController
public class HelloWorldController {

  @Autowired
  private AsyncApiService _asyncApiService;

  @GetMapping("/hello")
  public String hello() {
    System.out.println("Thread handling /hello request: " + Thread.currentThread().getName());
    return "Hello World!";
  }

  // Two way to invoke API's from within an application
  // RestTemplate -> Sync
  /// web client -> Sync and Async
  @GetMapping("/hello2")
  public String hello2() {
    System.out.println("Thread handling /hello2 request: " + Thread.currentThread().getName());
    return _asyncApiService.invokeHelloEndpoint();
  }

  @GetMapping("/dummyProducts")
  public ProductResult getDummyProductsSync() {
    System.out.println("Thread handling /dummyProducts request: " + Thread.currentThread().getName());
    return _asyncApiService.fetchProductsSync();
  }

  @GetMapping("/dummyProductsAsync")
  public Mono<ProductResult> getProductsAsync() {
    System.out.println("Thread handling /dummyProductsAsync request: " + Thread.currentThread().getName());
    return _asyncApiService.fetchProductsAsync();
  }

  @GetMapping("/dummProductsSyncWebClient")
  public ProductResult getProductsSyncWebClient() {
    System.out.println("Thread handling /dummProductsSyncWebClient request: " + Thread.currentThread().getName());
    return _asyncApiService.fetchProductsSyncWebClient();
  }
}
