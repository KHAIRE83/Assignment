package com.db.tradestore.processor.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.db.tradestore.Trade;
import com.db.tradestore.data.load.TradeDataLoadService;
import com.db.tradestore.processor.TradeStoreProcessor;
import com.db.tradestore.sync.TradeSyncService;
import com.db.tradestore.validate.TradeValidator;

/**
 * Processor class for loading , validating and Updating Expired Trades
 * 
 * @author vikram
 *
 */
@Component
public class TradeStoreProcessorImpl implements TradeStoreProcessor {

	public static final String FILE_NAME = "trades.json";

	Logger logger = LoggerFactory.getLogger(TradeStoreProcessorImpl.class);

	@Autowired
	private TradeDataLoadService tradeDataLoadService;

	@Autowired
	private TradeValidator tradeValidator;

	@Autowired
	private TradeSyncService tradeSyncService;

	/**
	 * calls loading , validating and Updating Expired Trades
	 */
	public void processBatchTrades() {
		logger.info("****Inside processBatchTrades****");
		try {
			//Invoke data service to get JSON data to Trade List
			List<Trade> trades = tradeDataLoadService.loadDataJson(FILE_NAME);
			
			//Invoke validator service to validate Trade data as per Business Requirement
			Map<String, Trade> storeData = tradeValidator.validateTrades(trades);
			
			//Automatically update the expire flag if in a store the trade crosses the maturity date.
			updateExpiredTrades(storeData);

			logger.info("****Final Data****");

			storeData.forEach((key, value) -> logger.info(value.toString()));

			// Latest store data.
			/*
			 * for (String tradeId : storeData.keySet()) {
			 * logger.info(storeData.get(tradeId).toString()); }
			 */

		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("Exception in processBatchTrades");
			e.printStackTrace();
		}
	}

	@Override
	public void processSingleTrade() {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateExpiredTrades(Map<String, Trade> tradeMap) {
		tradeSyncService.updateExpiredTrades(tradeMap);
	}

}
