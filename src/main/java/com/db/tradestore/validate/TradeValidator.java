package com.db.tradestore.validate;

import java.util.List;
import java.util.Map;

import com.db.tradestore.Trade;

public interface TradeValidator {
	Map<String, Trade> validateTrades(List<Trade> listTrade);

	void validateTrade(Trade trade);
}
