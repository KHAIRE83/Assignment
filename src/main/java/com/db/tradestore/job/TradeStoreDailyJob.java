
package com.db.tradestore.job;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.db.tradestore.Trade;
import com.db.tradestore.data.load.TradeDataLoadService;
import com.db.tradestore.sync.TradeSyncService;

@Component
public class TradeStoreDailyJob {
	
	 @Autowired
	 private TradeDataLoadService tradeDataLoadService;
	 
	@Autowired
	private TradeSyncService tradeSyncService;
	 
	private static final Logger log = LoggerFactory.getLogger(TradeStoreDailyJob.class);


	@Scheduled(cron="*/120 * * * * *")
	public void processExpiredTrades() {
		
    	try {
			List<Trade> data = tradeDataLoadService.loadDataJson("dailyjob.json");
			//Map<String, Trade> tradeMap = getTradeMap(data);
			Map<String, Trade> tradeMap = data.stream().collect(Collectors.toMap(Trade::getTradeId, trade->trade));
			
			log.info("***DAILY JOB STARTED**");
			log.info("***INPUT DATA **");
			System.out.println(tradeMap);
			tradeSyncService.updateExpiredTrades(tradeMap);
			log.info("***OUTPUT POST JOB RUN **");
			System.out.println(tradeMap);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private Map<String, Trade> getTradeMap(List<Trade> trades ){
		Map<String, Trade> storeData = new HashMap<String, Trade>();
		for (Trade trade : trades) {
				storeData.put(trade.getTradeId(), trade);
			}
		return storeData;
	}
	
}
