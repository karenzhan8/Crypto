package utils;

import java.util.ArrayList;
import java.util.List;

import gui.MainUI;

/**
 * communicates with GUI. Allows users to add brokers and store broker data
 * uses singleton design pattern
 */
public class UserSelection {
	private static UserSelection instance;
	
	private ArrayList<Broker> brokerList = new ArrayList<Broker>(); 
	private ArrayList<String> strategyList = new ArrayList<String>();
	private ArrayList<String[]> coinsList = new ArrayList<String[]>();
	private int numBrokers;
	
	private static List<List<String>> frequency = new ArrayList<List<String>>();
		
	/**
	 * Class that creates a global point of access to UserSelection using singleton design pattern  
	 */ 
	private UserSelection getInstance() {
		if (instance == null) {
			instance = new UserSelection();
		}
		
		return instance;
	}
	
	/**
	 * Class to check if broker is already in the broker list
	 * @param brokerList	the list of brokers
	 * @param name			the name of the broker we want to check if in list
	 * @return				true if in list and false if not in list
	 */
	private static boolean containsBroker (ArrayList<Broker> brokerList, String name) {
    	for (int i = 0; i < brokerList.size(); i++) {
    		if (brokerList.get(i).getName().equals(name)) {
    			return true;
    		}
    	}
    	return false;
    }
	
	/**
	 * getter class for brokerList
	 * @return brokerList
	*/
    public ArrayList<Broker> getBrokerList() {
        return brokerList;
    }
	
	/**
     * getter class for strategyList
     * @return strategyList
     */
    public ArrayList<String> getStrategyList() {
        return strategyList;
    }
    
	/**
     * getter class for coinsList
     * @return coinsList
     */
    public ArrayList<String[]> getCoinLists() {
        return coinsList;
    }
	
	/**
     * getter class for number of brokers
     * @return numBrokers
     */
    public int getNumBrokers() {
	    return numBrokers;
    }   
    
	/**
     * getter class for frequencies
     * @return frequency
     */
    public static List<List<String>> getFrequencies() {
    	return frequency;
    }
    
    /**
     * set frequencies of trades in this trading round, based on user selection 
     * @param traderList
     */
	public static void setFrequencies(UserSelection traderList) {
		TradeStrategy trader = new TradeStrategy(); // used to perform trades
		boolean found = false;
		int update;
		ArrayList<String> current;
		
		for (int i=0; i < traderList.getNumBrokers(); i++) {
			Broker currBroker = traderList.getBrokerList().get(i);
			List<String> tradeResult = trader.getExecution(currBroker.getStrategy(), currBroker.getCoinList(), currBroker.getName());
			
			//check that trading round was successful
			// size == 7 means a buy/sell action was enacted (ensures no faulty trades are shown in histo)
			if (tradeResult.size() == 7) {
				//determine if an entry in frequency already exists
				for (int j = 0; j < frequency.size(); j++) {
					//if there exists an entry in frequency counter for a particular
					//broker name and trading strategy, increase count by one
					if (frequency.get(j).get(1).equals(currBroker.getName()) && frequency.get(j).get(2).equals(currBroker.getStrategy())) {
						found = true;
						
						update = Integer.parseInt(frequency.get(j).get(0));
						update++;
						frequency.get(j).set(0, Integer.toString(update));
						
						break;
					}
				}

				//if there is no pre-existing array and strategy is not None, then create
				//new entry in frequency list
				current = new ArrayList<String>();
				if (!found && !currBroker.getStrategy().equals("None")) {
					current = new ArrayList<String>();
					current.add(Integer.toString(1));
					current.add(currBroker.getName());
					current.add(currBroker.getStrategy());
					
					frequency.add(current);
				}	
			}
		}		
	}
	
	/**
	 * Class to add a broker
	 * @param name		name of the broker
	 * @param strategy	strategy for broker
	 * @param coinList	list of coins for broker
	 * @return		true if successfully added and false is not added 
	 */
	public boolean addBroker(String name, String strategy, String[] coinList) {
		if (!containsBroker(brokerList, name)) { //if broker is not in list yet
			Broker newBroker = new Broker(name, strategy, coinList);
			
			brokerList.add(newBroker);
			strategyList.add(strategy);
			numBrokers++;
	        coinsList.add(coinList);
	        
	        return true;
	        
		} else {
			System.out.println("Broker already in list");
			return false;
		}
	}
}
