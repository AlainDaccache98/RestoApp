package ca.mcgill.ecse223.resto.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Scanner;

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
import ca.mcgill.ecse223.resto.model.PricedMenuItem;
import ca.mcgill.ecse223.resto.model.Seat;
import ca.mcgill.ecse223.resto.model.Table;

public class CancelOrderedItemPage extends JFrame {

	//private JPanel contentPane;
	private static final long serialVersionUID = -7826310869335015542L;
	
	private JLabel errorMessage;
	private JLabel orderItemSelected;
	private JLabel tableAndSeatSelected;
	private JComboBox<String> pricedMenuItemList;
	private JComboBox<String> tableAndSeatList;
	private JButton cancelOrderItemButton;
	private JButton homeButton;
	
    private TableVisualizerForCancelOI tableVisualizerForCancelOI;
    
	private static final int WIDTH_Table_VISUALIZATION = 200;
	private static final int HEIGHT_Table_VISUALIZATION = 200;


	private String error = null;

	private Integer selectedPricedMenuItem = -1;
	private HashMap<Integer, PricedMenuItem> pmi ;
	
	private Integer selectedTableAndSeat = -1;
	private HashMap<Integer, Table> tables ;
	private HashMap<Integer, Seat> seats ;
	
	
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
		
		tableAndSeatSelected = new JLabel();
		pricedMenuItemList = new JComboBox<String>(new String[0]);
		
		pricedMenuItemList.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt){ 
				JComboBox<String> cb   = (JComboBox<String>) evt.getSource();
				selectedPricedMenuItem = cb.getSelectedIndex();
			}
		});
		
		tableAndSeatList = new JComboBox<String>(new String[0]);

		tableAndSeatList.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt){ 
				JComboBox<String> cb   = (JComboBox<String>) evt.getSource();
				selectedTableAndSeat = cb.getSelectedIndex();
			}
		});
		
		tableVisualizerForCancelOI = new TableVisualizerForCancelOI();
		tableVisualizerForCancelOI.setMinimumSize(new Dimension(WIDTH_Table_VISUALIZATION, HEIGHT_Table_VISUALIZATION));
		
			
		cancelOrderItemButton = new JButton();
		homeButton = new JButton();
	
		
		
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("Cancel Order Item");
		
		orderItemSelected.setText("Select priced menu item: ");
		tableAndSeatSelected.setText("Select table and seat number: ");
		
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
						
						.addGroup(layout.createParallelGroup()
								.addComponent(orderItemSelected)
								.addComponent(tableAndSeatSelected))
						.addGroup(layout.createParallelGroup()
								.addComponent(pricedMenuItemList)
								.addComponent(tableAndSeatList)
								.addComponent(cancelOrderItemButton, 70,70,140)))
						
				
				.addComponent(horizontalLineBottom)
				.addGroup(layout.createParallelGroup()
						.addGroup(layout.createParallelGroup()
						.addComponent(tableVisualizerForCancelOI)))
				);
		
		
		layout.setVerticalGroup(
				layout.createSequentialGroup()
				.addComponent(errorMessage)	
				.addComponent(horizontalLineTop)
				.addComponent(homeButton,70,100,120)
				.addComponent(horizontalLineMiddle)
				.addGroup(layout.createParallelGroup()
						.addComponent(orderItemSelected)
						.addComponent(pricedMenuItemList))		
				.addGroup(layout.createParallelGroup()
						.addComponent(tableAndSeatSelected)
						.addComponent(tableAndSeatList))		

				.addComponent(cancelOrderItemButton,70,70,140)
				.addGroup(layout.createParallelGroup()
						.addComponent(horizontalLineBottom))
				.addGroup(layout.createParallelGroup()
						.addGroup(layout.createParallelGroup()
						.addComponent(tableVisualizerForCancelOI)))
				);
		
		pack();

	}
	
	//action after pressing the update table number button
	private void cancelOrderItemButtonActionPerformed(ActionEvent evt) {
		
		// clear error message
		error = null;
				
		// call the controller
		/*try {
			PricedMenuItem pmi = (PricedMenuItem)pricedMenuItemList.getSelectedItem();
			
			String tableAndSeat = (String) tableAndSeatList.getSelectedItem();
			
			Scanner in = new Scanner(tableAndSeat).useDelimiter("[^0-9]+");
			int tableNumber = in.nextInt();
			
			int currentSeatNumber = in.nextInt();
			
			Table currentTable = Table.getWithNumber(tableNumber);
			Order currentOrder = Table.getWithNumber(tableNumber).getCurrentOrder();
			OrderItem oi = (OrderItem) orderItemList.getSelectedItem();
			Controller.cancelOrderedItem(oi, table);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
				
		// update visuals
		refreshData();*/
			
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
			//update the combo box
			pmi = new HashMap<Integer, PricedMenuItem>();
			pricedMenuItemList.removeAllItems();
			Integer index1 = 0;
			
			for(PricedMenuItem item : Controller.getCurrentPricedMenuItems()){
				pmi.put(index1, item);
				pricedMenuItemList.addItem(item.getMenuItem().getName());
				index1++;
			}
			
			selectedPricedMenuItem = -1;
			pricedMenuItemList.setSelectedIndex(selectedPricedMenuItem);
			
			Integer index2 = 0;
			Integer tableCounter = 0;

			seats = new HashMap<Integer, Seat>();
			tables = new HashMap<Integer, Table>();

			for(Table table: Controller.getCurrentTables()){
				index2 = 0;
				tableAndSeatList.addItem("Table " + table.getNumber() + " and no seat");
				for(Seat seat: table.getCurrentSeats()){
					seats.put(index2, seat);
					tableAndSeatList.addItem("Table " + table.getNumber() + " and seat " + index2);
					index2++;
				}
				tables.put(tableCounter, table);
				tableCounter++;
			}
			
			selectedTableAndSeat = -1;
			tableAndSeatList.setSelectedIndex(selectedTableAndSeat);
		}
		
		pack();
		
	}

	

}
