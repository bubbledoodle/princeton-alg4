/*
Generic data type for deque
write by shuaiyu liang on 11/15/2017
*/

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

	private Node head;
	private Node tail;
	private int size;

	// inner class with overhead 8bytes. reference 8 + 8, item 8. object overhead 16bytes.
	// total ~ 48N
   	private class Node {
   		Item item;
   		Node prev;
   		Node next;
   		Node(Item item) {
   			this.item = item;
   		}
   	}

   	// construct an empty deque
   	public Deque() {
   		head = new Node(null);
   		tail = new Node(null);
   		head.next = tail;
   		tail.prev = head;
   		size = 0;
   	}

   	// is the deque empty?                         
   	public boolean isEmpty() {
   		return (size == 0);
   	}            

   	// return the number of items on the deque    
   	public int size() {
   		return size;
   	}               

   	// add the item to the front   
   	public void addFirst(Item item) {
   		if (item == null) throw new IllegalArgumentException("can't add null to a deque");
   		Node newNode = new Node(item);
   		newNode.next = head.next;
   		newNode.prev = head;
      head.next.prev = newNode;
   		head.next = newNode;
   		size++;
   	}      

   	// add the item to the end
   	public void addLast(Item item) {
   		if (item == null) throw new IllegalArgumentException("can't add null to a deque");
   		Node newNode = new Node(item);
   		newNode.next = tail;
   		newNode.prev = tail.prev;
   		tail.prev.next = newNode;
   		tail.prev = newNode;
   		size++;
   	}        

	// remove and return the item from the front
   	public Item removeFirst() {
   		if (size == 0) throw new NoSuchElementException("Can't remove element from an empty deque");
   		Node node = head.next;
   		head.next = node.next;
   		node.prev = head;
   		size--;
   		return node.item;
   	}   

   	// remove and return the item from the end
   	public Item removeLast() {
   		if (size == 0) throw new NoSuchElementException("Can't remove element from an empty deque");
   		Node node = tail.prev;
   		tail.prev = node.prev;
   		tail.prev.next = tail;
   		size--;
   		return node.item;
   	}         


   	// return an iterator over items in order from front to end
   	@Override
   	public Iterator<Item> iterator() {
   		return new DequeIterator();
   	}         

   	private class DequeIterator implements Iterator<Item> {
   		private Node current = head;

  		@Override
  		public boolean hasNext() {
  			return current.next != tail;
  		}

  		@Override
  		public void remove() {
  			throw new UnsupportedOperationException("Unsupported Operation");
  		}

  		@Override
  		public Item next() {
  			if (!hasNext()) throw new NoSuchElementException("No more element");
  			current = current.next;
  			return current.item;
  		}
   	}


   	// unit testing (optional)
   	public static void main(String[] args) {
   	}  
}