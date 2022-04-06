package strategy;

import java.util.List;
/**
 * Strategy factory B creates Strategy B
 */

public class StrategyFactoryB extends StrategyFactory {
	@Override
	/**
	 * @param strategy
	 * @param coins
	 * @param prices
	 * @param name
	 * @return Strategy B object
	 */
	public Strategy doStrategy(String strategy, String[] coins, List<Double> prices, String name) {
		return new StrategyB(coins, prices, name, strategy);
	}
}
