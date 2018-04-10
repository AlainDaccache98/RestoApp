package ca.mcgill.ecse223.resto.view;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.WindowConstants;

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
import ca.mcgill.ecse223.resto.model.MenuItem.ItemCategory;
import ca.mcgill.ecse223.resto.model.RestoApp;
import ca.mcgill.ecse223.resto.model.Table;

public class AddMenuItemPage extends JFrame {
	private static final long serialVersionUID = -7403802774454467833L;

	//private RestoApp resto;

	private JButton viewMenu, addMenuItem, home;
	private JLabel errorMessage;
	private String error = null;

	private JLabel nameLabel;
	private JTextField nameTextField;

	private JLabel priceLabel;
	private JTextField priceTextField;

	private JComboBox<ItemCategory> menuCategoryList;
	private JLabel menuCategorySelected;

	private Integer selectedMenuCategory = -1;
	private HashMap<Integer, ItemCategory> menuCategories;

	public AddMenuItemPage() {
		//this.resto = r;
		initComponents();
		this.setSize(1400, 500);
		refreshData();
	}

	private void initComponents() {

		errorMessage = new JLabel();
		errorMessage.setForeground(Color.RED);
		nameLabel = new JLabel();
		priceLabel = new JLabel();
		menuCategorySelected = new JLabel();
		
		viewMenu = new JButton();
		addMenuItem = new JButton();
		home = new JButton();

		nameTextField = new JTextField();
		priceTextField = new JTextField();

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("Add Menu Item");

		// Set text
		viewMenu.setText("View Menu");
		addMenuItem.setText("Add");
		home.setText("Home");
		nameLabel.setText("Name");
		priceLabel.setText("Price");
		menuCategorySelected.setText("Category");

		viewMenu.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent evt){
				new ViewMenuPage().setVisible(true);
			}
		});

		addMenuItem.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent evt){
				addMenuItemButtonActionPerformed(evt);
			}
		});
		home.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent evt){
				new RestoHomePage().setVisible(true);
			}
		});

		menuCategoryList = new JComboBox<ItemCategory>();
		menuCategoryList.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt){ 
				JComboBox<String> cb   = (JComboBox<String>) evt.getSource();
				selectedMenuCategory = cb.getSelectedIndex();
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
								.addComponent(priceLabel))
						.addGroup(layout.createParallelGroup()    
								.addComponent(nameTextField, 70, 70, 140)
								.addComponent(menuCategoryList)
								.addComponent(priceTextField, 70, 70, 140))
						.addComponent(addMenuItem, 30, 30, 140)
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
								.addComponent(addMenuItem, 30, 30, 140)
								)
						.addGroup(layout.createParallelGroup()
								.addComponent(horizontalLineBottom))
						)
				);
		pack();

	}

	private void addMenuItemButtonActionPerformed(ActionEvent evt) {

		// clear error message
		error = null;

		// call the controller
		try {
			ItemCategory object = (ItemCategory) menuCategoryList.getSelectedItem();
			double price = Double.parseDouble(priceTextField.getText());
			String name = nameTextField.getText();
			Controller.addMenuItem(name, object, price);
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
			selectedMenuCategory = -1;
			menuCategoryList.setSelectedIndex(selectedMenuCategory);
			
			//update the combo box
			menuCategories = new HashMap<Integer, ItemCategory>();
			menuCategoryList.removeAllItems();
			Integer index = 0;
			
			for(ItemCategory category : Controller.getItemCategories()){
				menuCategories.put(index, category);
				menuCategoryList.addItem(category);
				index++;
			}
			
		}
	}
}
