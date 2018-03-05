package ca.mcgill.ecse223.resto.view;

import java.awt.Color;
import java.util.HashMap;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
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



    // layout
    GroupLayout layout = new GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setAutoCreateGaps(true);
    layout.setAutoCreateContainerGaps(true);
    layout.setHorizontalGroup(
        layout.createParallelGroup(Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()

            .addGroup(layout.createParallelGroup(Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(updateTableLocationButton)
                    )

                .addGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(newYLabel)
                            .addComponent(newYTextField, 0, 0, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(newXLabel)
                            .addComponent(newYTextField, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)))
                    .addComponent(tableNumberLabel)
                    .addComponent(tableNumberList, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
                    )))
        );

    layout.setVerticalGroup(
        layout.createParallelGroup(Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()

            .addGroup(layout.createParallelGroup(Alignment.BASELINE)
                .addComponent(newXLabel)
                .addComponent(newXTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(tableNumberList, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(tableNumberLabel)
                )

            .addGroup(layout.createParallelGroup(Alignment.BASELINE)
                .addComponent(newYLabel)
                .addComponent(newYTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
            .addComponent(updateTableLocationButton)
            )
        );
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
}
