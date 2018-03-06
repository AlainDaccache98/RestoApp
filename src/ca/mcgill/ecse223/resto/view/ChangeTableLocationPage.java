package ca.mcgill.ecse223.resto.view;

import java.awt.Color;
import java.util.HashMap;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import ca.mcgill.ecse223.resto.application.RestoAppApplication;
import ca.mcgill.ecse223.resto.controller.Controller;
import ca.mcgill.ecse223.resto.controller.InvalidInputException;
import ca.mcgill.ecse223.resto.model.RestoApp;
import ca.mcgill.ecse223.resto.model.Table;

public class ChangeTableLocationPage extends JFrame {
  private static final long serialVersionUID = -4426310869335015542L;
  // UI elements
  private JLabel errorMessage;  

  // table number
  private JLabel tableNumberLabel;
  private JComboBox<String> tableNumberList;


  // table location
  private JLabel newXLabel;
  private JTextField newXTextField;

  private JLabel newYLabel;
  private JTextField newYTextField;

  private JButton updateTableLocationButton;

  // data elements
  private String error = null;

  // table selection
  private Integer selectedTable = -1;
  private HashMap<Integer, Table> currentTables;

  public ChangeTableLocationPage() {
    initComponents();
    refreshData();
  }
  private void initComponents() {

    // elements for error message
    errorMessage = new JLabel();
    errorMessage.setForeground(Color.RED);

    // elements for table
    tableNumberLabel = new JLabel();
    newXTextField = new JTextField();
    newXLabel = new JLabel ();
    newYTextField = new JTextField();
    newYLabel = new JLabel();
    updateTableLocationButton = new JButton();
    tableNumberList = new JComboBox<String>(new String[0]);
    tableNumberList.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        JComboBox<String> cb = (JComboBox<String>) evt.getSource();
        selectedTable = cb.getSelectedIndex();
      }
    });

    //global settings and listeners
    tableNumberLabel.setText("Select table number: ");
    newXLabel.setText("New X");
    newYLabel.setText("New Y");
    updateTableLocationButton.setText("Update Table Location");
    updateTableLocationButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        updatetableLocationButtonActionPerformed(evt);
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
        .addComponent(horizontalLineMiddle1)
        .addComponent(horizontalLineMiddle2)
        .addComponent(horizontalLineBottom)

        .addGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup()
                .addComponent(tableNumberLabel)
                .addComponent(newXLabel)
                .addComponent(newYLabel))

            .addGroup(layout.createParallelGroup()
                .addComponent(tableNumberList)
                .addComponent(newXTextField, 200, 200, 400)
                .addComponent(newYTextField, 200, 200, 400)
                .addComponent(updateTableLocationButton, 70, 70, 140))
            )
        );

    //layout.linkSize(SwingConstants.VERTICAL, new java.awt.Component[] {driverNameTextField, addDriverButton});

    layout.setVerticalGroup(
        layout.createParallelGroup()
        .addGroup(layout.createSequentialGroup()
            .addComponent(errorMessage)
            .addGroup(layout.createParallelGroup()
                .addComponent(tableNumberLabel)
                .addComponent(tableNumberList)
                )
            .addGroup(layout.createParallelGroup()
                .addComponent(horizontalLineTop))
            .addGroup(layout.createParallelGroup()
                .addComponent(newXLabel)
                .addComponent(newXTextField, 200, 200, 400)
                )
            .addGroup(layout.createParallelGroup()
                .addComponent(horizontalLineMiddle1))
            .addGroup(layout.createParallelGroup()
                .addComponent(newYLabel)
                .addComponent(newYTextField, 200, 200, 400)
                )
            .addGroup(layout.createParallelGroup()
                .addComponent(horizontalLineMiddle2))
            .addGroup(layout.createParallelGroup()
                .addComponent(updateTableLocationButton, 70, 70, 140)
                )
            .addGroup(layout.createParallelGroup()
                .addComponent(horizontalLineBottom))
            )
        );
    pack();
  }
  private void updatetableLocationButtonActionPerformed(java.awt.event.ActionEvent evt) {
    // clear error message

    int newX = Integer.parseInt(newXTextField.getText());
    int newY = Integer.parseInt(newYTextField.getText());
    String error = "";
    if (selectedTable < 0) {
      error = "Table needs to be selected to update table location! ";
    }
    if (newX < 0 || newY < 0) {
      error = error + "Coordinate(s) cannot be negative!";
    }
    if (error.length() == 0) {
      // call the controller
      try {
        Controller.moveTable(currentTables.get(selectedTable), newX ,newY);
      } catch (InvalidInputException e) {
        error = e.getMessage();
      }
    }
    // update visuals
    refreshData();
  }

  private void refreshData() {
    // error
    errorMessage.setText(error);
    if (error == null || error.length() == 0) {
      // populate page with data
      // new X
      newXTextField.setText("");
      // new Y
      newYTextField.setText("");

      // selected table
      currentTables = new HashMap<Integer, Table>();
      tableNumberList.removeAllItems();
      RestoApp r = RestoAppApplication.getRestoApp();
      int index = 0;
      for (Table table : r.getCurrentTables()) {
        currentTables.put(index, table);
        tableNumberList.addItem("#" + table.getNumber());
        index++;
      };
      selectedTable = -1;
      tableNumberList.setSelectedIndex(selectedTable);
    }
    pack();
  }
}
