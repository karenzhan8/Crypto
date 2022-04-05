package utils;

import java.util.*;

/**
 * 
 * @author zoed1
 * Implementation of factory method for trade strategy execution: Strategy A
 *
 */
public class StrategyA extends Strategy {
	
	/**
	 *  Constructor: If price of BTC > 46000 & price ETH < 3800 -> buy 800 ADA, else sell 2 BTC
	 * @param coins
	 * @param prices
	 * @param name
	 * @param strategy
	 */
	public StrategyA(String[] coins, List<Double> prices, String name, String strategy) {
		
		super (strategy, coins, prices, name);
		
		// add all parts of action to list for use in table log
		List<String> execution = new ArrayList<>();
		DataFetcher coinGecko = new DataFetcher();
		
		execution.add(name);
		execution.add(strategy);
		
		Double Bitcoin = null;
		Double Ethereum = null;	
		
		for (int i = 0; i < coins.length; i++) {
			if("BTC".equals(coins[i])) {
				Bitcoin = coinGecko.getPriceForCoin("bitcoin", date());
			} else if("ETH".equals(coins[i])) {
				Ethereum = coinGecko.getPriceForCoin("ethereum", date());
			}
		}
		
		if (Bitcoin != null && Ethereum != null) { // if both coins are in the list of requested coins
			Double Cardano = coinGecko.getPriceForCoin("cardano", date());
			
			if(Bitcoin > 58000 && Ethereum < 4000) {
				execution.add("buy");
				execution.add("ADA");
				execution.add("800");
				execution.add(Double.toString(Cardano)); 
			} else {
				execution.add("sell");
				execution.add("BTC");
				execution.add("2");
				execution.add(Double.toString(Bitcoin));
			}
		} else { // list entries if trade fails
			List<String> failedTrade = FailTrade();
			for(int i = 0; i < failedTrade.size(); i++) {
				execution.add(failedTrade.get(i));
			}
		}
		
		execution.add(date());
		setExecution(execution); // set the Strategy class list to results of Strategy A
	}
	
}