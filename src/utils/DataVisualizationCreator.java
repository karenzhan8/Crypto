package utils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.LogAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.Range;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import gui.MainUI;

/**
 * mechanism behind displaying table and map
 * utilizes singleton design pattern
 */
public class DataVisualizationCreator {
	private static DataVisualizationCreator instance;
	
	/**
	 * mechanism for singleton design pattern
	 */
	private static DataVisualizationCreator getInstance() {
		if (instance == null) {
			instance = new DataVisualizationCreator();
		}
		return instance;
	}
	
	/**
	 *  outputs table and histogram onto mainUI
	 *  @param tableData includes the data to be fed into the table display
	 *  @param histogramData includes the data to be fed into the histogram
	 */
	public void createCharts(List<List<String>> tableData, List<List<String>> histogramData) {
		createTableOutput(tableData);
		createBar(histogramData);
	}
	
	/**
	 * outputs table displaying summary of trading actions onto mainUI
	 * @param tradeActions contains the list of all buy/sell actions enacted in most recent trade
	 */
	private void createTableOutput(List<List<String>> tradeActions) {
		Object[] columnNames = {"Trader","Strategy","Action", "CryptoCoin","Quantity","Price","Date"};
		
		Object[][] data = new Object[tradeActions.size()][7];
		for (int i=0; i < data.length; i++) {
			List<String> currLine = tradeActions.get(i);
			for (int j=0; j < currLine.size(); j++) {
				if (currLine.size() == 3) {
					data[i][0] = currLine.get(0);
					data[i][1] = currLine.get(1);
					data[i][6] = currLine.get(2);
				} else {
					data[i][j] = currLine.get(j);
				};
			}
		};

		JTable table = new JTable(data, columnNames);
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBorder (BorderFactory.createTitledBorder (BorderFactory.createEtchedBorder (),
                "Trader Actions",
                TitledBorder.CENTER,
                TitledBorder.TOP));
		
		scrollPane.setPreferredSize(new Dimension(800, 300));
		table.setFillsViewportHeight(true);;
		
		MainUI.getInstance().updateStats(scrollPane);
	}
	
	/**
	 * outputs histogram displaying frequency of each trading strategy onto MainUI
	 * @param strategyFrequencies includes list of number of times each strategy has been enacted by each broker
	 */
	public void createBar(List<List<String>> strategyFrequencies) {
		
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();	
		for (int i=0; i < strategyFrequencies.size(); i++) {
			List<String> tradeItem = strategyFrequencies.get(i);
			dataset.setValue(Integer.parseInt(tradeItem.get(0)), tradeItem.get(1), tradeItem.get(2));
		};
		
		CategoryPlot plot = new CategoryPlot();
		BarRenderer barrenderer1 = new BarRenderer();

		plot.setDataset(0, dataset);
		plot.setRenderer(0, barrenderer1);
		CategoryAxis domainAxis = new CategoryAxis("Strategy");
		plot.setDomainAxis(domainAxis);
		LogAxis rangeAxis = new LogAxis("Actions(Buys or Sells)");
		rangeAxis.setRange(new Range(0.1, 20.0));
		plot.setRangeAxis(rangeAxis);

		JFreeChart barChart = new JFreeChart("Actions Performed By Traders So Far", new 
				Font("Serif", java.awt.Font.BOLD, 18), plot,true);

		ChartPanel chartPanel = new ChartPanel(barChart);
		chartPanel.setPreferredSize(new Dimension(600, 300));
		chartPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		chartPanel.setBackground(Color.white);
		MainUI.getInstance().updateStats(chartPanel);
	}
}
