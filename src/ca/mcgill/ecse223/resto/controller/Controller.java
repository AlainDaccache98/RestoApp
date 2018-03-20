package ca.mcgill.ecse223.resto.controller;

import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse223.resto.application.RestoAppApplication;
import ca.mcgill.ecse223.resto.model.MenuItem;
import ca.mcgill.ecse223.resto.model.Order;
import ca.mcgill.ecse223.resto.model.RestoApp;
import ca.mcgill.ecse223.resto.model.Seat;
import ca.mcgill.ecse223.resto.model.Table;
import ca.mcgill.ecse223.resto.model.Table.Status;
import ca.mcgill.ecse223.resto.persistence.PersistenceObjectStream;

public class Controller {

  public static void createTable(int number, int x, int y, int width, int length, int numberOfSeats) throws InvalidInputException {
    
	  if(number < 0 || x < 0 || y < 0 ||width < 0 || length < 0 || numberOfSeats < 0) {
  		throw new InvalidInputException("input cant be negative");
	  }
	  
	RestoApp r = RestoAppApplication.getRestoApp();

    System.out.println(r.getCurrentTables().size());
    
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
    System.out.println(r.getCurrentTables().size());

    RestoAppApplication.save();
  }

  public static void removeTable(Table table) throws InvalidInputException {

	RestoAppApplication.load();
    RestoApp r = RestoAppApplication.getRestoApp();

    System.out.println("abcd :" + r.getCurrentTables().size());
    
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

    RestoAppApplication.save();
  }

  public static void updateTable(Table table, int newNumber, int numberOfSeats ) throws InvalidInputException{

    //update table number
    if(table==null || newNumber < 0 || numberOfSeats<0){
      throw new InvalidInputException("Invalid arguments");
    }

    RestoApp r = RestoAppApplication.getRestoApp();
    
    boolean reserved = table.hasReservations();

    if(reserved==true){
      throw new InvalidInputException("Table already reserved");
    }
    
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

    for(int i=0; i<(numberOfSeats-n);i++){
      Seat seat = table.addSeat();
      table.addCurrentSeat(seat);
    }

    //remove seat
    for(int i=1; i<(n-numberOfSeats);i++){
      Seat seat = table.getCurrentSeat(0);
      table.removeCurrentSeat(seat);
    }			

    RestoAppApplication.save();
  }		

  public static void moveTable (Table table, int x, int y) throws InvalidInputException{
	try {
	if (table == null) {
		throw new InvalidInputException("Select a valid table."); 
	} 
	if (x <  0) {
		throw new InvalidInputException("x cannot be negative"); 
		
	}
	if (y<0) {
		throw new InvalidInputException("y cannot be negative"); 
		
	}
	
	RestoApp r =  RestoAppApplication.getRestoApp();
	List<Table> currentTables = r.getCurrentTables();
	
	for (Table currentTable : currentTables){
		if ((currentTable.doesOverlap(x,y, table.getWidth(),table.getLength()))&& !(table.equals(currentTable))) {
			throw new InvalidInputException("Target location overlaps with at least another table");
		}
	}
	table.setX(x);
	table.setY(y);
	
	RestoAppApplication.save();
	} catch (RuntimeException e) {
		throw new InvalidInputException(e.getMessage());
	}
	
}
  
  public static void startOrder(List<Table> tables) throws Exception {
	  
	  if(tables.equals(null)) {
		  throw new InvalidInputException("Null table");
	  }
	  
	  RestoApp r = RestoAppApplication.getRestoApp();
	  
	  List<Table> currentTables = r.getCurrentTables();
	  
	  for(Table t : tables) {
		  if(!currentTables.contains(t)) {
			  throw new Exception("Table does not exist");
		  }
	  }
	  
	  boolean orderCreated = false;
	  Order newOrder = null;
	  
	  for(Table t : tables) {
		  	  if(orderCreated) {
		  		  t.addToOrder(newOrder);
		  	  }
		  	  else {
		  		  Order lastOrder = null;
		  		  if(t.numberOfOrders()>0) {
		  			  lastOrder = t.getOrder(t.numberOfOrders()-1);
		  		  }
		  		  t.startOrder();
		  		  if(t.numberOfOrders()>0 && !t.getOrder(t.numberOfOrders()-1).equals(lastOrder)) {
		  			  orderCreated = true;
		  			  newOrder = t.getOrder(t.numberOfOrders()-1);
		  		  }
		  	  }
	  }
	  
	  if(orderCreated == false) {
		  throw new InvalidInputException("Order not created");
	  }
	  
	  r.addCurrentOrder(newOrder);
	  
	  RestoAppApplication.save();
  }

  public static void endOrder(Order order) throws InvalidInputException {
	  	  
	  if(order == null) {
		  throw new InvalidInputException("Null order");
	  }
	  
	  RestoApp r = RestoAppApplication.getRestoApp();
	  
	  List<Order> currentOrders = r.getCurrentOrders();
	  
	  if(!currentOrders.contains(order)) {
		  throw new InvalidInputException("Order does not exist");
	  }
	  
	  List<Table> tables = order.getTables();
	  
	  boolean orderCreated = false;
	  Order newOrder = null;
	  
	  for(Table table : tables) {
		  table.endOrder(order);
	  }
	  
	  if(allTablesAvailableOrDifferentOrder(tables, order)) {
		  r.removeCurrentOrder(order);
	  }
	  
	  
	  
//	  if(table.getStatus() == Status.NothingOrdered) {
//		  table.cancelOrder();
//		  setSuccess = true;
//	  }
//	  else if (table.getStatus() == Status.Ordered) {
//		  		  
//		  setSuccess = true;
//	  }
//	  
	  RestoAppApplication.save();
  }
  
  public static boolean allTablesAvailableOrDifferentOrder(List<Table> tables, Order order) {
	  RestoApp r = RestoAppApplication.getRestoApp();
	  boolean ans = true;
	  List<Order> currentOrders = r.getCurrentOrders();
	  for(Table t: tables) {
		  if(t.getStatus() != Status.Available) {
			  ans = false;
		  }
	  }
	  if(!currentOrders.contains(order)) {
		  ans = false;
	  }
	return ans;
	
  }
  
  public static ArrayList<MenuItem> getMenuItems(MenuItem.ItemCategory itemCategory) throws InvalidInputException {
		if (itemCategory.equals(null)) {
			throw new InvalidInputException("Invalid input!");
		}
		ArrayList<MenuItem> list = new ArrayList<MenuItem>();
		
		return null;
  }
  
  public static List<Table> getTables() {
		return RestoAppApplication.getRestoApp().getTables();
  }	
}
