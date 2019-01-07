package AdventureGame;

import java.util.Random;
import java.util.Scanner;

/* TO DO
* - Inventory class
* - generell beskrivning av miljö

    1. Superklasser Items och Characters
    3. Hitta skatter
    5. Plocka upp
*
* */

public class Main {
    //Character (player) constructor
    private Character character = new Character ("Conan", 150, 200, 1, 1, 3, 1, 2, 0, false, false);
    private Weapon currentWeapon = new Weapon("Basic Sword", 1, 30, 0, true);

    // Final boss constructor
    private Boss dragonBoss = new Boss("placeHolderDragon", 1, 1, 1, 1, 1,1,1);
    private Potion potion = new Potion("Healing Potion",10, 30,"HP", 20);

    private boolean gameLoop;

    public static void main(String[] args) {
        //init
        Main menu = new Main();
        menu.getMainMenu();
    }

    public void getMainMenu() {
        Scanner input = new Scanner(System.in);
        character.setHealth(150);

        //Continue game menu loop variable
        boolean mainMenuLoop = true;

        int menuChoice = 0;

        System.out.println("Welcome to the adventure game. " +
                "Choose one of the following options by using the keys on your keyboard:");
        while (mainMenuLoop) { // try catch input for the options in main menu.
            boolean validInputloop = false;
            while(!validInputloop) {
                try {
                    System.out.println("1. Enter the dungeon \n2. Set character name \n3. Quit game");
                    menuChoice = input.nextInt(); // player input
                    validInputloop = true;
                } catch (Exception e) {
                    System.out.println("Invalid input. Enter number from 1 to 3.");
                    input.next();
                }
            }


            switch (menuChoice) {

                case 1: { //Enter the dungeon.
                    enterDungeon();
                    break;
                }

                case 2: { //Set player character name.
                    System.out.println("Provide a new name for your character: ");
                    Scanner nameInput = new Scanner(System.in);
                    String name = nameInput.nextLine();
                    character.setName(name);
                    getMainMenu();
                    break;
                }

                case 3: { //Quit the game.
                    mainMenuLoop = false;
                    System.exit(0);
                    break;
                }
                default: {
                    System.out.println("Input out of bounds. Please enter a number from 1 to 3");
                    break;
                }

            }
        }
    }

    public void enterDungeon() {
        boolean pathFound;
        boolean actionEncountered = false;
        boolean pickedUpSomething = false;
        gameLoop = true;

        Movement moveChar = new Movement();
        Combat combat = new Combat();


        System.out.println("You enter the dungeon. On the wall you see a map. You draw a copy of it.\n");
        // Build the map, spawn character and boss.
        character.setTestingRoute(true);
        do {
            Room.createRooms(character);
            dragonBoss = Boss.spawnBoss();
            Room.spawnKey();
            pathFound = Room.checkValidRoute(character, moveChar, dragonBoss);
        } while (!pathFound);
        character.setTestingRoute(false);

        while (gameLoop) {
            boolean hasOccurred = false;
            // an object which holds the current enemy is constructed..
            Enemy currentEnemy = new Enemy("placeholder", 1, 1,1,1, 1);

            while (!actionEncountered) {
                if (character.getCurrentX() == Room.withKey().getX() && character.getCurrentY() == Room.withKey().getY() ) {
                    if (!hasOccurred) {
                        character.setHasKey(true);
                        System.out.println("\n*\n*\n* You find a golden key on the floor. You pick it up.\n*\n*\n");
                        hasOccurred = true;
                    }
                }
                if (character.getCurrentX() == dragonBoss.getCurrentX()  && character.getCurrentY() == dragonBoss.getCurrentY() ) {
                    if (character.getHasKey() == false) {
                        System.out.println("There's a golden lock in the wall. Looks like you have to find a key to open it.");
                        actionEncountered = true;
                        break;
                    }
                    else {
                        enemyTrigger(dragonBoss);
                    }
                    break;
                }

                Room.printRoom(character, dragonBoss);
                boolean characterMoved = false;
                while (!characterMoved) {
                    moveChar.availableDirections(character);
                    characterMoved = moveChar.selectMovement(character, dragonBoss);

                }

                pickedUpSomething = potion.potionFound(character, potion); // potion appears in room according to dropchance

                if (!pickedUpSomething) { // random weapon appears in room according to drop chance (very low)
                    //pickedUpSomething = Weapon.weaponFound(character);

                }
                if (!pickedUpSomething) {
                        actionEncountered = combat.combatChance();
                    if (actionEncountered) {
                        actionEncountered = enemyTrigger(currentEnemy);
                    }
                }
            }
        }
    }

    public boolean enemyTrigger (Enemy currentEnemy) {

        boolean actionEncountered = false;

        if (character.getCurrentX() == dragonBoss.getCurrentX() && character.getCurrentY() == dragonBoss.getCurrentY()) {
            currentEnemy = dragonBoss;
            printDragon();
            System.out.printf("\nYou have encountered a %s with %s HP. Your current HP is %s.\n",
                    currentEnemy.getName(), currentEnemy.getHealth(), character.getHealth());
            inCombatMenu(currentEnemy);
            actionEncountered = false;

            if (dragonBoss.getHealth() <= 0) {
                printTreasure();
                character.setCoins(character.getCoins() + 100);
                System.out.println("\nCongratulations " + character.getName() + "!" + "\nYou have defeated the dragon and " +
                        "amassed: " + character.getCoins() + " gold coins.");
            }
            else if (character.getHealth() <= 0) {
                System.out.printf("You have been slain by the %s. \n", dragonBoss.getName());
            }
            characterDeath();

        }

        // the currentEnemy object is passed to the randomizeEnemy method,
        // which will randomly assign the currentEnemy to one of the three preexisting enemies (see enemies class).
        else {
            currentEnemy = currentEnemy.randomizeEnemy(currentEnemy);
            System.out.printf("\nYou have encountered a %s with %s HP. Your current HP is %s.\n",
                    currentEnemy.getName(), currentEnemy.getHealth(), character.getHealth());

            inCombatMenu(currentEnemy); // Enter a new menu which handles the combat with this enemy.
            actionEncountered = false;

        }
        return actionEncountered;
    }

    public void inCombatMenu (Enemy currentEnemy) {

        Scanner input = new Scanner(System.in);
        boolean loopMenu = true;
        int menuChoice = 0;
        Combat combat = new Combat();

        while (loopMenu) {
            boolean validInput = false;
            while (!validInput) {
                try {
                    System.out.println("1. Attack Monster \n2. Drink a health potion \n3. Buy items \n4. View inventory \n5. Run away");
                    menuChoice = input.nextInt();
                    validInput = true;
                } catch (Exception e) {
                    System.out.println("Invalid input. Enter a number from 1 to 5.");
                    input.next();
                }
            }

            switch (menuChoice) {

                case 1: { // Enter combat

                    combat.hitEnemy(currentEnemy, character, currentWeapon); // method which substract the enemy health by the attackDamage of the character(player) weapon.
                    if (currentEnemy.getHealth() <= 0) { //if the enemy has no more health, do this.
                        System.out.printf("\nYou have killed the %s!\n", currentEnemy.getName());
                        loopMenu = false;

                        character.getLoot(character, currentEnemy);
                        character.getHealthPotion(character);

                        break;
                    }

                    combat.hitCharacter(currentEnemy, character); // method which subtracts the character health by the attackDamage of the enemy.

                    if (character.getHealth() <= 0) { //if the character has no more health, do this.
                        System.out.printf("\nYou have been killed by the %s.\n", currentEnemy.getName());
                        characterDeath();
                        loopMenu = false;
                        break;
                    }

                    break;
                }

                case 2: { // Drink a health potion
                    // method which adds health to character and substracts numberOfHealthPotions that the character has.
                    character = potion.usePotion(character);
                    break;
                }
                case 3: { // Buy items


                    // allows the player to select between 3 pre-existing weapons (functionality will be different later on).
                    currentWeapon = currentWeapon.buyItem(currentWeapon, character, potion);
                    // method which "equips" selected weapon by equalling the attackDamage and attackRate of the weapon to the character attributes.
                    // change: add the attackDamage instead of equalling it to the characters attribute?
                    break;

                }

                case 4: { // Check your inventory

                    System.out.printf("You have the following weapon: %s.\n", currentWeapon.getName());
                    System.out.println("You have " + character.getNumberOfHealthPotions() + " health potions");
                    System.out.println("You have " + character.getCoins() + " coins");
                    break;

                }

                case 5: { // Run away
                    //runAway();
                    loopMenu = false;
                    System.out.println("You run away, like a coward.");
                    character.setHealth(character.getHealth() - 25);
                    System.out.println("You take 25 damage while running away.");
                    break;
                }
                default:
                    System.out.println("Input out of range. Please enter a number from 1 to 5.");

                    break;
            }
        }
    }

    public void characterDeath() {
        character.setHealth(150);
        character.setNumberOfHealthPotions(3);
        currentWeapon = new Weapon ("Basic Sword", 1, 30, 0, true);
        System.out.println("Press Enter key to return to main menu...");
        try
        {
            System.in.read();
            gameLoop = false;
            getMainMenu();
        }
        catch(Exception e)
        {
        }


    }
    public void printTreasure() {
        System.out.println("" +
                "                            _.--.\n" +
                "                        _.-'_:-'||\n" +
                "                    _.-'_.-::::'||\n" +
                "               _.-:'_.-::::::'  ||\n" +
                "             .'`-.-:::::::'     ||\n" +
                "            /.'`;|:::::::'      ||_\n" +
                "           ||   ||::::::'     _.;._'-._\n" +
                "           ||   ||:::::'  _.-!oo @.!-._'-.\n" +
                "           \\'.  ||:::::.-!()oo @!()@.-'_.|\n" +
                "            '.'-;|:.-'.&$@.& ()$%-'o.'\\U||\n" +
                "              `>'-.!@%()@'@_%-'_.-o _.|'||\n" +
                "               ||-._'-.@.-'_.-' _.-o  |'||\n" +
                "               ||=[ '-._.-\\U/.-'    o |'||\n" +
                "               || '-.]=|| |'|      o  |'||\n" +
                "               ||      || |'|        _| ';\n" +
                "               ||      || |'|    _.-'_.-'\n" +
                "               |'-._   || |'|_.-'_.-'\n" +
                "            jgs '-._'-.|| |' `_.-'\n" +
                "                    '-.||_/.-'");
    }

    public void printDragon() {
        System.out.println("" +
                "                                               _   __,----'~~~~~~~~~`-----.__\n" +
                "                                        .  .    `//====-              ____,-'~`\n" +
                "                        -.            \\_|// .   /||\\\\  `~~~~`---.___./\n" +
                "                  ______-==.       _-~o  `\\/    |||  \\\\           _,'`\n" +
                "            __,--'   ,=='||\\=_    ;_,_,/ _-'|-   |`\\   \\\\        ,'\n" +
                "         _-'      ,='    | \\\\`.    '',/~7  /-   /  ||   `\\.     /\n" +
                "       .'       ,'       |  \\\\  \\_  \"  /  /-   /   ||      \\   /\n" +
                "      / _____  /         |     \\\\.`-_/  /|- _/   ,||       \\ /\n" +
                "     ,-'     `-|--'~~`--_ \\     `==-/  `| \\'--===-'       _/`\n" +
                "               '         `-|      /|    )-'\\~'      _,--\"'\n" +
                "                           '-~^\\_/ |    |   `\\_   ,^             /\\\n" +
                "                                /  \\     \\__   \\/~               `\\__\n" +
                "                            _,-' _/'\\ ,-'~____-'`-/                 ``===\\\n" +
                "                           ((->/'    \\|||' `.     `\\.  ,                _||\n" +
                "             ./                       \\_     `\\      `~---|__i__i__\\--~'_/\n" +
                "            <_n_                     __-^-_    `)  \\-.______________,-~'\n" +
                "             `B'\\)                  ///,-'~`__--^-  |-------~~~~^'\n" +
                "             /^>                           ///,--~`-\\\n" +
                "            `  `                                       -Tua Xiong\n");
    }
}
