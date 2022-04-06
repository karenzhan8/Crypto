package utils;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import gui.MainUI;

import utils.AvailableCryptoList;
import utils.DataVisualizationCreator;
import utils.ExecuteTrade;
import utils.UpdateData;
import utils.User;
import utils.UserSelection;

import javax.swing.JTextField;
import javax.swing.JLabel;
// renders charts
import utils.DataVisualizationCreator;

/**
 * Created to implement facade design pattern
 */
public class Facade {
	private static Facade instance;

	/**
	 * Uses singleton design pattern
	 * @return instance
	 */
	public static Facade getInstance() {
		if (instance == null) {
			instance = new Facade();
		}
		return instance;
	}
	
	/**
	 * refreshes mainui
	 * @param dtm
	 * @param cumulativeTrades
	 * @param stats
	 */
	public void refresh(DefaultTableModel dtm, ExecuteTrade cumulativeTrades, JPanel stats) {
		UserSelection brokerDatabase = new UserSelection();
		
		for (int count = 0; count < dtm.getRowCount(); count++){
			Object traderObject = dtm.getValueAt(count, 0); 
			if (traderObject == null) {
				JOptionPane.showMessageDialog(MainUI.getInstance(), "please fill in Trader name on line " + (count + 1) );
				return;
			}
			String traderName = traderObject.toString();

			Object coinObject = dtm.getValueAt(count, 1); 
			if (coinObject == null) {
				JOptionPane.showMessageDialog(MainUI.getInstance(), "please fill in cryptocoin list on line " + (count + 1) );
				return;
			}
			String[] coinNames = coinObject.toString().split(","); // list of coins
			// ensures AvailableCryptoList can properly read the ticker symbol
			for (int i=0; i < coinNames.length; i++) {
				coinNames[i] = coinNames[i].trim();
			}
			Object strategyObject = dtm.getValueAt(count, 2);
			if (strategyObject == null) {
				JOptionPane.showMessageDialog(MainUI.getInstance(), "please fill in strategy name on line " + (count + 1) );
				return;
			} else {
					String ticker = AvailableCryptoList.getInstance().coinAvailable(coinNames);
				if (ticker != null) {
					JOptionPane.showMessageDialog(MainUI.getInstance(), "the following coin is not valid: " + ticker + " on line " + (count + 1) );
					return;
				}
			}
			String strategyName = strategyObject.toString(); // strategy name string
			boolean add = brokerDatabase.addBroker(traderName, strategyName, coinNames);
			
			if (!add) {
				JOptionPane.showMessageDialog(MainUI.getInstance(), "repeat broker name added, please change name on line " + (count + 1) + "!");
				return;
			}
		}
		
		cumulativeTrades.performTrade(brokerDatabase);
		
		stats.removeAll();
		
		//add observer (DataVisualizationCreator) to subject (UpdateData)
		//draws table and histo
		UpdateData updater = new UpdateData();
		DataVisualizationCreator creator = new DataVisualizationCreator(updater);
		updater.attach(creator);
		
		//set frequencies so histogram can be displayed
		UserSelection.setFrequencies(brokerDatabase);
		List<List<String>> histoList = UserSelection.getFrequencies();
		
		//notify OBSERVERs that trading data is updated 
		updater.notifyObservers(cumulativeTrades.getCumulativeTrades(), histoList);
	}
	
	/**
	 * Method allows user to add row
	 * @param dtm
	 */
	public void addRow(DefaultTableModel dtm) {
		dtm.addRow(new String[3]); 
	}
	
	/**
	 * Method allows user to remove row 
	 * @param dtm
	 * @param table
	 */
	public void removeRow(DefaultTableModel dtm, JTable table) {
		int selectedRow = table.getSelectedRow();
		if (selectedRow != -1) {
			dtm.removeRow(selectedRow); // connect to remove the selected row data from here (THIS IS PERFORMED AFTER TRADE BUTTON HIT)
		}
	}
	
	/**
	 * Verifies user log in
	 * @param credentials
	 * @param username
	 * @param password
	 * @param loginFrame
	 * @param frame
	 */
	public void login(boolean credentials, JTextField username, JPasswordField password, JFrame loginFrame, JFrame frame) {
		/* 
		* verify that user credentials are correct
		* FACADE pattern implemented, masking back-end operations required to verify username and password
		* user input sent to User class for verification
		*/
		User info = User.getInstance();
		
		//get user input 
		String user = username.getText();
		String pass = password.getText();

		credentials = info.verify(user, pass);
		
		//display success/failure to user
		if (!credentials) {
			loginFrame.setVisible(false);
			JOptionPane.showMessageDialog(MainUI.getInstance(), "Log in failed, system will terminate!");
		}	
		
		//hide log in window, whether or not user log in was successful
		loginFrame.setVisible(false);
		
		if (credentials) {
			frame.setVisible(true);
		}
		else {
			//exit system
			System.exit(1);
		}
		return;
	}

}
