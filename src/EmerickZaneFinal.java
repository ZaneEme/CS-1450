
public class EmerickZaneFinal {

	public static void main(String[] args) {
		
		// EXAMPLE linked list for testing purposes only!
		// DO NOT HARDCODE LIKE THIS IN YOUR SOLUTION, 
		// I hardcoded to provide an example which I will
		// add/remove nodes from when I test your code.
		Node head = new Node(-2);
		Node tail = head;

		Node aNode = new Node(6);
		tail.next = aNode;
		tail = aNode;

		aNode = new Node(5);
		tail.next = aNode;
		tail = aNode;

		aNode = new Node(-62);
		tail.next = aNode;
		tail = aNode;
		
		aNode = new Node(4);
		tail.next = aNode;
		tail = aNode;
		
		aNode = new Node(3);
		tail.next = aNode;
		tail = aNode;

		aNode = new Node(2);
		tail.next = aNode;
		tail = aNode;
		
		aNode = new Node(-43);
		tail.next = aNode;
		tail = aNode;

		aNode = new Node(1);
		tail.next = aNode;
		tail = aNode;

		// Print the list before deleting and rebuilding 
		System.out.println("Linked List BEFORE");
		Node current = head;
		while (current != null) {
			int data = current.data;
			current = current.next;
			System.out.print(data + "-->");
		}
		System.out.println("Null");

		// ******* ADD SOLUTION HERE *******
		// Write all code in main - no need to create methods
		// Create the new linked list using the provided head and Node class
		// Do not hardcode as I did above

     		
		// Very confused what I need to do exactly?
		// I need to delete the list and then recreate it manually?


		//find size of list
		int size = 0;
		aNode = head;
		while(aNode != null) {
			size++;
			aNode = aNode.next;
		}

		//create array of data and fill it
		int[] nodeData = new int[size];
		aNode = head;
		int arrayPosition = 0;

		while(aNode != null) {
			nodeData[arrayPosition] = aNode.data;
			aNode = aNode.next;
			arrayPosition++;
		}

		// deletes the old list
		head = null;

		//start creating the new list
		head = new Node(-2);
		tail = head;

		for(int i = 1; i < nodeData.length; i++) {
			aNode = new Node(nodeData[i]);
			tail.next = aNode;
			tail = aNode;
		}
		

		// Print the list after recreate
		System.out.println();
		System.out.println("Linked List AFTER");
		current = head;
		while (current != null) {
			int data = current.data;
			current = current.next;
			System.out.print(data + "-->");
		}
		System.out.println("Null");

	} // main
	
	// Inner Node Class
	private static class Node {
		private int data;
		private Node next;
		
		public Node (int data) {
			this.data = data;
			next = null;
		}
	} // Node

} // Final 


