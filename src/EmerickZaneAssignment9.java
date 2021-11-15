import java.util.Scanner;
import java.io.File;
import java.io.IOException;

/**
 * Name: Zane Emerick 
 * Class: CS 1450 Section 001
 * Assignment #9 
 * Due: April 23, 2020
 *
 * Description: Design a custon linked-list that can hold Elephant objects.
 * The list needs to be able to add an elephant to the end and remove
 * an elephant from anywhere on the list. As Elephants are read rom a file,
 * print each one to the console. Next, add up the total weight of all the 
 * elephants. Finally, remove them one-by-one starting from heaviest to
 * lightest.
 */


public class EmerickZaneAssignment9 {
    public static void main(String[] args) throws IOException {
        File elephantsFile = new File("elephants.txt");
        Scanner elephantsReader = new Scanner(elephantsFile);

        ElephantLinkedList elephantsList = new ElephantLinkedList();

        //add all the elephants
        while(elephantsReader.hasNext()) {
            Elephant elephant = new Elephant(elephantsReader.next(), elephantsReader.nextInt());
            elephantsList.add(elephant);
        }
        System.out.println("Put elephants in the list:");
        elephantsList.printList();

        System.out.println("\nTotal weight is: " + elephantsList.getTotalWeight());

        System.out.println("\nFinding and removing elephants, heaviest to lowest:");

        //loop through the whole list
        while(!elephantsList.isEmpty()) {
            Elephant tempElephant = elephantsList.findHeaviest();
            System.out.printf("removing %-7s who weights %-7d\n", tempElephant.getName(),tempElephant.getWeight());
            elephantsList.remove(tempElephant);
        }

        elephantsReader.close();
    }
}

class Elephant {
    private String name;
    private int weight;

    public Elephant(String name, int weight) {
        this.name = name;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }

    public void printInfo() {
        System.out.printf("Name: %-7s Weight: %-7d\n", name, weight);
    }
}

class ElephantLinkedList {
    private Node head;
    private Node tail;

    public ElephantLinkedList() {
        this.head = null;
        this.tail = null;
    }

    public boolean isEmpty() {
        return (head == null);
    }

    public int getTotalWeight() {
        int totalWeight = 0;
        Node tempNode = head;

        //loop through whole list
        while(tempNode.next != null) {
            totalWeight += tempNode.elephant.getWeight();
            tempNode = tempNode.next;
        }
        //add the last elephant
        totalWeight += tempNode.elephant.getWeight();

        return totalWeight;
    } 

    public void add(Elephant elephant) {
        Node node = new Node(elephant);

        // if the list is empty set node to be both head and tail
        if(tail == null) {
            head = node;
            tail = node;
        } else { 
            /**
             * if list isn't empty set the current tail to point to new node
             * set tail to be new node
             */
            tail.next = node;
            tail = node;
        } 

    } 

    public Elephant findHeaviest() {
        Elephant topElephant = head.elephant;
        Node tempNode = head;

        while(tempNode != tail) {
            if(topElephant.getWeight() <= tempNode.elephant.getWeight()) {
                topElephant = tempNode.elephant;
            }
            tempNode = tempNode.next;
        }
        //check for the tail
        if(topElephant.getWeight() <= tempNode.elephant.getWeight()) {
            topElephant = tempNode.elephant;
        }

        return topElephant;
    }

    public void remove(Elephant elephant) {
        Node tempNode = head;
        Node nodeOne = null;

        //if the first one is the elephant
       if(head.elephant == elephant) {
            head = head.next;
       } else {
           //loop through while it's not the tail, and the next isn't the tail or the elephant
            while(tempNode != tail && tempNode.next != tail && tempNode.next.elephant != elephant) {
                tempNode = tempNode.next;
            }
            // if it's not the tail, and the next one isn't, but the next one is the elephant
            if(tempNode != tail && tempNode.next.elephant == elephant && tempNode.next != tail) {
                nodeOne = tempNode.next;
                tempNode.next = nodeOne.next; 
                nodeOne = null;
            // if the next one is both teh tail and the elephant
            } else if(tempNode.next == tail && tempNode.next.elephant == elephant) {
                tempNode.next = null;
                tail = tempNode;
            } else {
                System.out.println("Elephant not found");
            }
       }
    }

    public void printList() {
        Node tempNode = head;
        while(tempNode.next != null) {
            tempNode.elephant.printInfo();
            tempNode = tempNode.next;
        }
        // print to get the last one in the list to print
        tempNode.elephant.printInfo();
    }

    /**
     * each node has a data (Elephant) field and a
     * next Node it points to
     */
    private static class Node {
        private Elephant elephant;
        private Node next;

        public Node(Elephant elephant) {
            this.elephant = elephant;
            this.next = null;
        }
    } // end Node
} // end ElephantLinkedList