/////////////////////////////////////////////////////////////////////////////
// Semester:         CS367 Fall 2017 
// PROJECT:          Train hub
// FILE:             TrainHub.java
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
 * This class represents a train hub and provides the common operations
 * needed for managing the incoming and outgoing trains.
 *
 * It has a LinkedList of Train as a member variable and manages it.
 *
 * COMPLETE THIS CLASS AND HAND IN THIS FILE
 *
 * @see LinkedList
 * @see Train
 * @see Config
 */
public class TrainHub {

    /** The internal data structure of a hub is a linked list of Trains */
    private LinkedList<Train> trains;

    /**
     * Constructs and initializes TrainHub object
     */
    public TrainHub(){
        this.trains = new LinkedList<Train>();
    }

    /**
     * This method processes the incoming train.
     * It iterates through each of the cargo car of the incoming train.
     * If there is an outgoing train in the train list going to the
     * destination city of the cargo car, then it removes the cargo car
     * from the incoming train and adds the cargo car at the correct location
     * in the outgoing train.  The correct location is to become the first
     * of the matching cargo cars, with the cargo cars in alphabetical order
     * by their cargo name.
     *
     * If there is no train going to the destination city,
     * it creates a new train and adds the cargo to this train.
     *
     * @param train Incoming train (list or cargo cars)
     */
    public void processIncomingTrain(Train train){
    	// initialize the iterator
    	LinkedListIterator<CargoCar> itr1 = train.iterator();
    	
    	while(itr1.hasNext())
    	{
    		//store the current car reference
    		CargoCar c = itr1.next();
   
    		// store the destination, and find the desired train
    		String tempDest = c.getDestination();
    		Train tempTrain = findTrain(tempDest);
    		
    		//if the train does not exist
    		if(tempTrain == null)
    		{
    			// create a new train and add to the train hub
    			Train newTrain = new Train(tempDest);
    			
    			// store the car to a new train
    			newTrain.add(c);
    			trains.add(newTrain);
    		}
    		
    		else
    		{
    			// construct the train if the car finds a match in the existing trains
    			Listnode<CargoCar> tempCar = tempTrain.getHeaderNode().getNext();
    			
    			// the position integer used to identify the position to add
    			int pos = 0;
    			
    			while(tempCar != null)
    			{
    				// if the car equals the train's n position, then continue the loop, skip the adding
    				if(c.getName().compareToIgnoreCase(tempCar.getData().getName()) <= 0)
    				{
    					break;
    				}
    				
    				// if not equal, then update the position and car in the train
    				pos++;
    				tempCar = tempCar.getNext();
    			}
    			
    			tempTrain.add(pos, c);
    		}
    		train.removeCargo(c.getName());
    	}
    	
    }

    /**
     * This method tries to find the train in the list of trains, departing to the given destination city.
     *
     * @param dest Destination city for which train has to be found.
     * @return  Pointer to the train if the train going to the given destination exists. Otherwise returns null.
     */
    public Train findTrain(String dest){
    	
    	// set a reference to the header node of the train list
    	Listnode<Train> tempTrains = trains.getHeaderNode();
    	
    	// if the trains list still contain one more node, then continue the loop
    	while(tempTrains.getNext() != null)
    	{
    		// 
    		if(tempTrains.getNext().getData().getDestination().equalsIgnoreCase(dest))
    		{
    			return tempTrains.getNext().getData();
    		}
    		
    		tempTrains = tempTrains.getNext();
    	}
    	
    	return null;	
    }

    /**
     * This method removes the first cargo car going to the given
     * destination city and carrying the given cargo.
     *
     * @param dest Destination city
     * @param name Cargo name
     * @return If there is a train going to the the given destination and is carrying the given cargo,
     * it returns the cargo car. Else it returns null.
     */
    public CargoCar removeCargo(String dest, String name){
    	
    	// trim the destination and name to correct format, avoiding wrong formatting
    	dest = dest.trim();
    	name = name.toLowerCase().trim();
    	
    	// use the defined method to find the desired train
    	Train tempTrain = findTrain(dest);
    	
    	// if the desired train contains nothing, then return null immediately
    	if(tempTrain == null){
    		return null;
    	}
    	
    	// after finding the train, remove the desired cargo car
    	CargoCar removedCar = tempTrain.removeCargo(name);
    	
    	// return the car that has been removed.
    	return removedCar;
    }

    /**
     * This method iterates through all the trains in the list
     * and finds the sum of weights of given cargo in all trains.
     *
     * @param name Name of the cargo
     * @return Total weight of given cargo in all departing trains.
     */
    public int getWeight(String name){
    	
    	// construct a new iterator to traverse the list
    	LinkedListIterator<Train> itr = trains.iterator();
    	
    	// construct the sum integer
    	int sum = 0;
    	
    	// if there is still train left, then keep iterating
    	while(itr.hasNext())
    	{
    		// sums up all the weight of same cargo in a whole train
    		sum = sum + itr.next().getWeight(name);
    	}
    	
    	// return the sum
    	return sum;
    }

    /**
     * This method is used to depart the train to the given destination.
     * To depart the train, one needs to delete the train from the list.
     *
     * @param dest Destination city for which corresponding train has to be departed/deleted.
     * @return True if train to the given destination city exists. If not, then return false.
     */
    public boolean departTrain(String dest){
    	
    	// find the specific train
    	Train tempTrain = findTrain(dest);
    	
    	//set a reference to the trains
    	Listnode<Train> tempTrains = trains.getHeaderNode();
    	
    	//set a boolean to determine the state of finding
    	boolean isFound = false;
    	while(tempTrains.getNext() != null)
    	{
    		// check if any tempTrain is found to be equal
    		if(tempTrains.getNext().getData().equals(tempTrain))
    		{
    			tempTrains.setNext(tempTrains.getNext().getNext());
    			isFound = true;
    		}
    		else
    			tempTrains = tempTrains.getNext();
    	}
    	return isFound;
    	
    }
    /**
     * This method deletes all the trains.
     *
     * @return True if there was at least one train to delete. False if there was no train to delete.
     */
    public boolean departAllTrains(){
    	//if trains contain no train return false
        if(trains.isEmpty())
        	return false;
        
        //if exists, set the next node to be null;
    	trains.getHeaderNode().setNext(null);
    	return true;

    }

    /**
     * Display the specific train for a destination.
     *
     * @param dest Destination city for the train the to be displayed.
     * @return True if train to the given destination city exists. If not, then return false.
     */
    public boolean displayTrain(String dest){
    	// construct a train object to be the desired train, and find it
        Train tempTrain = findTrain(dest);
        
        //if the train is not empty
        if(tempTrain != null)
        {
        	// print every other line the train
        	System.out.println(tempTrain.toString().trim());
        	return true;
        }
        else
        	// if the train is null then return false
        	return false;
    }

    /**
     * This method is used to display all the departing trains in the train hub.
     * Train should be displayed in the specified format.
     *
     * @return True if there is at least one train to print. False if there is no train to print.
     */
    public boolean displayAllTrains(){
    	
    	// construct the iterator to iterate through the trains
    	LinkedListIterator<Train> itr = trains.iterator();
   
    	// if trains have not next train then return false
    	if(!itr.hasNext())
    		return false;
    	else{
    		// as long as the trains have next train, keep iterating
	    	while(itr.hasNext())
	    	{
	    		// print all the trains
	    		System.out.println(itr.next().toString());
	    	}
	    	return true;
    	}
    	
    }

    /**
     * Move all cargo cars that match the cargo name from a
     * source (src) train to a destination (dst) train.
     *
     * The matching cargo cars are added before the first cargo car
     * with a name match in the destination train.
     *
     * All matching cargo is to be moved as one chain of nodes and inserted
     * into the destination train's chain of nodes before the first cargo matched node.
     *
     * PRECONDITION: there is a source train and a destination train,
     * and the source train of nodes has at least one node with
     * matching cargo.  We will not test other conditions.
     *
     * NOTE: This method requires direct access to the chain of nodes of a
     * Train object.  Therefore, the Train class has a method in addition to
     * ListADT methods so that you can get direct access to header node
     * of the train's chain of nodes.
     *
     * @param src a reference to a Train that contains at least one node with cargo.
     *
     * @param dst a reference to an existing Train.  The destination is the
     * train that will have the cargo added to it.  If the destination chain
     * does not have any matching cargo, add the chain at its correct location
     * alphabetically.  Otherwise, add the chain of cargo nodes before the
     * first matching cargo node.
     *
     * @param cargoName The name of cargo to be moved from one chain to another.
     */
    public static void moveMultipleCargoCars(Train srcTrain, Train dstTrain, String cargoName) {
        // TODO Implement this method last.  It is not needed for other parts of program

        // get references to train header nodes
        // get references to train header nodes
        Listnode<CargoCar> srcHeader, dstHeader, prev, curr;
        srcHeader = srcTrain.getHeaderNode();
        dstHeader = dstTrain.getHeaderNode();

        Listnode<CargoCar> first_prev = null, first = null, last = null;
        boolean hasFound = false;
        
        curr = srcHeader;
        last = curr.getNext();
        first_prev = curr;
        
        boolean isFound = false;
        boolean isDone = false;

        // 1. Find references to the node BEFORE the first matching cargo node
        //    and a reference to the last node with matching cargo.
        while(curr.getNext() != null)
        {
        	// set the boolean statement to determine which condition to execute
        	if(!isFound && curr.getNext().getData().getName().equalsIgnoreCase(cargoName))
        	{
        		first_prev = curr;
        		first = curr;
        		isFound = true;
        	}
        	
        	if(isFound && !curr.getNext().getData().getName().equalsIgnoreCase(cargoName))
        	{
        		last = curr;
        		first_prev.setNext(last);
        		isDone = true;
        		break;
        	}
        	
        	curr = curr.getNext();
        }
        
		
    	if(isDone && isFound)
    	{
    		last = curr;
    		first_prev.setNext(null);
    	}
      
            // NOTE : We know we can find this cargo,
            //        so we are not going to deal with other exceptions here.

        // 2. Remove from matching chain of nodes from src Train
        //    by linking node before match to node after matching chain
        

    	curr = dstHeader.getNext();
    	prev = dstHeader;

		if(curr == null){
			prev.setNext(first);
		}
		
		while(curr !=null){
			
			if(curr.getData().getName().compareToIgnoreCase(cargoName) >= 0) {
				
				last.setNext(curr);
				prev.setNext(first);
				hasFound = true;
				
				break;
			}
			if(curr.getNext() == null){
				curr.setNext(first);
				break;
			}
			
			// Update pointer
			curr = curr.getNext();
			prev = prev.getNext();
		}
		
		if(!hasFound){
			curr.setNext(first);
		}
        // 3-1. Find reference to first matching cargo in dst Train
        

                // 3-2. If found, insert them before cargo found in dst


            // 3-3. If no matching cargo, add at correct location in dst
    }
}
