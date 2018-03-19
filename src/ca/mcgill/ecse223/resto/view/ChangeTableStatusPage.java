package ca.mcgill.ecse223.resto.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import ca.mcgill.ecse223.resto.application.RestoAppApplication;

//import org.jdatepicker.impl.JDatePanelImpl;
//import org.jdatepicker.impl.JDatePickerImpl;
//import org.jdatepicker.impl.SqlDateModel;

import ca.mcgill.ecse223.resto.controller.Controller;
import ca.mcgill.ecse223.resto.controller.InvalidInputException;
import ca.mcgill.ecse223.resto.model.RestoApp;
import ca.mcgill.ecse223.resto.model.Table;



public class ChangeTableStatusPage extends JFrame {
    
    private static final long serialVersionUID = -4426310869335015542L;
    
    // UI elements
    private JLabel errorMessage;
    // driver
    private JTextField tableNumberTextField;
    private JLabel tableNumberLabel;
    
	private JComboBox<Table> tableList;
	private JLabel tableNumberSelected;
    
//    private JTextField numberOfSeatsTextField;
//    private JLabel numberOfSeatsLabel;
    
    private JButton setTableInUseButton;
    private JButton setTableAvailableButton;
    private JButton homeButton;

	private Integer selectedTableNumber = -1;
	private ArrayList<Table> tables;
    
    private TableVisualizer tableVisualizer;
    
	private static final int WIDTH_Table_VISUALIZATION = 200;
	private static final int HEIGHT_Table_VISUALIZATION = 200;


	private String error = null;

    public ChangeTableStatusPage() {
        initComponents();
        refreshData();
    }
    
    private void initComponents() {
        // elements for error message
        errorMessage = new JLabel();
        errorMessage.setForeground(Color.RED);
        
		tableVisualizer = new TableVisualizer();
		tableVisualizer.setMinimumSize(new Dimension(WIDTH_Table_VISUALIZATION, HEIGHT_Table_VISUALIZATION));
        
        // elements for driver
        tableNumberTextField = new JTextField();
        tableNumberLabel = new JLabel();

        homeButton = new JButton();
        setTableInUseButton = new JButton();
        setTableAvailableButton = new JButton();
        
        // global settings and listeners
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("resto Management System");
        
        tableNumberLabel.setText("Table Number:");
		tableList.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt){ 
				JComboBox<Table> cb   = (JComboBox<Table>) evt.getSource();
				selectedTableNumber = cb.getSelectedIndex();
			}
		});
        
        setTableInUseButton.setText("Set In-Use");
        setTableInUseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	setTableInUseButtonActionPerformed(evt);
            }
        });
        
        setTableAvailableButton.setText("Set Available");
        setTableAvailableButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	setTableAvailableButtonActionPerformed(evt);
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
						.addGroup(layout.createParallelGroup()
								.addComponent(tableNumberLabel)							
								)
						.addGroup(layout.createParallelGroup()
								.addComponent(setTableInUseButton, 70,70,140)
								.addComponent(setTableAvailableButton)))
				.addGroup(layout.createParallelGroup()
						.addGroup(layout.createParallelGroup()
						.addComponent(tableVisualizer))));
		
		layout.setVerticalGroup(
				layout.createSequentialGroup()
				.addComponent(errorMessage)	
				.addComponent(homeButton)
				.addGroup(layout.createParallelGroup()
						.addComponent(tableNumberLabel)
						.addComponent(tableNumberTextField)
						)
				.addGroup(layout.createParallelGroup()
						.addComponent(horizontalLineTop))
				.addGroup(layout.createParallelGroup()
						.addComponent(setTableInUseButton)
						.addComponent(setTableAvailableButton))
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createSequentialGroup()
						.addComponent(tableVisualizer)))
				);

        pack();
    }

	private void completeTableList(JComboBox<Table> tableList2) {
		// TODO Auto-generated method stub
		RestoAppApplication.load();
		RestoApp r = RestoAppApplication.getRestoApp();

//		System.out.println("Size: " + r.getCurrentTables().size());
		for(Table table : r.getCurrentTables()){
			tableList.add(("# " + table.getNumber()), tableList);
		}
		
	}
    
	private void refreshData() {
		
		errorMessage.setText(error);
		if (error == null || error.length() == 0) {
			
			//update the combo box
			tables = new ArrayList<Table>();
			tableList.removeAllItems();
			Integer index = 0;
			
			RestoApp r = RestoAppApplication.getRestoApp();
			
			for(Table table : r.getTables()){
				tables.add(table);
				index++;
			}
			
			completeTableList(tableList);
			
			selectedTableNumber = -1;
			tableList.setSelectedIndex(selectedTableNumber);
		}
            
    }
	
        private void setTableInUseButtonActionPerformed(java.awt.event.ActionEvent evt) {
    		// clear error message
    		error = null;
    				
    		// call the controller
			Table table = tables.get(selectedTableNumber);
			Controller.setTableInUse(table);
    		
    		// update visuals
    		refreshData();
        }
        
        private void setTableAvailableButtonActionPerformed(java.awt.event.ActionEvent evt) {
    		// clear error message
    		error = null;
    				
    		// call the controller
			Table table = tables.get(selectedTableNumber);
			Controller.setTableAvailable(table);
    		
    		// update visuals
    		refreshData();
        }
        
        protected void homeButtonActionPerformed(ActionEvent evt) {
    		// TODO Auto-generated method stub
        	new RestoHomePage().setVisible(true);
    	}
}
