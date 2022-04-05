package utils;

import java.util.ArrayList;
import java.util.List;

/*
 *  contains a cumulative list of all trade actions performed thus far, updated after every trade performed;
 *  each time a trade is performed:
 *  	1. the trade is added to this cumulative list
 *  	2. the cumulative list is sent to DataVisulationCreator to create an updated table 
 *  		- this is done by fetching (with getter method) getCumulativeTrades to use as argument when createCharts is called in MainUI
 */
public class ExecuteTrade {
	/*
	 * stores a cumulative list of all trading actions performed
	 */
	private List<List<String>> cumulativeTrades = new ArrayList<List<String>>(); // stores list of cumulative trading history for log table
	/*
	 * used to perform trades
	 */
	private TradeStrategy trader = new TradeStrategy(); 
	
	/*
	 *  executes one round of trading, iterating through each broker and updating cumulative trades for each buy/sell action performed
	 */
	public void performTrade(UserSelection traderList) {
		
		Broker currBroker;
		
		for (int i=0; i < traderList.getNumBrokers(); i++) {
			currBroker = traderList.getBrokerList().get(i);
			
			List<String> tradeResult = trader.getExecution(currBroker.getStrategy(), currBroker.getCoinList(), currBroker.getName());
		// conditionals ensure item is only added to cumulative trades if an actual buy/sell action was enacted
			if (tradeResult.size() == 7 && tradeResult.get(2) != "Fail") {
				cumulativeTrades.add(tradeResult);
			};
		};
	}
	
	/*
	 * returns the list of cumulative trading history
	 */
	
	public List<List<String>> getCumulativeTrades () {
		return cumulativeTrades;	
	}
}
