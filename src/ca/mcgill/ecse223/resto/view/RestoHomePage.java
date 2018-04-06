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
    
    private JLabel header;
    
    private JButton addTableButton;
    private JButton removeTableButton;
    private JButton changeTableLocationButton;
    private JButton updateTableButton;
    private JButton orderButton;
    private JButton menuButton;
    private JButton reserveButton;
    private JButton loyaltyCardButton;
    private JButton issueBillButton;


    private TableVisualizer tableVisualizer;
    
	private static final int WIDTH_Table_VISUALIZATION = 200;
	private static final int HEIGHT_Table_VISUALIZATION = 200;


	private String error = null;

    public RestoHomePage() {
        initComponents();
        this.setSize(1400, 500);
        refreshData();
    }
    
    private void initComponents() {
        
		tableVisualizer = new TableVisualizer();
		tableVisualizer.setMinimumSize(new Dimension(WIDTH_Table_VISUALIZATION, HEIGHT_Table_VISUALIZATION));
        
        // elements for driver
		header = new JLabel();
		
        addTableButton = new JButton();
        removeTableButton = new JButton();
        changeTableLocationButton = new JButton();
        updateTableButton = new JButton();
        orderButton = new JButton();
        menuButton = new JButton();
        reserveButton = new JButton();
        loyaltyCardButton = new JButton();
        issueBillButton = new JButton();


        // global settings and listeners
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("resto Management System");
        
        header.setText("RestoApp");
        
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
        
        orderButton.setText("Orders");
        orderButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	orderButtonActionPerformed(evt);
            }
        });
        
        menuButton.setText("View Menu");
        menuButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	menuButtonActionPerformed(evt);
            }
        });
        
        reserveButton.setText("Reserve");
        reserveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	reserveButtonActionPerformed(evt);
            }
        });
        
        loyaltyCardButton.setText("Add Loyalty Card");
        loyaltyCardButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	loyaltyCardButtonActionPerformed(evt);
            }
        });
        
        issueBillButton.setText("Issue Bill");
        issueBillButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	issueBillButtonActionPerformed(evt);
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
				.addComponent(horizontalLineTop)
				.addGroup(layout.createSequentialGroup()								
						.addGroup(layout.createParallelGroup()
								.addComponent(addTableButton, 70,70,140)
								.addComponent(removeTableButton, 70,70,140)
								.addComponent(changeTableLocationButton)
								.addComponent(updateTableButton, 70,70,140)
								.addComponent(orderButton)
								.addComponent(menuButton, 70,70,140)
								.addComponent(reserveButton, 70,70,140)
								.addComponent(loyaltyCardButton, 70,70,140)
								.addComponent(issueBillButton, 70,70,140)
								)
				)
				.addComponent(horizontalLineBottom)
				.addGroup(layout.createParallelGroup()
						.addGroup(layout.createParallelGroup()
						.addComponent(tableVisualizer))));
		
		layout.setVerticalGroup(
				layout.createSequentialGroup()
				.addComponent(horizontalLineTop)
				.addGroup(layout.createParallelGroup()
						.addComponent(addTableButton))
				.addGroup(layout.createParallelGroup()
						.addComponent(removeTableButton))
				.addGroup(layout.createParallelGroup()
						.addComponent(changeTableLocationButton))
				.addGroup(layout.createParallelGroup()
						.addComponent(updateTableButton))
				.addGroup(layout.createParallelGroup()
						.addComponent(orderButton))
				.addGroup(layout.createParallelGroup()
						.addComponent(menuButton))
				.addGroup(layout.createParallelGroup()
						.addComponent(reserveButton))
				.addGroup(layout.createParallelGroup()
						.addComponent(loyaltyCardButton))
				.addGroup(layout.createParallelGroup()
						.addComponent(issueBillButton))
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
//    	System.out.println("added");
		refreshData();
		this.setVisible(false);
    }
	
	protected void removeTableButtonActionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub
    	new RemoveTablePage().setVisible(true);
//    	System.out.println("removed");
        refreshData();
		this.setVisible(false);
	}
	protected void changeTableLocationButtonActionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub
    	new ChangeTableLocationPage().setVisible(true);
		refreshData();
		this.setVisible(false);
	}
	
	protected void updateTableButtonActionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub
    	new UpdateTablePage().setVisible(true);
//    	System.out.println("hi");
		refreshData();
		this.setVisible(false);
	}
	
	protected void orderButtonActionPerformed(ActionEvent evt) {
    	new OrderHomePage().setVisible(true);
		refreshData();
		this.setVisible(false);
	}
	
    protected void menuButtonActionPerformed(ActionEvent evt) {
		new MenuPage().setVisible(true);
    	refreshData();
		this.setVisible(false);
	}
    
    protected void reserveButtonActionPerformed(ActionEvent evt) {
		new ReservationPage().setVisible(true);
    	refreshData();
		this.setVisible(false);
	}

    protected void loyaltyCardButtonActionPerformed(ActionEvent evt) {
		new AddLoyaltyCardPage().setVisible(true);
    	refreshData();
		this.setVisible(false);
	}
    
    protected void issueBillButtonActionPerformed(ActionEvent evt) {
		new IssueBillPage().setVisible(true);
    	refreshData();
		this.setVisible(false);
	}
    
}
