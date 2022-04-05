package utils;

import java.util.List;

public class StrategyFactoryB extends StrategyFactory {
	@Override
	public Strategy doStrategy(String strategy, String[] coins, List<Double> prices, String name) {
		return new StrategyB(coins, prices, name, strategy);
	}
}
