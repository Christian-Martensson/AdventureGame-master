package AdventureGame;

import java.util.ArrayList;
import java.util.Random;

public class Character extends Being {

    private int currentX;
    private int currentY;
    private int numberOfHealthPotions;
    private int coins;
    private boolean hasKey;
    private boolean testingRoute;
    //private static ArrayList<Character> inventory = new ArrayList<>();

    //class constructor
    //ArrayList inventory
    public Character(String name, int HP, int AD, double AR, double DM, int numberOfHealthPotions, int currentX, int currentY, int coins, boolean hasKey, boolean testingRoute) {
        super(name, HP, AD, AR, DM);
        setNumberOfHealthPotions((numberOfHealthPotions));
        setCurrentX(currentX);
        setCurrentY(currentY);
        setCoins(coins);
        setHasKey(getHasKey());
        setTestingRoute(testingRoute);
        //addToInventory(inventory);
    }


    public void printCharacter(Character character) {
        //System.out.printf("[  %s ]", character.getName());
        System.out.printf("[  YOU  ]");
    }

    public void getLoot(Character character, Enemy currentEnemy) {
        Random rand = new Random();
        int loot = currentEnemy.getGoldCoinLoot();
        loot = rand.nextInt(15) + loot;
        character.setCoins(character.getCoins() + loot);
        System.out.println("You have received " + loot + " coins");
    }
    public void getHealthPotion(Character character) {
        Random rand = new Random();
        int potionChance = rand.nextInt(100) + 1;
        if (potionChance > 50) {
            character.setNumberOfHealthPotions(character.getNumberOfHealthPotions() + 1);
            System.out.printf("You loot a health potion from the enemy. You now have %s health potions.\n", character.getNumberOfHealthPotions());
        }

    }

    public int getNumberOfHealthPotions() {
        return numberOfHealthPotions;
    }

    public void setNumberOfHealthPotions(int numberOfHealthPotions) {
        this.numberOfHealthPotions = numberOfHealthPotions;
    }

    public int getCurrentX() {
        return currentX;
    }

    public void setCurrentX(int currentX) {
        this.currentX = currentX;
    }

    public int getCurrentY() {
        return currentY;
    }

    public void setCurrentY(int currentY) {
        this.currentY = currentY;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public boolean getHasKey() {
        return hasKey;
    }

    public void setHasKey(boolean hasKey) {
        this.hasKey = hasKey;
    }

    public boolean isTestingRoute() {
        return testingRoute;
    }

    public void setTestingRoute(boolean testingRoute) {
        this.testingRoute = testingRoute;
    }

    //public ArrayList<Character> addToInventory()

    //public static ArrayList<Character> getInventory() {
        //return inventory;
    //}

}
