package com.lac.component;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import java.math.BigDecimal;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.lac.component.dao")
@EnableRedisHttpSession
public class ComponentApplication {

	public static void main(String[] args) {

		BigDecimal bg = new BigDecimal("778987.2").setScale(0,BigDecimal.ROUND_UP);
		System.out.println(bg);
		SpringApplication.run(ComponentApplication.class, args);
	}

}
