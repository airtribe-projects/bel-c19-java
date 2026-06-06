//package org.airtribe.LearnerManagementSystem;
//
//import com.fasterxml.jackson.annotation.JsonInclude;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.cache.RedisCacheConfiguration;
//import org.springframework.data.redis.serializer.GenericJacksonJsonRedisSerializer;
//import org.springframework.data.redis.serializer.RedisSerializationContext;
//import tools.jackson.databind.ObjectMapper;
//
//
//@Configuration
//public class config {
//
//  @Bean
//  public RedisCacheConfiguration cacheConfiguration() {
//    ObjectMapper objectMapper = new ObjectMapper();
//    return RedisCacheConfiguration.defaultCacheConfig().disableCachingNullValues().serializeValuesWith(
//        RedisSerializationContext.SerializationPair.fromSerializer(new GenericJacksonJsonRedisSerializer(objectMapper))
//    );
//  }
//}
