package ca.mcgill.ecse223.resto.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.text.DateFormat;
import java.time.LocalTime;
import java.text.SimpleDateFormat;
import ca.mcgill.ecse223.resto.application.RestoAppApplication;
import ca.mcgill.ecse223.resto.model.MenuItem;
import ca.mcgill.ecse223.resto.model.Order;
import ca.mcgill.ecse223.resto.model.Reservation;
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
    boolean test = r.addCurrentTable(table);
    System.out.println("ADD TEST: " + test);
    
    for(int i =0; i< numberOfSeats; i++){
      Seat seat = table.addSeat();
      table.addCurrentSeat(seat);
    }
    System.out.println(r.getCurrentTables().size());

    RestoAppApplication.save();
  }

  public static void removeTable(Table table) throws InvalidInputException {

	if(table == null) {
		throw new InvalidInputException("Null table");
	}
	  
    if(table.hasReservations()) {
        throw new InvalidInputException("Table is reserved");
      }
    
	//RestoAppApplication.load();
    RestoApp r = RestoAppApplication.getRestoApp();

    System.out.println("before remove size :" + r.getCurrentTables().size());

    List<Order> currentOrder = r.getCurrentOrders();
    
    for(Order order : currentOrder) {
    	
      List<Table> tables = order.getTables();

      boolean inUse = tables.contains(table);

      if(inUse) {
        throw new InvalidInputException("Table is in use");
      }
    }
    boolean test;
    test = r.removeCurrentTable(table);
    RestoAppApplication.save();
    
    System.out.println("after remove size :" + r.getCurrentTables().size() + "done? " + test);
    System.out.println("RRRRRRRRR: " + r.getCurrentTable(0).getNumber());
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

//public static void reserve(Date date, Time time, int numberInParty, String contactName, String contactEmailAddress, String contactPhoneNumber, List<Table> tables) {
//	Date currentDate = new Date();   // the waiter doesn`t enter the seconds, how to handle that?
//	LocalTime currentTime = LocalTime.now();
//	int dateDifference = date.compareTo(currentDate);
//	int timeDifference = time.compareTo(currentTime);
//	
//	if(((date || time || contactName || contactEmailAddress || contactPhoneNumber) = null) || (dateDifference <= 0) || (timeDifference <= 0) || (numberInparty <= 0) ||((contactName || contactEmailAddress || contactPhoneNumber)="")) {
//			throw new InvalidInputException("Please Check the entries for errors!");
//	}
//	
//	RestoApp r = RestoAppApplication.getRestoApp();
//	List<Table> currentTables = r.getCurrentTables();
//	
//	int seatCapacity = 0;
//	for(Table table : tables) {
//		  if(!currentTables.contains(table)) {
//			  throw new Exception("Table does not exist");
//		  }													// Not sure after this point
//		  seatCapacity += table.numberOfCurrentSeats();
//		  List<Reservation> reservations = table.getReservations();
//		  for(Reservation reservation : reservations) {
//			  if(reservation.doesOverlap(date, time)) {			//WRITE THE OVERLAP CODE IN RESERVATION CLASS
//				  throw new InvalidInputException("Overlap!");
//			  }
//		  }
//	  }
//	 if(seatCapacity < numberInParty) {
//		 throw new InvalidInputException("Seat capacity can`t be smaller than number in party!");
//	 }
//	 												// Not sure about converting the list into an array before the constructor part
//	 Table[] tableArray = ((reservation.getTables()).toArray());
//	 Reservation res = new Reservation(date, time, numberInParty, contactName, contactEmailAddress, contactPhoneNumber, r, tableArray);
//	 												// Didn`t sort the list
//	 RestoAppApplication.save();
//}
	
//  public static void startOrder(List<Table> tableNums) throws InvalidInputException{
//		RestoApp r = RestoAppApplication.getRestoApp();
//			
//		System.out.println("WWWWWWWWWWWWWWWWWW" + r.getCurrentOrders().size());
//
//		
//		if (tableNums.size() == 0) {
//			throw new InvalidInputException("tables is null");
//		}
//		
//		List<Table> currentTables = r.getCurrentTables();
//		for (Table b : tableNums) {
//			if(!currentTables.contains(b)) {
//				throw new InvalidInputException("table not in currentTables");
//			}
//		}
//		boolean orderCreated = false;
//		Order lastOrder = null;
//		Order newOrder = null;
//		for (Table c : tableNums) {
//			System.out.println("lalallaalal before: " + c.numberOfOrders());
//			if (orderCreated) {
//				List<Order> cOrders = c.getOrders();
//				for (Order o : cOrders) {
//					System.out.println("aaaaaaaaaaaa");
//
//					c.addToOrder(newOrder);
//					orderCreated = true;
//					newOrder = o;
//				}	
//			}else {
//				System.out.println("lalallaalal middle0: " + c.numberOfOrders());
//
//				c.startOrder();
//				System.out.println("XXXXXXXXXXXXXXXXXXXX");
//				System.out.println("lalallaalal middle1: " + c.numberOfOrders());
//
//			}
//			if(c.numberOfOrders() > 0) {
//				System.out.println("bbbbbbbbbbb");
//				System.out.println("lalallaalal middle2: " + c.numberOfOrders());
//
//				lastOrder = c.getOrder(c.numberOfOrders() -1);
//			}
//			if(c.numberOfOrders() > 0 && !c.getOrder(c.numberOfOrders() - 1).equals(lastOrder)) {
//				orderCreated = true;
//				System.out.println("ccccccccccccccc");
//
//				newOrder = c.getOrder(c.numberOfOrders() -1);
//			}
//			System.out.println("lalallaalal after : " + c.numberOfOrders());
//
//					
//		}
//		
//		  if(orderCreated == false) {
//		  throw new InvalidInputException("Order not created");
//	  }
//		
//		System.out.println("QQQQQQQQQQQQQ" + r.getCurrentOrders().size());
//		
//		r.addCurrentOrder(newOrder);
//		RestoAppApplication.save();
//		
//	}
  
  //ours
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
		  System.out.println("lalallaalal middle0: " + t.numberOfOrders());

		  	  if(orderCreated) {
		  		  t.addToOrder(newOrder);
		  		  orderCreated = true;
		  		  System.out.println("XXXXXXXXXXXX");
		  	  }
		  	  else {
		  		  
		  		  System.out.println("lalallaalal middle0: " + t.numberOfOrders());

		  		  Order lastOrder = null;
		  		  if(t.numberOfOrders()>0) {
		  			  lastOrder = t.getOrder(t.numberOfOrders()-1);
		  			  System.out.println("aaaaaaaaaaaaaaaaa");
		  		  }
		  		  
		  		  System.out.println("lalallaalal middle1: " + t.numberOfOrders());
		  		  t.startOrder();
		  		  System.out.println("lalallaalal middle2: " + t.numberOfOrders());

		  		  if(t.numberOfOrders()>0 && !t.getOrder(t.numberOfOrders()-1).equals(lastOrder)) {
		  			  orderCreated = true;
		  			  newOrder = t.getOrder(t.numberOfOrders()-1);
		  			  System.out.println("bbbbbbbbbbbbbbbb");
		  		  }
		  		  System.out.println("lalallaalal middle3: " + t.numberOfOrders());

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
//	  Table table1 = tables.get(0);
//	  table1.endOrder(order);
	  for(int i =0; i < tables.size(); i++) {
		  tables.get(i).endOrder(order);
	  }
	  
//	  for(Table table : tables) {
//		  System.out.println(tables.size() + "SSSSSIIIIIIZZZZEEEE");
//		  table.endOrder(order);
//		  
//	  }
	  
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
  
  public static List<Table> getCurrentTables() {
	  RestoAppApplication.load();
	  return RestoAppApplication.getRestoApp().getCurrentTables();
  }	
}
