package com.db.tradestore.data.load;

import java.io.IOException;
import java.util.List;

import com.db.tradestore.Trade;

public interface TradeDataLoadService {
	List<Trade> loadDataJson(String file) throws IOException;
}
