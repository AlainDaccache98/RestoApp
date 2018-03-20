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
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;

import ca.mcgill.ecse223.resto.application.RestoAppApplication;

//import org.jdatepicker.impl.JDatePanelImpl;
//import org.jdatepicker.impl.JDatePickerImpl;
//import org.jdatepicker.impl.SqlDateModel;

import ca.mcgill.ecse223.resto.controller.Controller;
import ca.mcgill.ecse223.resto.controller.InvalidInputException;
import ca.mcgill.ecse223.resto.model.RestoApp;
import ca.mcgill.ecse223.resto.model.Table;
import javafx.scene.control.CheckBox;



public class StartOrderPage extends JFrame {
    
    private static final long serialVersionUID = -4426310869335015542L;
    
    // UI elements
    private JLabel errorMessage;
    
    private List<JCheckBox> checkBoxList;
            
    private JButton startOrderButton;
    private JButton homeButton;
    
    private TableVisualizer tableVisualizer;
    
	private static final int WIDTH_Table_VISUALIZATION = 200;
	private static final int HEIGHT_Table_VISUALIZATION = 200;

	
	private String error = null;

    public StartOrderPage() {
        initComponents();
        refreshData();
    }
    
    private void initComponents() {
    	
        // elements for error message
        errorMessage = new JLabel();
        errorMessage.setForeground(Color.RED);
        
		tableVisualizer = new TableVisualizer();
		tableVisualizer.setMinimumSize(new Dimension(WIDTH_Table_VISUALIZATION, HEIGHT_Table_VISUALIZATION));
        
        homeButton = new JButton();
        startOrderButton = new JButton();
        
        RestoApp r = RestoAppApplication.getRestoApp();
        
        for(Table t : r.getCurrentTables()) {
        	JCheckBox box1 = new JCheckBox("Table #" + t.getNumber());
        	checkBoxList.add(box1);
        	add(box1);
        	}
        
        
        
        // global settings and listeners
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("resto Management System");
        
        startOrderButton.setText("Start Order");
        startOrderButton.addActionListener(new java.awt.event.ActionListener() {
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
						.addGroup(layout.createParallelGroup()
								.addComponent(startOrderButton, 70,70,140)))
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
						.addComponent(startOrderButton))
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
    		
    		List<Table> tables = null;
    		//RestoApp r = RestoAppApplication.getRestoApp();
//    		for(JCheckBox j : checkBoxList) {
//    			if(j.isSelected()) {
//    				tables.add(j.get)
//    			}
//    		}
    		
    		try {
				Controller.startOrder(tables);
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
