import java.io.*;
import java.util.Scanner;

/**
 * Name: Zane Emerick 
 * Class: CS 1450 Section 001
 * Assignment #1 
 * Due: Feb 6, 2020
 * 
 * Description: Create a parent train class with four subclasses
 * for different types of trains. Next, read from a file containing a
 * list of trains and place them into a polymorphic array.
 * Finally, create a chart of the different trains' attributes
 * and display them to the user.
 */

public class EmerickZaneAssignment2 {
    public static void main(String[] args) throws IOException {
        File file = new File("trains.txt");
        Scanner reader = new Scanner(file);
        
        int listLength = reader.nextInt();
        Train[] trains = new Train[listLength];

        //loop to create train objects
        for(int i = 0; i < listLength; i++) {
            String type = reader.next();
            double topSpeed = reader.nextDouble();
            String name = reader.nextLine();
            //determine type of train
            switch(type) {
                case "highspeed":
                    trains[i] = new HighSpeed(name, topSpeed);
                    break;
                case "lightrail":
                    trains[i] = new Lightrail(name, topSpeed);
                    break;
                case "monorail":
                    trains[i] = new Monorail(name, topSpeed);
                    break;
                case "cog":
                    trains[i] = new Cog(name, topSpeed);
                    break;
            }
        }

        //display section of assignment
        System.out.println("+----------------------------------------------------------+");
        System.out.printf("| %-11s%-24s%-15s%-3s|%n", "Type", "Name", "Top Speed", "Benefit");
        System.out.println("+----------------------------------------------------------+");

        for(Train i : trains) {
            System.out.printf("  %-10s%-25s%-15.1f%-10s%n", i.getType(), i.getName(), i.getTopSpeed(), i.benefit());
        }
        reader.close();
    }
    
}


class Train {
    private String type;
    private String name;
    private double topSpeed;

    public Train(String type, String name, double topSpeed) {
        this.type = type;
        this.name = name;
        this.topSpeed = topSpeed;
    }

    //getters and setters
    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public double getTopSpeed() {
        return topSpeed;
    }

    //general methods
    public String benefit(){
        return "Better than walking.";
    }
}

class HighSpeed extends Train {
    public HighSpeed(String name, double topSpeed) {
        super("HighSpeed", name, topSpeed);
    }

    @Override
    public String benefit() {
        return "Travels at speeds between 125 and 267 mph";
    }
}

class Monorail extends Train {
    public Monorail(String name, double topSpeed) {
        super("Monorail", name, topSpeed);
    }

    @Override
    public String benefit() {
        return "Minimal footprint and quieter";
    }
}

class Lightrail extends Train {
    public Lightrail(String name, double topSpeed) {
        super("Lightrail", name, topSpeed);
    }

    @Override
    public String benefit() {
        return "Tighter turning radius";
    }
}

class Cog extends Train {
    public Cog(String name, double topSpeed) {
        super("Cog", name, topSpeed);
    }

    @Override
    public String benefit() {
        return "Can climb grades up to 48%";
    }
}