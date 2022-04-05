package utils;

import java.util.*;

public abstract class Subject {
	  private List<Observer> observers = new ArrayList<>();

	  public void attach(Observer observer) {
	    observers.add(observer);
	  }

	  public void detach(Observer observer) {
	    observers.remove(observer);
	  }

	  public void notifyObservers(List<List<String>> tableData, List<List<String>> histogramData) {
	    for (Observer observer : observers)
	      observer.update(this, tableData, histogramData);
	  }
}
