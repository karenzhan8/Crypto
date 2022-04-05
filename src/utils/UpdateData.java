package utils;

import java.util.*;

/**
 * Acts as the subject for DataVisualizationCreator
 * Notifies observers of trading data 
 */
public class UpdateData extends Subject {
	/**
	 * Method to display 
	 * @param tableData
	 * @param histogramData
	 */
	public void notify(List<List<String>> tableData, List<List<String>> histogramData) {
		notifyObservers(tableData, histogramData);
	}
}
