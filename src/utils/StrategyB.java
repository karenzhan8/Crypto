package utils;

import java.util.*;

public class StrategyB extends Strategy {
	
	public StrategyB(String[] coins, List<Double> prices, String name, String strategy) {

		super (strategy, coins, prices, name);
		
		List<String> execution = new ArrayList<String>();
		DataFetcher coinGecko = new DataFetcher();
		
		execution.add(name);
		execution.add(strategy);
		
		Double Cardano = null;
		Double Luna = null;
		Double Bitcoin = null;		
		
		for (int i = 0; i < coins.length; i++) {
			//If Cardano can be found in request 
			if("ADA".equals(coins[i])) {
				Cardano = coinGecko.getPriceForCoin("cardano", date());
			} 
		}
		
		if (Cardano != null) { // if both coins are in the list of requested coins
			Bitcoin = coinGecko.getPriceForCoin("bitcoin", date());
			Luna = coinGecko.getPriceForCoin("terra-luna", date());
			
			if(Cardano > 1) {
				execution.add("buy");
				execution.add("LUNA"); //what is the shorthand 
				execution.add("10");
				execution.add(Double.toString(Luna)); 
			} else {
				execution.add("sell");
				execution.add("BTC");
				execution.add("3");
				execution.add(Double.toString(Bitcoin));
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