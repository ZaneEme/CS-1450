import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

/**
 * Name: Zane Emerick 
 * Class: CS 1450 Section 001
 * Assignment #3 
 * Due: Feb 13, 2020
 * 
 * Description: Create an insect abstract class and multiple subclasses that 
 * represent various types of insects. These insects need to use interfaces 
 * to represent their different abilities. Next, read text from a file and 
 * categorize it into the various insect types and create methods that can 
 * categorize them based on whether the insect helps with decomposition or 
 * not. After that, create a method that can determine the insect with the 
 * highest overall stats. Finally, display all of this data to the user.
 */

public class EmerickZaneAssignment3 {
    public static void main(String[] args) throws IOException {
        File file = new File("insects.txt");
        Scanner reader = new Scanner(file);
        
        int arrayLength = reader.nextInt();
        Insect[] insects = new Insect[arrayLength];

        //read info and create new insect
        for(int i = 0; i < insects.length; i++) {
            String type = reader.next().substring(0, 1);
            String name = reader.next();
            int pollinateAbility = reader.nextInt();
            int buildAbility = reader.nextInt();
            int predatorAbility = reader.nextInt();
            int decomposeAbility = reader.nextInt();

            switch(type) {
                case "h":
                    insects[i] = new HoneyBee(pollinateAbility, buildAbility, name);
                    insects[i].setType("honey bee");
                    break;
                case "l":
                    insects[i] = new LadyBug(predatorAbility, pollinateAbility, name);
                    insects[i].setType("lady Bug");
                    break;
                case "a":
                    insects[i] = new Ant(predatorAbility, buildAbility, decomposeAbility, name);
                    insects[i].setType("ant");
                    break;
                case "p":
                    insects[i] = new PrayingMantis(predatorAbility, name);
                    insects[i].setType("praying mantis");
                    break;
            }
        }
        //writing portion of assignment
        ArrayList<Insect> nonDecomposers = findDoNotDecompose(insects);
        int mostAbilities = findMostAble(insects);

        System.out.println("Insects that do not help with decomposition:");
        System.out.println("--------------------------------------------");
        for(int i = 0; i < nonDecomposers.size(); i++) {
            System.out.printf("%n%s is a %s and does not help with decomposition%n", nonDecomposers.get(i).getName(), nonDecomposers.get(i).getType());
            System.out.println(nonDecomposers.get(i).purpose());
            displayAbilities(nonDecomposers.get(i));
        }

        System.out.println("\nInsect with the most abilities:");
        System.out.println("-------------------------------\n");
        System.out.printf("The winner is %s the %s%n", insects[mostAbilities].getName(), insects[mostAbilities].getType());
        System.out.println(insects[mostAbilities].purpose());
        displayAbilities(insects[mostAbilities]);

        reader.close();
    }

    /**
     * parse the list for insects who do not help decompose matter.
     * @param insects an array containing all of the insects
     * @return an ArrayList containing every insect who is not a decomposer
     */
    public static ArrayList<Insect> findDoNotDecompose(Insect[] insects) {
        ArrayList<Insect> nonDecomposers = new ArrayList<Insect>();
        
        for(int i = 0; i < insects.length; i++) {
            if(!(insects[i] instanceof Decomposer)) {
                nonDecomposers.add(insects[i]);
            }
        }
        return nonDecomposers;
    }

    /**
     * find the insect with the highest overall stats
     * @param insects  an array containing all of the insects
     * @return the index of the insect with the highest total stats
     */
    public static int findMostAble(Insect[] insects) {
        int topAbilityNumber = 0;
        int mostAbleIndex = 0;
        for(int i = 0; i < insects.length; i++) {
            int currentAbilityNumber = 0;
            if(insects[i] instanceof HoneyBee) {
                currentAbilityNumber = ((HoneyBee)insects[i]).pollinate();
                currentAbilityNumber += ((HoneyBee)insects[i]).build();

            } else if(insects[i] instanceof LadyBug) {
                currentAbilityNumber = ((LadyBug)insects[i]).pollinate();
                currentAbilityNumber += ((LadyBug)insects[i]).predator();

            } else if(insects[i] instanceof Ant) {
                currentAbilityNumber = ((Ant)insects[i]).build();
                currentAbilityNumber += ((Ant)insects[i]).decompose();
                currentAbilityNumber += ((Ant)insects[i]).predator();

            } else if(insects[i] instanceof PrayingMantis) {
                currentAbilityNumber += ((PrayingMantis)insects[i]).predator();
            }  

            if(currentAbilityNumber > topAbilityNumber) {
                topAbilityNumber = currentAbilityNumber;
                mostAbleIndex = i;
            }
        }
        
        return mostAbleIndex;
    }
    /** 
     * Display the various abilities of a specified insect
     * @param insect the insect to be displayed
     */
    public static void displayAbilities(Insect insect) {
        if(insect instanceof HoneyBee) {
            System.out.println("Pollinating ability: " + ((HoneyBee)insect).pollinate());
            System.out.println("Building ability: " + ((HoneyBee)insect).build());
        } else if (insect instanceof LadyBug) {
            System.out.println("Pollinating ability: " + ((LadyBug)insect).pollinate());
            System.out.println("Predator ability: " + ((LadyBug)insect).predator());
        } else if (insect instanceof Ant) {
            System.out.println("Building ability: " + ((Ant)insect).build());
            System.out.println("Predator ability: " + ((Ant)insect).predator());
            System.out.println("Decomposing ability: " + ((Ant)insect).decompose());
        } else if (insect instanceof PrayingMantis) {
            System.out.println("Predator ability: " + ((PrayingMantis)insect).predator());
        }
    }
}

interface Pollinator {
    public abstract int pollinate();
}

interface Builder {
    public abstract int build();
}

interface Predator {
    public abstract int predator();
}

interface Decomposer {
    public abstract int decompose();
}


abstract class Insect {
    private String name;
    private String type;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    abstract String purpose();
}

class HoneyBee extends Insect implements Pollinator, Builder {
    private int pollinateAbility;
    private int buildAbility;

    public HoneyBee(int pollinateAbility, int buildAbility, String name) {
        this.pollinateAbility = pollinateAbility;
        this.buildAbility = buildAbility;
        setName(name);
        setType("HoneyBee");
    }

    @Override
    public String purpose() {
        return "I'm popular for producing honey but I also pollinate 35% of the crops!" + 
        " Without me, 1/3 of the food you eat would not be available!";
    }

    @Override
    public int build() {
        return buildAbility;
    }

    @Override
    public int pollinate() {
        return pollinateAbility;
    }
}

class LadyBug extends Insect implements Pollinator, Predator {
    private int predatorAbility;
    private int pollinateAbility;

    public LadyBug(int predatorAbility, int pollinateAbility, String name) {
        this.predatorAbility = predatorAbility;
        this.pollinateAbility = pollinateAbility;
        setName(name);
        setType("LadyBug");
    }

    @Override
    public String purpose() {
        return "Named after the Virgin Mary, I'm considered good luck if I land on you!" + 
        " I'm a pest control expert eating up to 5,000 plant pests during my life span.";
    }

    @Override
    public int predator() {
        return predatorAbility;
    }

    @Override
    public int pollinate() {
        return pollinateAbility;
    }
}

class Ant extends Insect implements Builder, Predator, Decomposer {
    private int buildAbility;
    private int predatorAbility;
    private int decomposeAbility;

    public Ant(int predatorAbility, int buildAbility, int decomposeAbility, String name) {
        this.predatorAbility = predatorAbility;
        this.buildAbility = buildAbility;
        this.decomposeAbility = decomposeAbility;
        setName(name);
        setType("Ant");
    }

    @Override
    public String purpose() {
        return "Don't squash me, I'm an ecosystem engineer! Me and my 20 million friends" + 
        " accelerate decomposition of dead wood, aerate soil, improve drainage, and eat " + 
        "insects like ticks and termites!";
    }

    @Override
    public int build() {
        return buildAbility;
    }

    @Override
    public int predator() {
        return predatorAbility;
    }

    @Override
    public int decompose() {
        return decomposeAbility;
    }
}

class PrayingMantis extends Insect implements Predator {
    private int predatorAbility;

    public PrayingMantis(int predatorAbility, String name) {
        this.predatorAbility = predatorAbility;
        setName(name);
        setType("PrayingMantis");
    }

    @Override
    public String purpose() {
        return "I'm an extreme predator quick enough to catch a fly. Release me in a garden" + 
        "and I'll eat beetles, grasshoppers, crickets and even pesky moths.";
    }

    @Override
    public int predator() {
        return predatorAbility;
    }
}