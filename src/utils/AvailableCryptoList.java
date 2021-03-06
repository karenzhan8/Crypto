package utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * details crypto coins available for broker use
 * usese singleton design pattern
 */

public class AvailableCryptoList {
	private static AvailableCryptoList instance = null;
	private Map<String, String> tickerIDMap = new HashMap<>();
	private List<String> availableCryptosList = new ArrayList<>();
	
	/**
	 * private method that allows for singleton design pattern
	 * @return instance
	 */
	public static AvailableCryptoList getInstance() {
		if (instance == null) {
			instance = new AvailableCryptoList();
		}
		return instance;
	}
	
	/**
	 * method finds available cryptos for trading
	 */
	private AvailableCryptoList() {
		findAvailableCryptos();
	}
	
	/**
	 * calls API 
	 */
	public void call() {
		String urlString = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=IBM&apikey=VNEY4VV2AWF1EB51";
		try {
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.connect();
			int responsecode = conn.getResponseCode();
			if (responsecode == 200) {
				String inline = "";
				Scanner sc = new Scanner(url.openStream());
				while (sc.hasNext()) {
					inline += sc.nextLine();
				}
				sc.close();		
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block e.printStackTrace();
		}
	}
	
	/**
	 * finds available crypto coins
	 */
	private void findAvailableCryptos() {
		String urlString = "https://api.coingecko.com/api/v3/coins/markets" + "?vs_currency=usd&order=market_cap_desc&per_page=100&page=1&sparkline=false";
		
		//ALPHAVANTAGE API KEY = VNEY4VV2AWF1EB51
		try {
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.connect();
			int responsecode = conn.getResponseCode();
			if (responsecode == 200) {
				String inline = "";
				Scanner sc = new Scanner(url.openStream());
				while (sc.hasNext()) {
					inline += sc.nextLine();
				}
				sc.close();
				JsonArray jsonArray = new JsonParser().parse(inline).getAsJsonArray();
				int size = jsonArray.size();
				
				String name, id, symbol;
				for (int i = 0; i < size; i++) {
					JsonObject object = jsonArray.get(i).getAsJsonObject();
					name = object.get("name").getAsString();
					id = object.get("id").getAsString();
					symbol = object.get("symbol").getAsString();
				
					
					tickerIDMap.put(symbol, id);
					
					availableCryptosList.add(symbol);
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block e.printStackTrace();
		}
	}
	
	/**
	 * Returns string array of available cryptos
	 * @return available cryptos
	 */
	public String[] getAvailableCryptos() {
		return availableCryptosList.toArray(new String[availableCryptosList.size()]);
	}
	
	/**
	 * returns if coin is available
	 * @param coinList
	 * @return if coin is available
	 */
	public String coinAvailable(String[] coinList) {
		String[] availableCoins = availableCryptosList.toArray(new String[availableCryptosList.size()]);
		
		for (int i=0; i < coinList.length; i++) {
			if (!Arrays.asList(availableCoins).contains(coinList[i].toLowerCase())) {
				return coinList[i];
			}
		} 
		return null;
	}
	
	/**
	 * returns ticker code for cryptocoin
	 * @param tickerName
	 * @return ticker 
	 */   
	public String getCryptoIDfromTicker(String tickerName) {
		return tickerIDMap.get(tickerName.toLowerCase());
	}
}
