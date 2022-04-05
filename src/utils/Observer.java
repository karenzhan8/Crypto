package utils;

import java.util.List;

/**
 * Observer interface for observer design pattern 
 */

interface Observer {
	/**
	 * Update method so data can be drawn
	 * @param subject
	 * @param tableData
	 * @param histogramData
	 */
	public void update(Subject subject, List<List<String>> tableData, List<List<String>> histogramData);
}
