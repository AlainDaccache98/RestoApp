package ca.mcgill.ecse223.resto.view;

import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.Color;
import java.awt.event.ActionEvent;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.EmptyBorder;
import javax.swing.LayoutStyle.ComponentPlacement;

import ca.mcgill.ecse223.resto.controller.InvalidInputException;
import ca.mcgill.ecse223.resto.controller.Controller;
import ca.mcgill.ecse223.resto.model.MenuItem;
import ca.mcgill.ecse223.resto.model.RestoApp;
import ca.mcgill.ecse223.resto.model.Table;
import ca.mcgill.ecse223.resto.model.MenuItem.ItemCategory;

public class RemoveMenuItemPage extends JFrame {
	private static final long serialVersionUID = -7403802774454467835L;

	private JButton viewMenu, removeMenuItem, home;
	private JLabel errorMessage;
	private JComboBox<MenuItem> menuItemList;
	private JLabel menuItemSelected;
	private String error = null;

	private Integer selectedMenuItem = -1;
	private HashMap<Integer, MenuItem> menuItems;

	public RemoveMenuItemPage() {
		//this.resto = r;
		initComponents();
		this.setSize(1400, 500);
		refreshData();
	}

	private void initComponents() {

		errorMessage = new JLabel();
		errorMessage.setForeground(Color.RED);
		menuItemSelected = new JLabel();


		viewMenu = new JButton();
		removeMenuItem = new JButton();
		home = new JButton();

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("Remove Menu Item");

		// Set text
		viewMenu.setText("View Menu");
		removeMenuItem.setText("Remove");
		home.setText("Home");
		menuItemSelected.setText("Item");

		menuItemList = new JComboBox<MenuItem>();
		menuItemList.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt){ 
				JComboBox<MenuItem> cb   = (JComboBox<MenuItem>) evt.getSource();
				selectedMenuItem = cb.getSelectedIndex();
			}
		});

		removeMenuItem.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent evt){
				removeMenuItemButtonActionPerformed(evt);
			}
		});

		home.setText("Home");
	    home.addActionListener(new java.awt.event.ActionListener() {
	      public void actionPerformed(java.awt.event.ActionEvent evt) {
	        homeButtonActionPerformed(evt);
	      }
	    });
	    
		

		// horizontal line elements
		JSeparator horizontalLineTop = new JSeparator();
		JSeparator horizontalLineMiddle1 = new JSeparator();
		JSeparator horizontalLineMiddle2 = new JSeparator();
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
				.addComponent(home)
				.addComponent(horizontalLineMiddle1)
				.addGroup(layout.createSequentialGroup()
						.addComponent(menuItemSelected)
						.addGroup(layout.createParallelGroup())    
						.addComponent(menuItemList)
						.addComponent(removeMenuItem, 30, 30, 140)

						)
				.addComponent(horizontalLineBottom)

				);

		layout.setVerticalGroup(
				layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup()
						.addComponent(errorMessage)
						.addComponent(horizontalLineTop)
						.addComponent(home)
						.addComponent(horizontalLineMiddle1)
						.addGroup(layout.createParallelGroup()
								.addComponent(menuItemSelected)
								.addComponent(menuItemList)
								)
						.addGroup(layout.createParallelGroup()
								.addComponent(removeMenuItem, 70, 70, 140)
								)
						.addGroup(layout.createParallelGroup()
								.addComponent(horizontalLineBottom))
						)
				);
		pack();


	}

	private void homeButtonActionPerformed(java.awt.event.ActionEvent evt) {
	    // clear error message

	  new RestoHomePage().setVisible(true);
	  this.setVisible(false);
	    // update visuals
	    refreshData();
	  }
	

	private void refreshData() {

		errorMessage.setText(error);
		if (error == null || error.length() == 0) {
			selectedMenuItem = -1;
			menuItemList.setSelectedIndex(selectedMenuItem);


			menuItems = new HashMap<Integer, MenuItem>();
			menuItemList.removeAllItems();
			Integer index1 = 0;

			for (ItemCategory category : Controller.getItemCategories()) {

				try {
					for(MenuItem item : Controller.getMenuItems(category)){
						menuItems.put(index1, item);
						menuItemList.addItem(item);
						index1++;
					}
				} catch (InvalidInputException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		} 
	}
	

	private void removeMenuItemButtonActionPerformed(ActionEvent evt) {

		// clear error message
		error = null;

		// call the controller
		try {
			MenuItem menuItem = (MenuItem) menuItemList.getSelectedItem();

			Controller.removeMenuItem(menuItem);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}

		// update visuals
		refreshData();

	}

}
