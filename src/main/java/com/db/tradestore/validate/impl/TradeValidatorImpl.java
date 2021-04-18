package com.db.tradestore.validate.impl;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.db.tradestore.Trade;
import com.db.tradestore.validate.TradeValidator;

@Service
public class TradeValidatorImpl implements TradeValidator {

	Logger logger = LoggerFactory.getLogger(TradeValidatorImpl.class);
	LocalDate today = LocalDate.now();

	@Override
	public Map<String, Trade> validateTrades(List<Trade> trades) {
		Map<String, Trade> storeData = new HashMap<String, Trade>();
		logger.info("*****validateTrades..");
		
		// Store should not allow the trade which has less maturity date than today date
		List<Trade> filterdTrades = filterOldTrades(trades);

		for (Trade trade : filterdTrades) {
			// Store should not allow the trade which has less maturity date than today date
			//boolean isValidTrade = validateMaturityDate(trade);
			
			// During transmission if the lower version is being received by the store it
			// will reject the trade and throw an exception.
			// If the version is same it will override the existing record.

			if (storeData.containsKey(trade.getTradeId())) {
				if (trade.getVersion() >= storeData.get(trade.getTradeId()).getVersion()) {
					storeData.put(trade.getTradeId(), trade);
				} else {
					logger.error("Exception - Lower version is received-->" + trade);
				}
			} else {
				storeData.put(trade.getTradeId(), trade);
			}
		}

		return storeData;
	}

	private List<Trade> filterOldTrades(List<Trade> trades) {
		List<Trade> filterdTrades = trades.parallelStream().filter(trade-> {return (trade.getMaturityDate().compareTo(today) >= 0);})
		.collect(Collectors.toList());
		return filterdTrades;
	}
	
	/*
	 * public boolean validateMaturityDate(Trade trade) { if
	 * (trade.getMaturityDate().compareTo(today) < 0) {
	 * logger.info("less maturity date Trade ignored-->" + trade); return false; }
	 * return true; }
	 */

	@Override
	public void validateTrade(Trade trade) {
		// TODO Auto-generated method stub

	}

}
