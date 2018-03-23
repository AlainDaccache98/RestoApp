package ca.mcgill.ecse223.resto.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.util.HashMap;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import ca.mcgill.ecse223.resto.controller.Controller;
import ca.mcgill.ecse223.resto.controller.InvalidInputException;
import ca.mcgill.ecse223.resto.model.Table;

public class UpdateTablePage extends JFrame {

	//private JPanel contentPane;
	private static final long serialVersionUID = -4426310869335015542L;
	
	private JLabel errorMessage;

	private JComboBox<String> tableList;
	private JLabel tableNumberSelected;
	
	private JLabel newTableNumberLabel;
	private JTextField newTableNumberTextField;

	private JLabel updatedSeatsLabel;
	private JTextField updatedSeatsTextField;
	
	private JButton updateTableFeaturesButton;

	//temp elements
	//private JLabel hint1;
	//private JLabel hint2;

	private String error = null;

	private Integer selectedTableNumber = -1;
	private HashMap<Integer, Table> tables;


	/**
	 * Create the frame.
	 */
	public UpdateTablePage() {
		initComponents();
		refreshData();
	}
	
	private void initComponents() {
		
		errorMessage = new JLabel();
		errorMessage.setForeground(Color.RED);
		//label for selecting table whose features are to be updated
		tableNumberSelected = new JLabel();
		
		//comboBox for selecting from the existing tables 
		tableList = new JComboBox<String>(new String[0]);
		tableList.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt){ 
				JComboBox<String> cb   = (JComboBox<String>) evt.getSource();
				selectedTableNumber = cb.getSelectedIndex();
			}
		});
		
		//UI elements for updating table number
		newTableNumberLabel = new JLabel();
		newTableNumberTextField = new JTextField();
		updateTableFeaturesButton = new JButton();
		
		//UI elements for updating seats
		updatedSeatsLabel = new JLabel();
		updatedSeatsTextField = new JTextField();
		
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("RestoApp");
		
		tableNumberSelected.setText("Select table: ");
		newTableNumberLabel.setText("New table number: ");
		updatedSeatsLabel.setText("New number of seats: ");
		
		updateTableFeaturesButton.setText("Update");
		//updatedSeatsButton.setText("Update");
		
		updateTableFeaturesButton.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent evt){
				newTableNumberButtonActionPerformed(evt);
			}

			
		});
		
		
		/*updatedSeatsButton.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent evt){
				updateSeatsButtonActionPerformed(evt);
			}

		});*/
		
		JSeparator horizontalLineTop = new JSeparator();
		JSeparator horizontalLineMiddle = new JSeparator();
		JSeparator horizontalLineBottom = new JSeparator();
		
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		layout.setHorizontalGroup(
				layout.createParallelGroup()
				.addComponent(errorMessage)
				.addComponent(horizontalLineTop)
				.addComponent(horizontalLineMiddle)
				.addComponent(horizontalLineBottom)
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup()
								.addComponent(tableNumberSelected)
								.addComponent(newTableNumberLabel)
								.addComponent(updatedSeatsLabel))
						.addGroup(layout.createParallelGroup()
								.addComponent(tableList)
								.addComponent(newTableNumberTextField,200,200,400)
								.addComponent(updatedSeatsTextField,200,200,400)
								.addComponent(updateTableFeaturesButton, 70,70,140))
				
				));
		
		/*layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {newTableNumberTextField, newTableNumberButton});

		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {updatedSeatsTextField, updatedSeatsButton});*/

		
		layout.setVerticalGroup(
				layout.createSequentialGroup()
				.addComponent(errorMessage)				
				.addGroup(layout.createParallelGroup()
						.addComponent(tableNumberSelected)
						.addComponent(tableList))
				.addGroup(layout.createParallelGroup()
						.addComponent(horizontalLineTop))
				.addGroup(layout.createParallelGroup()
						.addComponent(newTableNumberLabel)
						.addComponent(newTableNumberTextField))
				
				.addGroup(layout.createParallelGroup()
						.addComponent(horizontalLineMiddle))		
				.addGroup(layout.createParallelGroup()
						.addComponent(updatedSeatsLabel)
						.addComponent(updatedSeatsTextField))
				.addGroup(layout.createParallelGroup()
						.addComponent(updateTableFeaturesButton))
				.addGroup(layout.createParallelGroup()
						/*.addComponent(updatedSeatsButton))*/
				.addGroup(layout.createParallelGroup()
						.addComponent(horizontalLineBottom))
						));
		pack();

	}
	
	//action after pressing the update table number button
	private void newTableNumberButtonActionPerformed(ActionEvent evt) {
		
		// clear error message
		error = null;
				
		// call the controller
		try {
			int newTableNumber = Integer.parseInt(newTableNumberTextField.getText());
			Table table = Table.getWithNumber(newTableNumber);
			int newSeatCount = Integer.parseInt(updatedSeatsTextField.getText());
			Controller.updateTable(table, newTableNumber, newSeatCount );
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
				
		// update visuals
		refreshData();
			
	}

	//refreshing the data after a new entry
	private void refreshData() {
		
		errorMessage.setText(error);
		if (error == null || error.length() == 0) {

			//update 2 text fields
			newTableNumberTextField.setText("");
			updatedSeatsTextField.setText("");
			
			//update the combo box
			tables = new HashMap<Integer, Table>();
			tableList.removeAllItems();
			Integer index = 0;
			
			for(Table table : Controller.getCurrentTables()){
				tables.put(index, table);
				tableList.addItem("# " + table.getNumber());
				index++;
			}
			
			selectedTableNumber = -1;
			tableList.setSelectedIndex(selectedTableNumber);
			
		}
		
		pack();
		
	}

	

}
