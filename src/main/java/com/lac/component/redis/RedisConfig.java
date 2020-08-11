package com.lac.component.redis;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;



@Configuration
public class RedisConfig {

@Bean
public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory ) {
    //设置序列化
    Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
    ObjectMapper om = new ObjectMapper();
    om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
    jackson2JsonRedisSerializer.setObjectMapper(om);
    RedisSerializer redisSerializer = new FastJsonRedisSerializer(Object.class);
    // 配置redisTemplate
    RedisTemplate redisTemplate = new RedisTemplate<String, Object>();
    redisTemplate.setConnectionFactory(redisConnectionFactory);
    RedisSerializer stringSerializer = new StringRedisSerializer();
    redisTemplate.setKeySerializer(stringSerializer); // key序列化
    redisTemplate.setValueSerializer(jackson2JsonRedisSerializer); // value序列化
    redisTemplate.setHashKeySerializer(stringSerializer); // Hash key序列化
    redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer); // Hash value序列化
    redisTemplate.afterPropertiesSet();
    return redisTemplate;
}




}
