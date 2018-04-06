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
import ca.mcgill.ecse223.resto.model.Bill;
import ca.mcgill.ecse223.resto.model.Menu;
import ca.mcgill.ecse223.resto.model.MenuItem;
import ca.mcgill.ecse223.resto.model.MenuItem.ItemCategory;
import ca.mcgill.ecse223.resto.model.Order;
import ca.mcgill.ecse223.resto.model.OrderItem;
import ca.mcgill.ecse223.resto.model.PricedMenuItem;
import ca.mcgill.ecse223.resto.model.Reservation;
import ca.mcgill.ecse223.resto.model.RestoApp;
import ca.mcgill.ecse223.resto.model.Seat;
import ca.mcgill.ecse223.resto.model.Table;
import ca.mcgill.ecse223.resto.model.Table.Status;
import ca.mcgill.ecse223.resto.persistence.PersistenceObjectStream;

public class Controller {
	public static void addMenuItem(String name, ItemCategory category, double price) throws InvalidInputException{
		 if(name == null || name.isEmpty() || category.equals(null) || price < 0){
		      throw new InvalidInputException("Invalid arguments");
		    }
		 RestoApp r = RestoAppApplication.getRestoApp();	
		 Menu menu = r.getMenu();
		 MenuItem menuItem = new MenuItem(name, menu);
		  try{
		      menu.addMenuItem(menuItem);
		    }
		    catch(RuntimeException e){
		      throw new InvalidInputException("Name is duplicate");
		 		 }
	             menuItem.setItemCategory(category);
	             PricedMenuItem pmi = menuItem.addPricedMenuItem(price, r);
	             menuItem.setCurrentPricedMenuItem(pmi);
	             RestoAppApplication.save();
	}



     public static void removeMenuItem(MenuItem menuItem) throws InvalidInputException{
	 if(menuItem.equals(null)){
	      throw new InvalidInputException("Invalid arguments");
	    }
	 boolean current = menuItem.hasCurrentPricedMenuItem();
	 if(current == false) {
	      throw new InvalidInputException("error");
	 }
	 menuItem.setCurrentPricedMenuItem(null);
     RestoAppApplication.save();	 
   }

     public static void updateMenuItem(MenuItem menuItem, String name, ItemCategory category, double price) throws InvalidInputException{
	 if(name == null || menuItem == null || name.isEmpty() || category.equals(null) || price < 0){
	      throw new InvalidInputException("Invalid arguments");
	    }
	 boolean current = menuItem.hasCurrentPricedMenuItem();

	    if(current==false){
	      throw new InvalidInputException("Invalid Input");
	    }
     boolean duplicate = menuItem.setName(name);
     
     if(duplicate == false) {
    	 throw new InvalidInputException("Invalid Input");
     }
     menuItem.setItemCategory(category);
     
     if (price != menuItem.getCurrentPricedMenuItem().getPrice()) {
    	 RestoApp r = RestoAppApplication.getRestoApp();	
    	 PricedMenuItem pmi = menuItem.addPricedMenuItem(price, r);
         menuItem.setCurrentPricedMenuItem(pmi);
     }
     RestoAppApplication.save();
  }
     



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
      if (x<0 || y<0) {
        throw new InvalidInputException("Coordinate(s) should be greater than 0"); 

      }

      int width = table.getWidth();
      int length = table.getLength();
      if ((table.doesOverlap(x,y, width, length))) {
        throw new InvalidInputException("New location overlaps with at least another table");
      }

      table.setX(x);
      table.setY(y);

      RestoAppApplication.save();
    } catch (RuntimeException e) {
      throw new InvalidInputException(e.getMessage());
    }

  }  
  
    public static void reserve(Date date, Time time, int numberInParty, String contactName, String contactEmailAddress, String contactPhoneNumber, List<Table> tables) throws Exception {
        if(date == null || time == null || contactName == null || contactEmailAddress == null || contactPhoneNumber == null || numberInParty<0 || contactName == "" || contactEmailAddress == "" || contactPhoneNumber == "") { //|| new java.sql.Date(System.currentTimeMillis()).after(date) ||  new java.sql.Time(System.currentTimeMillis()).after(time)) {
            throw new InvalidInputException("input not valid");
        }
        RestoApp r = RestoAppApplication.getRestoApp();
        List<Table> currentTables = r.getCurrentTables();
        int seatCapacity = 0;
        for(Table t : tables) {
            if(!currentTables.contains(t)) {
                throw new Exception("Table does not exist");
            }
            seatCapacity += t.numberOfCurrentSeats();
            List<Reservation> reservations = t.getReservations();
//            for(Reservation reservation : reservations) {
//                if(reservation.doesOverlap(date, time)) {
//                    throw new Exception("reservation has to be 2 hours before or after any other reservation");
//                    
//                }
//            }
        }
        if(seatCapacity < numberInParty) {
            throw new Exception("Seat capacity is less than number of people");
        }
        Table[] tableArray = new Table[tables.size()];
        //tableArray = (Table[]) tables.toArray();
        
        int i=0;
        for(Table t : tables) {
        	tableArray[i] = t;
        	i++;
        }
        Reservation res = new Reservation(date, time, numberInParty, contactName, contactEmailAddress, contactPhoneNumber, r, tableArray);
        r.addReservation(res);
        //Array.sort(tableArray);
        
        System.out.println(r.getReservations().size() + "..................");
        RestoAppApplication.save();
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
		  //System.out.println("lalallaalal middle0: " + t.numberOfOrders());

		  	  if(orderCreated) {
		  		  t.addToOrder(newOrder);
		  		  orderCreated = true;
		  		 // System.out.println("XXXXXXXXXXXX");
		  	  }
		  	  else {
		  		  
		  		  //System.out.println("lalallaalal middle0: " + t.numberOfOrders());

		  		  Order lastOrder = null;
		  		  if(t.numberOfOrders()>0) {
		  			  lastOrder = t.getOrder(t.numberOfOrders()-1);
		  			  //System.out.println("aaaaaaaaaaaaaaaaa");
		  		  }
		  		  
		  		  //System.out.println("lalallaalal middle1: " + t.numberOfOrders());
		  		  t.startOrder();
		  		  //System.out.println("lalallaalal middle2: " + t.numberOfOrders());

		  		  if(t.numberOfOrders()>0 && !t.getOrder(t.numberOfOrders()-1).equals(lastOrder)) {
		  			  orderCreated = true;
		  			  newOrder = t.getOrder(t.numberOfOrders()-1);
		  			  //System.out.println("bbbbbbbbbbbbbbbb");
		  		  }
		  		  //System.out.println("lalallaalal middle3: " + t.numberOfOrders());

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

  public static ArrayList<MenuItem.ItemCategory> getItemCategories() {
    MenuItem.ItemCategory[] itemCategories = MenuItem.ItemCategory.values();
    ArrayList<MenuItem.ItemCategory> storingArray = new ArrayList<MenuItem.ItemCategory>();
    for (MenuItem.ItemCategory itemCategory : itemCategories) {
      storingArray.add(itemCategory);
    }
    return storingArray;
  }

  public static ArrayList<MenuItem> getMenuItems(MenuItem.ItemCategory itemCategory) throws InvalidInputException {

    if (itemCategory.equals(null)) {
      throw new InvalidInputException("Invalid input");
    }
    
    RestoApp r = RestoAppApplication.getRestoApp();
    ArrayList<MenuItem> storingArray = new ArrayList<MenuItem>();
    
    List<MenuItem> menuItems = r.getMenu().getMenuItems();
    for (MenuItem menuItem : menuItems) {
      MenuItem.ItemCategory category = menuItem.getItemCategory();
      if (menuItem.hasCurrentPricedMenuItem() && category.equals(itemCategory)) {
        storingArray.add(menuItem);
      }
    }       
    return storingArray;
  }

  public static List<Table> getCurrentTables() {
    RestoAppApplication.load();
    return RestoAppApplication.getRestoApp().getCurrentTables();
  }
  
  
  public static void cancelOrderedItem(OrderItem oi) throws InvalidInputException{
	  RestoApp r = RestoAppApplication.getRestoApp();
	  
	  if(oi.equals(null)){
		  throw new InvalidInputException("Invalid input");
	  }
	  
	  List<Seat> seats = oi.getSeats();
	  
	  Order order = oi.getOrder(); 	 
	  
	  ArrayList<Table> tables = new ArrayList<Table>();
	  for(Seat seat: seats){
		  Table table = seat.getTable();
		  Order lastOrder = table.getOrder(table.numberOfOrders()-1);
		  if(lastOrder.equals(order) && !tables.contains(table)){
			  tables.add(table);
		  }
	  }
	  
	  for(Table table: tables){
		  table.cancelOrderItem(oi);
	  }
	  
	  RestoAppApplication.save();

  }
  
  public static void cancelOrder(Table table) throws InvalidInputException{
	  
	  RestoApp r = RestoAppApplication.getRestoApp();
	  
	  if(table.equals(null)){
		  throw new InvalidInputException("Null table");
	  }
	  
	  List<Table> currentTables = r.getCurrentTables();
	  	  
	  if(!currentTables.contains(table)){  
		  throw new InvalidInputException("Table does not exist");
	  }
	  
	  table.cancelOrder();
	  
	  RestoAppApplication.save();
	 
  }
   
    public static List<OrderItem>getOrderItems(Table table) throws Exception {
    	if(table == null) {
    		throw new Exception ("Null table"); 
    	}
	    RestoApp r = RestoAppApplication.getRestoApp();
	    List<Table> currentTables = r.getCurrentTables();
	    if(!currentTables.contains(table)) {
	    	throw new InvalidInputException("Table should be valid"); 
	    }
	    if (table.getStatus() == Status.Available) {
	    	throw new InvalidInputException("Table is available");
	    }
	    Order lastOrder = null;
	    if(table.numberOfOrders()>0) {
	    	lastOrder = table.getOrder(table.numberOfOrders()-1);
	    }
	    else {
	    	throw new InvalidInputException("Table has no orders");
	    }
	    
	    List<Seat> currentSeats = table.getCurrentSeats();
	    ArrayList<OrderItem> result = new ArrayList<OrderItem>(); 
	    for (Seat seat : currentSeats) {
	    	List<OrderItem> orderItems = seat.getOrderItems();
	    	for (OrderItem orderItem : orderItems) {
	    		Order order = orderItem.getOrder();
	    		if(lastOrder.equals(order)&& !result.contains(orderItem)) {
	    			result.add(orderItem);
	    		}
	    	}
	    }
	    
	    return result;
  }
    
    public static void orderItem(MenuItem item, int quantity, List<Seat> seatsList) throws Exception {
  	  
  	  //throw exception if seatsList or item is null/empty
  	  if(item == null) {
  		  throw new InvalidInputException("Null item");
  	  }
  	  if(seatsList == null) {
  		throw new InvalidInputException("Null seats list");
  	  }
  	  if(seatsList.size() == 0) {
  		throw new InvalidInputException("No seats selected!");
  	  }
  	  if(quantity <= 0) {
  		throw new InvalidInputException("Invalid quantity (quantity should be a positive value)");
  	  }
  	  
  	  RestoApp r = RestoAppApplication.getRestoApp();
  	  
  	  if(!item.hasCurrentPricedMenuItem()) {
    		throw new InvalidInputException("Selected item is not available");
  	  }
  	  
  	  Order lastOrder = null;
  	  
  	  for (Seat seat : seatsList) {
  		  Table table = seat.getTable();
  		  
  		  if(!r.getCurrentTables().contains(table)) {
  			  throw new InvalidInputException("Table is not a currentTable");
  		  }
  		  
  		  if(!table.getCurrentSeats().contains(seat)) {
  			  throw new InvalidInputException("Seat does not belong to the current table");
  		  }
  		  
  		  if(lastOrder == null) {
  			  if(table.numberOfOrders() > 0) {
  				  lastOrder = table.getOrder(table.numberOfOrders()-1);
  			  }
  			  else {
  				  throw new InvalidInputException("Order for the table has not started");
  			  }
  		  }
  		  else {
  			  Order comparedOrder = null;
  			  if(table.numberOfOrders() > 0) {
  				  comparedOrder = table.getOrder(table.numberOfOrders()-1);
  			  }
  			  else {
  				  throw new InvalidInputException("Order for table does not exist");
  			  }
  			  
  			  if(!comparedOrder.equals(lastOrder)) {
  				  throw new InvalidInputException("Order not updated");
  			  }
  		  }
  		  
  	  }
  	  
  	  if(lastOrder == null) {
  		  throw new InvalidInputException("Last order is null");
  	  }
  	  
  	  PricedMenuItem pmi = item.getCurrentPricedMenuItem();
  	  boolean itemCreated = false;
  	  OrderItem newItem = null;
  	  
  	  for(Seat seat : seatsList) {
  		  Table table = seat.getTable();
  		  
  		  if(itemCreated) {
  			  table.addToOrderItem(newItem, seat);
  		  }
  		  else {
  			  OrderItem lastItem = null;
  			  if(lastOrder.numberOfOrderItems() > 0) {
  				  lastItem = lastOrder.getOrderItem(lastOrder.numberOfOrderItems()-1);
  			  }
  			  table.orderItem(quantity, lastOrder, seat, pmi);
  			  
  			  if(lastOrder.numberOfOrderItems() > 0 && !lastOrder.getOrderItem(lastOrder.numberOfOrderItems()-1).equals(lastItem)) {
  				  itemCreated = true;
  				  newItem = lastOrder.getOrderItem(lastOrder.numberOfOrderItems()-1);
  			  }
  		  }
  	  }
  	  
  	  if(!itemCreated) {
  		  throw new Exception("Unaable to create new item");
  	  }
  	  
  	  RestoAppApplication.save();
    }
	
	public static void issueBill(List<Seat> seats) throws InvalidInputException {
    if (seats.equals(null)) {
      throw new InvalidInputException("Seats is null");
    }
    if (seats.size() == 0) {
      throw new InvalidInputException("Seats list is empty");
    }
    RestoApp r = RestoAppApplication.getRestoApp();
    List<Table> currentTables = r.getCurrentTables();
    Order lastOrder = null;
    for(Seat seat: seats) {
      Table table = seat.getTable();
      Boolean current = currentTables.contains(table);
      if (current == false) {
        throw new InvalidInputException("At least a table is not in use"); 
      }
      List<Seat> currentSeats = table.getCurrentSeats();
      Boolean current2 = currentSeats.contains(seat);
      if (current2 == false) {
        throw new InvalidInputException("At least a seat is not in use"); 
      }
      if(lastOrder == null) {
        if(table.numberOfOrders() > 0) {
          lastOrder = table.getOrder(table.numberOfOrders()-1);
        }
        else { 
          throw new InvalidInputException("Table has no associated order");
        }
      }
      else {
        Order comparedOrder = null;
        if (table.numberOfOrders() > 0) {
          comparedOrder = table.getOrder(table.numberOfOrders()-1);
        }
        else { 
          throw new InvalidInputException("Table has no associated order");
        }
        if (!comparedOrder.equals(lastOrder)) {
          throw new InvalidInputException("Cannot issue bill for two different orders");
        }
      }
    }
    if (lastOrder == null) {
      throw new InvalidInputException("Last order is null");
    }
    Boolean billCreated = false;
    Bill newBill = null;
    for (Seat seat: seats) {
      Table table = seat.getTable();
      if (billCreated) {
        table.addToBill(newBill, seat);
      }
      else {
        Bill lastBill = null;
        if(lastOrder.numberOfBills() > 0) { 
          lastBill = lastOrder.getBill(lastOrder.numberOfBills()-1);
        }
        table.billForSeat(lastOrder, seat);
        if (lastOrder.numberOfBills() > 0 && !lastOrder.getBill(lastOrder.numberOfBills()-1).equals(lastBill)) {
          billCreated = true;
          newBill = lastOrder.getBill(lastOrder.numberOfBills()-1);
        }
      }
    }
    if (billCreated == false) {
      throw new InvalidInputException("Bill has not been created");
    }
    RestoAppApplication.save();
  }

  
}
