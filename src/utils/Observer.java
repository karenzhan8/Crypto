package utils;

import java.util.List;

interface Observer {
	  public void update(Subject subject, List<List<String>> tableData, List<List<String>> histogramData);
}
