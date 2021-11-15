import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * EmerickZaneAssignment5
 */
public class EmerickZaneAssignment5 {
    public static void main(String[] args) throws IOException {
       GenericStack<Integer> stackIntOne = new GenericStack<>();
       GenericStack<Integer> stackIntTwo = new GenericStack<>();
       File numbersOne = new File("numbers1.txt");
       File numbersTwo = new File("numbers2.txt");

       Scanner numbersOneReader = new Scanner(numbersOne);
       Scanner numbersTwoReader = new Scanner(numbersTwo);
       
       while(numbersOneReader.hasNext()) {
           stackIntOne.push(numbersOneReader.nextInt());
       }

       while(numbersTwoReader.hasNext()) {
           stackIntTwo.push(numbersTwoReader.nextInt());
       }
       //print portion of Numbers
       System.out.println("Numbers Stack 1, from numbers1.txt");
       printStack(stackIntOne);

       System.out.println("Numbers Stack 2, from numbers2.txt");
       printStack(stackIntTwo);

       GenericStack<Integer> mergedInts = new GenericStack<>();

       mergeStacks(stackIntOne, stackIntTwo, mergedInts);

       GenericStack<Integer> reversedStackInt = new GenericStack<>();
       reverseStack(mergedInts, reversedStackInt);

       System.out.println("Merged stack, lowest on top");
       printStack(reversedStackInt);
       
       numbersOneReader.close();
       numbersTwoReader.close();
    }

    public static <E> void printStack(GenericStack<E> stack) {
        GenericStack<E> tempStack = new GenericStack<>();
        
        while(!stack.isEmpty()) {
            tempStack.push(stack.peek());
            System.out.println(stack.pop());
        }

        while(!stack.isEmpty()) {
            stack.push(tempStack.pop());
        }
        
    }

    public static <E extends Comparable<E>> void mergeStacks(GenericStack<E> stack1, GenericStack<E> stack2, GenericStack<E> mergedStack) {
        boolean oneHasMore = true;
        boolean twoHasMore = true;
        GenericStack<E> tempStack1 = new GenericStack<>();
        GenericStack<E> tempStack2 = new GenericStack<>();

        E tempItem;

        //loop through and compare both stacks
        while(oneHasMore && twoHasMore) {
            //if stack1 is > stack2
            System.out.println(stack1.peek());
            System.out.println(stack2.peek());
            
            if(stack1.peek().compareTo(stack2.peek()) > 0) {
                tempItem = stack1.pop();
                mergedStack.push(tempItem);
                tempStack1.push(tempItem);
            //if stack2 > stack1
            } else if (stack1.peek().compareTo(stack2.peek()) < 0)  {
                tempItem = stack2.pop();
                mergedStack.push(tempItem);
                tempStack2.push(tempItem);
            //if they are equal
            } else {
                tempItem = stack1.pop();
                mergedStack.push(tempItem);
                tempStack1.push(tempItem);

                tempItem = stack2.pop();
                tempStack2.push(tempItem);
            }

            if(stack1.isEmpty()) {
                oneHasMore = false;
                break;
            } 
            if(stack2.isEmpty()) {
                twoHasMore = false;
                break;
            }
        }

        //finish off the remaining stack
        if(oneHasMore) {
            while(oneHasMore) {
                tempItem = stack1.pop();
                mergedStack.push(tempItem);
                tempStack1.push(tempItem);

                if(stack1.isEmpty()) oneHasMore = false;
            }
        }

        if(twoHasMore) {
            while(twoHasMore) {
                tempItem = stack2.pop();
                mergedStack.push(tempItem);
                tempStack2.push(tempItem);

                if(stack2.isEmpty()) twoHasMore = false;
            }
        }

        //replace values in the original stacks
        while(!tempStack1.isEmpty()) {
            stack1.push(tempStack1.pop());
        }

        while(!tempStack2.isEmpty()) {
            stack2.push(tempStack2.pop());
        }
    
    
    }  

    public static <E> void reverseStack (GenericStack<E> stack, GenericStack<E> finalMergedStack) {
        while(!stack.isEmpty()) {
            finalMergedStack.push(stack.pop());
        }
    }

    //print stacks side by side method
}

class GenericStack<E> {
    private ArrayList<E> stack;

    public GenericStack() {
       stack = new ArrayList<>();
    }

    public int getSize() {
         return stack.size();
    }

    public boolean isEmpty() {
        return getSize() == 0;
    }

    public E peek() {
        return stack.get(stack.size() - 1);
    }

    public void push(E item) {
        stack.add(item);
    }

    public E pop() {
        E item;
        int length = stack.size();

        item = stack.get(length - 1);
        stack.remove(length - 1);

        return item;
    }
}