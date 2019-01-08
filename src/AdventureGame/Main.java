package AdventureGame;

import java.util.Scanner;

/*
* This is an adventure game with features such as randomized map
*
*
*
* */


public class Main {
    // Constructors for objects which are present throughout the entire game.
    private Character character = new Character ("Conan", 150, 30, 1, 1, 3, 1, 2, 0, false, false);
    private Weapon currentWeapon = new Weapon("Basic Sword", 1, 25, 0, true, "normal");
    private Boss dragonBoss = new Boss("placeHolderDragon", 1, 1, 1, 1, 1,1,1);
    private Potion potion = new Potion("Healing Potion",25, 30,"HP", 20);

    // Variable necessary to stop the game from various methods
    private boolean gameLoop;

    public static void main(String[] args) {
        //Initialisation of the program
        Main menu = new Main();
        menu.getMainMenu();
    }

    public void getMainMenu() {
        Scanner input = new Scanner(System.in);

        // Variable for main menu loop
        boolean mainMenuLoop = true;
        // Player menu input
        int menuChoice = 0;

        System.out.println("Welcome to the adventure game. " +
                "Choose one of the following options by using the keys on your keyboard:");

        // try catch input for the options in main menu.
        while (mainMenuLoop) {
            boolean validInputloop = false;
            while(!validInputloop) {
                try {
                    System.out.println("1. Enter the dungeon \n2. Set character name \n3. Quit game");
                    menuChoice = input.nextInt(); // player input
                    validInputloop = true; // if no exception, exit loop
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

                case 2: { //Set character name.
                    Scanner nameInput = new Scanner(System.in);
                    System.out.println("Provide a new name for your character: ");
                    String name = nameInput.nextLine();
                    character.setName(name);
                    System.out.printf("Your character name is %s.\n\n", character.getName());
                    getMainMenu();
                    break;
                }

                case 3: { //Quit game.
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

    public void buildMap(Movement moveChar) {
        boolean pathFound; // path from starting position to boss (must be controlled before the game starts)
        character.setTestingRoute(true); // while true, doesn't trigger events that would normally print (would flood the chat during random movement to check path).
        // Loop through this until a playable randomly generated room has been generated (must be a path from start to finish)
        do {
            Room.createRooms(character); // create random rooms.
            dragonBoss = Boss.spawnBoss(); // randomly select which room will contain the boss and end (always the same x-coordinate: 6).
            Room.spawnKey(); // randomly select which room that will contain the key (always one of the four on x-coordinate 4, because these are set to always exist).
            pathFound = Room.checkValidRoute(character, moveChar, dragonBoss); // method which randomly moves the character from start until it reaches the end room. Breaks after 400 random moves.
        } while (!pathFound);
        character.setTestingRoute(false);

    }

    public void enterDungeon() {
        boolean actionEncountered = false; // variable to allow only one action at a time.
        gameLoop = true;

        Movement moveChar = new Movement();
        Combat combat = new Combat();

        // Build the map, spawn character and boss.
        buildMap(moveChar);

        System.out.println("You enter the dungeon. On the wall you see a map. You draw a copy of it.\n");

        while (gameLoop) { // the gameplay loops in here until death or victory.
            boolean hasOccurred = false;

            // a placeholder object which holds the current enemy is constructed..
            Enemy currentEnemy = new Enemy("placeholder", 1, 1,1,1, 1);


            // do this until something happens (i.e. let the player move around the rooms until a random event is triggered).
            while (!actionEncountered) {
                //if character enters the room containing the key, run this code and nothing else.
                if (character.getCurrentX() == Room.withKey().getX() && character.getCurrentY() == Room.withKey().getY() ) {
                    // if this is the first time happening, run this. Otherwise do nothing.
                    if (!hasOccurred) {
                        character.setHasKey(true);
                        System.out.println("\n");
                        Room.printKey();
                        System.out.printf("You find a golden key on the floor. You pick it up.\n\n");
                        hasOccurred = true;
                    }
                }
                // if character tries to enter the boss room, do this
                if (character.getCurrentX() == dragonBoss.getCurrentX()  && character.getCurrentY() == dragonBoss.getCurrentY() ) {
                    // if character doesn't have key, do this
                    if (character.getHasKey() == false) {
                        Room.printLock();
                        System.out.println("There's a golden lock in the wall. Looks like you have to find a key to open it.");
                        actionEncountered = true;
                        break;
                    }
                    // else (character has key), fight boss
                    else {
                        enemyTrigger(dragonBoss);
                    }
                    break;
                }

                //print room
                Room.printRoom(character, dragonBoss);
                boolean characterMoved = false;
                // loop which allows character movement until something else happens
                while (!characterMoved) {
                    moveChar.availableDirections(character);
                    characterMoved = moveChar.selectMovement(character, dragonBoss);
                }


                boolean pickedUpSomething; // variable to determine if something has been picked up, which will have an effect on which further actions can occur.
                // random chance to pick up a potion in the room. If this happens, set variable to true so that no more events are triggered in this room.
                pickedUpSomething = potion.potionFound(character, potion);

                // if nothing was picked up, run this.
                if (!pickedUpSomething) {
                    // random chance to encounter enemy. If enemy encountered, set actionEncountered == true.
                    actionEncountered = combat.combatChance();
                    // if enemy encountered, continue to generate a random enemy (out of three possible), and fight it.
                    if (actionEncountered) {
                        enemyTrigger(currentEnemy);
                        actionEncountered = false;
                    }
                    // if none of the above happened, simply print a room description.
                    else {
                        System.out.println(Room.getRoomWith(character.getCurrentX(), character.getCurrentY()).getDescriptionOfRoom());
                    }
                }
            }
        }
    }

    public void enemyTrigger (Enemy currentEnemy) {

        // if character is in the boss room, initialise combat with dragonBoss
        if (character.getCurrentX() == dragonBoss.getCurrentX() && character.getCurrentY() == dragonBoss.getCurrentY()) {
            currentEnemy = dragonBoss; // set current enemy to dragonboss
            printDragon();
            System.out.printf("\nYou have encountered a %s with %s HP. Your current HP is %s.\n",
                    currentEnemy.getName(), currentEnemy.getHealth(), character.getHealth());
            inCombatMenu(currentEnemy); // enter the combat menu (fight)


            // if dragonboss dies, do this.
            if (dragonBoss.getHealth() <= 0) {
                printTreasure();

                character.setCoins(character.getCoins() + 100);

                System.out.println("\nCongratulations " + character.getName() + "!" + "\nYou have defeated the dragon and " +
                        "amassed: " + character.getCoins() + " gold coins.");
            }
            // if the character dies, do this.
            else if (character.getHealth() <= 0) {
                printDeath();
                System.out.printf("You have been slain by the %s. \n", dragonBoss.getName());
            }
            characterDeath(); // execute character death regardless of win or death, as this resets the character stats for a new game.

        }

        // the currentEnemy object is passed to the randomizeEnemy method,
        // which will randomly assign the currentEnemy to one of the three preexisting enemies (see enemies class).
        else {
            currentEnemy = currentEnemy.randomizeEnemy(currentEnemy);

            // if statement to print ASCII pictures depending on which enemy was selected.
            if (currentEnemy.getName().equals("Skeleton")) {
                currentEnemy.printSkeleton();
            }
            else if (currentEnemy.getName().equals("Zombie")) {
                currentEnemy.printZombie();
            }
            else if (currentEnemy.getName().equals("Warrior")) {
                currentEnemy.printWarrior();
            }

            // print information about the enemy encountered
            System.out.printf("\nYou have encountered a %s with %s HP. Your current HP is %s.\n",
                    currentEnemy.getName(), currentEnemy.getHealth(), character.getHealth());

            // Enter a new menu which handles the combat with this enemy.
            inCombatMenu(currentEnemy);


        }

    }

    public void inCombatMenu (Enemy currentEnemy) {

        Scanner input = new Scanner(System.in);
        boolean loopMenu = true;
        int menuChoice = 0;
        Combat combat = new Combat();

        // try catch loop to check that the input doesn't cause an error
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

                case 1: { // Attack monster

                    combat.hitEnemy(currentEnemy, character, currentWeapon); // method which substract the enemy health by the attackDamage of the character(player) weapon.
                    if (currentEnemy.getHealth() <= 0) { //if the enemy has no more health, do this.
                        System.out.printf("\nYou have killed the %s!\n", currentEnemy.getName());
                        loopMenu = false;

                        character.getLoot(character, currentEnemy); // give the character gold coins according to how many the enemy is worth (slightly randomized, some enemies give more).
                        character.getHealthPotion(character); // chance for character to gain a healthpotion from combat

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
                    // allows the player to buy either a weapon or a potion.
                    currentWeapon = currentWeapon.buyItem(currentWeapon, character, potion);
                    // method which "equips" selected weapon by equalling the attackDamage and attackRate of the weapon to the character attributes.
                    break;

                }

                case 4: { // Check your inventory
                    // displays the items that the character is carrying.
                    System.out.printf("You have the following weapon: %s. Attack Damage: %s. Attack Speed: %s.\n", currentWeapon.getName(), currentWeapon.getAttackDamage(), currentWeapon.getAttackSpeedWord());
                    System.out.println("You have " + character.getNumberOfHealthPotions() + " health potions");
                    System.out.println("You have " + character.getCoins() + " coins");
                    if (character.getHasKey()) {
                        System.out.println("You have a golden key");
                    }
                    System.out.println();
                    break;

                }

                case 5: { // Run away
                    loopMenu = false;
                    System.out.println("You run away, like a coward.");
                    // take 25 damage for running away.
                    character.setHealth(character.getHealth() - 25);
                    System.out.println("You take 25 damage while running away.");
                    if (character.getHealth() <= 0) {
                        System.out.println("You have died in oblivion. No one will tell tales of your courage.");
                        characterDeath();
                    }
                    break;
                }
                default:
                    System.out.println("Input out of range. Please enter a number from 1 to 5.");

                    break;
            }
        }
    }



    public void characterDeath() {
        // Reset character statistics
        character.setHealth(150);
        character.setNumberOfHealthPotions(3);
        character.setCoins(0);
        currentWeapon = new Weapon ("Basic Sword", 1, 25, 0, true, "normal");
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

    public void printDeath() {
        System.out.println("" +
                "" +
                "               ...\n" +
                "             ;::::;\n" +
                "           ;::::; :;\n" +
                "         ;:::::'   :;\n" +
                "        ;:::::;     ;.\n" +
                "       ,:::::'       ;           OOO\\\n" +
                "       ::::::;       ;          OOOOO\\\n" +
                "       ;:::::;       ;         OOOOOOOO\n" +
                "      ,;::::::;     ;'         / OOOOOOO\n" +
                "    ;:::::::::`. ,,,;.        /  / DOOOOOO\n" +
                "  .';:::::::::::::::::;,     /  /     DOOOO\n" +
                " ,::::::;::::::;;;;::::;,   /  /        DOOO\n" +
                ";`::::::`'::::::;;;::::: ,#/  /          DOOO\n" +
                ":`:::::::`;::::::;;::: ;::#  /            DOOO\n" +
                "::`:::::::`;:::::::: ;::::# /              DOO\n" +
                "`:`:::::::`;:::::: ;::::::#/               DOO\n" +
                " :::`:::::::`;; ;:::::::::##                OO\n" +
                " ::::`:::::::`;::::::::;:::#                OO\n" +
                " `:::::`::::::::::::;'`:;::#                O\n" +
                "  `:::::`::::::::;' /  / `:#\n" +
                "   ::::::`:::::;'  /  /   `#");

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
                "                '-._'-.|| |' `_.-'\n" +
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
                "            `  `                                       \n");
    }
}
