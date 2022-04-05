package utils;

import java.util.*;

/**
 * Subject for Observer design pattern
 */
public abstract class Subject {
	private DataVisualizationCreator observer;
	
	/**
	 * attaches a new observer to this subject
	 * @param observer
	 */
	public void attach(Observer observer) {
		this.observer = (DataVisualizationCreator) observer;
	}
	
	/**
	 * notifies Observers of new incoming data
	 * @param tableData
	 * @param histogramData
	 */
	public void notifyObservers(List<List<String>> tableData, List<List<String>> histogramData) {
		observer.update(this, tableData, histogramData);
	}
}
