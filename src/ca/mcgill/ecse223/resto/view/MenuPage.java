package ca.mcgill.ecse223.resto.view;

import java.util.ArrayList;

import javax.swing.GroupLayout;
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

  private JLabel menuLabel, appzsLabel, mainsLabel, dessertsLabel, alcBevLabel, nonAlcBevLabel;

  public MenuPage() {
    //this.resto = r;
    initComponents();
    this.setSize(1400, 500);
  }

  private void initComponents() {
    // elements
    menuLabel = new JLabel();
    appzsLabel = new JLabel();
    mainsLabel = new JLabel();
    dessertsLabel = new JLabel();
    alcBevLabel = new JLabel();
    nonAlcBevLabel = new JLabel();

    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setTitle("Menu Page");

    ArrayList<String> menuValues = new ArrayList<String>();
    for (MenuItem.ItemCategory itemCat : Controller.getItemCategories()) {
      try {
        menuValues.add(menuItemsToString(Controller.getMenuItems(itemCat)));
      } catch (InvalidInputException e) {
        e.printStackTrace();
      }
    }

    // Set text
    menuLabel.setText("MENU");
    appzsLabel.setText("Appetizers: \n" + menuValues.get(0));
    mainsLabel.setText("Mains: \n" + menuValues.get(1));
    dessertsLabel.setText("Desserts: \n" + menuValues.get(2));
    alcBevLabel.setText("Alcoholic beverages: \n" + menuValues.get(3));
    nonAlcBevLabel.setText("Non-Alcoholic Beverages: \n" + menuValues.get(4));


    // layout
    GroupLayout layout = new GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setAutoCreateGaps(true);
    layout.setAutoCreateContainerGaps(true);

    layout.setHorizontalGroup(layout.createSequentialGroup()
        .addGroup(layout.createParallelGroup().addGap(5).addComponent(menuLabel).addGap(5).addComponent(appzsLabel).addComponent(mainsLabel).addGap(5).addComponent(dessertsLabel).addComponent(alcBevLabel)
            .addComponent(nonAlcBevLabel))

        );      

    layout.setVerticalGroup(layout.createSequentialGroup()
        .addGroup(layout.createParallelGroup().addComponent(menuLabel).addGap(5))
        .addGroup(layout.createParallelGroup().addComponent(appzsLabel).addGap(5))
        .addGroup(layout.createParallelGroup().addComponent(dessertsLabel))
        .addGroup(layout.createParallelGroup().addComponent(mainsLabel).addGap(5))
        .addGroup(layout.createParallelGroup().addComponent(alcBevLabel))
        .addGroup(layout.createParallelGroup().addGap(5).addComponent(nonAlcBevLabel)));
    pack();
  }

  private void refreshData() {
    pack();
  }

  private String menuItemsToString(ArrayList<MenuItem> menuItems) {
    String returned = "";
    for (MenuItem mI : menuItems) {
      returned += (mI.getName() + " : " + mI.getCurrentPricedMenuItem().getPrice() + "\n");
    }
    return returned;
  }
}
