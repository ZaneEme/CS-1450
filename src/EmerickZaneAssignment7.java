import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * Name: Zane Emerick 
 * Class: CS 1450 Section 001
 * Assignment #7 
 * Due: April 9, 2020
 * 
 * Description: Design an escape room game that takes an input as a list of
 * players and assigns each one a hashed score based upon their name and seat.
 * Store these players and their scores in a custom-built Priority Que so they
 * can be ordered and read through. Finally, print the final scores out to the
 * user in the console.
 */
public class EmerickZaneAssignment7 {
    public static void main(String[] args) throws IOException {
        //variables / reader
        File file = new File("players.txt");
        Scanner in = new Scanner(file);
        Player[] seats = new Player[25];

        //read through the entire file and add to seats array
        while(in.hasNext()) {
            String name = in.next();
            int ranking = in.nextInt();
            int seat = in.nextInt();

            Player player = new Player(name, ranking, seat);
            seats[player.getSeat()] = player;
        }

        //create escape game and run
        EscapeGame game = new EscapeGame();
        EscapeGameController gameController = new EscapeGameController();

        gameController.movePlayersIntoEscapeGame(seats, game);
        gameController.simulateGame(game);
        gameController.displayResults(game);
        
        in.close();
    }
}

class Player implements Comparable <Player> {
    private String name;
    private int ranking, seat, score;

    //initialize player, sets score to 0
    public Player(String name, int ranking, int seat) {
        this.name = name;
        this.ranking = ranking;
        this.seat = seat;
        score = 0;
    }

    //getters and setters
    public String getName() {
        return name;
    }

    public int getRanking() {
        return ranking;
    }

    public int getScore() {
        return score;
    }

    public int getSeat() {
        return seat;
    }

    public void setScore(int score) {
        this.score = score;
    }

    //for comparable
    public int compareTo(Player p) {
        if(score < p.getScore() || p == null) {
            return -1;
        } else if(this.score == p.getScore()) {
            return 0;
        }
        return 1;
    }
}

class EscapeRoom {
    //creates a hashed code
    private int hash(String key) {
        int hash = 0;
        for (int i = 0; i < key.length(); i++) {
            hash += key.charAt(i);
            hash += (hash << 10);
            hash ^= (hash >> 6);   
        }   
        hash += (hash << 3);   
        hash ^= (hash >> 11);   
        hash += (hash << 15);
        return Math.abs(hash);
    }

    //runs the hash based on name and ranking
    public int tryToEscape(String playerName, int playerRanking) {
        String key = playerName + playerRanking;

        int score = hash(key) % (101);
        return score;
    }
}

class EscapeGame {
    private Queue<Player> waitingQue;
    private PriorityQue resultsQue;
    private EscapeRoom escapeRoom;

    public EscapeGame() {
        waitingQue = new LinkedList<>();
        resultsQue = new PriorityQue();
        escapeRoom = new EscapeRoom();
    }

    public boolean isWaitingQueEmpty() {
        return waitingQue.isEmpty();
    }

    public void addPlayerToWaitingQue(Player player) {
        waitingQue.offer(player);
    }

    public Player removePlayerFromWaitingQueue() {
        return waitingQue.remove();
    }

    public boolean isResultsQueueEmpty() {
        return resultsQue.isEmpty();
    }

    public void addPlayerToResultsQueue(Player player) {
        resultsQue.offer(player);
    }

    public Player removePlayerFromResultsQueue() {
        return resultsQue.remove();
    }

    public Player peekResultsQueue() {
        return resultsQue.peek();
    }

    public int tryToEscape(String playerName, int playerRanking) {
        return escapeRoom.tryToEscape(playerName, playerRanking);
    }
}

class EscapeGameController {

    public void movePlayersIntoEscapeGame (Player[] seats, EscapeGame game) {
        System.out.println("Moving players from seats into game:");
        System.out.println("------------------------------------");

        for(int i = 0; i < seats.length; i++) {
            if(seats[i] != null) {
                System.out.println("Moved to game: " + seats[i].getName() + " from seat " + i);
                game.addPlayerToWaitingQue(seats[i]);
            }
        }
    }

    public void simulateGame(EscapeGame game) {
        Player player;

        System.out.println("\nStarting escape game:");
        System.out.println("---------------------");
        System.out.printf("%-7s%-7s%-7s%n", "Name", "Score", "Leader");
        System.out.println("---------------------");
        while(!game.isWaitingQueEmpty()) {
            player = game.removePlayerFromWaitingQueue();
            player.setScore(game.tryToEscape(player.getName(), player.getRanking()));
            game.addPlayerToResultsQueue(player);

            System.out.printf("%-7s%-7d%-7s%n", player.getName(), player.getScore(), game.peekResultsQueue().getName());

        }
    }

    public void displayResults(EscapeGame game) {
        Player player;
        System.out.println("\nEscape game Results:");
        System.out.println("--------------------");
        System.out.printf("%-7s%-7s%n", "Name", "Score");
        System.out.println("--------------------");
        while(!game.isResultsQueueEmpty()) {
            player = game.removePlayerFromResultsQueue();
            System.out.printf("%-7s%-7d%n", player.getName(), player.getScore());
        }
    }
}

class PriorityQue {
    private Player[] list;
    private int numPlayers;

    public PriorityQue() {
        list = new Player[30];
        numPlayers = 0;
    }

    public boolean isEmpty() {
        return (numPlayers == 0);
    }

    public Player peek() {
        if(numPlayers == 0) {
            return null;
        } else {
            return list[numPlayers - 1];
        }
    }

    public boolean offer(Player player) {
        if(list[list.length - 1] != null) {
            return false;
        } else {
            list[numPlayers] = player;
            numPlayers++;
            selectionSort(list);
            return true;
        }
    }

    public Player remove() {
        Player player = list[numPlayers - 1];
        list[numPlayers - 1] = null;
        numPlayers -= 1;
        return player;
    }

    //run a selection sort algorithm on the array
    private void selectionSort(Player[] list) {

        for(int i = 0; i < list.length - 1; i++) {
            Player smallest = list[i];
            int smallestIndex = i;

            for(int j = i + 1; j < list.length; j++) {
                if(list[j] != null && smallest.compareTo(list[j]) > 0) {
                    smallest = list[j];
                    smallestIndex = j;
                }
            }

            if(smallestIndex != i) {
                list[smallestIndex] = list[i];
                list[i] = smallest;
            }
        }
    }
}