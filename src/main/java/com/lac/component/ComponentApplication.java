package com.lac.component;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
// @EnableDiscoveryClient
@MapperScan("com.lac.component.dao")
public class ComponentApplication {

	public static void main(String[] args) {
		SpringApplication.run(ComponentApplication.class, args);
	}

}
