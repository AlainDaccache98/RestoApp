package ca.mcgill.ecse223.resto.view;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

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

public class ViewMenuPage extends JFrame {

  /**
   * 
   */
  private static final long serialVersionUID = -7403802774454467836L;

  //private RestoApp resto;

  private JLabel menuLabel, appzsLabel, mainsLabel, dessertsLabel, alcBevLabel, nonAlcBevLabel;
  private JButton appetizer, dessert, main, alcoholicBev, nonAlcoholicBev, homeButton;
  private ArrayList<String> menuValues;
  
  public ViewMenuPage() {
    //this.resto = r;
    initComponents();
    this.setSize(1400, 500);
    refreshData();
  }

  private void initComponents() {
    // elements
    menuLabel = new JLabel();
    appzsLabel = new JLabel();
    mainsLabel = new JLabel();
    dessertsLabel = new JLabel();
    alcBevLabel = new JLabel();
    nonAlcBevLabel = new JLabel();
    appetizer = new JButton();
    dessert = new JButton();
    main = new JButton();
    alcoholicBev = new JButton();
    nonAlcoholicBev = new JButton();
    homeButton = new JButton();
    menuValues = new ArrayList<String>();

    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setTitle("Menu Page");

    
    for (MenuItem.ItemCategory itemCat : Controller.getItemCategories()) {
      try {
    	  //System.out.println(menuItemsToString(Controller.getMenuItems(itemCat)));
    	  menuValues.add(menuItemsToString(Controller.getMenuItems(itemCat)));
      } catch (InvalidInputException e) {
        e.printStackTrace();
      }
    }

    // Set text
    menuLabel.setText("MENU");
    //System.out.println(menuValues.get(0));
    appetizer.setText("Appetizers");
    main.setText("Mains");
    dessert.setText("Desserts");
    alcoholicBev.setText("Alcoholic beverages");
    nonAlcoholicBev.setText("Non-Alcoholic Beverages");
    
    System.out.println(menuValues.get(1));
	homeButton.setText("Home");
	
	appetizer.addActionListener(new java.awt.event.ActionListener(){
		public void actionPerformed(java.awt.event.ActionEvent evt){
			new AppzPage().setVisible(true);
		}
	});

	dessert.addActionListener(new java.awt.event.ActionListener(){
		public void actionPerformed(java.awt.event.ActionEvent evt){
			new DessertPage().setVisible(true);
		}
	});
	
	main.addActionListener(new java.awt.event.ActionListener(){
		public void actionPerformed(java.awt.event.ActionEvent evt){
			new MainsPage().setVisible(true);
		}
	});
	
	alcoholicBev.addActionListener(new java.awt.event.ActionListener(){
		public void actionPerformed(java.awt.event.ActionEvent evt){
			new AlcBevPage().setVisible(true);
		}
	});
	
	nonAlcoholicBev.addActionListener(new java.awt.event.ActionListener(){
		public void actionPerformed(java.awt.event.ActionEvent evt){
			new NonAlcBevPage().setVisible(true);
		}
	});

    // layout
    GroupLayout layout = new GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setAutoCreateGaps(true);
    layout.setAutoCreateContainerGaps(true);

    layout.setHorizontalGroup(layout.createSequentialGroup()
        .addGroup(layout.createParallelGroup().addGap(5).addComponent(menuLabel).addGap(5).addComponent(appetizer).addComponent(main).addGap(5).addComponent(dessert).addComponent(alcoholicBev)
            .addComponent(nonAlcoholicBev))

        );      

    layout.setVerticalGroup(layout.createSequentialGroup()
        .addGroup(layout.createParallelGroup().addComponent(menuLabel).addGap(5))
        .addGroup(layout.createParallelGroup().addComponent(appetizer).addGap(5))
        .addGroup(layout.createParallelGroup().addComponent(dessert))
        .addGroup(layout.createParallelGroup().addComponent(main).addGap(5))
        .addGroup(layout.createParallelGroup().addComponent(alcoholicBev))
        .addGroup(layout.createParallelGroup().addGap(5).addComponent(nonAlcoholicBev)));
    pack();
  }

  private void displayNonAlcoholicBevActionPerformed(ActionEvent evt) {
	// TODO Auto-generated method stub
	
}

private void displayAlcoholicBevActionPerformed(ActionEvent evt) {
	// TODO Auto-generated method stub
	
}

private void displayMainActionPerformed(ActionEvent evt) {
	// TODO Auto-generated method stub
	
}

private void displayDessertActionPerformed(ActionEvent evt) {
	// TODO Auto-generated method stub
	
}

private void displayAppetizerActionPerformed(ActionEvent evt) {
	// TODO Auto-generated method stub
	System.out.println(menuValues.get(0));
}

private void refreshData() {
    pack();
  }

  private String menuItemsToString(ArrayList<MenuItem> menuItems) {
    String returned = "";
    for (MenuItem mI : menuItems) {
      returned += (mI.getName() + " : " + mI.getCurrentPricedMenuItem().getPrice() + " \n");
    }
    
    //System.out.println(returned);
    return returned;
  }
  
  
  
}
