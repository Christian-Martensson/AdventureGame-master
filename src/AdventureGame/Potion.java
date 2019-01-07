package AdventureGame;

import java.util.Random;

public class Potion extends Item {
    private String name; //the name of the item.
    private int dropChance;
    private int healingAmount = 30;
    private String unit;

    public Potion(String name, int dropChance, int healingAmount, String unit, int price) {
        setName(name);
        setDropChance(dropChance);
        setHealingAmount(healingAmount);
        setUnit(unit);
        setPrice(price);
    }

    public Character usePotion(Character character) {
        if (character.getNumberOfHealthPotions() <= 0) {
            System.out.printf("You don't have any %ss.\n", getName());
            return character;
        }
        else {
            character.setNumberOfHealthPotions(character.getNumberOfHealthPotions() - 1);
            character.setHealth(character.getHealth() + getHealingAmount());
            System.out.printf("You used [%s] which gives you bonus %s %s. \nCurrent %s is %s. You have %s remaining %ss.\n",
                    getName(), getHealingAmount(), getUnit(), getUnit(), character.getHealth(), character.getNumberOfHealthPotions(), getName());
            return character;
        }
    }

    public boolean potionFound(Character character, Potion potion) {
        Random rand = new Random();
        boolean foundSomething = false;
        int randomNumber = rand.nextInt(100) + 1;
        if (randomNumber <= potion.getDropChance() ) {
            character.setNumberOfHealthPotions(character.getNumberOfHealthPotions() + 1);
            System.out.printf("You found a health potion on the floor! You now have %s health potions.\n", character.getNumberOfHealthPotions());
            foundSomething = true;
        }
        return foundSomething;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDropChance() {
        return dropChance;
    }

    public void setDropChance(int dropChance) {
        this.dropChance = dropChance;
    }

    public int getHealingAmount() {
        return healingAmount;
    }

    public void setHealingAmount(int healingAmount) {
        this.healingAmount = healingAmount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
