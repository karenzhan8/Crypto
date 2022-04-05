package utils;

import java.util.List;

/**
 * Strategy factory A creates Strategy A
 */
public class StrategyFactoryA extends StrategyFactory {
	@Override
	/**
	 * @param strategy
	 * @param coins
	 * @param prices
	 * @param name
	 * @return Strategy A object
	 */
	public Strategy doStrategy(String strategy, String[] coins, List<Double> prices, String name) {
		return new StrategyA(coins, prices, name, strategy);
	}
}
