/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.27.0.3728.d139ed893 modeling language!*/

package ca.mcgill.ecse223.resto.model;
import java.io.Serializable;
import java.util.*;

// line 102 "../../../../../RestoAppPersistence.ump"
// line 78 "../../../../../RestoApp.ump"
public class Card implements Serializable
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<Integer, Card> cardsByNumber = new HashMap<Integer, Card>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Card Attributes
  private int number;

  //Card Associations
  private RestoApp restoApp;
  private List<Order> orders;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Card(int aNumber, RestoApp aRestoApp)
  {
    if (!setNumber(aNumber))
    {
      throw new RuntimeException("Cannot create due to duplicate number");
    }
    boolean didAddRestoApp = setRestoApp(aRestoApp);
    if (!didAddRestoApp)
    {
      throw new RuntimeException("Unable to create card due to restoApp");
    }
    orders = new ArrayList<Order>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setNumber(int aNumber)
  {
    boolean wasSet = false;
    Integer anOldNumber = getNumber();
    if (hasWithNumber(aNumber)) {
      return wasSet;
    }
    number = aNumber;
    wasSet = true;
    if (anOldNumber != null) {
      cardsByNumber.remove(anOldNumber);
    }
    cardsByNumber.put(aNumber, this);
    return wasSet;
  }

  public int getNumber()
  {
    return number;
  }

  public static Card getWithNumber(int aNumber)
  {
    return cardsByNumber.get(aNumber);
  }

  public static boolean hasWithNumber(int aNumber)
  {
    return getWithNumber(aNumber) != null;
  }

  public RestoApp getRestoApp()
  {
    return restoApp;
  }

  public Order getOrder(int index)
  {
    Order aOrder = orders.get(index);
    return aOrder;
  }

  public List<Order> getOrders()
  {
    List<Order> newOrders = Collections.unmodifiableList(orders);
    return newOrders;
  }

  public int numberOfOrders()
  {
    int number = orders.size();
    return number;
  }

  public boolean hasOrders()
  {
    boolean has = orders.size() > 0;
    return has;
  }

  public int indexOfOrder(Order aOrder)
  {
    int index = orders.indexOf(aOrder);
    return index;
  }

  public boolean setRestoApp(RestoApp aRestoApp)
  {
    boolean wasSet = false;
    if (aRestoApp == null)
    {
      return wasSet;
    }

    RestoApp existingRestoApp = restoApp;
    restoApp = aRestoApp;
    if (existingRestoApp != null && !existingRestoApp.equals(aRestoApp))
    {
      existingRestoApp.removeCard(this);
    }
    restoApp.addCard(this);
    wasSet = true;
    return wasSet;
  }

  public static int minimumNumberOfOrders()
  {
    return 0;
  }

  public boolean addOrder(Order aOrder)
  {
    boolean wasAdded = false;
    if (orders.contains(aOrder)) { return false; }
    Card existingCards = aOrder.getCards();
    if (existingCards == null)
    {
      aOrder.setCards(this);
    }
    else if (!this.equals(existingCards))
    {
      existingCards.removeOrder(aOrder);
      addOrder(aOrder);
    }
    else
    {
      orders.add(aOrder);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeOrder(Order aOrder)
  {
    boolean wasRemoved = false;
    if (orders.contains(aOrder))
    {
      orders.remove(aOrder);
      aOrder.setCards(null);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addOrderAt(Order aOrder, int index)
  {  
    boolean wasAdded = false;
    if(addOrder(aOrder))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfOrders()) { index = numberOfOrders() - 1; }
      orders.remove(aOrder);
      orders.add(index, aOrder);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveOrderAt(Order aOrder, int index)
  {
    boolean wasAdded = false;
    if(orders.contains(aOrder))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfOrders()) { index = numberOfOrders() - 1; }
      orders.remove(aOrder);
      orders.add(index, aOrder);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addOrderAt(aOrder, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    cardsByNumber.remove(getNumber());
    RestoApp placeholderRestoApp = restoApp;
    this.restoApp = null;
    if(placeholderRestoApp != null)
    {
      placeholderRestoApp.removeCard(this);
    }
    while( !orders.isEmpty() )
    {
      orders.get(0).setCards(null);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "number" + ":" + getNumber()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "restoApp = "+(getRestoApp()!=null?Integer.toHexString(System.identityHashCode(getRestoApp())):"null");
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 105 "../../../../../RestoAppPersistence.ump"
  private static final long serialVersionUID =  1301571235893682821L ;

  
}