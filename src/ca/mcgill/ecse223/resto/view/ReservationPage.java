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

import ca.mcgill.ecse223.resto.application.RestoAppApplication;

//import org.jdatepicker.impl.JDatePanelImpl;
//import org.jdatepicker.impl.JDatePickerImpl;
//import org.jdatepicker.impl.SqlDateModel;

import ca.mcgill.ecse223.resto.controller.Controller;
import ca.mcgill.ecse223.resto.controller.InvalidInputException;
import ca.mcgill.ecse223.resto.model.RestoApp;
import ca.mcgill.ecse223.resto.model.Table;

public class ReservationPage extends JFrame {
    
    private static final long serialVersionUID = -4426310869335015542L;
    
    // UI elements
    private JLabel errorMessage;
    
    //private List<JCheckBox> checkBoxList;
    private JList displayList;
    private Object selectedTables[];   
    
    private JTextField dateTextField;
    private JLabel dateLabel;
    
    private JTextField timeTextField;
    private JLabel timeLabel;
    
    private JTextField numberInPartyTextField;
    private JLabel numberInPartyLabel;
    
    private JTextField contactNameTextField;
    private JLabel contactNameLabel;
    
    private JTextField contactEmailTextField;
    private JLabel contactEmailLabel;
    
    private JTextField contactPhoneTextField;
    private JLabel contactPhoneLabel;
    
    private JButton reserveTableButton;
    private JButton homeButton;
    
    private TableVisualizer tableVisualizer;
    
	private static final int WIDTH_Table_VISUALIZATION = 200;
	private static final int HEIGHT_Table_VISUALIZATION = 200;

	
	private String error = null;

    public ReservationPage() {
        initComponents();
        refreshData();
    }
    
    private void initComponents() {
    	
        // elements for error message
        errorMessage = new JLabel();
        errorMessage.setForeground(Color.RED);
        
        numberInPartyTextField = new JTextField();
        numberInPartyLabel = new JLabel();
        
        contactNameTextField = new JTextField();
        contactNameLabel = new JLabel();
        
        contactEmailTextField = new JTextField();
        contactEmailLabel = new JLabel();
        
        contactPhoneTextField = new JTextField();
        contactPhoneLabel = new JLabel();
        
        dateTextField = new JTextField();
        dateLabel = new JLabel();
        
        timeTextField = new JTextField();
        timeLabel = new JLabel();
        
		tableVisualizer = new TableVisualizer();
		tableVisualizer.setMinimumSize(new Dimension(WIDTH_Table_VISUALIZATION, HEIGHT_Table_VISUALIZATION));
        
        homeButton = new JButton();
        reserveTableButton = new JButton();
        
        //multi-select panel
        RestoApp r = RestoAppApplication.getRestoApp();
        ArrayList myList = new ArrayList();
        for(Table t : r.getTables()) {
        	if(!t.hasOrders()) {
        		myList.add(String.valueOf(t.getNumber()));
        	}
        }
        displayList = new JList(myList.toArray());
        displayList.setVisibleRowCount(5);
        displayList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        
        selectedTables = null;
        
        ListSelectionListener abcd = new ListSelectionListener() {
        	
        	public void valueChanged(ListSelectionEvent listSelectionEvent) {
        		
                System.out.println("First index: " + listSelectionEvent.getFirstIndex());
                System.out.println("Last index: " + listSelectionEvent.getLastIndex());
                boolean adjust = listSelectionEvent.getValueIsAdjusting();
                //System.out.println(", Adjusting? " + adjust);
                
                if (!adjust) {
                	JList list = (JList) listSelectionEvent.getSource();
                	int selections[] = list.getSelectedIndices();
                	Object selectionValues[] = list.getSelectedValues();
                	selectedTables = list.getSelectedValues();
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
        
        numberInPartyLabel.setText("Number In Party: ");
        contactNameLabel.setText("Contact Name: ");
        contactEmailLabel.setText("Contact Email Address: ");
        contactPhoneLabel.setText("Contact Phone Number: ");
        dateLabel.setText("Date (dd/mm/yy): ");
        timeLabel.setText("Time (hh.mm.ss): ");
        
        reserveTableButton.setText("Start Order");
        reserveTableButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	startOrderButtonActionPerformed(evt);
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
//				.addGroup(layout.createSequentialGroup()
//						.addComponent((Component) checkBoxList))
				.addGroup(layout.createSequentialGroup()
				.addComponent(displayList))
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup()
								.addComponent(dateLabel)
								.addComponent(timeLabel)
								.addComponent(numberInPartyLabel)
								.addComponent(contactNameLabel)
								.addComponent(contactPhoneLabel)
								.addComponent(contactEmailLabel))
						.addGroup(layout.createParallelGroup()
								.addComponent(dateTextField, 200, 200, 400)
								.addComponent(timeTextField, 200, 200, 400)
								.addComponent(numberInPartyTextField,200,200,400)
								.addComponent(contactNameTextField,200,200,400)
								.addComponent(contactPhoneTextField,200,200,400)
								.addComponent(contactEmailTextField,200,200,400)))
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup()
								.addComponent(reserveTableButton, 70,70,140)))
				.addComponent(horizontalLineBottom)
				.addGroup(layout.createParallelGroup()
						.addGroup(layout.createParallelGroup()
								.addComponent(tableVisualizer))));
		
		layout.setVerticalGroup(
				layout.createSequentialGroup()
				.addComponent(errorMessage)	
				.addComponent(homeButton)
				.addGroup(layout.createParallelGroup()
						.addComponent(horizontalLineTop))
//				.addGroup(layout.createParallelGroup()
//						.addComponent((Component) checkBoxList))
				.addGroup(layout.createParallelGroup()
				.addComponent(displayList))
				.addGroup(layout.createParallelGroup()
						.addComponent(dateLabel)
						.addComponent(dateTextField))
				.addGroup(layout.createParallelGroup()
						.addComponent(timeLabel)
						.addComponent(timeTextField))
				.addGroup(layout.createParallelGroup()
						.addComponent(numberInPartyLabel)
						.addComponent(numberInPartyTextField))
				.addGroup(layout.createParallelGroup()
						.addComponent(contactNameLabel)
						.addComponent(contactNameTextField))
				.addGroup(layout.createParallelGroup()
						.addComponent(contactPhoneLabel)
						.addComponent(contactPhoneTextField))
				.addGroup(layout.createParallelGroup()
						.addComponent(contactEmailLabel)
						.addComponent(contactEmailTextField))
				.addGroup(layout.createParallelGroup()
						.addComponent(reserveTableButton))
				.addComponent(horizontalLineBottom)
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createSequentialGroup()
						.addComponent(tableVisualizer)))
				);

        pack();
    }
    
	private void refreshData() {
		
		
		errorMessage.setText(error);            
    }
	
			private void startOrderButtonActionPerformed(java.awt.event.ActionEvent evt) {
    		// clear error message
    		error = null;
    		
    		List<Table> tables = new ArrayList<Table>();
    		for(int i = 0; i< selectedTables.length; i++) {
    			tables.add(Table.getWithNumber(Integer.parseInt(selectedTables[i].toString())));
    		}
    		
    		int day = Integer.parseInt(dateTextField.getText().substring(0,  2));
    		int month = Integer.parseInt(dateTextField.getText().substring(3,  5));
    		int year = Integer.parseInt(dateTextField.getText().substring(6,  8));

    		int hour = Integer.parseInt(timeTextField.getText().substring(0, 2));
    		int min = Integer.parseInt(timeTextField.getText().substring(3, 5));
    		int sec = Integer.parseInt(timeTextField.getText().substring(6, 8));    		
    		
    		Date date = new Date(year, month, day);    		
    		Time time = new Time(hour, min, sec);;
    		
//    		int d = Integer.parseInt(dateTextField.getText().substring(0, 2));
//    		System.out.println("D: " + d);
//    		//date.setDate(d);
//    		System.out.println(date.getDate() + "woianadwoiwnoiawndoi");
    		System.out.println("Date: " + date.getDate());
    		
    		int numberInParty = Integer.parseInt(numberInPartyTextField.getText());
    		String contactName = contactNameTextField.getText();
    		String contactEmailAddress = contactEmailTextField.getText();
    		String contactPhoneNumber = contactPhoneTextField.getText();
    		
    		try {
				Controller.reserve(date, time, numberInParty, contactName, contactEmailAddress, contactPhoneNumber, tables);;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		// update visuals
    		refreshData();
        }
        
        protected void homeButtonActionPerformed(ActionEvent evt) {
    		// TODO Auto-generated method stub
        	new RestoHomePage().setVisible(true);
    	}
}
