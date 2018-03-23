package ca.mcgill.ecse223.resto.view;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Properties;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;

import ca.mcgill.ecse.btms.view.BusVehicle;
import ca.mcgill.ecse223.resto.controller.Controller;
import ca.mcgill.ecse223.resto.controller.InvalidInputException;
import ca.mcgill.ecse223.resto.model.Reservation;
import ca.mcgill.ecse223.resto.model.Table;
import ca.mcgill.ecse223.resto.view.TableVisualizer; // is import complete?
public class ReservationPage extends JFrame {

	private static final long serialVersionUID = -4426310869335015542L; // rewrite
																		// the
																		// serialversion
	// UI elements
	private JLabel errorMessage;

	// Reservation
	private JComboBox<String> tableNumberToggleList; 
	private JLabel tableNumberToggleLabel;
	private JTextField dateAndTimeTextField;
	private JLabel dateAndTimeLabel;
	private JTextField numberInPartyTextField;
	private JLabel numberInPartyLabel;
	private JTextField contactNameTextField;
	private JLabel contactNameLabel;
	private JTextField contactEmailTextField;
	private JLabel contactEmailLabel;
	private JTextField contactPhoneTextField;
	private JLabel contactPhoneLabel;
	private JButton reserveButton;
	private TableVisualizer tableVisualizer;
	private static final int WIDTH_Table_VISUALIZATION = 200;
	private static final int HEIGHT_Table_VISUALIZATION = 200;

	// data elements
	private String error = null;			// do we need to adjust the variables according to variables in the controller method?
	// toggle repairs status
	private Integer selectedTableNumber = -1;
	private HashMap<Integer, Table> tables; // not sure about this

	public ReservationPage() {
		initComponents();
		refreshData();
	}
	private void initComponents() {
		// elements for error message
		errorMessage = new JLabel();
		errorMessage.setForeground(Color.RED);

		tableVisualizer = new TableVisualizer();
		tableVisualizer.setMinimumSize(new Dimension(WIDTH_Table_VISUALIZATION,
				HEIGHT_Table_VISUALIZATION));

		// elements
		tableNumberToggleList = new JComboBox<String>(new String[0]);
		tableNumberToggleList
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(
							java.awt.event.ActionEvent evt) {
						JComboBox<String> cb = (JComboBox<String>) evt
								.getSource();
						selectedTableNumber = cb.getSelectedIndex();
					}
				});
		tableNumberToggleLabel = new JLabel();
		dateAndTimeTextField = new JTextField();
		dateAndTimeLabel = new JLabel();
		numberInPartyTextField = new JTextField();
		numberInPartyLabel = new JLabel();
		contactNameTextField = new JTextField();
		contactNameLabel = new JLabel();
		contactEmailTextField = new JTextField();
		contactEmailLabel = new JLabel();
		contactPhoneTextField = new JTextField();
		contactPhoneLabel = new JLabel();
		reserveButton = new JButton();
		// global settings and listeners
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("resto Management System");
		tableNumberToggleLabel.setText("Table Number: ");
		dateAndTimeLabel.setText("Date and Time: ");
		numberInPartyLabel.setText("Number In Party: ");
		contactNameLabel.setText("Contact Name: ");
		contactEmailLabel.setText("Contact E-Mail: ");
		contactPhoneLabel.setText("Contact Phone: ");
		reserveButton.setText("Reserve");
		reserveButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				reserveButtonActionPerformed(evt);
			}
		});
		// Vertical element
		JSeparator verticalLine = new JSeparator(JSeparator.VERTICAL); // Not
																		// sure
																		// about
																		// this
																		// part
		// Layout
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		layout.setVerticalGroup(layout.createSequentialGroup()			// Check the layout of the UI for any arrangement mistakes
				.addComponent(errorMessage).addComponent(tableVisualizer)
				.addComponent(verticalLine)
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup()
								.addComponent(tableNumberToggleLabel)
								.addComponent(dateAndTimeLabel)
								.addComponent(numberInPartyLabel)
								.addComponent(contactNameLabel)
								.addComponent(contactEmailLabel)
								.addComponent(contactPhoneLabel)
								.addComponent(reserveButton, 70, 70, 140))
						.addGroup(layout.createParallelGroup()
								.addComponent(tableNumberToggleList)
								.addComponent(dateAndTimeTextField, 200, 200,
										400)
								.addComponent(numberInPartyTextField, 200, 200,
										400)
								.addComponent(contactNameTextField, 200, 200,
										400)
								.addComponent(contactEmailTextField, 200, 200,
										400)
								.addComponent(contactPhoneTextField, 200, 200,
										400))));
		pack();
	}
	private void refreshData() {
		// error
		errorMessage.setText(error);
		if (error == null || error.length() == 0) {
			// populate page with data
			dateAndTimeTextField.setText("");
			numberInPartyTextField.setText("");
			contactNameTextField.setText("");
			contactEmailTextField.setText("");
			contactPhoneTextField.setText("");
			// toggle table number
			tables = new HashMap<Integer, Table>();
			tableNumberToggleList.removeAllItems();
			index = 0;
			for (Table table : Controller.getTables()) {
				tables.put(index, table);
				tableNumberToggleList.addItem(table.getNumber());
				index++;
			} ;
			selectedTableNumber = -1;
			tableNumberToggleList.setSelectedIndex(selectedTableNumber);

		}
	}

	private void reserveButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// clear error message
		error = "";					 // not sure about setting it to an empty string
		if (selectedTableNumber < 0)
			error = "Table number needs to be selected!";

		if (error.length() == 0) {
			// call the controller
			try {
				Controller.reserve());      // TO BE COMPLETED WHEN THE CONTROLLER METHOD IS READY
			} catch (InvalidInputException e) { // not sure about e
				error = e.getMessage();
			}
		}
		
		// update visuals
		refreshData();
	}
}
