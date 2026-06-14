package org.airtribe.AsyncApiApplicationC19.service;

import org.airtribe.AsyncApiApplicationC19.dto.ProductResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Service
public class AsyncApiService {

  @Autowired
  private RestTemplate _restTemplate;

  @Autowired
  private WebClient _webClient;

  public String invokeHelloEndpoint() {
    System.out.println("Thread handling request: " + Thread.currentThread().getName());
    for (int i = 0; i < 10000000L; i++) {
      // Simulate some processing
      double temp = Math.sqrt(i) * Math.pow(i, 2);
    }
    // blocking API call
    // synchronous API call
     String returnedResult = _restTemplate.getForObject("http://localhost:1011/hello", String.class);
     return returnedResult;
  }

  public ProductResult fetchProductsSync() {
    System.out.println("Thread handling request: " + Thread.currentThread().getName());
    for (int i = 0; i < 10000000L; i++) {
      // Simulate some processing
      double temp = Math.sqrt(i) * Math.pow(i, 2);
    }
    ProductResult result = _restTemplate.getForObject("https://dummyjson.com/products", ProductResult.class);
    return result;
  }

  public Mono<ProductResult> fetchProductsAsync() {
    System.out.println("Thread handling request: " + Thread.currentThread().getName());
    for (int i = 0; i < 10000000L; i++) {
      // Simulate some processing
      double temp = Math.sqrt(i) * Math.pow(i, 2);
    }

    Mono<ProductResult> result = _webClient.get().uri("https://dummyjson.com/products").retrieve().bodyToMono(ProductResult.class)
        .doOnSuccess(products -> {
          System.out.println("Received response for async products request: " + products);
          System.out.println("Thread handling the async products request: " + Thread.currentThread().getName());
        }).doOnError(error -> {
          System.out.println("Error occurred while fetching products asynchronously: " + error.getMessage());
        });

    return result;
  }

  public ProductResult fetchProductsSyncWebClient() {
    System.out.println("Thread handling request: " + Thread.currentThread().getName());
    for (int i = 0; i < 10000000L; i++) {
      // Simulate some processing
      double temp = Math.sqrt(i) * Math.pow(i, 2);
    }

    ProductResult result = _webClient.get().uri("https://dummyjson.com/products").retrieve().bodyToMono(ProductResult.class).block();

    System.out.println("Received response for sync products request using WebClient: " + result);
    System.out.println("Thread handling the sync products request using WebClient: " + Thread.currentThread().getName());
    return result;
  }
}
