
import java.util.Arrays;
import java.util.Scanner;
import java.io.*;

/**
 * Name: Zane Emerick 
 * Class: CS 1450 
 * Assignment #1 
 * Due: Jan 30, 2020
 * 
 * Description: Create a program that fills randomly sized arrays
 *  with random numbers. Sort and display both arrays, before 
 *  merging and writing them to a file. Finally, read both arrays
 *  from the file and remove duplicate numbers.
 */
public class EmerickZaneAssignment1 {
    public static void main(String[] args) throws IOException{

        //create lengths of arrays and display
        int sizeA = (int)(Math.random() * 10) + 1;
        int sizeB = (int)(Math.random() * 10) + 1;

        System.out.println("Size 1 = " + sizeA);
        System.out.println("Size 2 = " + sizeB);

        //generate and fill arrays
        int[] arrayA = new int[sizeA];
        int[] arrayB = new int[sizeB];

        for (int i = 0; i < arrayA.length; i++) {
            arrayA[i] = (int)(Math.random() * 25) + 1;
        }

        for (int i = 0; i < arrayB.length; i++) {
            arrayB[i] = (int)(Math.random() * 25) + 1;
        }

        //sort then display arrays
        Arrays.sort(arrayA);
        Arrays.sort(arrayB);

        System.out.println("\nThe first array has " + sizeA + " numbers");
        for (int i : arrayA) {
            System.out.println(i);
        }
        
        System.out.println("\nThe second array has " + sizeB + " numbers");
        for (int i : arrayB) {
            System.out.println(i);
        }


        //start file writing section of assignment
        System.out.println("\nWriting " + (sizeA + sizeB) + " values to the file:");

        File file = new File("assignment1.txt");
        PrintWriter resultsPrinter = new PrintWriter(file);

        //runs while both arrays still have leftover values
        int countA = 0;
        int countB = 0;
        while(countA < sizeA && countB < sizeB) {
            if(arrayA[countA] <= arrayB[countB]) {
                resultsPrinter.println(arrayA[countA]);
                System.out.println(arrayA[countA]);
                countA++;
            } else {
                resultsPrinter.println(arrayB[countB]);
                System.out.println(arrayB[countB]);
                countB++;
            }
        }

        //runs when one array empties, and finishes the other array
        while(countA < sizeA) {
            resultsPrinter.println(arrayA[countA]);
            System.out.println(arrayA[countA]);
            countA++;
        }
        while(countB < sizeB) {
            resultsPrinter.println(arrayB[countB]);
            System.out.println(arrayB[countB]);
            countB++;
        }
        resultsPrinter.close();
    

        //start file reading portion of assignment
        System.out.println("\nReading from file:");
        Scanner fileReader = new Scanner(file);

        int[] ReadArrayNoDuplicates = new int[sizeA + sizeB];

        ReadArrayNoDuplicates[0] = fileReader.nextInt();

        //loop through file and save to array if value isn't a duplicate
        int readCount = 1;
        do{
            int currentValue = fileReader.nextInt();
            if(ReadArrayNoDuplicates[readCount - 1] != currentValue) {
                ReadArrayNoDuplicates[readCount] = currentValue;
                readCount++;
            }
        } while(fileReader.hasNext());

        fileReader.close();

        //copy read array with zeros to a shorter array without them
        int readArrayLength = 0;
        for (int i : ReadArrayNoDuplicates) {
            if(i != 0) {
                readArrayLength++;
            }
        }

        int[] finalReadArray = new int[readArrayLength];

        for (int i = 0; i < finalReadArray.length; i++) {
            finalReadArray[i] = ReadArrayNoDuplicates[i];
        }

        //print final values to console
        for (int i : finalReadArray) {
            System.out.println(i);
        }
    }
    
}