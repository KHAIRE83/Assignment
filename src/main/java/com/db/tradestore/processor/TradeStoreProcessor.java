package com.db.tradestore.processor;

import java.util.Map;

import com.db.tradestore.Trade;

public interface TradeStoreProcessor {
	void processBatchTrades();

	void processSingleTrade();

	void updateExpiredTrades(Map<String, Trade> tradeMap);
}
