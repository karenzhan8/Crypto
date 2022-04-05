package utils;

import java.util.*;

public class UpdateData extends Subject{
	public void displayTableHisto(List<List<String>> tableData, List<List<String>> histogramData) {
		notifyObservers(tableData, histogramData);
	}
}
