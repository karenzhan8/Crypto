package utils;

import java.util.*;

public class StrategyC extends Strategy {
	
	public StrategyC(String[] coins, List<Double> prices, String name, String strategy) {

		super (strategy, coins, prices, name);
		
		List<String> execution = new ArrayList<String>();
		DataFetcher coinGecko = new DataFetcher();
		
		execution.add(name);
		execution.add(strategy);
		
		Double Ethereum = null;
		Double Cardano = null;	
		
		for (int i = 0; i < coins.length; i++) {
			if("ETH".equals(coins[i])) {
				Ethereum = coinGecko.getPriceForCoin("ethereum", date());
			} else if("ADA".equals(coins[i])) {
				Cardano = coinGecko.getPriceForCoin("cardano", date());
			}
		}
		
		if (Ethereum != null && Cardano != null) { // if both coins are in the list of requested coins
			Double Fantom = coinGecko.getPriceForCoin("fantom", date());	
			
			if(Ethereum > 38000 && Cardano < 1) {
				execution.add("buy");
				execution.add("FTM");
				execution.add("200");
				execution.add(Double.toString(Fantom)); 
			} else {
				execution.add("sell");
				execution.add("ADA");
				execution.add("500");
				execution.add(Double.toString(Cardano));
			}
		} else {
			List<String> failedTrade = FailTrade();
			for(int i = 0; i < failedTrade.size(); i++) {
				execution.add(failedTrade.get(i));
			}
		}
		execution.add(date());
		setExecution(execution);
	}
}
