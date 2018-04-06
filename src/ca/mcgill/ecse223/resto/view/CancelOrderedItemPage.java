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
import ca.mcgill.ecse223.resto.model.OrderItem;
import ca.mcgill.ecse223.resto.model.Table;

public class CancelOrderedItemPage extends JFrame {

	//private JPanel contentPane;
	private static final long serialVersionUID = -7826310869335015542L;
	
	private JLabel errorMessage;
	private JLabel orderItemSelected;
	private JTextField orderItemTF;
	private JButton cancelOrderItemButton;
	private JButton homeButton;
	
    private TableVisualizer tableVisualizer;
    
	private static final int WIDTH_Table_VISUALIZATION = 200;
	private static final int HEIGHT_Table_VISUALIZATION = 200;


	private String error = null;

	
	/**
	 * Create the frame.
	 */
	public CancelOrderedItemPage() {
		initComponents();
	    this.setSize(1400, 500);

		refreshData();
	}
	
	private void initComponents() {
		
		errorMessage = new JLabel();
		errorMessage.setForeground(Color.RED);
		//label for selecting table whose features are to be updated
		
		orderItemSelected = new JLabel();
		orderItemTF = new JTextField();
		tableVisualizer = new TableVisualizer();
		tableVisualizer.setMinimumSize(new Dimension(WIDTH_Table_VISUALIZATION, HEIGHT_Table_VISUALIZATION));
		
			
		cancelOrderItemButton = new JButton();
		homeButton = new JButton();
	
		
		
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("Cancel Order Item");
		
		orderItemSelected.setText("Select orderItem: ");
		
		cancelOrderItemButton.setText("Cancel Order Item");
		cancelOrderItemButton.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent evt){
				cancelOrderItemButtonActionPerformed(evt);
			}
		});
		
		homeButton.setText("Home");
		homeButton.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent evt){
				homeButtonActionPerformed(evt);
			}
		});
		
		
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
				.addComponent(homeButton, 70,100,120)
				.addComponent(horizontalLineMiddle)
				
				.addGroup(layout.createSequentialGroup()
						
						.addComponent(orderItemSelected)
						.addGroup(layout.createParallelGroup()
								.addComponent(orderItemTF,50,50,100)
								.addComponent(cancelOrderItemButton, 70,70,140)))
						
				
				.addComponent(horizontalLineBottom)
				.addGroup(layout.createParallelGroup()
						.addGroup(layout.createParallelGroup()
						.addComponent(tableVisualizer)))
				);
		
		
		layout.setVerticalGroup(
				layout.createSequentialGroup()
				.addComponent(errorMessage)	
				.addComponent(horizontalLineTop)
				.addComponent(homeButton,70,100,120)
				.addComponent(horizontalLineMiddle)
				.addGroup(layout.createParallelGroup()
						.addComponent(orderItemSelected)
						.addComponent(orderItemTF))		
				
				.addComponent(cancelOrderItemButton,70,70,140)
				.addGroup(layout.createParallelGroup()
						.addComponent(horizontalLineBottom))
				.addGroup(layout.createParallelGroup()
						.addGroup(layout.createParallelGroup()
						.addComponent(tableVisualizer)))
				);
		
		pack();

	}
	
	//action after pressing the update table number button
	private void cancelOrderItemButtonActionPerformed(ActionEvent evt) {
		
		// clear error message
		/*error = null;
				
		// call the controller
		try {
			//int newTableNumber = Integer.parseInt(newTableNumberTextField.getText());
			Table table = (Table)tableList.getSelectedItem();
			//int newSeatCount = Integer.parseInt(updatedSeatsTextField.getText());
			OrderItem oi = (OrderItem) orderItemList.getSelectedItem();
			Controller.cancelOrderedItem(oi, table);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}*/
				
		// update visuals
		refreshData();
			
	}
	
	private void homeButtonActionPerformed(ActionEvent evt) {
		// clear error message
		error = null;
				
		new RestoHomePage().setVisible(true);
		
		// update visuals
		refreshData();
		this.setVisible(false);
			
	}	
	
	//refreshing the data after a new entry
	private void refreshData() {
		
		errorMessage.setText(error);
		if (error == null || error.length() == 0) {
			orderItemTF.setText("");
		}
		
		pack();
		
	}

	

}
