package com.db.tradestore.sync;

import java.util.Map;

import com.db.tradestore.Trade;

public interface TradeSyncService {
	
	void updateExpiredTrades(Map<String,Trade> tradeMap);

}
