package ca.mcgill.ecse223.resto.view;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

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

public class NonAlcBevPage extends JFrame {

  /**
   * 
   */
  private static final long serialVersionUID = -7403802774454467836L;

  //private RestoApp resto;

  private JLabel nonAlcBevLabel;
  private JButton backButton;
  private List<String> menuValues;
  
  public NonAlcBevPage() {
    //this.resto = r;
    initComponents();
    this.setSize(1400, 500);
  }

  private void initComponents() {
    // elements
    nonAlcBevLabel = new JLabel();
    
    menuValues = new ArrayList<String>();
    backButton= new JButton();
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setTitle("Alcoholic Beverages");

    
    for (MenuItem.ItemCategory itemCat : Controller.getItemCategories()) {
      try {
    	  //System.out.println(menuItemsToString(Controller.getMenuItems(itemCat)));
    	  menuValues.add(menuItemsToString(Controller.getMenuItems(itemCat)));
      } catch (InvalidInputException e) {
        e.printStackTrace();
      }
    }

    // Set text
    backButton.setText("BACK");
    //System.out.println(menuValues.get(0));
    nonAlcBevLabel.setText(menuValues.get(4));
    
    backButton.addActionListener(new java.awt.event.ActionListener(){
		public void actionPerformed(java.awt.event.ActionEvent evt){
			backButtonActionPerformed(evt);
		}

	});
	
    // layout
    GroupLayout layout = new GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setAutoCreateGaps(true);
    layout.setAutoCreateContainerGaps(true);

    layout.setHorizontalGroup(layout.createSequentialGroup()
        .addGroup(layout.createParallelGroup().addGap(5).addComponent(nonAlcBevLabel).addGap(5).addComponent(backButton, 60,60,100))
        );      

    layout.setVerticalGroup(layout.createSequentialGroup()
        .addGroup(layout.createParallelGroup().addComponent(nonAlcBevLabel).addGap(5))
        .addGroup(layout.createParallelGroup().addComponent(backButton, 30, 30, 30)));
    pack();
  }


private void backButtonActionPerformed(ActionEvent evt) {
	// TODO Auto-generated method stub
	new MenuPage().setVisible(true);
}

private void refreshData() {
    pack();
}

  private String menuItemsToString(ArrayList<MenuItem> menuItems) {
    String returned = "";
    for (MenuItem mI : menuItems) {
      returned += (mI.getName() + " : " + mI.getCurrentPricedMenuItem().getPrice() + "             ");
    }
    
    //System.out.println(returned);
    return returned;
  }
  
  
  
}
