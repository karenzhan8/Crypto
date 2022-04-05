package utils;

import java.util.ArrayList;
import java.util.List;

import gui.MainUI;

public class UserSelection {
	private static UserSelection instance;
	
	private ArrayList<Broker> brokerList = new ArrayList<Broker>(); 
	private ArrayList<String> strategyList = new ArrayList<String>();
	private ArrayList<String[]> coinsList = new ArrayList<String[]>();
	private int numBrokers;
	
	private static List<List<String>> frequency = new ArrayList<List<String>>();
		
	//implementing a singleton design pattern 
	private UserSelection getInstance() {
		if (instance == null) {
			instance = new UserSelection();
		}
		
		return instance;
	}
	
	private static boolean containsBroker (ArrayList<Broker> brokerList, String name) {
    	for (int i = 0; i < brokerList.size(); i++) {
    		if (brokerList.get(i).getName().equals(name)) {
    			return true;
    		}
    	}
    	return false;
    }
	
    public static List<List<String>> getFrequencies() {
    	return frequency;
    }
    
	public static void setFrequencies(UserSelection traderList) {
		TradeStrategy trader = new TradeStrategy(); // used to perform trades
		boolean found = false;
		int update;
		ArrayList<String> current;
		
		for (int i=0; i < traderList.getNumBrokers(); i++) {
			Broker currBroker = traderList.getBrokerList().get(i);
			List<String> tradeResult = trader.getExecution(currBroker.getStrategy(), currBroker.getCoinList(), currBroker.getName());
			
			// size == 7 means a buy/sell action was enacted (ensures no faulty trades are shown in histo)
			if (tradeResult.size() == 7) {
				//determine if an entry in frequency already exists
				for (int j = 0; j < frequency.size(); j++) {
					if (frequency.get(j).get(1).equals(currBroker.getName()) && frequency.get(j).get(2).equals(currBroker.getStrategy())) {
						found = true;
						
						update = Integer.parseInt(frequency.get(j).get(0));
						update++;
						frequency.get(j).set(0, Integer.toString(update));
						
						break;
					}
				}
				
				current = new ArrayList<String>();
				
				//if there is no pre-existing array and strategy is not None
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
		
    public ArrayList<Broker> getBrokerList() {
        return brokerList;
    }
    public ArrayList<String> getStrategyList() {
        return strategyList;
    }
    
    public ArrayList<String[]> getCoinLists() {
        return coinsList;
    }
	
    public int getNumBrokers() {
	    return numBrokers;
    }   
    
}
