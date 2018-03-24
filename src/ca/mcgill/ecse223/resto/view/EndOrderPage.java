package ca.mcgill.ecse223.resto.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.util.Date;
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

import ca.mcgill.ecse223.resto.application.RestoAppApplication;
import ca.mcgill.ecse223.resto.controller.Controller;
import ca.mcgill.ecse223.resto.controller.InvalidInputException;
import ca.mcgill.ecse223.resto.model.Order;
import ca.mcgill.ecse223.resto.model.RestoApp;
import ca.mcgill.ecse223.resto.model.Table;

public class EndOrderPage extends JFrame {

	//private JPanel contentPane;
	private static final long serialVersionUID = -4426310869335015542L;
	
	private JLabel errorMessage;

	private JComboBox<String> orderList;
	private JLabel orderNumberSelected;
	
	private JButton endOrderButton;
	private JButton homeButton;
    
    private TableVisualizer tableVisualizer;
    
	private static final int WIDTH_Table_VISUALIZATION = 200;
	private static final int HEIGHT_Table_VISUALIZATION = 200;
	
	//temp elements
	//private JLabel hint1;
	//private JLabel hint2;

	private String error = null;

	private Integer selectedOrderNumber = -1;
	private HashMap<Integer, Order> orders;


	/**
	 * Create the frame.
	 */
	public EndOrderPage() {
		initComponents();
		refreshData();
	}
	
	private void initComponents() {
		
		errorMessage = new JLabel();
		errorMessage.setForeground(Color.RED);
		//label for selecting table whose features are to be updated
		orderNumberSelected = new JLabel();
		
		homeButton = new JButton();
		tableVisualizer = new TableVisualizer();
		tableVisualizer.setMinimumSize(new Dimension(WIDTH_Table_VISUALIZATION, HEIGHT_Table_VISUALIZATION));
		
		//comboBox for selecting from the existing tables 
		orderList = new JComboBox<String>(new String[0]);
		orderList.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt){ 
				JComboBox<String> cb   = (JComboBox<String>) evt.getSource();
				selectedOrderNumber = cb.getSelectedIndex();
			}
		});
		
		//UI elements for updating table number
		endOrderButton = new JButton();

		
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("RestoApp");
		
		orderNumberSelected.setText("Select order: ");
		
		endOrderButton.setText("End Order");
		homeButton.setText("Home");
		
		endOrderButton.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent evt){
				endOrderButtonActionPerformed(evt);
			}
		});
		
		
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
				.addComponent(homeButton)
				.addComponent(horizontalLineBottom)
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup()
								.addComponent(orderNumberSelected))
						.addGroup(layout.createParallelGroup()
								.addComponent(orderList, 70, 140, 140)
								.addComponent(endOrderButton, 70,70,140))
						.addGroup(layout.createParallelGroup()
								.addGroup(layout.createParallelGroup()
								.addComponent(tableVisualizer)))
				
				));
		
		/*layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {newTableNumberTextField, newTableNumberButton});

		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {updatedSeatsTextField, updatedSeatsButton});*/

		
		layout.setVerticalGroup(
				layout.createSequentialGroup()
				.addComponent(errorMessage)	
				.addComponent(homeButton)
				.addGroup(layout.createParallelGroup()
						.addComponent(orderNumberSelected)
						.addComponent(orderList, 20, 30, 30))		
				.addGroup(layout.createParallelGroup()
						.addComponent(endOrderButton))
				.addGroup(layout.createParallelGroup()
						/*.addComponent(updatedSeatsButton))*/
				.addGroup(layout.createParallelGroup()
						.addComponent(horizontalLineBottom))
				.addGroup(layout.createSequentialGroup()
						.addComponent(tableVisualizer))
						));
		pack();

	}
	
	//action after pressing the update table number button
	private void endOrderButtonActionPerformed(ActionEvent evt) {
		
		// clear error message
		error = null;
				
//		// call the controller
		try {
			String object = (String)orderList.getSelectedItem();
			System.out.println("OBJ: " + object);
			//String number = object.substring(1,3);
			//System.out.println(number);
			int orderNum = Integer.parseInt(object);
			//System.out.println(originalTableNumber);
			
			RestoApp r = RestoAppApplication.getRestoApp();

			Order order = null;
			
			for(Order o : r.getCurrentOrders()) {
				if(o.getNumber() == orderNum) {
					order = o;
				}
			}
			
			System.out.println("OrderNumber: " + order.getNumber());
			System.out.println(order);
			
			Controller.endOrder(order);;
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
			orders = new HashMap<Integer, Order>();
			orderList.removeAllItems();
			Integer index = 0;
			
			RestoApp r = RestoAppApplication.getRestoApp();
			
			System.out.println(r.getCurrentOrders().size() + "ewhheudehkddhkwa");
			for(Order order : r.getCurrentOrders()){
				orders.put(index, order);
				orderList.addItem("" + order.getNumber());
				index++;
			}
			
			selectedOrderNumber = -1;
			orderList.setSelectedIndex(selectedOrderNumber);
			
		}
		
		pack();
		
	}

	

}