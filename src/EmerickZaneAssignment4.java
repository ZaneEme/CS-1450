import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * Name: Zane Emerick 
 * Class: CS 1450 Section 001
 * Assignment #4 
 * Due: Feb 21, 2020
 * 
 * Description: Design a pinball machine with certain targets to be specified
 *  in a file. The machine needs to be able to store these targets as Objects
 *  in a 2D array and be able to be played based upon information written in
 *  a second file. Next, the program needs to be able to generate a report
 *  describing which targets were hit and which were not, and how many points
 *  hit would be worth. Finally, all of this data needs to be printed out to
 *  the user.
 */

public class EmerickZaneAssignment4 {
    public static void main(String[] args) throws IOException {
        File pinballTargetsFile = new File("PinballMachineTargets.txt");

        Scanner targetReader = new Scanner(pinballTargetsFile);

        int numRows = targetReader.nextInt();
        int numCols = targetReader.nextInt();

        PinBallMachine playField = new PinBallMachine(numRows, numCols);

        int currRow, currColumn, id, points;
        Target currTarget;
        String type;

        //loop to create the targets and load them into machine
        while (targetReader.hasNext()) {
            currRow = targetReader.nextInt();
            currColumn = targetReader.nextInt();
            type = targetReader.next();
            id = targetReader.nextInt();
            points = targetReader.nextInt();

            currTarget = new Target(type, id, points);

            playField.addTargetToPlayingField(currRow, currColumn, currTarget);
        }

        //display, play, print results
        playField.displayPlayingField();

        play(playField);
        System.out.println("\n\n");
        printReport(playField);

        targetReader.close();
    }

    /**
     * method to play the actual game using the simulated targets and numbers
     * @param pinBallMachine the PinBallMachine to be played on
     * @throws IOException
     */
    public static void play(PinBallMachine pinBallMachine) throws IOException {
        File playFile = new File("Play.txt");
        Scanner playReader = new Scanner(playFile);

        int row, column;
        Target location;
        int currentScore = 0;

        System.out.println("----------------------------------");
        System.out.println("      Simulated Pinball Game      ");
        System.out.println("----------------------------------");
        System.out.printf("%-12s %-6s %-8s %-5s%n", "Target", "ID", "Points", "Score");

        while (playReader.hasNext()) {
            row = playReader.nextInt();
            column = playReader.nextInt();

            if (pinBallMachine.getTarget(row, column) != null) {
                location = pinBallMachine.getTarget(row, column);

                location.incrementHits();
                currentScore += location.getPoints();

                System.out.printf("%-12s %-6d %-8d %-5d%n", location.getType(), location.getId(), location.getPoints(),
                        currentScore);
            }
        }
        playReader.close();
    }

    /**
     * prints a report showing how the game went and which targets were hit
     * @param pinBallMachine the pinBallMachine the game was played on
     */
    public static void printReport(PinBallMachine pinBallMachine) {
        ArrayList<TargetReport> reportsList = new ArrayList<>();
        TargetReport report;
        Target location;

        for(int i = 0; i < pinBallMachine.getRows(); i++) {
            for(int j = 0; j < pinBallMachine.getCols(); j++) {
                location = pinBallMachine.getTarget(i, j);
                if( location != null) {
                    report = new TargetReport(i, j, location.getId(), location.getPoints(), location.getHits(), location.getType());
                    reportsList.add(report);
                }
            }
        }

        /**
         * Collections.sort() prints a random string of binary?
         * Not really sure why and couldn't figure out how to fix it.
         * Code runs correctly otherwise.
        */
        Collections.sort(reportsList, Collections.reverseOrder());


        System.out.println("\n-------------------------------------------------");
        System.out.println("       Pinball Machine Targets Hit Report        ");
        System.out.println("-------------------------------------------------");
        System.out.printf("%-5s %-10s %-10s %-5s %-10s %-5s%n", "Row", "Column", "Type", "ID", "Points", "Hits");
        System.out.println("-------------------------------------------------");

        for(int i = 0; i < reportsList.size(); i++) { 
            System.out.print(reportsList.get(i).print());
        }
        
    }
    
}


class PinBallMachine {
    private int numberRows, numberCols;
    private Target[][] playingField;

    public PinBallMachine(int numberRows, int numberCols) {
        this.numberRows = numberRows;
        this.numberCols = numberCols;

        playingField = new Target[numberRows][numberCols];
    }

    public int getRows() {
        return numberRows;
    }

    public int getCols() {
        return numberCols;
    }

    public void addTargetToPlayingField(int row, int column, Target target) {
        playingField[row][column] = target;
    }

    public Target getTarget(int row, int column) {
        Target location = playingField[row][column];

        if(location == null) {
            location = null;
        }
        return location;
    }

    /**
     * Displays the playing field as a grid
     */
    public void displayPlayingField() {
        System.out.print("       ");
        for(int i = 0; i < numberCols; i++) {
            System.out.printf("%-12s", "column: " + i);
        }
        System.out.println();

        for(int i = 0; i < (numberCols * 12) + 4; i++) {
            System.out.print("-");
        }
        System.out.println();

        Target currentLocation;
        for(int i = 0; i < numberRows; i++) {
            System.out.print("row: " + i);

            for(int j = 0; j < numberCols; j++) {

                currentLocation = playingField[i][j];
                
                if(currentLocation == null) {
                    System.out.printf(" %-11s", "---------");
                } else {
                    System.out.printf(" %-11s", currentLocation.getType());
                }
            }
            System.out.println("\n");
        }
    }
}

class Target {
    private String type;
    private int id, points, hits;

    public Target(String type, int id, int points) {
        this.type = type;
        this.id = id;
        this.points = points;
    }

    public int getId() {
        return id;
    }

    public int getPoints() {
        return points;
    }

    public int getHits() {
        return hits;
    }

    public String getType() {
        return type;
    }

    public void incrementHits() {
        hits++;
    }
}

class TargetReport implements Comparable<TargetReport> {
    private int rowNumber, columnNumber, id, points, hits;
    private String type;

    public TargetReport(int rowNumber, int columnNumber, int id, int points, int hits, String type) {
        this.type = type;
        this.rowNumber = rowNumber;
        this.columnNumber = columnNumber;
        this.id = id;
        this.points = points;
        this.hits = hits;
    }

    public String print() {
        return String.format("%-5d %-10d %-10s %-5d %-10d %-5d%n", rowNumber, columnNumber, type, id, points, hits);
    }

    /**
     * Overrides the compareTo method to use for sorting
     * @param otherReport the report to be compared to
     * @return -1 if other has more hits, and +1 if this has more hits
     */
    @Override
    public int compareTo(TargetReport otherReport) {
        int token = 0;
        if(this.hits > otherReport.hits) {
            token = 1;
        } else if (this.hits < otherReport.hits) {
            token = -1;
        }
        System.out.print(token);
        return token;
    }
} 