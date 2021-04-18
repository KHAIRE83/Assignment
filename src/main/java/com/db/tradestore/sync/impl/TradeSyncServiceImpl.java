package com.db.tradestore.sync.impl;

import java.time.LocalDate;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.db.tradestore.Trade;
import com.db.tradestore.sync.TradeSyncService;

@Service
public class TradeSyncServiceImpl implements TradeSyncService {

	Logger logger = LoggerFactory.getLogger(TradeSyncServiceImpl.class);

	@Override
	public void updateExpiredTrades(Map<String, Trade> tradeMap) {
		LocalDate today = LocalDate.now();

		logger.info("****Updating expired trades on " + today);

		// Store should automatically update the expire flag if in a store the trade
		// crosses the maturity date.
		for (String tradeId : tradeMap.keySet()) {
			if (tradeMap.get(tradeId).getMaturityDate().compareTo(today) < 0) {
				logger.info("Updating expired trade-->" + tradeId);
				tradeMap.get(tradeId).setExpired("Y");
			}
		}
	}
}
