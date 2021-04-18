package com.db.tradestore.data.load.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.db.tradestore.Trade;
import com.db.tradestore.data.load.TradeDataLoadService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class TradeDataLoadServiceImpl implements TradeDataLoadService {

	Logger logger = LoggerFactory.getLogger(TradeDataLoadServiceImpl.class);

	@Override
	public List<Trade> loadDataJson(String file) throws IOException {
		logger.info("loadDataJson()");
		// create ObjectMapper instance
		ObjectMapper objectMapper = new ObjectMapper();
		TypeReference<List<Trade>> typeReference = new TypeReference<List<Trade>>() {
		};

		// read json file and convert to customer object
		List<Trade> trades = objectMapper.readValue(new File(file), typeReference);
		logger.info("JSON data converted...");
		return trades;
	}

}
