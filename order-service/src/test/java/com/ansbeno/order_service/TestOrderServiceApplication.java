package com.ansbeno.order_service;

import org.springframework.boot.SpringApplication;

public class TestOrderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.from(OrderServiceApplication::main).with(ContainersConfig.class)
				.with(ContainersConfig.class)
				.run(args);
	}

}