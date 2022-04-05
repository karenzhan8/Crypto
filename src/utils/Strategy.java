package utils;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Strategy class acts as 
 * 
 */
public abstract class Strategy {
	
	private List<String> action;
	
	public Strategy(String strategy, String[] coins, List<Double> prices, String name) {
		action = new ArrayList<String>();
	}
	
	//class returns today's date such that when prices are added to String array coin, 
	//coin prices are up to date and accurate
	protected static String date() {
		Format f = new SimpleDateFormat("dd-MM-20yy");
		String strDate = f.format(new Date());
		return (strDate);
	}
	
	protected List<String> FailTrade() {
		List<String> failAction = new ArrayList<>();
		failAction.add("Fail");
		failAction.add("Null");
		failAction.add("Null");
		failAction.add("Null");
		return failAction;
	}
	
	public void setExecution(List<String> trade) {
		action = trade;
	}
	
	public List<String> getExecution() {
		return action;
	}
}
