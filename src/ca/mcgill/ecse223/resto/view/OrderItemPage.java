package ca.mcgill.ecse223.resto.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.ListModel;
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
import ca.mcgill.ecse223.resto.model.RestoApp;
import ca.mcgill.ecse223.resto.model.Seat;
import ca.mcgill.ecse223.resto.model.Table;

public class OrderItemPage extends JFrame {

  private static final long serialVersionUID = -4426310869335015543L;

  // UI elements
  private JLabel errorMessage;

  //private List<JCheckBox> checkBoxList;
  private JList displayList;
  private Object selectedSeats[];   
  
  private JLabel selectSeatLabel;
  
  private JLabel qtyLabel;
  private JTextField qtyTextField;
  
  private JButton selectItemButton; 

  private JButton orderItemButton;
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

    qtyLabel = new JLabel();
    qtyTextField = new JTextField();
    
    selectSeatLabel = new JLabel();
    
    tableVisualizer = new TableVisualizer();
    tableVisualizer.setMinimumSize(new Dimension(WIDTH_Table_VISUALIZATION, HEIGHT_Table_VISUALIZATION));

    selectItemButton = new JButton();
    homeButton = new JButton();
    orderItemButton = new JButton();

    RestoApp r = RestoAppApplication.getRestoApp();
    ArrayList myList = new ArrayList();
    for(Table t: r.getTables()) { 
      for(Seat s: t.getSeats()) {
        myList.add(s);
      }    
    }
    displayList = new JList(myList.toArray());

    displayList.setVisibleRowCount(5);
    displayList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

    selectedSeats = null;

    ListSelectionListener abcd = new ListSelectionListener() {

      public void valueChanged(ListSelectionEvent listSelectionEvent) {

        System.out.println("First index: " + listSelectionEvent.getFirstIndex());
        System.out.println("Last index: " + listSelectionEvent.getLastIndex());
        boolean adjust = listSelectionEvent.getValueIsAdjusting();

        if (!adjust) {
          JList list = (JList) listSelectionEvent.getSource();
          int selections[] = list.getSelectedIndices();
          Object selectionValues[] = list.getSelectedValues();
          selectedSeats = list.getSelectedValues();
          for (int i = 0, n = selections.length; i < n; i++) {
            if (i == 0) {
              System.out.println(" Selections: ");
            }
            System.out.println(selections[i] + "/" + selectionValues[i] + " ");
          }
        }
      }
    };

    displayList.addListSelectionListener(abcd);


    // global settings and listeners
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setTitle("resto Management System");

    qtyLabel.setText("Quantity:");
    selectSeatLabel = new JLabel();

    selectItemButton.setText("Select Item");
    selectItemButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        selectItemButtonActionPerformed(evt);
      }
    });
    
    orderItemButton.setText("Order");
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
        .addGroup(layout.createSequentialGroup()
        		.addComponent(selectSeatLabel)
                .addComponent(qtyLabel))
        .addGroup(layout.createSequentialGroup()
            .addComponent(displayList)
            .addComponent(qtyTextField))
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
        .addGroup(layout.createParallelGroup()
        	.addComponent(selectSeatLabel)
            .addComponent(displayList))
        .addGroup(layout.createParallelGroup()
        		.addComponent(qtyLabel)
                .addComponent(qtyTextField))
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
	  qtyTextField.setText("");
	  errorMessage.setText(error);            
  }


  private void orderItemButtonActionPerformed(java.awt.event.ActionEvent evt) {
    // clear error message
    error = null;

    List<Seat> seats = new ArrayList<Seat>();

    for(int i = 0; i< selectedSeats.length; i++) {
      seats.add((Seat)selectedSeats[i]);
          //Table.getWithNumber(Integer.parseInt(selectedSeats[i].toString())));
    }
    
    int quantity = Integer.parseInt(qtyTextField.getText());
    
    try {
      //Controller.orderItem(item, quantity, seats););
    } catch (Exception e) {
      // TODO Auto-generated catch block
      error = e.getMessage();
    }

    // update visuals
    refreshData();
  }
  
  private void selectItemButtonActionPerformed(java.awt.event.ActionEvent evt) {
	  
	  	//INCOMPLETE*****************************************************************
	  
	    // update visuals
	    refreshData();
	  }

  protected void homeButtonActionPerformed(ActionEvent evt) {
    // TODO Auto-generated method stub
    new RestoHomePage().setVisible(true);
    this.setVisible(false);
  }
}
