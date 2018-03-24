package ca.mcgill.ecse223.resto.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.WindowConstants;


//import org.jdatepicker.impl.JDatePanelImpl;
//import org.jdatepicker.impl.JDatePickerImpl;
//import org.jdatepicker.impl.SqlDateModel;

import ca.mcgill.ecse223.resto.controller.Controller;
import ca.mcgill.ecse223.resto.controller.InvalidInputException;



public class RestoHomePage extends JFrame {
    
    private static final long serialVersionUID = -4426310869335015542L;
    
    // UI elements
    
    private JButton addTableButton;
    private JButton removeTableButton;
    private JButton changeTableLocationButton;
    private JButton updateTableButton;
    private JButton changeTableStatusButton;
    private JButton menuButton;

    
    private TableVisualizer tableVisualizer;
    
	private static final int WIDTH_Table_VISUALIZATION = 200;
	private static final int HEIGHT_Table_VISUALIZATION = 200;


	private String error = null;

    public RestoHomePage() {
        initComponents();
        refreshData();
    }
    
    private void initComponents() {
        
		tableVisualizer = new TableVisualizer();
		tableVisualizer.setMinimumSize(new Dimension(WIDTH_Table_VISUALIZATION, HEIGHT_Table_VISUALIZATION));
        
        // elements for driver
        addTableButton = new JButton();
        removeTableButton = new JButton();
        changeTableLocationButton = new JButton();
        updateTableButton = new JButton();
        changeTableStatusButton = new JButton();
        menuButton = new JButton();

        // global settings and listeners
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("resto Management System");
        

        addTableButton.setText("Add Table");
        addTableButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	addTableButtonActionPerformed(evt);
            }
        });
        
        removeTableButton.setText("Remove Table");
        removeTableButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	removeTableButtonActionPerformed(evt);
            }
        });
        
        changeTableLocationButton.setText("Move Table");
        changeTableLocationButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	changeTableLocationButtonActionPerformed(evt);
            }
        });
        
        updateTableButton.setText("Update Table");
        updateTableButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	updateTableButtonActionPerformed(evt);
            }
        });
        
        changeTableStatusButton.setText("Change Table Status");
        changeTableStatusButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	changeTableStatusButtonActionPerformed(evt);
            }
        });
        
        menuButton.setText("View Menu");
        menuButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	menuButtonActionPerformed(evt);
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
				.addGroup(layout.createSequentialGroup()								
						.addGroup(layout.createParallelGroup()
								.addComponent(addTableButton, 70,70,140)
								.addComponent(removeTableButton, 70,70,140)
								.addComponent(changeTableLocationButton)
								.addComponent(updateTableButton, 70,70,140)
								.addComponent(changeTableStatusButton)
								.addComponent(menuButton, 70,70,140))
				)
				.addComponent(horizontalLineBottom)
				.addGroup(layout.createParallelGroup()
						.addGroup(layout.createParallelGroup()
						.addComponent(tableVisualizer))));
		
		layout.setVerticalGroup(
				layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup()
						.addComponent(addTableButton))
				.addGroup(layout.createParallelGroup()
						.addComponent(removeTableButton))
				.addGroup(layout.createParallelGroup()
						.addComponent(changeTableLocationButton))
				.addGroup(layout.createParallelGroup()
						.addComponent(updateTableButton))
				.addGroup(layout.createParallelGroup()
						.addComponent(changeTableStatusButton))
				.addGroup(layout.createParallelGroup()
						.addComponent(menuButton))
				.addGroup(layout.createParallelGroup()
						.addComponent(horizontalLineBottom))
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createSequentialGroup()
						.addComponent(tableVisualizer)))
				);

        
        pack();
    }

	private void refreshData() {
		//refresh tableVisualizer
		
		pack();
    }
       
	private void addTableButtonActionPerformed(java.awt.event.ActionEvent evt) {
    	new AddTablePage().setVisible(true);
System.out.println("added");
		refreshData();
    }
	
	protected void removeTableButtonActionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub
    	new RemoveTablePage().setVisible(true);
    	System.out.println("removed");

        refreshData();
	}
	protected void changeTableLocationButtonActionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub
    	new ChangeTableLocationPage().setVisible(true);
		refreshData();
	}
	
	protected void updateTableButtonActionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub
    	new UpdateTablePage().setVisible(true);
    	System.out.println("hi");
		refreshData();
	}
	
	protected void changeTableStatusButtonActionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub
    	new ChangeTableStatusPage().setVisible(true);
		refreshData();
	}
	
    protected void menuButtonActionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub
		new MenuPage().setVisible(true);
    	refreshData();
	}

}
