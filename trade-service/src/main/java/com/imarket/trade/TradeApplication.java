package com.imarket.trade;

import com.imarket.api.interceptor.FeignConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = "com.imarket.api.client",defaultConfiguration = FeignConfig.class)
@MapperScan("com.imarket.trade.mapper")
@SpringBootApplication
public class TradeApplication {
	public static void main(String[] args) {
		SpringApplication.run(TradeApplication.class, args);
	}
}