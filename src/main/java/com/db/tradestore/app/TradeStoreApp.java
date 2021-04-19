package com.db.tradestore.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.db.tradestore.processor.TradeStoreProcessor;
import com.db.tradestore.processor.impl.TradeStoreProcessorImpl;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.db.tradestore")
@EnableScheduling
public class TradeStoreApp {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(TradeStoreApp.class, args);
		TradeStoreProcessor tradeStoreProcessor = context.getBean(TradeStoreProcessorImpl.class);
		// Process the trades from external file
		tradeStoreProcessor.processBatchTrades();
	}

}
