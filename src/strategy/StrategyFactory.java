package strategy;

import java.util.*;

/** 
 * Acts as as abstract class for all other factory classes
 */
public abstract class StrategyFactory {
	/**
	 * parent constructor for other factory classes
	 * @param strategy
	 * @param coins
	 * @param prices
	 * @param name
	 * @return
	 */
	public abstract Strategy doStrategy(String strategy, String[] coins, List<Double> prices, String name);
}
