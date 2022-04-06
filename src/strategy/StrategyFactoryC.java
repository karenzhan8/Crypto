package strategy;

import java.util.List;
/**
 * Strategy factory C creates Strategy C
 */

public class StrategyFactoryC extends StrategyFactory {
	@Override
	/**
	 * @param strategy
	 * @param coins
	 * @param prices
	 * @param name
	 * @return Strategy C object
	 */
	public Strategy doStrategy(String strategy, String[] coins, List<Double> prices, String name) {
		return new StrategyC(coins, prices, name, strategy);
	}
}
