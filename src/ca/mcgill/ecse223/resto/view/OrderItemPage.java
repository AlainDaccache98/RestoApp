package ca.mcgill.ecse223.resto.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ca.mcgill.ecse223.resto.application.RestoAppApplication;

//import org.jdatepicker.impl.JDatePanelImpl;
//import org.jdatepicker.impl.JDatePickerImpl;
//import org.jdatepicker.impl.SqlDateModel;

import ca.mcgill.ecse223.resto.controller.Controller;
import ca.mcgill.ecse223.resto.controller.InvalidInputException;
import ca.mcgill.ecse223.resto.model.MenuItem;
import ca.mcgill.ecse223.resto.model.RestoApp;
import ca.mcgill.ecse223.resto.model.Seat;
import ca.mcgill.ecse223.resto.model.Table;
import ca.mcgill.ecse223.resto.model.MenuItem.ItemCategory;

public class OrderItemPage extends JFrame {

  private static final long serialVersionUID = -4426310869335015543L;

  // UI elements
  private JLabel errorMessage;
	private JLabel selectTableLabel;
	private JLabel selectSeatLabel;
	
	private JLabel qtyLabel;
	private JTextField qtyTextField;

  //private List<JCheckBox> checkBoxList;	
  private JList displaySeatsList;
  private Object[] selectedSeats;  
  
  //table display
	private JComboBox<String> tableList;
	private Integer selectedTableNumber = -1;
	private HashMap<Integer, Table> tables;
	//table
	
  private Table currentSelectedTable;
  private List<Seat> listSelectedSeats;
  
	private JComboBox<MenuItem> menuItemList;
	
	private Integer selectedMenuItem = -1;
	private HashMap<Integer, String> menuItems;
  
  private JButton orderItemButton;
  private JButton selectTableButton;
  private JButton selectSeatButton;
  private JButton homeButton;

  private TableVisualizer tableVisualizer;

  private static final int WIDTH_Table_VISUALIZATION = 200;
  private static final int HEIGHT_Table_VISUALIZATION = 200;


  private String error = null;

  public OrderItemPage() {
    initComponents();
    this.setSize(1400, 500);

    refreshData();
  }

  private void initComponents() {

    // elements for error message
    errorMessage = new JLabel();
    errorMessage.setForeground(Color.RED);
    
    RestoApp r = RestoAppApplication.getRestoApp();
    
    //table display
    currentSelectedTable = r.getCurrentTable(0);

    
    	//comboBox for selecting from the existing tables 
  		tableList = new JComboBox<String>(new String[0]);
  		tableList.addActionListener(new java.awt.event.ActionListener() {
  			public void actionPerformed(java.awt.event.ActionEvent evt){ 
  				JComboBox<String> cb   = (JComboBox<String>) evt.getSource();
  				selectedTableNumber = cb.getSelectedIndex();
  			}
  		});
  		
  	    menuItemList = new JComboBox<MenuItem>();
  		menuItemList.addActionListener(new java.awt.event.ActionListener() {
  			public void actionPerformed(java.awt.event.ActionEvent evt){ 
  				JComboBox<String> cb   = (JComboBox<String>) evt.getSource();
  				selectedMenuItem = cb.getSelectedIndex();
  			}
  		});
  		
  		//table
    
    
    selectTableLabel = new JLabel();
    selectSeatLabel = new JLabel();
    
    listSelectedSeats = new ArrayList<Seat>();

    tableVisualizer = new TableVisualizer();
    tableVisualizer.setMinimumSize(new Dimension(WIDTH_Table_VISUALIZATION, HEIGHT_Table_VISUALIZATION));

    qtyTextField = new JTextField();
    qtyLabel = new JLabel();
    
    homeButton = new JButton();
    orderItemButton = new JButton();
    selectTableButton = new JButton();
    selectSeatButton = new JButton();
    

//    ArrayList myList = new ArrayList();
//      for(Seat s: currentSelectedTable.getSeats()) {
//        myList.add(s);
//      }    
    
    qtyLabel.setText("Quantity");
    
    ArrayList<Seat> myList = new ArrayList<Seat>();
    for(Seat s: currentSelectedTable.getSeats()) {
      myList.add(s);
    }   
  
    displaySeatsList = new JList<Object>(myList.toArray());

    displaySeatsList.setVisibleRowCount(5);
    displaySeatsList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

    selectedSeats = null;

    ListSelectionListener abcde = new ListSelectionListener() {

      public void valueChanged(ListSelectionEvent listSelectionEvent) {

//        System.out.println("First index: " + listSelectionEvent.getFirstIndex());
//        System.out.println("Last index: " + listSelectionEvent.getLastIndex());
        boolean adjust = listSelectionEvent.getValueIsAdjusting();

        if (!adjust) {
          JList<?> list = (JList<?>) listSelectionEvent.getSource();
          int selections[] = list.getSelectedIndices();
          Object[] selectionValues = list.getSelectedValues();
          selectedSeats = list.getSelectedValues();
          for (int i = 0, n = selections.length; i < n; i++) {
            if (i == 0) {
//              System.out.println(" Selections: ");
            }
//            System.out.println(selections[i] + "/" + selectionValues[i] + " ");
          }
        }
      }
    };

    displaySeatsList.addListSelectionListener(abcde);
    
    


    // global settings and listeners
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setTitle("resto Management System");

    orderItemButton.setText("Order Item");
    orderItemButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        orderItemButtonActionPerformed(evt);
      }
    });

    homeButton.setText("Home");
    homeButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        homeButtonActionPerformed(evt);
      }
    });
    
    selectTableButton.setText("Select Table");
    selectTableButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
    	  selectTableButtonActionPerformed(evt);
      }
    });
    
    selectSeatButton.setText("Select Seat");
    selectSeatButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
    	  selectSeatButtonActionPerformed(evt);
      }
    });

    // horizontal line elements
    JSeparator horizontalLineTop = new JSeparator();
    JSeparator horizontalLineMiddle = new JSeparator();
    JSeparator horizontalLineBottom = new JSeparator();
    // layout
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
            .addGroup(layout.createParallelGroup()
            		.addComponent(qtyLabel)
            		.addComponent(selectTableLabel)
            		.addComponent(selectSeatLabel))
            .addGroup(layout.createParallelGroup()
            	.addComponent(qtyTextField)
            	.addComponent(tableList)
                .addComponent(displaySeatsList)
                .addComponent(menuItemList))
            .addGroup(layout.createParallelGroup()
            		.addComponent(selectTableButton)
                    .addComponent(selectSeatButton))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup()
                    .addComponent(orderItemButton, 70,70,140)))
            .addComponent(horizontalLineBottom)
            .addGroup(layout.createParallelGroup()
                .addGroup(layout.createParallelGroup()
                    .addComponent(tableVisualizer))));

        layout.setVerticalGroup(
            layout.createSequentialGroup()
            .addComponent(errorMessage)	
            .addComponent(horizontalLineTop)
            .addComponent(homeButton)
            .addGroup(layout.createParallelGroup()
                .addComponent(horizontalLineMiddle))
            .addComponent(selectTableLabel)
            .addComponent(tableList)
            .addGroup(layout.createParallelGroup()
                    .addComponent(selectTableButton))
            .addGroup(layout.createParallelGroup()
            	.addComponent(selectSeatLabel)
                .addComponent(displaySeatsList))
            .addComponent(selectSeatButton)
            .addGroup(layout.createParallelGroup()
            		.addComponent(qtyLabel)
            		.addComponent(qtyTextField))
    		.addComponent(menuItemList)
            .addGroup(layout.createParallelGroup()
                .addComponent(orderItemButton))
            .addComponent(horizontalLineBottom)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createSequentialGroup()
                    .addComponent(tableVisualizer)))
        );

    pack();
  }

  private void refreshData() {
		
		errorMessage.setText(error);
		if (error == null || error.length() == 0) {

			
			//update the combo box
			tables = new HashMap<Integer, Table>();
			tableList.removeAllItems();
			Integer index = 0;
			
			qtyTextField.setText("");
			
			
			//********************************************************************
			//change currentTables to active tables
			
			selectedMenuItem = -1;
			menuItemList.setSelectedIndex(selectedMenuItem);


			menuItems = new HashMap<Integer, String>();
			menuItemList.removeAllItems();
			Integer index1 = 0;

			for (ItemCategory category : Controller.getItemCategories()) {

				try {
					for(MenuItem item : Controller.getMenuItems(category)){
						menuItems.put(index1, item.getName());
						menuItemList.addItem(item);
						index1++;
					}
				} catch (InvalidInputException e) {
					// TODO Auto-generated catch block
					error = e.getMessage();
				}
			}
			
			
			for(Table table : Controller.getCurrentTables()){
				tables.put(index, table);
				tableList.addItem("" + table.getNumber());
				index++;
			}
			
			selectedTableNumber = -1;
			tableList.setSelectedIndex(selectedTableNumber);
			//table part finished
			
			
		    RestoApp r = RestoAppApplication.getRestoApp();
		    
		    displaySeatsList.removeAll();

		    ArrayList<Seat> myList2 = new ArrayList<Seat>();
		    int i =1;
		      for(Seat s: currentSelectedTable.getSeats()) {
		        myList2.add(s);
		      }
		      
		      displaySeatsList.setListData(myList2.toArray());
			
		}
		
		pack();
  }


  private void orderItemButtonActionPerformed(java.awt.event.ActionEvent evt) {
    // clear error message
    error = null;
    
//	String menuItemName = menuItemList.getSelectedItem().toString();
//	System.out.println(menuItemName + "fweutbn");	
//	MenuItem menuItem = MenuItem.getWithName(menuItemName);
//	System.out.println("aoaiw"+menuItem);
    
	MenuItem menuItem = (MenuItem) menuItemList.getSelectedItem();
	
	int qty = Integer.parseInt(qtyTextField.getText());

    System.out.println("lalalaladuaiudwibuawiub" + menuItem.getName() + "*" + qty + "-Seats: " + listSelectedSeats.size());
	
    try {
      Controller.orderItem(menuItem, qty, listSelectedSeats);;
    } catch (Exception e) {
      // TODO Auto-generated catch block
    	e.printStackTrace();
      error = e.getMessage();
    }

    // update visuals
    refreshData();
  }
  
  private void selectTableButtonActionPerformed(java.awt.event.ActionEvent evt) {
	    // clear error message
	    error = null;
	    
		String object = tableList.getSelectedItem().toString();
		//System.out.println(object);
		int tableNumber = Integer.parseInt(object);
		//System.out.println(originalTableNumber);
		currentSelectedTable = Table.getWithNumber(tableNumber);	    
//	    System.out.println("Tableselected=" + currentSelectedTable.getNumber());
		
	    // update visuals
	    refreshData();
	  }
  
  private void selectSeatButtonActionPerformed(java.awt.event.ActionEvent evt) {
	    // clear error message
	    error = null;

	    for(int i = 0; i< selectedSeats.length; i++) {
	    	listSelectedSeats.add((Seat) selectedSeats[i]);
	    }
	    // update visuals
	    refreshData();
	  }

  protected void homeButtonActionPerformed(ActionEvent evt) {
    // TODO Auto-generated method stub
    new RestoHomePage().setVisible(true);
    this.setVisible(false);
  }
}
