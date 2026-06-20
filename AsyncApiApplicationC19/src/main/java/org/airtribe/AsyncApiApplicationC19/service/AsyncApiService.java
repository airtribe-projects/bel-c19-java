package org.airtribe.AsyncApiApplicationC19.service;

import java.time.Duration;
import java.util.List;
import org.airtribe.AsyncApiApplicationC19.dto.ProductResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
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
    ProductResult result = _restTemplate.getForObject("https://dummyjson.com/products", ProductResult.class);
    return result;
  }

  public Mono<ProductResult> fetchProductsAsync() {
    System.out.println("Thread handling request: " + Thread.currentThread().getName());

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

  public Mono<List<ProductResult>> fetchProductsParallelAll() {
    System.out.println("Thread handling request: " + Thread.currentThread().getName());

    Mono<ProductResult> result1 = _webClient.get().uri("https://dummyjson.com/products").retrieve().bodyToMono(ProductResult.class);
    Mono<ProductResult> result2 = _webClient.get().uri("https://dummyjson.com/products").retrieve().bodyToMono(ProductResult.class);
    Mono<ProductResult> result3 = _webClient.get().uri("https://dummyjson.comfghghghghg/products").retrieve().bodyToMono(ProductResult.class);

    return Mono.zip(result1, result2, result3).map(tuple -> List.of(tuple.getT1(), tuple.getT2(), tuple.getT3())).doOnSuccess(result -> {
      System.out.println("Thread handling the request " + Thread.currentThread().getName());
    }).doOnError(error -> {
      System.out.println("Error ocurred in parallel api invocation " + error.getMessage());
    });
  }

  public Mono<ProductResult> fetchProductsParallelFastest() {
    System.out.println("Thread handling request: " + Thread.currentThread().getName());
    Mono<ProductResult> result1 = _webClient.get().uri("https://dummyjson.com/products").retrieve().bodyToMono(ProductResult.class);
    Mono<ProductResult> result2 = _webClient.get().uri("https://dummyjson.com/products").retrieve().bodyToMono(ProductResult.class);
    Mono<ProductResult> result3 = _webClient.get().uri("https://dummyjson.com/products").retrieve().bodyToMono(ProductResult.class);

    return Mono.first(result1, result2, result3).doOnSuccess(result -> {
      System.out.println("Thread handling the request " + Thread.currentThread().getName());
    }).doOnError(error -> {
      System.out.println("Error ocurred in parallel api invocation " + error.getMessage());
    });

  }

  public List<ProductResult> fetchProductsChainedSync() {
    System.out.println("Thread handling the request " + Thread.currentThread().getName());
    ProductResult result1 = _restTemplate.getForObject("https://dummyjson.com/products", ProductResult.class);

    ProductResult result2 = _restTemplate.getForObject("https://dummyjson.com/products", ProductResult.class);

    ProductResult result3 = _webClient.get().uri("https://dummyjson.com/products").retrieve().bodyToMono(ProductResult.class).block();

    System.out.println("Thread handling the request " + Thread.currentThread().getName());

    return List.of(result1, result2, result3);
  }

  public Mono<List<ProductResult>> fetchProductsChainedAsync() {

    System.out.println("Thread handling the request " + Thread.currentThread().getName());

    return _webClient.get().uri("https://dummyjson.com/products").retrieve().bodyToMono(ProductResult.class)
        .doOnSuccess(products -> {
          System.out.println("Received response for async products request: " + products);
          System.out.println("Thread handling the async products request: " + Thread.currentThread().getName());
        }).doOnError(error -> {
          System.out.println("Error occurred while fetching products asynchronously: " + error.getMessage());
        }).flatMap(apiResult -> {
          return _webClient.get().uri("https://dummyjson.com/products").retrieve().bodyToMono(ProductResult.class)
              .doOnSuccess(products -> {
                System.out.println("Received response for async products request: " + products);
                System.out.println("Thread handling the async products request: " + Thread.currentThread().getName());
              }).doOnError(error -> {
                System.out.println("Error occurred while fetching products asynchronously: " + error.getMessage());
              }).flatMap(apiResult2 -> {
                return _webClient.get().uri("https://dummyjson.com/products").retrieve().bodyToMono(ProductResult.class)
                    .doOnSuccess(products -> {
                      System.out.println("Received response for async products request: " + products);
                      System.out.println("Thread handling the async products request: " + Thread.currentThread().getName());
                    }).doOnError(error -> {
                      System.out.println("Error occurred while fetching products asynchronously: " + error.getMessage());
                    }).map(apiResult3 -> List.of(apiResult, apiResult2, apiResult3));
              });
        });


  }

  public Flux<ProductResult> fetchProductsStream() {
    return Flux.interval(Duration.ofSeconds(5)).take(20).flatMap(i -> {
      return _webClient.get().uri("https://dummyjson.com/products").retrieve().bodyToMono(ProductResult.class)
          .doOnSuccess(products -> {
            System.out.println("Received response for async products request: " + products);
            System.out.println("Thread handling the async products request: " + Thread.currentThread().getName());
          }).doOnError(error -> {
            System.out.println("Error occurred while fetching products asynchronously: " + error.getMessage());
          });
    });
  }
}
