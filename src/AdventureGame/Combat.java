package AdventureGame;
import java.util.Random;

public class Combat {

    public void hitEnemy(Enemy currentEnemy, Character character, Weapon currentWeapon) {
        Random rand = new Random();
        int randomAttack = 0;
        System.out.println("YOUR TURN:");

        if (currentWeapon.getAttackRate() == 1 && !(currentEnemy.getHealth() <= 0)) {
            randomAttack = rand.nextInt(10) + character.getAttackDamage();
            currentEnemy.setHealth(currentEnemy.getHealth() - randomAttack);
            System.out.printf("You have dealt %s damage.\n", randomAttack);
        }
        if (currentWeapon.getAttackRate() == 2 && !(currentEnemy.getHealth() <= 0)) {
            randomAttack = rand.nextInt(10) + character.getAttackDamage();
            currentEnemy.setHealth(currentEnemy.getHealth() - randomAttack);
            System.out.printf("You have dealt %s damage.\n", randomAttack);
            randomAttack = rand.nextInt(10) + character.getAttackDamage();
            currentEnemy.setHealth(currentEnemy.getHealth() - randomAttack);
            System.out.printf("You have dealt %s damage.\n", randomAttack);


        }
        else if (currentWeapon.getAttackRate() == 3 && !(currentEnemy.getHealth() <= 0)) {
            randomAttack = rand.nextInt(10) + character.getAttackDamage();
            currentEnemy.setHealth(currentEnemy.getHealth() - randomAttack);
            System.out.printf("You have dealt %s damage.\n", randomAttack);
            randomAttack = rand.nextInt(10) + character.getAttackDamage();
            currentEnemy.setHealth(currentEnemy.getHealth() - randomAttack);
            System.out.printf("You have dealt %s damage.\n", randomAttack);
            randomAttack = rand.nextInt(10) + character.getAttackDamage();
            currentEnemy.setHealth(currentEnemy.getHealth() - randomAttack);
            System.out.printf("You have dealt %s damage.\n", randomAttack);


        }

        if (currentEnemy.getHealth() <= 0) {
            System.out.printf("The %s now has 0 health points left.\n",
                    currentEnemy.getName());
        }
        else {
            System.out.printf("The enemy %s now has %s health points left.\n",
                    currentEnemy.getName(), currentEnemy.getHealth());
        }
    }

    public void hitCharacter (Enemy currentEnemy, Character character) {
        Random rand = new Random();
        System.out.println("\nENEMY TURN: ");

        int randomAttack = rand.nextInt(10) + currentEnemy.getAttackDamage();
        character.setHealth(character.getHealth() - randomAttack);
        if (character.getHealth() <= 0) {
            System.out.printf("%s has has hit you for %s damage!\nYou now have 0 health points left.\n", currentEnemy.getName(),
                    randomAttack);
        }
        else {
            System.out.printf("%s has has hit you for %s damage!\nYou now have %s health points left.\n\n", currentEnemy.getName(),
                    randomAttack, character.getHealth());
        }
    }


    public boolean combatChance() {
        Random rand = new Random();
        int randomNumber = rand.nextInt(100);
        boolean enemyEncountered = false;

        if (randomNumber > 50) {
            enemyEncountered = true;
        }
        return enemyEncountered;
    }
}
