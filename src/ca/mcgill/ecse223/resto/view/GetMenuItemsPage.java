//package ca.mcgill.ecse223.resto.view;
//
//import java.awt.Color;
//
//
//import javax.swing.GroupLayout;
//import javax.swing.JButton;
//import javax.swing.JFrame;
//import javax.swing.JLabel;
//import javax.swing.JSeparator;
//import javax.swing.JTextField;
//import javax.swing.WindowConstants;
//
//
////import org.jdatepicker.impl.JDatePanelImpl;
////import org.jdatepicker.impl.JDatePickerImpl;
////import org.jdatepicker.impl.SqlDateModel;
//
//import ca.mcgill.ecse223.resto.controller.Controller;
//import ca.mcgill.ecse223.resto.controller.InvalidInputException;
//
//
//
//public class GetMenuItemsPage extends JFrame {
//    
//    private static final long serialVersionUID = -7403802774454467836L;
//    
//    // UI elements
//    private JLabel errorMessage;
//    
//    private JTextField tableNumberTextField;
//    private JLabel tableNumberLabel;
//    
//    private JTextField xTextField;
//    private JLabel xLabel;
//    
//    private JTextField yTextField;
//    private JLabel yLabel;
//    
//    private JTextField lengthTextField;
//    private JLabel lengthLabel;
//    
//    private JTextField widthTextField;
//    private JLabel widthLabel;
//    
//    private JTextField numberOfSeatsTextField;
//    private JLabel numberOfSeatsLabel;
//    
//    private JButton addTableButton;
//
//
//	private String error = null;
//
//    public GetMenuItemsPage() {
//        initComponents();
//        refreshData();
//    }
//    
//    private void initComponents() {
//        // elements for error message
//        errorMessage = new JLabel();
//        errorMessage.setForeground(Color.RED);
//        
//        // elements
//        tableNumberTextField = new JTextField();
//        tableNumberLabel = new JLabel();
//        xTextField = new JTextField();
//        xLabel = new JLabel();
//        yTextField = new JTextField();
//        yLabel = new JLabel();
//        lengthTextField = new JTextField();
//        lengthLabel = new JLabel();
//        widthTextField = new JTextField();
//        widthLabel = new JLabel();
//        numberOfSeatsTextField = new JTextField();
//        numberOfSeatsLabel = new JLabel();
//        addTableButton = new JButton();
//
//        // global settings and listeners
//        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//        setTitle("resto Management System");
//        
//        tableNumberLabel.setText("Table Number:");
//        xLabel.setText("x:");
//        yLabel.setText("Y:");
//        lengthLabel.setText("Length:");
//        widthLabel.setText("Width:");
//        numberOfSeatsLabel.setText("number of seats:");
//        addTableButton.setText("Add Table");
//        addTableButton.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//            	addTableButtonActionPerformed(evt);
//            }
//        });
//        // horizontal line elements
//        JSeparator horizontalLineTop = new JSeparator();
//        JSeparator horizontalLineMiddle = new JSeparator();
//        JSeparator horizontalLineBottom = new JSeparator();
//        // layout
//        GroupLayout layout = new GroupLayout(getContentPane());
//        getContentPane().setLayout(layout);
//        layout.setAutoCreateGaps(true);
//        layout.setAutoCreateContainerGaps(true);
//		layout.setHorizontalGroup(
//				layout.createParallelGroup()
//				.addComponent(errorMessage)
//				.addComponent(horizontalLineTop)
//				.addComponent(horizontalLineMiddle)
//				.addComponent(horizontalLineBottom)
//				.addGroup(layout.createSequentialGroup()
//						.addGroup(layout.createParallelGroup()
//								.addComponent(tableNumberLabel)
//								.addComponent(xLabel)
//								.addComponent(yLabel)
//								.addComponent(lengthLabel)
//								.addComponent(widthLabel)
//								.addComponent(numberOfSeatsLabel)
//							
//								)
//								
//						.addGroup(layout.createParallelGroup()
//								.addComponent(tableNumberTextField,200,200,400)
//								.addComponent(xTextField,200,200,400)
//								.addComponent(yTextField,200,200,400)
//								.addComponent(lengthTextField,200,200,400)
//								.addComponent(widthTextField,200,200,400)
//								.addComponent(numberOfSeatsTextField,200,200,400))
//						.addGroup(layout.createParallelGroup()
//								.addComponent(addTableButton, 70,70,140))
//				));
//		
//		layout.setVerticalGroup(
//				layout.createSequentialGroup()
//				.addComponent(errorMessage)				
//				.addGroup(layout.createParallelGroup()
//						.addComponent(tableNumberLabel)
//						.addComponent(tableNumberTextField)
//						)
//				.addGroup(layout.createParallelGroup()
//						.addComponent(horizontalLineTop))
//				.addGroup(layout.createParallelGroup()
//						.addComponent(xLabel)
//						.addComponent(xTextField)
//						)
//				.addGroup(layout.createParallelGroup()
//						.addComponent(horizontalLineMiddle))	
//				.addGroup(layout.createParallelGroup()
//						.addComponent(yLabel)
//						.addComponent(yTextField)
//						)
//				.addGroup(layout.createParallelGroup()
//						.addComponent(horizontalLineMiddle))	
//				.addGroup(layout.createParallelGroup()
//						.addComponent(lengthLabel)
//						.addComponent(lengthTextField)
//						)
//				.addGroup(layout.createParallelGroup()
//						.addComponent(horizontalLineMiddle))	
//				.addGroup(layout.createParallelGroup()
//						.addComponent(widthLabel)
//						.addComponent(widthTextField)
//						)
//				.addGroup(layout.createParallelGroup()
//						.addComponent(horizontalLineMiddle))	
//				.addGroup(layout.createParallelGroup()
//						.addComponent(numberOfSeatsLabel)
//						.addComponent(numberOfSeatsTextField)
//						)
//				.addGroup(layout.createParallelGroup()
//						.addComponent(horizontalLineBottom))
//				.addGroup(layout.createParallelGroup()
//						.addComponent(addTableButton))
//						);
//
//        
//        pack();
//    }
//    private void refreshData() {
//                                    // error
//                                    errorMessage.setText(error);
//                                    if (error == null || error.length() == 0) {
//                                        // populate page with data
//                                        tableNumberTextField.setText("");
//                                        xTextField.setText("");
//                                        yTextField.setText("");
//                                        lengthTextField.setText("");
//                                        widthTextField.setText("");
//                                    }
//                                    pack();
//            
//    }
//        private void GetMenuItemsPageButtonActionPerformed(java.awt.event.ActionEvent evt) {
//            // clear error message
//            error = null;
//            
//            // call the controller
//            try {
//            	
//                Controller.getMenuItems(Integer.parseInt(MenuItem.ItemCategory.getText()));
//            } catch (InvalidInputException e) {
//                error = e.getMessage();
//            }
//            
//            // update visuals
//            refreshData();
//        }
//}
