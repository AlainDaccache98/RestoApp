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

import ca.mcgill.ecse223.resto.application.RestoAppApplication;

//import org.jdatepicker.impl.JDatePanelImpl;
//import org.jdatepicker.impl.JDatePickerImpl;
//import org.jdatepicker.impl.SqlDateModel;

import ca.mcgill.ecse223.resto.controller.Controller;
import ca.mcgill.ecse223.resto.controller.InvalidInputException;
import ca.mcgill.ecse223.resto.model.RestoApp;



public class AddLoyaltyCardPage extends JFrame {
    
    private static final long serialVersionUID = -4426310869335015542L;
    
    // UI elements
    private JLabel errorMessage;
    // driver
    private JTextField tableNumberTextField;
    private JLabel tableNumberLabel;
    

    
    private JButton addTableButton;
    private JButton homeButton;
    



	private String error = null;

    public AddLoyaltyCardPage() {
        initComponents();
        this.setSize(1400, 500);

        refreshData();
    }
    
    private void initComponents() {
        // elements for error message
        errorMessage = new JLabel();
        errorMessage.setForeground(Color.RED);
        
        
        // elements for driver
        tableNumberTextField = new JTextField();
        tableNumberLabel = new JLabel();
        addTableButton = new JButton();
        homeButton = new JButton();
        
        // global settings and listeners
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("resto Management System");
        
        tableNumberLabel.setText("Loyalty card Number:");
       
        addTableButton.setText("Add Card");
        addTableButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	try {
					addTableButtonActionPerformed(evt);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
						.addGroup(layout.createParallelGroup()
								.addComponent(tableNumberLabel)

								)
						.addGroup(layout.createParallelGroup()
								.addComponent(tableNumberTextField,200,200,400)

								)
						.addComponent(addTableButton, 70,70,140))
				.addComponent(horizontalLineBottom)

				.addGroup(layout.createParallelGroup()
						.addGroup(layout.createParallelGroup()
						)));
		
		layout.setVerticalGroup(
				layout.createSequentialGroup()
				.addComponent(errorMessage)	
				.addComponent(horizontalLineTop)
				.addComponent(homeButton)
				.addComponent(horizontalLineMiddle)
				.addGroup(layout.createParallelGroup()
						.addComponent(tableNumberLabel)
						.addComponent(tableNumberTextField)
						)

				.addGroup(layout.createParallelGroup()
						.addComponent(addTableButton))
				.addGroup(layout.createParallelGroup()
						.addComponent(horizontalLineBottom))
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createSequentialGroup()
						))
				);

        
        pack();
    }

	private void refreshData() {
                                    // error
                                    errorMessage.setText(error);
                                    if (error == null || error.length() == 0) {
                                        // populate page with data
                                        tableNumberTextField.setText("");
                                        
                                    }
                                    //tableVisualizer set active or refresh
                                    pack();
            
    }
        private void addTableButtonActionPerformed(java.awt.event.ActionEvent evt) throws NumberFormatException, Exception {
            // clear error message
            error = null;
    		RestoApp r = RestoAppApplication.getRestoApp();

            // call the controller
            try {
            
                Controller.createCard(Integer.parseInt(tableNumberTextField.getText()));
            } catch (InvalidInputException e) {
                error = e.getMessage();
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