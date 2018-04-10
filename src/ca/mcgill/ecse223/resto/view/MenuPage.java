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

public class MenuPage extends JFrame {

  /**
   * 
   */
  private static final long serialVersionUID = -7403802774454467836L;

  //private RestoApp resto;

  private JButton viewMenu, updateMenuItem, addItem, removeItem, home;
  
  public MenuPage() {
    //this.resto = r;
    initComponents();
    this.setSize(1400, 500);
    refreshData();
  }

  private void initComponents() {
    // elements

    viewMenu = new JButton();
    updateMenuItem = new JButton();
    addItem = new JButton();
    removeItem = new JButton();
    home = new JButton();

    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setTitle("Menu Page");

    // Set text
    viewMenu.setText("View Menu");
    addItem.setText("Add Menu Item");
    updateMenuItem.setText("Update Menu Item");
    removeItem.setText("Remove Menu Item");
    home.setText("Home");	
    
	viewMenu.addActionListener(new java.awt.event.ActionListener(){
		public void actionPerformed(java.awt.event.ActionEvent evt){
        	viewMenuActionPerformed(evt);

		}
	});

	updateMenuItem.addActionListener(new java.awt.event.ActionListener(){
		public void actionPerformed(java.awt.event.ActionEvent evt){
			updateMenuActionPerformed(evt);
		}
	});
	
	addItem.addActionListener(new java.awt.event.ActionListener(){
		public void actionPerformed(java.awt.event.ActionEvent evt){
			addItemActionPerformed(evt);
		}
	});
	
	removeItem.addActionListener(new java.awt.event.ActionListener(){
		public void actionPerformed(java.awt.event.ActionEvent evt){
			removeItemActionPerformed(evt);
		}
	});
	
	home.addActionListener(new java.awt.event.ActionListener(){
		public void actionPerformed(java.awt.event.ActionEvent evt){
			homeButtonActionPerformed(evt);
		}
	});

    // layout
    GroupLayout layout = new GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setAutoCreateGaps(true);
    layout.setAutoCreateContainerGaps(true);

    layout.setHorizontalGroup(layout.createSequentialGroup()
        .addGroup(
        		layout.createParallelGroup()
                .addComponent(home)
        		.addComponent(viewMenu)
        		.addComponent(addItem)
        		.addComponent(updateMenuItem)
        		.addComponent(removeItem))

        );      

    layout.setVerticalGroup(layout.createSequentialGroup()
         .addGroup(layout.createParallelGroup()
            	.addComponent(home))	
        .addGroup(layout.createParallelGroup()
        		.addComponent(viewMenu))
        .addGroup(layout.createParallelGroup()
        		.addComponent(updateMenuItem))
        .addGroup(layout.createParallelGroup()
        		.addComponent(addItem))
        .addGroup(layout.createParallelGroup()
        		.addComponent(removeItem)));
    pack();
  }

protected void removeItemActionPerformed(ActionEvent evt) {
	// TODO Auto-generated method stub
	new RemoveMenuItemPage().setVisible(true);
	this.setVisible(false);
}

protected void addItemActionPerformed(ActionEvent evt) {
	// TODO Auto-generated method stub
	new AddMenuItemPage().setVisible(true);
	this.setVisible(false);
}

protected void updateMenuActionPerformed(ActionEvent evt) {
	// TODO Auto-generated method stub
	new UpdateMenuItemPage().setVisible(true);
	this.setVisible(false);
}

protected void viewMenuActionPerformed(ActionEvent evt) {
	// TODO Auto-generated method stub
	new ViewMenuPage().setVisible(true);
	this.setVisible(false);
}

protected void homeButtonActionPerformed(ActionEvent evt) {
	// TODO Auto-generated method stub
	new RestoHomePage().setVisible(true);
	this.setVisible(false);
}

private void refreshData() {
    pack();
  }  
  
  
}
