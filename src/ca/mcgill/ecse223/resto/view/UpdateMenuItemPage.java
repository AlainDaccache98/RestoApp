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
import javax.swing.JTextField;
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

public class UpdateMenuItemPage extends JFrame {
	private static final long serialVersionUID = -7403802774454467834L;

	//private RestoApp resto;

	private JButton viewMenu, updateMenuItem, home;
	private JLabel errorMessage;
	private String error = null;

	private JLabel nameLabel;
	private JTextField nameTextField;

	private JLabel priceLabel;
	private JTextField priceTextField;

	private JComboBox<ItemCategory> menuCategoryList;
	private JLabel menuCategorySelected;

	private JComboBox<MenuItem> menuItemList;
	private JLabel menuItemSelected;

	private Integer selectedMenuItem = -1;
	private HashMap<Integer, MenuItem> menuItems;

	private Integer selectedMenuCategory = -1;
	private HashMap<Integer, ItemCategory> menuCategories;


	public UpdateMenuItemPage() {
		//this.resto = r;
		initComponents();
		this.setSize(1400, 500);
		refreshData();
	}

	private void initComponents() {

		errorMessage = new JLabel();
		errorMessage.setForeground(Color.RED);

		viewMenu = new JButton();
		updateMenuItem = new JButton();
		home = new JButton();

		priceLabel = new JLabel();
		nameLabel = new JLabel();
		menuCategorySelected = new JLabel();
		menuItemSelected = new JLabel();

		nameTextField = new JTextField();
		priceTextField = new JTextField();

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("Update Menu Item");

		// Set text
		viewMenu.setText("View Menu");
		updateMenuItem.setText("Update");
		home.setText("Home");

		priceLabel.setText("Price");
		nameLabel.setText("Name");
		menuCategorySelected.setText("Category");
		menuItemSelected.setText("Item");

		menuItemList = new JComboBox<MenuItem>();
		menuItemList.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt){ 
				JComboBox<String> cb   = (JComboBox<String>) evt.getSource();
				selectedMenuItem = cb.getSelectedIndex();
			}
		});

		menuCategoryList = new JComboBox<ItemCategory>();
		menuCategoryList.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt){ 
				JComboBox<String> cb   = (JComboBox<String>) evt.getSource();
				selectedMenuCategory = cb.getSelectedIndex();
			}
		});

		updateMenuItem.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent evt){
				updateMenuItemButtonActionPerformed(evt);
			}
		});

		home.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent evt){
				new RestoHomePage().setVisible(true);
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
						.addGroup(layout.createParallelGroup()
								.addComponent(nameLabel)
								.addComponent(menuCategorySelected)
								.addComponent(priceLabel)
								.addComponent(menuItemSelected))

						.addGroup(layout.createParallelGroup()    
								.addComponent(nameTextField, 70, 70, 140)
								.addComponent(menuCategoryList)
								.addComponent(priceTextField, 70, 70, 140)
								.addComponent(menuItemList))
						.addComponent(updateMenuItem, 30, 30, 140)
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
								.addComponent(nameLabel)
								.addComponent(nameTextField)
								)
						.addGroup(layout.createParallelGroup()
								.addComponent(menuCategorySelected)
								.addComponent(menuCategoryList)
								)
						.addGroup(layout.createParallelGroup()
								.addComponent(priceLabel)
								.addComponent(priceTextField)
								)
						.addGroup(layout.createParallelGroup()
								.addComponent(menuItemSelected)
								.addComponent(menuItemList)
								)
						.addGroup(layout.createParallelGroup()
								.addComponent(updateMenuItem, 30, 30, 140)
								)
						.addGroup(layout.createParallelGroup()
								.addComponent(horizontalLineBottom))
						)
				);
		pack();

	}

	private void updateMenuItemButtonActionPerformed(ActionEvent evt) {

		// clear error message
		error = null;

		// call the controller
		try {
			MenuItem menuItem = (MenuItem) menuItemList.getSelectedItem();
			ItemCategory category = (ItemCategory) menuCategoryList.getSelectedItem();
			double price = Double.parseDouble(priceTextField.getText());
			String name = nameTextField.getText();
			Controller.updateMenuItem(menuItem, name, category, price);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}

		// update visuals
		refreshData();

	}
	private void refreshData() {

		errorMessage.setText(error);
		if (error == null || error.length() == 0) {
			nameTextField.setText("");
			priceTextField.setText("");
			selectedMenuItem = -1;
			selectedMenuCategory = -1;
			menuItemList.setSelectedIndex(selectedMenuItem);
			menuCategoryList.setSelectedIndex(selectedMenuCategory);

			//update the combo box
			menuCategories = new HashMap<Integer, ItemCategory>();
			menuCategoryList.removeAllItems();
			Integer index = 0;

			menuItems = new HashMap<Integer, MenuItem>();
			menuItemList.removeAllItems();
			Integer index1 = 0;


			for(ItemCategory category : Controller.getItemCategories()){
				menuCategories.put(index, category);
				menuCategoryList.addItem(category);
				index++;
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
}
