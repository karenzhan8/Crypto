package utils;

import java.util.List;

public class StrategyFactoryC extends StrategyFactory {
	@Override
	public Strategy doStrategy(String strategy, String[] coins, List<Double> prices, String name) {
		return new StrategyC(coins, prices, name, strategy);
	}
}
