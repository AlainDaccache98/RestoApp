package ca.mcgill.ecse223.resto.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.WindowConstants;
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

	private JComboBox<Order> orderList;
	private JLabel orderNumberSelected;
	
	private TableVisualizer tableVisualizer;
	
	private JButton endOrderButton;
    private JButton homeButton;

	private String error = null;

	private Integer selectedOrderNumber = -1;
	private ArrayList<Order> orders;
	

	
	private static final int WIDTH_Table_VISUALIZATION = 200;
	private static final int HEIGHT_Table_VISUALIZATION = 200;
	

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
		
		//tableVisualizer.setMinimumSize(new Dimension(WIDTH_Table_VISUALIZATION, HEIGHT_Table_VISUALIZATION));
		
		tableVisualizer = new TableVisualizer();
				
		//comboBox for selecting from the existing tables 
		orderList = new JComboBox<Order>();
//		System.out.println(tableList.getItemCount());
		completeOrderList(orderList);
//		System.out.println(tableList.getItemCount());
		
        // global settings and listeners
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("resto Management System");

		orderList.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt){ 
				JComboBox<Order> cb   = (JComboBox<Order>) evt.getSource();
				selectedOrderNumber = cb.getSelectedIndex();
			}
		});
		
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("RestoApp");
		
		orderNumberSelected.setText("Select order: ");
		
		endOrderButton = new JButton();
        homeButton = new JButton();

		endOrderButton.setText("End Order");
		
		endOrderButton.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent evt){
				endOrderButtonActionPerformed(evt);
			}
		});

		
        homeButton.setText("Home");
        homeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
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
				.addGroup(layout.createParallelGroup()
						)
				
				.addComponent(errorMessage)
				.addComponent(horizontalLineTop)
				.addComponent(homeButton)
				.addComponent(horizontalLineMiddle)
				.addComponent(horizontalLineBottom)
				.addGroup(layout.createSequentialGroup()
					.addGroup(layout.createParallelGroup()
							.addComponent(orderNumberSelected))
					.addGroup(layout.createParallelGroup()
							.addComponent(orderList, 70,70,140)
							.addComponent(endOrderButton, 70,70,140)))
				.addGroup(layout.createParallelGroup()
						.addComponent(tableVisualizer)));
		
		layout.setVerticalGroup(
				layout.createSequentialGroup()
					.addComponent(errorMessage)	
					.addComponent(homeButton)
				.addGroup(layout.createParallelGroup()
						.addComponent(horizontalLineTop))
				.addGroup(layout.createParallelGroup()
						.addComponent(horizontalLineMiddle))
				.addGroup(layout.createParallelGroup()
						.addComponent(orderNumberSelected)
						.addComponent(orderList))
				.addGroup(layout.createParallelGroup()
						.addComponent(horizontalLineMiddle))
				.addGroup(layout.createParallelGroup()
						.addComponent(endOrderButton))		
				.addGroup(layout.createParallelGroup()
						.addComponent(horizontalLineBottom))
				.addGroup(layout.createParallelGroup()
						.addComponent(tableVisualizer))
						);
		pack();

	}
	
	private void completeOrderList(JComboBox<Order> orders) {
		// TODO Auto-generated method stub
		RestoAppApplication.load();
		RestoApp r = RestoAppApplication.getRestoApp();

		for(Order order : r.getOrders()){
			orderList.add(("# " + order.getNumber()), orderList);
		}
	}

	//action after pressing the update table number button
	private void endOrderButtonActionPerformed(ActionEvent evt) {
		
		// clear error message
		error = null;
				
		// call the controller
		try {
			Order order = null;
			Controller.endOrder(order);;
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
			
			//update the combo box
			orders = new ArrayList<Order>();
			orderList.removeAllItems();
			Integer index = 0;
			
			RestoApp r = RestoAppApplication.getRestoApp();
			
			for(Order order : r.getOrders()){
				orders.add(order);
				index++;
			}
			
			completeOrderList(orderList);
			
			selectedOrderNumber = -1;
			orderList.setSelectedIndex(selectedOrderNumber);
			
		}
		
		pack();
		
	}
	
    protected void homeButtonActionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub
    	new RestoHomePage().setVisible(true);
	}
	

}