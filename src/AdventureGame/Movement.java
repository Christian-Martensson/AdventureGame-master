package AdventureGame;
import java.util.Random;
import java.util.Scanner;

public class Movement {

    public void availableDirections(Character character) {
        System.out.println("Use letters N, S, E, W to move around. You can move in the following directions: ");


        if (!(character.getCurrentY() + 1 > 4) && Room.isEmpty(character.getCurrentX(), character.getCurrentY() + 1)) { // North
            System.out.printf("North:[N]  ");
        }

        if (!(character.getCurrentY() - 1 < 1) && Room.isEmpty(character.getCurrentX(), character.getCurrentY() - 1)) { // South
            System.out.printf("South:[S]  ");
        }
        if (!(character.getCurrentX() + 1 > 6) && Room.isEmpty(character.getCurrentX() + 1, character.getCurrentY())) { // East
            System.out.printf("East:[E]  ");
        }
        if (!(character.getCurrentX() - 1 < 1) && Room.isEmpty(character.getCurrentX() - 1, character.getCurrentY())) { // West
            System.out.printf("West:[W]  ");
        }



        System.out.println(); // Extra line for formatting
    }

    public void moveRandom(Character character, Movement moveChar, Boss dragonBoss) {
        Random rand = new Random();
        int randomNumber = rand.nextInt(4) + 1;
        boolean foundPath = false;

        switch (randomNumber) {
            case 1: {
                if (moveChar.moveNorth(character, dragonBoss)) {
                    //System.out.println("north");
                }
                break;
            }
            case 2: {
                if (moveChar.moveSouth(character, dragonBoss)) {
                    //System.out.println("south");
                }
                break;
            }
            case 3: {
                if (moveChar.moveWest(character, dragonBoss)) {
                    //System.out.println("west");
                }
                break;
            }
            case 4: {
                if (moveChar.moveEast(character, dragonBoss)) {
                    //System.out.println("east");
                }
                break;
            }
        }
    }

    public boolean selectMovement(Character character, Boss dragonBoss) {
        boolean loop = true;
        boolean characterMoved = false;
        Scanner input = new Scanner(System.in);

        while (loop == true) {
            String direction = input.next();

            if (direction.equals("N") || direction.equals("n")) {
                if (moveNorth(character, dragonBoss) == false ) {

                }
                else {
                    characterMoved = true;
                }
                loop = false;
            }
            else if(direction.equals("S") || direction.equals("s")) {
                if (moveSouth(character, dragonBoss) == false ) {

                }
                else {
                    characterMoved = true;
                }
                loop = false;
            }
            else if (direction.equals("W") || direction.equals("w")) {
                if (moveWest(character, dragonBoss) == false ) {

                }
                else {
                    characterMoved = true;
                }
                loop = false;
            }
            else if (direction.equals("E") || direction.equals("e")) {
                if (moveEast(character, dragonBoss) == false ) {

                }
                else {
                    characterMoved = true;
                }
                loop = false;
            }
            else {
                System.out.println("Invalid choice.");
                loop = true;
            }
        }
        return characterMoved;

    }

    public boolean moveNorth (Character character, Boss dragonBoss) {
        boolean isPossible;

        if ((character.getHasKey() == false) && (character.isTestingRoute() == false) && (character.getCurrentY() + 1 == dragonBoss.getCurrentY() && character.getCurrentX() == dragonBoss.getCurrentX())) {
            System.out.println("There's a golden lock in the wall. Looks like you have to find a key to open it.");
            isPossible = false;
        }
//        if ((character.getHasKey() == false) && (character.isTestingRoute() == true) && (character.getCurrentY() + 1 == dragonBoss.getCurrentY() && character.getCurrentX() == dragonBoss.getCurrentX())) {
//            isPossible = false;
//            System.out.println("Bot fail north");
//        }
        else if (!(character.getCurrentY() + 1 > 4) && Room.isEmpty(character.getCurrentX(), character.getCurrentY() + 1)) {
            character.setCurrentY(character.getCurrentY() + 1);
            isPossible = true;
        }

        else {
            if (character.isTestingRoute() == false) {
                System.out.println("Error, can't go further north.\n");
            }
            isPossible = false;
            isPossible = false;
        }

       return isPossible;
    }

    public boolean moveSouth (Character character, Boss dragonBoss) {
        boolean isPossible;

        if ((character.getHasKey() == false) && (character.isTestingRoute() == false) && (character.getCurrentY() - 1 == dragonBoss.getCurrentY() && character.getCurrentX() == dragonBoss.getCurrentX())) {
            System.out.println("There's a golden lock in the wall. Looks like you have to find a key to open it.");
            isPossible = false;
        }
//        if ((character.getHasKey() == false) && (character.isTestingRoute() == true) && (character.getCurrentY() - 1 == dragonBoss.getCurrentY() && character.getCurrentX() == dragonBoss.getCurrentX())) {
//            isPossible = false;
//            System.out.println("Bot fail south");
//        }
        else if (!(character.getCurrentY() - 1 < 1) && Room.isEmpty(character.getCurrentX(), character.getCurrentY() - 1)) {
            character.setCurrentY(character.getCurrentY() - 1);
            isPossible = true;
        }

        else {
            if (character.isTestingRoute() == false) {
                System.out.println("Error, can't go further south.\n");
            }
            isPossible = false;
            isPossible = false;
        }
        return isPossible;
    }

    public boolean moveWest (Character character, Boss dragonBoss) {
        boolean isPossible;

        if ((character.getHasKey() == false) && (character.isTestingRoute() == false) && (character.getCurrentX() - 1 == dragonBoss.getCurrentX() && character.getCurrentY() == dragonBoss.getCurrentY())) {
            System.out.println("There's a golden lock in the wall. Looks like you have to find a key to open it.");
            isPossible = false;
        }
//        if ((character.getHasKey() == false) && (character.isTestingRoute() == true) && (character.getCurrentX() - 1 == dragonBoss.getCurrentX() && character.getCurrentY() == dragonBoss.getCurrentY())) {
//            isPossible = false;
//            System.out.println("Bot fail west");
//        }
        else if (!(character.getCurrentX() - 1 < 1) && Room.isEmpty(character.getCurrentX() - 1, character.getCurrentY())) {
            character.setCurrentX(character.getCurrentX() - 1);
            isPossible = true;
        }

        else {
            if (character.isTestingRoute() == false) {
                System.out.println("Error, can't go further west.\n");
            }
            isPossible = false;
            isPossible = false;
        }
        return isPossible;
    }

    public boolean moveEast (Character character, Boss dragonBoss) {
        boolean isPossible;

        if ((character.getHasKey() == false) && (character.isTestingRoute() == false) && (character.getCurrentX() + 1 == dragonBoss.getCurrentX() && character.getCurrentY() == dragonBoss.getCurrentY())) {
            System.out.println("There's a golden lock in the wall. Looks like you have to find a key to open it.");
            isPossible = false;
        }
//        if ((character.getHasKey() == false) && (character.isTestingRoute() == true) && (character.getCurrentX() + 1 == dragonBoss.getCurrentX() && character.getCurrentY() == dragonBoss.getCurrentY())) {
//            isPossible = false;
//            System.out.println("Bot fail east");
//        }
        else if (!(character.getCurrentX() + 1 > 6) && Room.isEmpty(character.getCurrentX() + 1, character.getCurrentY())) {
            character.setCurrentX(character.getCurrentX() + 1);
            isPossible = true;
        }

        else {
            if (character.isTestingRoute() == false) {
                System.out.println("Error, can't go further east.\n");
            }
            isPossible = false;
        }
        return isPossible;
    }


}
