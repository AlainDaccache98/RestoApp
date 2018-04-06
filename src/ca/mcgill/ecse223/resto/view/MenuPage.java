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
			new ViewMenuPage().setVisible(true);
		}
	});

	updateMenuItem.addActionListener(new java.awt.event.ActionListener(){
		public void actionPerformed(java.awt.event.ActionEvent evt){
			//new UpdateMenuPage().setVisible(true);
		}
	});
	
	addItem.addActionListener(new java.awt.event.ActionListener(){
		public void actionPerformed(java.awt.event.ActionEvent evt){
			//new AddItemPage().setVisible(true);
		}
	});
	
	removeItem.addActionListener(new java.awt.event.ActionListener(){
		public void actionPerformed(java.awt.event.ActionEvent evt){
			//new RemoveItemPage().setVisible(true);
		}
	});
	
	home.addActionListener(new java.awt.event.ActionListener(){
		public void actionPerformed(java.awt.event.ActionEvent evt){
			new RestoHomePage().setVisible(true);
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

private void refreshData() {
    pack();
  }  
  
  
}
