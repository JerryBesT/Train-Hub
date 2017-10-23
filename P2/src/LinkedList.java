/////////////////////////////////////////////////////////////////////////////
// Semester:         CS367 Fall 2017 
// PROJECT:          Train hub
// FILE:             LinkedList.java
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

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * An Iterable list that is implemented using a singly-linked chain of nodes
 * with a header node and without a tail reference.
 * 
 * The "header node" is a node without a data reference that will 
 * reference the first node with data once data has been added to list.
 * 
 * The iterator returned is a LinkedListIterator constructed by passing 
 * the first node with data.
 * 
 * CAUTION: the chain of nodes in this class can be changed without
 * calling the add and remove methods in this class.  So, the size()
 * method must be implemented by counting nodes.  This counting must
 * occur each time the size method is called.  DO NOT USE a numItems field.
 * 
 * COMPLETE THIS CLASS AND HAND IN THIS FILE
 */
public class LinkedList<E> implements ListADT<E> {

	// set a private header node field
	private Listnode<E> header;
	
    /**
     * Construct new Linkedlist with header node
     */
	public LinkedList()
	{
		// set the header node to null
		header = new Listnode<E>(null);
	}

	@Override
    /**
	 * Adds a data item to the end of the List.
	 * 
	 * @param item the item to add
	 * @throws IllegalArgumentException if item is null 
	 */
	public void add(E item) {
		// check for the condition of item which does not exist
		if(item == null)
			throw new IllegalArgumentException();
		
		// initialize a new node to the list node containing item.
		Listnode<E> newnode = new Listnode<E>(item);
		// set a reference to the header
		Listnode<E> curr = header;
		
		// if the current node has next node, continue to the end
		while(curr.getNext() != null)
			// update the node
			curr = curr.getNext();
		// if got the node, set the next node to the new node
		curr.setNext(newnode);
	}

	@Override

	/**
	 * Returns the number of items in the List.
	 * 
	 * @return the number of items in the List
	 */
	//calculate the size of the whole list
	public int size() {
		
		// initialize a integer 
		int numItems = 0;
		// set a reference to the header node
		Listnode<E> curr = header;
		// if the current node has next
		
		while(curr.getNext() != null) {
			// then update the number of total number, and update the node
			numItems++;
			curr = curr.getNext();
		}
		// return the total number, which is size
		return numItems;
	}
	
	@Override
	/**
	 * Adds a data item at position pos in the List, moving the items originally 
	 * in positions pos through size() - 1 one place to the right to make room.
	 * 
	 * @param pos the position at which to add the item
	 * @param item the item to add
	 * @throws IllegalArgumentException if item is null 
	 * @throws IndexOutOfBoundsException if pos is less than 0 or greater 
	 * than size()
	 */
	// add a specific node to a specific position
	public void add(int pos, E item) {
		// check for exception of illegal argument or bad position
		if(item == null)
			throw new IllegalArgumentException();
		else if(pos < 0 || pos > size())
			throw new IndexOutOfBoundsException();
	
		// set a reference to a new node containing item
		Listnode<E> newnode = new Listnode<E>(item);
		// set a reference to the header
		Listnode<E> curr = header;
		
		// calculate the total number of position
		for(int p=0;p < pos;p++)
			// update the node
			curr = curr.getNext();
		
		// set the new node's next node to the existing list
		newnode.setNext(curr.getNext());
		// connect the new node to the specific position.
		curr.setNext(newnode);
	}

	@Override
	/**
	 * Returns true iff item is in the List (i.e., there is an item x in the List 
	 * such that x.equals(item))
	 * 
	 * @param item the item to check
	 * @return true if item is in the List, false otherwise
	 */
	public boolean contains(E item) {
		// create a new node containing item in it
		Listnode<E> newnode = new Listnode<E>(item);
		// set a reference to the header node
		Listnode<E> curr = header;
		
		// if the current node's next node does not equal to null, continue
		while(curr.getNext() != null)
		{
			// if the next node does equal, then return true and finish
			if(curr.getNext().equals(newnode))
				return true;
			
			// if not, update the node
			curr = curr.getNext();
		}
		
		// if it does not find the item, return false.
		return false;
	}

	@Override
	/**
	 * Returns the item at position pos in the List.
	 * 
	 * @param pos the position of the item to return
	 * @return the item at position pos
	 * @throws IndexOutOfBoundsException if pos is less than 0 or greater than
	 * or equal to size()
	 */
	public E get(int pos) {
		
		// check for any bad position input
		if(pos < 0 || pos >= size())
			throw new IndexOutOfBoundsException();
		
		// set a reference to the header node
		Listnode<E> curr = header;
		
		//traverse the whole list for position times
		for(int p=0;p < pos;p++)
			// update the new node
			curr = curr.getNext();
		
		// return the data that you want to get
		return curr.getData();
	}

	@Override

	/**
	 * Returns true iff the List is does not have any data items.
	 * 
	 * @return true if the List is empty, false otherwise
	 */
	public boolean isEmpty() {
	
		// it returns false if there is no next node in the list
		return header.getNext() == null;
	}

	@Override
	/**
	 * Removes and returns the item at position pos in the List, moving the items 
	 * originally in positions pos+1 through size() - 1 one place to the left to 
	 * fill in the gap.
	 * 
	 * @param pos the position at which to remove the item
	 * @return the item at position pos
	 * @throws IndexOutOfBoundsException if pos is less than 0 or greater than
	 * or equal to size()
	 */
	public E remove(int pos) {
		// check any exceptions out of bounce
		if(pos < 0 || pos >= size())
			throw new IndexOutOfBoundsException();
		
		Listnode<E> curr = header;
		
		// traverse the list to get to the position p
		for(int p = 0;p < pos;p++)
			curr = curr.getNext();
		
		// store the item at position pos
		Listnode<E> theNode = new Listnode<E>(curr.getData());
		
		// remove the node, return the removed node info
		curr.setNext(curr.getNext().getNext());
		return theNode.getData();
	}
	
	
	//	 TODO: YOU MUST IMPLEMENT THE LINKED LIST CLASS AS FOLLOWS:
	//	 
	//	 It must be a SINGLY-LINKED chain of ListNode<E> nodes
	//	 It must have a "header node" ("dummy node" without data)
	//	 It must NOT have a tail reference
	//	 It must NOT keep a number of data items
	//       NOTE: in this program, the chains of nodes in this program may be 
	//   	 changed outside of the LinkedList class, so the actual data count 
	//       must be determined each time size is called.
	//
	//	 It must return a LinkedListIterator<E> as its iterator.
	//	 
	//	 Note: The "header node"'s data reference is always null and 
	//	 its next references the node with the first data of the list.
	//	 
	//	 Be sure to implement this LinkedList<E> using Listnode
	//	       you must use LinkedListIterator<E> instead of Iterator<E>
	//	

	/** 
	 * Returns a reference to the header node for this linked list.
	 * The header node is the first node in the chain and it does not 
	 * contain a data reference.  It does contain a reference to the 
	 * first node with data (next node in the chain). That node will exist 
	 * and contain a data reference if any data has been added to the list.
	 * 
	 * NOTE: Typically, a LinkedList would not provide direct access
	 * to the headerNode in this way to classes that use the linked list.  
	 * We are providing direct access to support testing and to 
	 * allow multiple nodes to be moved as a chain.
	 * 
	 * @return a reference to the header node of this list. 0
	 */
	public Listnode<E> getHeaderNode() {
		// simply return the header node
		return header;
	}

	/**
	 * Must return a reference to a LinkedListIterator for this list.
	 */
	public LinkedListIterator<E> iterator() {
		// return a linked list iterator, containing the next node of header node
		return new LinkedListIterator<E>(header.getNext());
	}

}
