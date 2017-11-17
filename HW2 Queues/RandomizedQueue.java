/*
Generic data type for radomized queue
write by shuaiyu liang on 11/15/2017
*/

import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
	private Item[] array;
	private int size;

	// construct an empty randomized queue
   	public RandomizedQueue() {
   		Item[] a = (Item[]) new Object[1];
   		array = a;
   		size = -1;
   	}                

   	// is the randomized queue empty?
   	public boolean isEmpty() {
   		return size() == 0;
   	}

	// return the number of items on the randomized queue
   	public int size() {
   		return size + 1;
   	}         

   	private void resize(int capacity) {
   		Item[] copy = (Item[]) new Object[capacity];
   		for (int i = 0; i < size(); i++) {
   			copy[i] = array[i];
   		}
   		array = copy;
   	}

   	// add the item              
   	public void enqueue(Item item) {
   		if (item == null) throw new NoSuchElementException("Can't add null item");
   		if (size() == array.length) resize(array.length * 2);
   		array[++size] = item;
   	}

   	// remove and return a random item
   	public Item dequeue() {
   		if (isEmpty()) throw new NoSuchElementException("Can't remove from an empty queue");
   		int rmIdx = StdRandom.uniform(size());
   		Item item = array[rmIdx];
   		array[rmIdx] = array[size];
   		array[size] = null;
   		size--;
   		if (size() > 0 && size() == array.length / 4) resize(array.length / 2);
   		return item;
   	}              

   	// return a random item (but do not remove it)     
   	public Item sample() {
   		if (isEmpty()) throw new NoSuchElementException("Can't sample from an empty queue");
   		int idx = StdRandom.uniform(size());
   		Item item = array[idx];
   		return item;
   	}             

   	// return an independent iterator over items in random order       
   	public Iterator<Item> iterator() {
   		return new RandomizedQueueIterator();
   	}      

   	private class RandomizedQueueIterator implements Iterator<Item> {
   		private Item[] copy;
   		private int N;

   		RandomizedQueueIterator() {
   			Item[] a = (Item[]) new Object[size()];
   			for (int i = 0; i < size(); i++) {
   				a[i] = array[i];
   			}
   			copy = a;
   			N = size;
   		}

   		@Override
   		public void remove() {
   			throw new UnsupportedOperationException("Unsupported Operation");
   		}

   		@Override
   		public boolean hasNext() {
   			return N >= 0;
   		}

   		@Override
   		public Item next() {
   			if (!hasNext()) throw new NoSuchElementException("No more element");
   			int idx = StdRandom.uniform(N + 1);
   			Item item = copy[idx];
   			copy[idx] = copy[N];
   			copy[N] = item;
            N--;
   			return item;
   		}
   	}

   	// unit testing (optional)
   	public static void main(String[] args) {	
   	}  
}