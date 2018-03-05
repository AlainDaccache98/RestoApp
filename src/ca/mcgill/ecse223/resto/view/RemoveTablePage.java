package ca.mcgill.ecse223.resto.view;

import java.awt.Color;
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
	
	private TableVisualizer tbView;
	
	private JButton removeTableButton;

	private String error = null;

	private Integer selectedTableNumber = -1;
	private ArrayList<Table> tables;

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
		
		tbView = new TableVisualizer();
		
		//comboBox for selecting from the existing tables 
		tableList = new JComboBox<Table>();
		completeTableList(tableList);
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
						.addComponent(tbView))
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
						.addComponent(tbView))
						);
		pack();

	}
	
	private void completeTableList(JComboBox<Table> tableList2) {
		// TODO Auto-generated method stub
		RestoApp r = RestoAppApplication.getRestoApp();

		for(Table table : r.getTables()){
			tableList.add("# " + table.getNumber(), tableList);
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