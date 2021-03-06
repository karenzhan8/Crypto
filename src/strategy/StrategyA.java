package strategy;

import java.util.*;

import utils.DataFetcher;

/**
 * Strategy A, purchase 800 ADA if BTC > 58000 & ETH < 4000, otherwise sell 2 BTC
 * Part of factory design pattern
 */
public class StrategyA extends Strategy {
	/**
	 * Informs execution of trade strategy
	 * @param coins
	 * @param prices
	 * @param name
	 * @param strategy
	 */
	public StrategyA(String[] coins, List<Double> prices, String name, String strategy) {
		super (strategy, coins, prices, name);
		
		List<String> execution = new ArrayList<String>();
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