package util;

import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.util.LinkedList;

public class TestList {

	public static void main(String[] args) {
		int numberOfElements = 100000;
		long myListElapsedTime = 0, linkedListElapsedTime= 0, arrayListElapsedTime= 0;
		int numberOfTries = 80;
		long start = 0;
		
		Random rand = new Random();
		int randomNumber = 0;
		
		MyLinkedList<Integer> myList = new MyLinkedList<Integer>(numberOfElements);
		LinkedList<Integer> linkedList = new LinkedList<Integer>();
		ArrayList<Integer> arrayList = new ArrayList<Integer>(numberOfElements);
		for(int i = 0; i < numberOfElements; i++){
			myList.addLast(i);
			linkedList.add(i);
			arrayList.add(i);
		}
		
		
		/**Test for using the get() function
		 * 
		 
		randomNumber = 0;
		for(int i = 0; i < numberOfTries; i++){
			randomNumber = rand.nextInt(numberOfElements);
			
			start = System.nanoTime();			
			System.out.println(myList.get(randomNumber));
			myListElapsedTime += System.nanoTime() - start;
			
			start = System.nanoTime();		
			System.out.println(linkedList.get(randomNumber));
			linkedListElapsedTime += System.nanoTime() - start;		
			
			start = System.nanoTime();
			System.out.println(arrayList.get(randomNumber));
			arrayListElapsedTime += System.nanoTime() - start;
		}*/
		
		/**Test for using the set(i,T) function
		 * 
		 
		randomNumber = 0;
		for(int i = 0; i < numberOfTries; i++){
			randomNumber = rand.nextInt(numberOfElements);
			
			start = System.nanoTime();			
			System.out.println(myList.set(randomNumber,randomNumber));
			myListElapsedTime += System.nanoTime() - start;
			
			start = System.nanoTime();		
			System.out.println(linkedList.set(randomNumber,randomNumber));
			linkedListElapsedTime += System.nanoTime() - start;		
			
			start = System.nanoTime();
			System.out.println(arrayList.set(randomNumber,randomNumber));
			arrayListElapsedTime += System.nanoTime() - start;
		}*/
		
		/**Test for using the remove(i) function
		 *
		 */
		randomNumber = 0;
		int indexToRemove = 80;
		for(int i = 0; i < numberOfTries; i++){
			randomNumber = rand.nextInt(numberOfElements);
			
			start = System.nanoTime();			
			System.out.println(myList.remove(indexToRemove));
			myListElapsedTime += System.nanoTime() - start;
			
			start = System.nanoTime();		
			System.out.println(linkedList.remove(indexToRemove));
			linkedListElapsedTime += System.nanoTime() - start;		
			
			start = System.nanoTime();
			System.out.println(arrayList.remove(indexToRemove));
			arrayListElapsedTime += System.nanoTime() - start;
		}
		
		System.out.println("MyLinkedList average time: " + (myListElapsedTime/numberOfTries));
		System.out.println("Java's LinkedList average time: " + (linkedListElapsedTime/numberOfTries));
		double dec =myListElapsedTime/numberOfTries - linkedListElapsedTime/numberOfTries;
		double eff =100 - (dec/(myListElapsedTime/numberOfTries) * 100);
		System.out.println("Java's Array average time: " + (arrayListElapsedTime/numberOfTries));
		
		System.out.println("MyLinkedList compared to Java's LinkedList: " + eff);
		System.out.println("Number of Tries: " + numberOfTries);
		System.out.println("Number of Elements: " + numberOfTries);
		//System.out.println(myList);
		//myList.remove(2);
		//System.out.println(myList);
	}

}
