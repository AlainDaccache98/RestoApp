package ca.mcgill.ecse223.resto.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.Time;
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

import ca.mcgill.ecse223.resto.*;

public class ViewOrderPage extends JFrame {

	private static final long serialVersionUID = : -4426310869335015542L; 
	// UI elements
	private JLabel errorMessage;

	private JList tableList;
	private JList seatList;
	private Object selectTable[];
	private Object selectSeatNumber[];

	private JLabel selectTableLabel;
	private JLabel selectSeatNumberLabel;

	private JTextField showOrderTextField;

	private JButton viewOrderButton;
	private JButton homeButton;

	private String error = null;

	public ViewOrderPage() {
		initComponents();
		refreshData();
	}

	private void initComponents() {
    	
        // elements for error message
        errorMessage = new JLabel();
        errorMessage.setForeground(Color.RED);
        
        showOrderTextField = new JTextField();
        
        selectTableLabel = new JLabel();
        selectSeatNumberLabel= new JLabel();
        

        homeButton = new JButton();
        viewOrderButton = new JButton();
        
        //multi-select panel
        RestoApp r = RestoAppApplication.getRestoApp();
        ArrayList myTableList = new ArrayList();
        ArrayList mySeatList = new ArrayList();
        for(Table t : r.getCurrentTables()) {
        	if(t.hasOrders()) {
        		myTableList.add(String.valueOf(t.getNumber()));
        		for ( Seats s : r.getCurrentSeats) { 
            		mySeatList.add(String.valueOf(s.getNumber()));
        		}
        	}
        }
        tableList = new JList(myTableList.toArray());
        tableList.setVisibleRowCount(5);
        tableList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        
        seatList = new JList(mySeatList.toArray());
        seatList.setVisibleRowCount(5);
        seatList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        
        selectTable = null;
        selectSeatNumber = null;
               
        ListSelectionListener abcd = new ListSelectionListener() { 
        	
        	public void valueChanged(ListSelectionEvent listSelectionEvent) {
        		
                System.out.println("First index: " + listSelectionEvent.getFirstIndex());
                System.out.println("Last index: " + listSelectionEvent.getLastIndex());
                boolean adjust = listSelectionEvent.getValueIsAdjusting();
                
                if (!adjust) {
                	JList list = (JList) listSelectionEvent.getSource();
                	int selections[] = list.getSelectedIndices();
                	Object selectionValues[] = list.getSelectedValues();
                	selectTables = list.getSelectedValues();
                	for (int i = 0, n = selections.length; i < n; i++) {
                		if (i == 0) {
                			System.out.println(" Selections: ");
                		}
                		System.out.println(selections[i] + "/" + selectionValues[i] + " ");
                	}
                }
              }
        };
        
        tableList.addListSelectionListener(abcd);
        
        ListSelectionListener def = new ListSelectionListener() { 
        	
        	public void valueChanged(ListSelectionEvent listSelectionEvent) {
        		
                System.out.println("First index: " + listSelectionEvent.getFirstIndex());
                System.out.println("Last index: " + listSelectionEvent.getLastIndex());
                boolean adjust = listSelectionEvent.getValueIsAdjusting();
                
                if (!adjust) {
                	JList list2 = (JList) listSelectionEvent.getSource();
                	int selections[] = list2.getSelectedIndices();
                	Object selectionValues[] = list2.getSelectedValues();
                	selectSeatNumber = list2.getSelectedValues();
                	for (int i = 0, n = selections.length; i < n; i++) {
                		if (i == 0) {
                			System.out.println(" Selections: ");
                		}
                		System.out.println(selections[i] + "/" + selectionValues[i] + " ");
                	}
                }
              }
        };
        
        seatList.addListSelectionListener(def);
        
        
        // global settings and listeners
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Resto Management System");
        
        selectTableLabel.setText("Select Table: ");
        selectSeatNumberLabel.setText("Select Seat Number: ");
        
        viewOrderButton.setText("Vıew Order");
        viewOrderButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	viewOrderButtonActionPerformed(evt);
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
				.addComponent(homeButton)
				.addComponent(horizontalLineTop)	
				.addGroup(layout.createSequentialGroup()
						.addComponent(tableList))
						.addComponent(seatList))
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup()
								.addComponent(selectTableLabel)
								.addComponent(selectSeatNumberLabel)
						.addGroup(layout.createParallelGroup()
								.addComponent(showOrderTextField, 200, 200, 400)
								.addComponent(timeTextField, 200, 200, 400)
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup()
								.addComponent(viewOrderButton, 70,70,140)))
				.addComponent(horizontalLineBottom)
		
		layout.setVerticalGroup(
				layout.createSequentialGroup()
				.addComponent(errorMessage)	
				.addComponent(homeButton)
				.addGroup(layout.createParallelGroup()
						.addComponent(horizontalLineTop))
				.addGroup(layout.createParallelGroup()
				.addComponent(tableList))
				.addComponent(seatList));

        pack();
    }

	private void refreshData() {

		errorMessage.setText(error);
	}

	private void viewOrderButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// clear error message
		error = null;

		List<Table> tables = new ArrayList<Table>();
		for (int i = 0; i < selectTables.length; i++) {
			tables.add(Table.getWithNumber(Integer.parseInt(selectTables[i].toString())));
		}

		List<Seats> seats = new ArrayList<Seat>();
		for (int i = 0; i < selectSeatNumber.length; i++) {
			seats.add(Seat.getWithNumber(Integer.parseInt(selectSeatNumber[i].toString())));
		}

		try {
			Controller.getOrderItem(table);
			;
		} catch (Exception e) {
			e.printStackTrace();
		}
		// update visuals
		refreshData();
	}

	protected void homeButtonActionPerformed(ActionEvent evt) {
		new RestoHomePage().setVisible(true);
	}
}
