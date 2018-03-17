/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.27.0.3728.d139ed893 modeling language!*/



// line 2 "RestoStateMachine.ump"
public class Table
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Table State Machines
  public enum Status { Available, InUse }
  public enum StatusInUse { Null, IdleOrder, ItemOrdered, Billed }
  private Status status;
  private StatusInUse statusInUse;

  //Table Do Activity Threads
  Thread doActivityStatusInUseBilledThread = null;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Table()
  {
    setStatusInUse(StatusInUse.Null);
    setStatus(Status.Available);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public String getStatusFullName()
  {
    String answer = status.toString();
    if (statusInUse != StatusInUse.Null) { answer += "." + statusInUse.toString(); }
    return answer;
  }

  public Status getStatus()
  {
    return status;
  }

  public StatusInUse getStatusInUse()
  {
    return statusInUse;
  }

  public boolean groupArrived()
  {
    boolean wasEventProcessed = false;
    
    Status aStatus = status;
    switch (aStatus)
    {
      case Available:
        // line 5 "RestoStateMachine.ump"
        makeTableInUse(table)
        setStatus(Status.InUse);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean orderItem(MenuItem item,Seat... allSeats)
  {
    boolean wasEventProcessed = false;
    
    StatusInUse aStatusInUse = statusInUse;
    switch (aStatusInUse)
    {
      case IdleOrder:
        exitStatusInUse();
        // line 10 "RestoStateMachine.ump"
        addMenuItemForSeats(item, allSeats);
        setStatusInUse(StatusInUse.ItemOrdered);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean makeTableAvailable(String table)
  {
    boolean wasEventProcessed = false;
    
    StatusInUse aStatusInUse = statusInUse;
    switch (aStatusInUse)
    {
      case IdleOrder:
        exitStatus();
        // line 11 "RestoStateMachine.ump"
        deleteCurrentOrder();
        setStatus(Status.Available);
        wasEventProcessed = true;
        break;
      case Billed:
        if (allSeatsBilled())
        {
          exitStatus();
        // line 23 "RestoStateMachine.ump"
          deleteCurrentOrder();
          setStatus(Status.Available);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean cancelItem(MenuItem item,Seat seat)
  {
    boolean wasEventProcessed = false;
    
    StatusInUse aStatusInUse = statusInUse;
    switch (aStatusInUse)
    {
      case ItemOrdered:
        if (itemsOrdered()>0&&!(isBilled(seat)))
        {
          exitStatus();
        // line 15 "RestoStateMachine.ump"
          deleteMenuItemForSeat(seat, item);
          setStatus(Status.InUse);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  private boolean __autotransition5__()
  {
    boolean wasEventProcessed = false;
    
    StatusInUse aStatusInUse = statusInUse;
    switch (aStatusInUse)
    {
      case ItemOrdered:
        if (itemsOrdered()==0)
        {
          exitStatusInUse();
          setStatusInUse(StatusInUse.IdleOrder);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean assignToBill(Seat... allSeats)
  {
    boolean wasEventProcessed = false;
    
    StatusInUse aStatusInUse = statusInUse;
    switch (aStatusInUse)
    {
      case ItemOrdered:
        exitStatusInUse();
        // line 17 "RestoStateMachine.ump"
        createBill(allSeats);
        setStatusInUse(StatusInUse.Billed);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean removeBill(Seat seat)
  {
    boolean wasEventProcessed = false;
    
    StatusInUse aStatusInUse = statusInUse;
    switch (aStatusInUse)
    {
      case Billed:
        if (!(isLastBill()))
        {
          exitStatusInUse();
        // line 22 "RestoStateMachine.ump"
          deleteBill(seat);
          setStatusInUse(StatusInUse.Billed);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  private void exitStatus()
  {
    switch(status)
    {
      case InUse:
        exitStatusInUse();
        break;
    }
  }

  private void setStatus(Status aStatus)
  {
    status = aStatus;

    // entry actions and do activities
    switch(status)
    {
      case InUse:
        if (statusInUse == StatusInUse.Null) { setStatusInUse(StatusInUse.IdleOrder); }
        break;
    }
  }

  private void exitStatusInUse()
  {
    switch(statusInUse)
    {
      case IdleOrder:
        setStatusInUse(StatusInUse.Null);
        break;
      case ItemOrdered:
        setStatusInUse(StatusInUse.Null);
        break;
      case Billed:
        if (doActivityStatusInUseBilledThread != null) { doActivityStatusInUseBilledThread.interrupt(); }
        setStatusInUse(StatusInUse.Null);
        break;
    }
  }

  private void setStatusInUse(StatusInUse aStatusInUse)
  {
    statusInUse = aStatusInUse;
    if (status != Status.InUse && aStatusInUse != StatusInUse.Null) { setStatus(Status.InUse); }

    // entry actions and do activities
    switch(statusInUse)
    {
      case ItemOrdered:
        __autotransition5__();
        break;
      case Billed:
        doActivityStatusInUseBilledThread = new DoActivityThread(this,"doActivityStatusInUseBilled");
        break;
    }
  }

  private void doActivityStatusInUseBilled()
  {
    try
    {
      // line 21 "RestoStateMachine.ump"
      makeBill(Seat... allSeats);
      Thread.sleep(1);
    }
    catch (InterruptedException e)
    {

    }
  }

  private static class DoActivityThread extends Thread
  {
    Table controller;
    String doActivityMethodName;
    
    public DoActivityThread(Table aController,String aDoActivityMethodName)
    {
      controller = aController;
      doActivityMethodName = aDoActivityMethodName;
      start();
    }
    
    public void run()
    {
      if ("doActivityStatusInUseBilled".equals(doActivityMethodName))
      {
        controller.doActivityStatusInUseBilled();
      }
    }
  }

  public void delete()
  {}

  // line 27 "RestoStateMachine.ump"
   private void addMenuItemForSeats(Menultem menuItem, Seat... allSeats){
    
  }

  // line 28 "RestoStateMachine.ump"
   private void makeTableAvailable(String table){
    
  }

  // line 29 "RestoStateMachine.ump"
   private void makeTableInUse(String table){
    
  }

  // line 30 "RestoStateMachine.ump"
   private void deleteMenultemForSeat(MenuItem menuItem, Seat seat){
    
  }

  // line 31 "RestoStateMachine.ump"
   private void makeBill(Seat... allSeats){
    
  }

  // line 32 "RestoStateMachine.ump"
   private void deleteCurrentOrder(){
    
  }

  // line 33 "RestoStateMachine.ump"
   private void removeBill(Seat seat){
    
  }

  // line 34 "RestoStateMachine.ump"
   private void deleteBill(String seat){
    
  }

  // line 35 "RestoStateMachine.ump"
   private int itemsOrdered(){
    return 0;
  }

  // line 36 "RestoStateMachine.ump"
   private boolean allSeatsBilled(){
    return false;
  }

  // line 37 "RestoStateMachine.ump"
   private boolean isLastBill(){
    return false;
  }

  // line 38 "RestoStateMachine.ump"
   private boolean isBilled(Seat seat){
    return false;
  }

}