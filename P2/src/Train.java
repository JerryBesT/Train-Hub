/////////////////////////////////////////////////////////////////////////////
// Semester:         CS367 Fall 2017 
// PROJECT:          Train hub
// FILE:             Train.java
//
// TEAM:    individual
// Author:  Zhenyu Zou
// email: zzou24@wisc.edu
// netID: 907 593 6980
// Lecture number: 001
//
//
// ---------------- OTHER ASSISTANCE CREDITS 
// Persons: Identify persons by name, relationship to you, and email. 
// Describe in detail the the ideas and help they provided. 
// 
// Online sources: Piazza Discussions
//////////////////////////// 80 columns wide //////////////////////////////////

/**
 * This class represents a train.  It has a destination and a linked list 
 * of CargoCar objects.  It implements Iterable<CargoCar> by returning 
 * a direct access iterator to its linked list of cargo cars. 
 * 
 * Several methods, such as getDestination(), removeCargo() and getWeight(), 
 * are provided to manage a train object. 
 * 
 * COMPLETE THIS CLASS AND HAND IN THIS FILE
 * 
 * @see LinkedList
 * @see CargoCar
 */
public class Train implements Iterable<CargoCar> {

	private String destination;
	private LinkedList<CargoCar> train;
	
	/**
	 * Constructs Train with its destination.
	 * 
	 * @param dest train destination
	 */
	public Train(String dest){
		this.destination = dest;
		train = new LinkedList<CargoCar>();
	}
	
	/**
	 * Get the destination of this train.
	 * 
	 * @return train destination
	 */
	public String getDestination(){
		return this.destination;
	}
	
	/**
	 * Set a new destination for this train.
	 * 
	 * @param newDest new train destination
	 */
	public void setDestination(String newDest){
		this.destination = newDest;
	}
	
	/**
	 * Get the total weight of a cargo in this train.
	 * 
	 * @param the name of the cargo to sum
	 * @return total weight of specified cargo in this train
	 */
	public int getWeight(String cargoName){
		// initialize the sum integer
		int sum = 0;
		
		// set a reference to the header node to train
		Listnode<CargoCar> curr = this.train.getHeaderNode();
	
		// if the next node after header node is not null, then keep looping
		while(curr.getNext() != null)
		{
			// when the name of the product equals to the incoming cargo's name, then update the total sum
			if(curr.getNext().getData().getName().equalsIgnoreCase(cargoName))
				// update the sum if the incoming cargo is found
				sum = sum + curr.getNext().getData().getWeight();
			
			//update the current node every time
			curr = curr.getNext();
		}
		
		// return the sum
		return sum;
	}
	
	/**
	 * Add cargo car to the end of the train
	 * 
	 * @param cargoCar the object need to be added to the end of car
	 */
	public void add(CargoCar cargoCar) {
		// add cargo car at end of train
		this.train.add(cargoCar);
	}

	/**
	 * Add cargo car as specified position 
	 * @param pos the position at which to add the item
	 * @param newCargo the cargo car to add
	 */
	public void add(int pos, CargoCar newCargo) {
		// add cargo car as specified position 
		this.train.add(pos, newCargo);
	}
	
	/**
	 * Remove the first CargoCar from this train which has the same cargo name 
	 * with the argument. If there are multiple CargoCar objects with the same 
	 * name, remove the first one.
	 * 
	 * @param The name of the cargo that you wish to remove.
	 * @return removed CargoCar object if you successfully removed a cargo, 
	 * otherwise null
	 */
	public CargoCar removeCargo(String cargoName){
		// set a reference to the header node of the train
		Listnode<CargoCar> curr = train.getHeaderNode();
		
		// store the removing car's node
		Listnode<CargoCar> RemovedCar = null;
		
		// initialize a new cargo
		CargoCar c = null;
		
		// if the the next node of current node is not null, then keep looping
		while(curr.getNext() != null)
		{
			// current product name is equal to the incoming cargo's name
			if(curr.getNext().getData().getName().equalsIgnoreCase(cargoName.toLowerCase().trim()))
			{
				// if the incoming cargo's name is found, then store the node
				RemovedCar = curr.getNext();
				
				// temporarily store the removed car to avoid nullpointer exception
				c = RemovedCar.getData();
				
				// set the next node to the one skipped the next node, which is removing
				curr.setNext(curr.getNext().getNext());
				
				// if it is found, stop the loop immediately
				break;
			}
			curr = curr.getNext();
		}
		
		// return the removed car
		return c;
	}

	public LinkedListIterator<CargoCar> iterator() {
		
		// return the iterator of the train list
		return new LinkedListIterator<CargoCar>(train.getHeaderNode().getNext());
		
	}

	/**
	 * Returns the number of cargo cars on this train.  
	 * 
	 * CAUTION: the number of actual cars on a train can be changed external
	 * to the Train type.  Make sure this returns a current count of the 
	 * cargo cars on this train.  Tip: call a LinkedList method from here
	 * and make sure that the LinkedList method iterates to count cars.
	 * 
	 * @return the number of cargo cars on this train.
	 */
	public int numCargoCars() {
		// return the size of train list
		return train.size();
	}

	/**
	 * Returns a reference to the header node from the linked list of
	 * CargoCar nodes.
	 * 
	 * CAUTION: Returning this node allows other
	 * code to change the contents of this train without
	 * calling train methods.  
	 * 
	 * It is being returned in this program to facilitate our testing
	 * and for moving sub-chains of nodes from one train to another.  
	 * THIS METHOD MAY ONLY BE CALLED BY moveMultipleCargoCars()
	 * of the TrainHub class.
	 * 
	 * @return the header node of the chain of nodes from the linked list.
	 */
	public Listnode<CargoCar> getHeaderNode() {
		
		// return the header node of train list
		return train.getHeaderNode();
		
	}

	/**
	 * Returns Train with a String format as following.
	 * <p>
	 * {ENGINE_START}{destination}{ENGINE_END}{CARGO_LINK}{cargo}:{weight}{CARGO_LINK}{cargo}:{weight}...
	 * <p>
	 * By default, {ENGINE_START} = ( , {ENGINE_END} = ) and {CARGO_LINK} = -> (defined in {@link Config}).
	 * So if you did not modify Config class, it will generate a String with following format.
	 * <p>
	 * ({destination})->{cargo}:{weight}->{cargo}:{weight}...
	 * 
	 * DO NOT EDIT THIS METHOD
	 * 
	 * @return train as a string format
	 */
	@Override
	public String toString(){
		String builtStr = "";
		
		builtStr += Config.ENGINE_START+this.destination+ Config.ENGINE_END;

		LinkedListIterator<CargoCar> itr = train.iterator();
		while(itr.hasNext()){
			CargoCar item = itr.next();
			builtStr += Config.CARGO_LINK + item.getName() +":" + item.getWeight();
		}
		
		return builtStr;
	}

}
