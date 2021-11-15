import java.util.Iterator;
import java.util.LinkedList;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Queue;

/**
 * Name: Zane Emerick 
 * Class: CS 1450 Section 001
 * Assignment #8 
 * Due: April 16, 2020
 * 
 * Description: Design a crossword game that can read letters and their,
 * and then knows how to store them in both a Queue and an Arraylist. Finally,
 * the program needs to be able to print out the games to the user through
 * the console.
 */

public class EmerickZaneAssignment8 {
    public static void main(String[] args) throws IOException {
        /**
         * ArrayList section of assignment
         */
        System.out.println("\n ArrayList section of assignment:\n");

        Scanner listLettersReader = new Scanner(new File("listLetters.txt"));
        Scanner listSlotsReader = new Scanner(new File("listSlots.txt"));

        ArrayList<Character> arrayLetters = new ArrayList<>();
        ArrayList<Slot> arraySlots = new ArrayList<>();

        int arrayListBoardRows = listSlotsReader.nextInt();
        int arrayListBoardCols = listSlotsReader.nextInt();

        //fill arrayLists with chars and slots
        while(listLettersReader.hasNext() && listSlotsReader.hasNext()) {
            Slot tempSlot = new Slot(listSlotsReader.nextInt(), listSlotsReader.nextInt());
            arraySlots.add(tempSlot);

            char charToAdd = listLettersReader.next().charAt(0);
            arrayLetters.add(charToAdd);
        }

        //create board
        Crossword arrayListGame = new Crossword(arrayListBoardRows, arrayListBoardCols);
        Iterator<Character> arrayListLettersIterator = arrayLetters.iterator();
        Iterator<Slot> arrayListSlotsIterator = arraySlots.iterator();

        arrayListGame.enterLetters(arrayListLettersIterator, arrayListSlotsIterator);

        arrayListGame.printCrossword();
        listLettersReader.close();
        listSlotsReader.close();

    
        /**
         * Queue section of assignment
         */
        System.out.println("\n Queue section of assignment:\n");

        Scanner queueLettersReader = new Scanner(new File("queueLetters.txt"));
        Scanner queueSlotsReader = new Scanner(new File("queueSlots.txt"));

        Queue<Character> queueLetters = new LinkedList<Character>();
        Queue<Slot> queueSlots = new LinkedList<Slot>();

        int queueBoardRows = queueSlotsReader.nextInt();
        int queueBoardCols = queueSlotsReader.nextInt();

        //fill queues with chars and slots
        while(queueLettersReader.hasNext() && queueSlotsReader.hasNext()) {
            Slot tempSlot = new Slot(queueSlotsReader.nextInt(), queueSlotsReader.nextInt());
            queueSlots.add(tempSlot);

            char charToAdd = queueLettersReader.next().charAt(0);
            queueLetters.add(charToAdd);
        }

         //create board
         Crossword queueGame = new Crossword(queueBoardRows, queueBoardCols);
         Iterator<Character> queueLettersIterator = queueLetters.iterator();
         Iterator<Slot> queueSlotsIterator = queueSlots.iterator();
 
         queueGame.enterLetters(queueLettersIterator, queueSlotsIterator);
 
         queueGame.printCrossword();

         queueLettersReader.close();
         queueSlotsReader.close();
    }   
}


class Slot {
    private int row, column;

    public Slot(int row, int column) {
        this.row = row;
        this.column = column;
    }


    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}

class Crossword {
    private char[][] array;
    private int numRows, numColums;
    private final char EMPTY_CHAR = '_';

    public Crossword(int numRows, int numColumns) {
        this.numRows = numRows;
        this.numColums = numColumns;
        array = new char[numRows][numColumns];

        for(int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                array[i][j] = EMPTY_CHAR;
            }
        }
    }

    public void enterLetters(Iterator<Character> letterIterator, Iterator<Slot> slotIterator) {
            Slot currentSlot;

            while(letterIterator.hasNext() && slotIterator.hasNext()) {
                currentSlot = slotIterator.next();
                array[currentSlot.getRow()][currentSlot.getColumn()] = letterIterator.next();
            }
    }

    public void printCrossword() {
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColums; j++) {
                System.out.print(array[i][j]);
            }
            System.out.println("");
        }
    }
}