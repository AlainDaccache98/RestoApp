package ca.mcgill.ecse223.resto.controller;

import java.util.List;

import ca.mcgill.ecse223.resto.application.RestoAppApplication;
import ca.mcgill.ecse223.resto.model.Order;
import ca.mcgill.ecse223.resto.model.RestoApp;
import ca.mcgill.ecse223.resto.model.Seat;
import ca.mcgill.ecse223.resto.model.Table;

public class Controller {


	

	public static List<Table> getTables() {
		return RestoAppApplication.getRestoApp().getTables();
	}		

  public static void createTable(int number, int x, int y, int width, int length, int numberOfSeats) throws InvalidInputException {
    RestoApp r = RestoAppApplication.getRestoApp();

    List<Table> currentTables = r.getCurrentTables();

    for( Table currentTable : currentTables){
      if(currentTable.doesOverlap(x, y, width, length)){
        throw new InvalidInputException("Overlapped");
      }
    }

    Table table = new Table(number, x,y, width, length, r);
    r.addCurrentTable(table);
    for(int i =0; i< numberOfSeats; i++){
      Seat seat = table.addSeat();
      table.addCurrentSeat(seat);
    }

  }

  public static void removeTable(Table table) throws InvalidInputException {

    RestoApp r = RestoAppApplication.getRestoApp();

    if(table.getReservations() != null) {
      throw new InvalidInputException("Table is reserved");
    }

    for(Order order : r.getCurrentOrders()) {
      List<Table> tables = order.getTables();

      boolean inUse = tables.contains(table);

      if(inUse) {
        throw new InvalidInputException("Table is in use");
      }
    }
    r.removeCurrentTable(table);

    //persistence XStream save
  }


  public static void updateTable(Table table, int newNumber, int numberOfSeats ) throws InvalidInputException{

    //update table number
    if(table==null || newNumber < 0 || numberOfSeats<0){
      throw new InvalidInputException("Invalid arguments");
    }

    boolean reserved = table.hasReservations();

    if(reserved==true){
      throw new InvalidInputException("Table already reserved");
    }


    RestoApp r = RestoAppApplication.getRestoApp();

    List<Order> currentOrders = r.getCurrentOrders();

    for(Order order: currentOrders){
      List<Table> tables = order.getTables();
      boolean inUse = tables.contains(table);

      if(inUse==true){
        throw new InvalidInputException("Table already in use");

      }
    }

    try{
      table.setNumber(newNumber);
    }
    catch(RuntimeException e){
      throw new InvalidInputException("New table number has already been assigned to a table");
    }

    //update number of seats

    //add seat
    int n = table.numberOfCurrentSeats();

    if(numberOfSeats-n >0){
	    for(int i=0; i<(numberOfSeats-n);i++){
	      Seat seat = table.addSeat();
	      table.addCurrentSeat(seat);
	    }
    }

    else if(numberOfSeats-n <0){
	    //remove seat
	    for(int i=1; i<(n-numberOfSeats);i++){
	      Seat seat = table.getCurrentSeat(0);
	      table.removeCurrentSeat(seat);
	    }			
    }

  }		
  public static void moveTable(Table table, int x, int y) throws InvalidInputException   { 
    try {
      int width = table.getWidth();
      int length = table.getLength();
      RestoApp r = RestoAppApplication.getRestoApp();

      List<Table> currentTables = r.getCurrentTables();
      if (table == null) {
        throw new InvalidInputException("Select a valid table."); 
      } 
      if (x<0) {
        throw new InvalidInputException("x cannot be negative"); 

      }
      if (y<0) {
        throw new InvalidInputException("y cannot be negative"); 
      }
      boolean overlaps = table.doesOverlap(x, y, width, length);
      if (overlaps == true) {
        throw new InvalidInputException("The target location of the table to move overlaps with at least another table");
      }
      table.setX(x);
      table.setY(y);
      //RestoAppApplication.save();
    } catch (RuntimeException e) {
      throw new InvalidInputException(e.getMessage());
    }
  }

}
