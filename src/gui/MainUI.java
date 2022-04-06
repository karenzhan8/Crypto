package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import utils.ExecuteTrade;
import utils.UserSelection;
import utils.Facade;

import javax.swing.JTextField;

/**
 * main method
 * utilizes singleton design pattern
 * is the SUBJECT for the DataVisualizationCreator class
 * behaves as a FACADE between user and other backend operations
 */
public class MainUI extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;

	private static MainUI instance;
	private JPanel stats;

	private DefaultTableModel dtm;
	private JTable table;
	
	//trading platform frame
	private static JFrame frame = new JFrame();
	
	//log in popup frame, button, labels, and fields
	private static JFrame loginFrame = new JFrame();
	private static JLabel userLabel, passwordLabel;
	private static JTextField username;
	private static JPasswordField password;
	private static JButton button;
	private static boolean credentials;
	
	// stores database of brokers
	UserSelection brokerDatabase = new UserSelection();
	// stores list of cumulative trades
	ExecuteTrade cumulativeTrades = new ExecuteTrade();

	/**
	 * utilize Singleton design pattern to create only one instance of MainUI
	 * @return instance
	 */
	public static MainUI getInstance() {
		if (instance == null) {
			instance = new MainUI();
		}
		return instance;
	}

	/**
	 * Constructor, main method and main UI for user
	 */
	private MainUI() {
		// Set window title
		super("Crypto Trading Tool");

		//facilitate log in and verifying credentials
		JPanel loginPanel = new JPanel();
		loginPanel.setLayout(null);
		
		loginFrame.setTitle("Login");
		loginFrame.add(loginPanel);
		loginFrame.setSize(new Dimension (400, 200));
		
		userLabel = new JLabel ("Username");
		userLabel.setBounds(100, 8, 70, 20);
		loginPanel.add(userLabel);
		
		username = new JTextField();
		username.setBounds(100, 27, 193, 28);;
		loginPanel.add(username);
		
		passwordLabel = new JLabel("Password");
		passwordLabel.setBounds(100, 55, 70, 20);
		loginPanel.add(passwordLabel);
		
		password = new JPasswordField();
		password.setBounds(100, 75, 193, 28);
		loginPanel.add(password);
		
		button = new JButton("Login");
		button.setBounds(145, 110, 90, 25);
		button.addActionListener(this);
		loginPanel.add(button);
		
		//allow user to log in
		loginFrame.setLocationRelativeTo(null);
		loginFrame.setVisible(true);
		loginFrame.setAlwaysOnTop(true);
		
		// Set top bar
		JPanel north = new JPanel();
		JButton trade = new JButton("Perform Trade");
		trade.setActionCommand("refresh");
		trade.addActionListener(this);

		JPanel south = new JPanel();
		south.add(trade);

		dtm = new DefaultTableModel(new Object[] { "Trading Client", "Coin List", "Strategy Name" }, 0);
		table = new JTable(dtm);
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Trading Client Actions", TitledBorder.CENTER, TitledBorder.TOP));
		Vector<String> strategyNames = new Vector<String>();
		strategyNames.add("None");
		strategyNames.add("Strategy-A");
		strategyNames.add("Strategy-B");
		strategyNames.add("Strategy-C");
		strategyNames.add("Strategy-D");
		TableColumn strategyColumn = table.getColumnModel().getColumn(2);
		JComboBox comboBox = new JComboBox(strategyNames);
		strategyColumn.setCellEditor(new DefaultCellEditor(comboBox));
		JButton addRow = new JButton("Add Row");
		JButton remRow = new JButton("Remove Row");
		addRow.setActionCommand("addTableRow");
		addRow.addActionListener(this);
		remRow.setActionCommand("remTableRow");
		remRow.addActionListener(this);

		scrollPane.setPreferredSize(new Dimension(800, 300));
		table.setFillsViewportHeight(true);
		
		JPanel east = new JPanel();
		east.setLayout(new BoxLayout(east, BoxLayout.Y_AXIS));
		east.add(scrollPane);
		
		JPanel buttons = new JPanel();
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));
		buttons.add(addRow);
		buttons.add(remRow);
		east.add(buttons);

		// Set charts region
		JPanel west = new JPanel();
		west.setPreferredSize(new Dimension(1250, 650));
		stats = new JPanel();
		stats.setLayout(new GridLayout(2, 2));

		west.add(stats);

		getContentPane().add(north, BorderLayout.NORTH);
		getContentPane().add(east, BorderLayout.EAST);
		getContentPane().add(west, BorderLayout.CENTER);
		getContentPane().add(south, BorderLayout.SOUTH);
	}

	/**
	 * method updates stats 
	 * @param component
	 */
	public void updateStats(JComponent component) {
		stats.add(component);
		stats.revalidate();
	}
	
	/**
	 * main method 
	 * @param args
	 */
	public static void main(String[] args) {
		frame = MainUI.getInstance();
		frame.setSize(900, 600);
		frame.pack();
		loginFrame.setVisible(true);
	}
	
	/**
	 * allows UI to respond to user actions
	 * @param e
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		Facade info = Facade.getInstance();
		
		/**
		* performs trade by storing all current brokers in each row in a database, and performing a trade on each based on coins and strategy
		* FACADE design enacted by sending all complex operations to other classes (i.e. executeTrade)
		* complex back-end operations are masked, user only presented with "perform trade" button and trade results
		*/
		if ("refresh".equals(command)) { 
			info.refresh(dtm, cumulativeTrades, stats);
		} else if ("addTableRow".equals(command)) {
			info.addRow(dtm);
		} else if ("remTableRow".equals(command)) {
			info.removeRow(dtm, table);
		} else if ("Login".equals(command)) {
			info.login(credentials, username, password, loginFrame, frame);
		}
	}
}
