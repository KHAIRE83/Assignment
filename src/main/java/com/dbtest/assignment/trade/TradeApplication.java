package com.dbtest.assignment.trade;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
public class TradeApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(TradeApplication.class, args);
	}
	
	  @Override
	    public void run(String[] args) throws IOException {
		  	Date today = new Date();
	        //create ObjectMapper instance
	        ObjectMapper objectMapper = new ObjectMapper();
			TypeReference<List<Trade>> typeReference = new TypeReference<List<Trade>>(){};

	        //read json file and convert to customer object
	        List<Trade> trades = objectMapper.readValue(new File("trade.json"), typeReference);
	        Map<String,Trade> storeData = new HashMap();

	        //print customer details
	        System.out.println(trades);
	        
	        for (Trade trade : trades) {
	        	//Store should not allow the trade which has less maturity date then today date.
				if(trade.getMaturityDate().before(today)) {
					System.out.println("less maturity date Trade ignored-->"+trade);
				} 
				
				//During transmission if the lower version is being received by the store it will reject the trade and throw an exception. 
				//If the version is same it will override the existing record.
				
				if(storeData.containsKey(trade.getTradeId())){
					if(trade.getVersion()>=storeData.get(trade.getTradeId()).getVersion()){
						storeData.put(trade.getTradeId(), trade);
					} else {
						System.out.println("Exception - Lower version is received-->"+trade);
					}
				} else {
					storeData.put(trade.getTradeId(), trade);
				}
				
				
			}
			//Store should automatically update the expire flag if in a store the trade crosses the maturity date.
	        for (String tradeId : storeData.keySet()) {
	        	if(storeData.get(tradeId).getMaturityDate().before(today)) {
	        		System.out.println("Updating expired trade-->"+tradeId);
	        		storeData.get(tradeId).setExpired("Y");
	        	}
	        }
	        
	    	//Latest store data.
	        for (String tradeId : storeData.keySet()) {
	        		System.out.println(storeData.get(tradeId));
	        	}
	        }
}



