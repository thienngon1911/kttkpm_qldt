package com.se.leopard.servicephone.config;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
	
	 @Value("${spring.redis.host}")
	  private String redisHost;

	  @Value("${spring.redis.port}")
	  private int redisPort;
	  
	@Bean
	public LettuceConnectionFactory redisConnectionFactory() {
	    RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration(redisHost, redisPort);
	    return new LettuceConnectionFactory(configuration);
	}
	
	@Bean
	public RedisCacheManager cacheManager() {
	    RedisCacheConfiguration cacheConfig = myDefaultCacheConfig(Duration.ofMinutes(10)).disableCachingNullValues();

	    return RedisCacheManager.builder(redisConnectionFactory())
	        .cacheDefaults(cacheConfig)
	        .withCacheConfiguration("tutorials", myDefaultCacheConfig(Duration.ofMinutes(5)))
	        .withCacheConfiguration("tutorial", myDefaultCacheConfig(Duration.ofMinutes(1)))
	        .build();
	}
	private RedisCacheConfiguration myDefaultCacheConfig(Duration duration) {
	    return RedisCacheConfiguration
	        .defaultCacheConfig()
	        .entryTtl(duration)
	        .serializeValuesWith(SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
	}
	
	@Bean
	public RedisTemplate<String, ?> redisTemplate(RedisConnectionFactory connectionFactory) {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(connectionFactory);
		
		GenericJackson2JsonRedisSerializer jackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
		
		redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        
        redisTemplate.afterPropertiesSet();
		return redisTemplate;
	}
}
