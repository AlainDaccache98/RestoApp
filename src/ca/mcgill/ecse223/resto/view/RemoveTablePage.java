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
import javax.swing.WindowConstants;
import ca.mcgill.ecse223.resto.application.RestoAppApplication;
import ca.mcgill.ecse223.resto.controller.Controller;
import ca.mcgill.ecse223.resto.controller.InvalidInputException;
import ca.mcgill.ecse223.resto.model.RestoApp;
import ca.mcgill.ecse223.resto.model.Table;

public class RemoveTablePage extends JFrame {

	//private JPanel contentPane;
	private static final long serialVersionUID = -4426310869335015542L;
	
	private JLabel errorMessage;

	private JComboBox<Table> tableList;
	private JLabel tableNumberSelected;
	
	private TableVisualizer tableVisualizer;
	
	private JButton removeTableButton;

	private String error = null;

	private Integer selectedTableNumber = -1;
	private ArrayList<Table> tables;
	
	private static final int WIDTH_Table_VISUALIZATION = 200;
	private static final int HEIGHT_Table_VISUALIZATION = 200;
	

	/**
	 * Create the frame.
	 */
	public RemoveTablePage() {
		initComponents();
		refreshData();
	}
	
	private void initComponents() {
		
		errorMessage = new JLabel();
		errorMessage.setForeground(Color.RED);
		//label for selecting table whose features are to be updated
		tableNumberSelected = new JLabel();
		
		//tableVisualizer.setMinimumSize(new Dimension(WIDTH_Table_VISUALIZATION, HEIGHT_Table_VISUALIZATION));
		
		tableVisualizer = new TableVisualizer();
				
		//comboBox for selecting from the existing tables 
		tableList = new JComboBox<Table>();
//		System.out.println(tableList.getItemCount());
		completeTableList(tableList);
//		System.out.println(tableList.getItemCount());
		
        // global settings and listeners
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("resto Management System");

		tableList.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt){ 
				JComboBox<Table> cb   = (JComboBox<Table>) evt.getSource();
				selectedTableNumber = cb.getSelectedIndex();
			}
		});
		
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("RestoApp");
		
		tableNumberSelected.setText("Select table: ");
		
		removeTableButton = new JButton();
		removeTableButton.setText("Remove");
		//updatedSeatsButton.setText("Update");
		
		removeTableButton.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent evt){
				removeTableButtonActionPerformed(evt);
			}

			
		});

		
		JSeparator horizontalLineTop = new JSeparator();
		JSeparator horizontalLineMiddle = new JSeparator();
		JSeparator horizontalLineBottom = new JSeparator();
		
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		layout.setHorizontalGroup(
				layout.createParallelGroup()
				.addGroup(layout.createParallelGroup()
						)
				
				.addComponent(errorMessage)
				.addComponent(horizontalLineTop)
				.addComponent(horizontalLineMiddle)
				.addComponent(horizontalLineBottom)
				.addGroup(layout.createParallelGroup()
						.addComponent(tableVisualizer))
				.addGroup(layout.createSequentialGroup()
					.addGroup(layout.createParallelGroup()
							.addComponent(tableNumberSelected))
					.addGroup(layout.createParallelGroup()
							.addComponent(tableList)
							.addComponent(removeTableButton, 70,70,140))
				));
		
		layout.setVerticalGroup(
				layout.createSequentialGroup()
					.addComponent(errorMessage)	
				.addGroup(layout.createParallelGroup()
						.addComponent(horizontalLineTop))
				.addGroup(layout.createParallelGroup()
						.addComponent(horizontalLineMiddle))
				.addGroup(layout.createParallelGroup()
						.addComponent(tableNumberSelected)
						.addComponent(tableList))
				.addGroup(layout.createParallelGroup()
						.addComponent(horizontalLineMiddle))
				.addGroup(layout.createParallelGroup()
						.addComponent(removeTableButton))		
				.addGroup(layout.createParallelGroup()
						.addComponent(horizontalLineBottom))
				.addGroup(layout.createParallelGroup()
						.addComponent(tableVisualizer))
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

	//action after pressing the update table number button
	private void removeTableButtonActionPerformed(ActionEvent evt) {
		
		// clear error message
		error = null;
				
		// call the controller
		try {
			Table table = tables.get(selectedTableNumber);
			Controller.removeTable(table);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
				
		// update visuals
		refreshData();
			
	}

	//refreshing the data after a new entry
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
		
		pack();
		
	}

	

}