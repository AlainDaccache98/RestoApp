package ca.mcgill.ecse223.resto.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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

public class CancelOrderPage extends JFrame {

	//private JPanel contentPane;
	private static final long serialVersionUID = -4426310869335015542L;
	
	private JLabel errorMessage;

	private JComboBox<String> tableList;
	private JLabel tableNumberSelected;
	
	private JButton cancelOrderButton;
	private JButton homeButton;

	
    private TableVisualizer tableVisualizer;
    
	private static final int WIDTH_Table_VISUALIZATION = 200;
	private static final int HEIGHT_Table_VISUALIZATION = 200;

	private String error = null;

	private Integer selectedTableNumber = -1;
	private HashMap<Integer, Table> tables;


	/**
	 * Create the frame.
	 */
	public CancelOrderPage() {
		initComponents();
		refreshData();
	}
	
	private void initComponents() {
		
		errorMessage = new JLabel();
		errorMessage.setForeground(Color.RED);
		//label for selecting table whose features are to be updated
		tableNumberSelected = new JLabel();
		
		tableVisualizer = new TableVisualizer();
		tableVisualizer.setMinimumSize(new Dimension(WIDTH_Table_VISUALIZATION, HEIGHT_Table_VISUALIZATION));
		
		//comboBox for selecting from the existing tables 
		tableList = new JComboBox<String>(new String[0]);
		tableList.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt){ 
				JComboBox<String> cb   = (JComboBox<String>) evt.getSource();
				selectedTableNumber = cb.getSelectedIndex();
			}
		});
		
		//UI elements for updating table number
		cancelOrderButton = new JButton();
		homeButton = new JButton();
		
		
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("Cancel Order");
		
		tableNumberSelected.setText("Select table: ");
		
		cancelOrderButton.setText("Cancel Order");
		cancelOrderButton.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent evt){
				cancelOrderButtonActionPerformed(evt);
			}
		});
		
		homeButton.setText("Home");
		homeButton.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent evt){
				homeButtonActionPerformed(evt);
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
				.addComponent(homeButton)
				.addComponent(horizontalLineMiddle)
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup()
								.addComponent(tableNumberSelected))
						.addGroup(layout.createParallelGroup()
								.addComponent(tableList)
								.addComponent(cancelOrderButton, 70,70,140)))
				.addComponent(horizontalLineBottom)
				.addGroup(layout.createParallelGroup()
						.addGroup(layout.createParallelGroup()
						.addComponent(tableVisualizer)))
				);
		
		/*layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {newTableNumberTextField, newTableNumberButton});

		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {updatedSeatsTextField, updatedSeatsButton});*/

		
		layout.setVerticalGroup(
				layout.createSequentialGroup()
				.addComponent(errorMessage)	
				.addComponent(horizontalLineTop)
				.addComponent(homeButton)
				.addComponent(horizontalLineMiddle)
				.addGroup(layout.createParallelGroup()
						.addComponent(tableNumberSelected)
						.addComponent(tableList))
				.addGroup(layout.createParallelGroup()
						.addComponent(horizontalLineTop))
				.addGroup(layout.createParallelGroup()
						.addComponent(cancelOrderButton))
				.addGroup(layout.createParallelGroup()
						.addComponent(horizontalLineBottom))
				.addGroup(layout.createParallelGroup()
						.addGroup(layout.createParallelGroup()
						.addComponent(tableVisualizer)))
				);
		pack();

	}
	
	//action after pressing the update table number button
	private void cancelOrderButtonActionPerformed(ActionEvent evt) {
		
		// clear error message
		error = null;
				
		// call the controller
		try {
			String object = (String)tableList.getSelectedItem();
			//System.out.println(object);
			String number = object.substring(2,3);
			//System.out.println(number);
			int originalTableNumber = Integer.parseInt(number);
			//System.out.println(originalTableNumber);
			Table table = Table.getWithNumber(originalTableNumber);
			Controller.cancelOrder(table);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
				
		// update visuals
		refreshData();

			
	}
	
	private void homeButtonActionPerformed(ActionEvent evt) {
		
		// clear error message
		error = null;
				
		new RestoHomePage().setVisible(true);
		
		// update visuals
		refreshData();
			
	}	
	
	//refreshing the data after a new entry
	private void refreshData() {
		
		errorMessage.setText(error);
		if (error == null || error.length() == 0) {

			
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
