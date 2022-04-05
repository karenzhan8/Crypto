package utils;

import java.util.List;

public class StrategyFactoryD extends StrategyFactory {
	@Override
	public Strategy doStrategy(String strategy, String[] coins, List<Double> prices, String name) {
		return new StrategyD(coins, prices, name, strategy);
	}
}
