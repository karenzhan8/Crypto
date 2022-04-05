package utils;

import java.util.List;
/**
 * Strategy factory D creates Strategy D
 */

public class StrategyFactoryD extends StrategyFactory {
	@Override
	/**
	 * @param strategy
	 * @param coins
	 * @param prices
	 * @param name
	 * @return Strategy D object
	 */
	public Strategy doStrategy(String strategy, String[] coins, List<Double> prices, String name) {
		return new StrategyD(coins, prices, name, strategy);
	}
}
