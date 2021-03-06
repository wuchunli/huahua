package com.huahua.article;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import huahua.common.utils.IdWorker;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@SpringBootApplication
public class ArticleApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArticleApplication.class, args);
	}

	@Bean
	public IdWorker idWorkker(){
		return new IdWorker(1, 1);
	}
	//序列化  乱码处理
	@Bean(name = "redisTemplate")
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {

		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		//参照StringRedisTemplate内部实现指定序列化器
		redisTemplate.setConnectionFactory(redisConnectionFactory);
		redisTemplate.setKeySerializer(keySerializer());
		redisTemplate.setHashKeySerializer(keySerializer());
		redisTemplate.setValueSerializer(valueSerializer());
		redisTemplate.setHashValueSerializer(valueSerializer());

		return redisTemplate;
	}

	@Primary
	@Bean
	public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory){
		//缓存配置对象
		RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
//设置缓存的默认超时时间：30分钟
		redisCacheConfiguration = redisCacheConfiguration.entryTtl(Duration.ofMinutes(30L))
				//如果是空值，不缓存
				.disableCachingNullValues()
				//设置key序列化器
				.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(keySerializer()))
				//设置value序列化器
				.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer((valueSerializer())));

		return RedisCacheManager
				.builder(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory))
				.cacheDefaults(redisCacheConfiguration).build();
	}







	private RedisSerializer<String> keySerializer() {
		return new StringRedisSerializer();
	}
	//使用Jackson序列化器

	private RedisSerializer<Object> valueSerializer() {
		return new GenericJackson2JsonRedisSerializer();
	}
	
}
