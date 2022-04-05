package utils;

import java.util.*;

public abstract class StrategyFactory {
	public abstract Strategy doStrategy(String strategy, String[] coins, List<Double> prices, String name);
}
