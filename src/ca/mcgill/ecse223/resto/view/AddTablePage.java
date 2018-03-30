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



public class AddTablePage extends JFrame {
    
    private static final long serialVersionUID = -4426310869335015542L;
    
    // UI elements
    private JLabel errorMessage;
    // driver
    private JTextField tableNumberTextField;
    private JLabel tableNumberLabel;
    
    private JTextField xTextField;
    private JLabel xLabel;
    
    private JTextField yTextField;
    private JLabel yLabel;
    
    private JTextField lengthTextField;
    private JLabel lengthLabel;
    
    private JTextField widthTextField;
    private JLabel widthLabel;
    
    private JTextField numberOfSeatsTextField;
    private JLabel numberOfSeatsLabel;
    
    private JButton addTableButton;
    private JButton homeButton;
    
    private TableVisualizer tableVisualizer;
    
	private static final int WIDTH_Table_VISUALIZATION = 200;
	private static final int HEIGHT_Table_VISUALIZATION = 200;


	private String error = null;

    public AddTablePage() {
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
        xTextField = new JTextField();
        xLabel = new JLabel();
        yTextField = new JTextField();
        yLabel = new JLabel();
        lengthTextField = new JTextField();
        lengthLabel = new JLabel();
        widthTextField = new JTextField();
        widthLabel = new JLabel();
        numberOfSeatsTextField = new JTextField();
        numberOfSeatsLabel = new JLabel();
        addTableButton = new JButton();
        homeButton = new JButton();
        
        // global settings and listeners
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("resto Management System");
        
        tableNumberLabel.setText("Table Number:");
        xLabel.setText("x:");
        yLabel.setText("Y:");
        lengthLabel.setText("Length:");
        widthLabel.setText("Width:");
        numberOfSeatsLabel.setText("number of seats:");
        addTableButton.setText("Add Table");
        addTableButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	addTableButtonActionPerformed(evt);
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
								.addComponent(xLabel)
								.addComponent(yLabel)
								.addComponent(lengthLabel)
								.addComponent(widthLabel)
								.addComponent(numberOfSeatsLabel)
								)
						.addGroup(layout.createParallelGroup()
								.addComponent(tableNumberTextField,200,200,400)
								.addComponent(xTextField,200,200,400)
								.addComponent(yTextField,200,200,400)
								.addComponent(lengthTextField,200,200,400)
								.addComponent(widthTextField,200,200,400)
								.addComponent(numberOfSeatsTextField,200,200,400))
						.addComponent(addTableButton, 70,70,140))
				.addComponent(horizontalLineBottom)

				.addGroup(layout.createParallelGroup()
						.addGroup(layout.createParallelGroup()
						.addComponent(tableVisualizer))));
		
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
						.addComponent(xLabel)
						.addComponent(xTextField)
						)	
				.addGroup(layout.createParallelGroup()
						.addComponent(yLabel)
						.addComponent(yTextField)
						)	
				.addGroup(layout.createParallelGroup()
						.addComponent(lengthLabel)
						.addComponent(lengthTextField)
						)
				.addGroup(layout.createParallelGroup()
						.addComponent(widthLabel)
						.addComponent(widthTextField)
						)	
				.addGroup(layout.createParallelGroup()
						.addComponent(numberOfSeatsLabel)
						.addComponent(numberOfSeatsTextField)
						)
				.addGroup(layout.createParallelGroup()
						.addComponent(addTableButton))
				.addGroup(layout.createParallelGroup()
						.addComponent(horizontalLineBottom))
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createSequentialGroup()
						.addComponent(tableVisualizer)))
				);

        
        pack();
    }

	private void refreshData() {
                                    // error
                                    errorMessage.setText(error);
                                    if (error == null || error.length() == 0) {
                                        // populate page with data
                                        tableNumberTextField.setText("");
                                        xTextField.setText("");
                                        yTextField.setText("");
                                        lengthTextField.setText("");
                                        widthTextField.setText("");
                                        numberOfSeatsTextField.setText("");
                                    }
                                    //tableVisualizer set active or refresh
                                    pack();
            
    }
        private void addTableButtonActionPerformed(java.awt.event.ActionEvent evt) {
            // clear error message
            error = null;
    		RestoApp r = RestoAppApplication.getRestoApp();

            // call the controller
            try {
                Controller.createTable(Integer.parseInt(tableNumberTextField.getText()), Integer.parseInt(xTextField.getText()), Integer.parseInt(yTextField.getText()), Integer.parseInt(lengthTextField.getText()), Integer.parseInt(widthTextField.getText()), Integer.parseInt(numberOfSeatsTextField.getText()));
            } catch (InvalidInputException e) {
                error = e.getMessage();
            }
            
            // update visuals
            refreshData();
        }
        
        protected void homeButtonActionPerformed(ActionEvent evt) {
    		// TODO Auto-generated method stub
        	new RestoHomePage().setVisible(true);
    	}
}
