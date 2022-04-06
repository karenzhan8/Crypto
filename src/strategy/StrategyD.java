package strategy;

import java.util.*;

import utils.DataFetcher;

/**
 * Strategy D, purchase 100 FTM if ADA > FTM, otherwise buy 100 ADA
 * Part of factory design pattern
 */
public class StrategyD extends Strategy {
	/**
	 * Informs execution of trade strategy
	 * @param coins
	 * @param prices
	 * @param name
	 * @param strategy
	 */
	public StrategyD(String[] coins, List<Double> prices, String name, String strategy) {
		
		super (strategy, coins, prices, name);
		
		List<String> execution = new ArrayList<String>();
		DataFetcher coinGecko = new DataFetcher();
		
		execution.add(name);
		execution.add(strategy);
		
		Double Cardano = null;
		Double Fantom = null;	
		
		for (int i = 0; i < coins.length; i++) {
			if("ADA".equals(coins[i])) {
				Cardano = coinGecko.getPriceForCoin("cardano", date());
			} else if("FTM".equals(coins[i])) {
				Fantom = coinGecko.getPriceForCoin("fantom", date());
			}
		}
		
		if (Cardano != null && Fantom != null) { // if both coins are in the list of requested coins			
			if(Cardano > Fantom) {
				execution.add("buy");
				execution.add("FTM");
				execution.add("100");
				execution.add(Double.toString(Cardano)); 
			} else {
				execution.add("buy");
				execution.add("ADA");
				execution.add("100");
				execution.add(Double.toString(Fantom));
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
