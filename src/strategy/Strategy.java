package strategy;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Class allows implementation of factory design pattern
 * Acts as abstract class for Strategy A, B, C, D
 */
public abstract class Strategy {
	private List<String> action;
	
	/**
	 * Constructor
	 * @param strategy
	 * @param coins
	 * @param prices
	 * @param name
	 */
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
	
	/**
	 * Trade performed if the conditions for other strategies not met
	 * @return list of action, coin, quantity, price
	 */
	protected List<String> FailTrade() {
		List<String> failAction = new ArrayList<>();
		failAction.add("Fail");
		failAction.add("Null");
		failAction.add("Null");
		failAction.add("Null");
		return failAction;
	}
	
	/**
	 * sets execution of trade
	 * @param trade
	 */
	public void setExecution(List<String> trade) {
		action = trade;
	}
	
	/** 
	 * getter returns execution of trade
	 * @return action
	 */
	public List<String> getExecution() {
		return action;
	}
}
