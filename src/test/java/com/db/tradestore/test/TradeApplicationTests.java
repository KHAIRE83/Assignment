package com.db.tradestore.test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.assertj.core.util.Arrays;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.db.tradestore.Trade;
import com.db.tradestore.app.TradeStoreApp;
import com.db.tradestore.data.load.TradeDataLoadService;
import com.db.tradestore.processor.TradeStoreProcessor;
import com.db.tradestore.sync.TradeSyncService;
import com.db.tradestore.validate.TradeValidator;

@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(classes = TradeStoreApp.class)
class TradeApplicationTests {
	
	 @Autowired
	 private TradeStoreProcessor tradeStoreProcessor;
	
	 @Autowired
	 private TradeDataLoadService tradeDataLoadService;
	 
	 @Autowired
	 private TradeValidator tradeValidator;
	    
     @Autowired
     private TradeSyncService tradeSyncService;
	    
	@Test
	void contextLoads() {
	}
	
	
  @Before
    public void setup() {
	  
    }

    @Test
    public void processTest() throws Exception {
		tradeStoreProcessor.processBatchTrades();
		assertTrue(true);
    }
    
    @Test
    public void jsonDataLoadSizeTest() throws Exception {
    	List<Trade> data = tradeDataLoadService.loadDataJson("test.json");
    	assertEquals(2, data.size());
    }
    
   
    @Test
    public void maturedTradeTest() throws Exception {
    	List<Trade> data = tradeDataLoadService.loadDataJson("maturedTrade.json");
    	assertEquals(0, tradeValidator.validateTrades(data).size());
    }
    
    @Test
    public void expireTradeFlagUpdateTest() throws Exception {
    	Map<String,Trade> tradeMap = new HashMap();
    	Trade oldTrade = new Trade("T1", 1, "CP-1", "B1", LocalDate.now().minusDays(1), LocalDate.now(), "N");
    	tradeMap.put("T1", oldTrade );
    	assertEquals("N",oldTrade.getExpired());
    	tradeSyncService.updateExpiredTrades(tradeMap);
    	assertEquals("Y",oldTrade.getExpired());
    }
   

}
