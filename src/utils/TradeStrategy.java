package utils;

import java.io.*;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.*;

/** 
 * Class implements different trading strategies based on user input
 */
public class TradeStrategy {
	/**
	 * returns execution of buying or selling of coins
	 * @param strategy
	 * @param coins
	 * @param name
	 * @return list of executions
	 */
	public List<String> getExecution(String strategy, String[] coins, String name) { //selection object	
		DataFetcher coinGecko = new DataFetcher();
		List<Double> prices = new ArrayList<>();
		for(int i = 0; i < coins.length; i++) {
			//example parameters: ("bitcoin", "04-02-2022")
			prices.add(coinGecko.getPriceForCoin(AvailableCryptoList.getInstance().getCryptoIDfromTicker(coins[i]), date())); 
		}
		
		StrategyFactory result = null;
		Strategy execution = null;
		
		if(strategy == "Strategy-A") {
			result = new StrategyFactoryA();
		} else if (strategy == "Strategy-B") {
			result = new StrategyFactoryB();
		}else if (strategy == "Strategy-C") {
			result = new StrategyFactoryC();
		}else if (strategy == "Strategy-D") {
			result = new StrategyFactoryD();
		}
		
		
		execution = result.doStrategy(strategy, coins, prices, name);
	// final format of TradeExecution: {name, strategy, action, coin, quantity, price}	
		return execution.getExecution();
	}
	
	/**
	 * class returns today's date such that when prices are added to String array coin, 
	 * coin prices are up to date and accurate
	 * @return today's date
	 */
	private static String date() { 
		Format f = new SimpleDateFormat("MM-dd-20yy");
		String strDate = f.format(new Date());
		return (strDate);
	}
}
