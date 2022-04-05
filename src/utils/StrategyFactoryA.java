package utils;

import java.util.List;

public class StrategyFactoryA extends StrategyFactory {
	@Override
	public Strategy doStrategy(String strategy, String[] coins, List<Double> prices, String name) {
		return new StrategyA(coins, prices, name, strategy);
	}
}
