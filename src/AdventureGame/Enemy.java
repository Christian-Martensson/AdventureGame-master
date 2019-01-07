package AdventureGame;

import java.util.Random;

public class Enemy extends Being {
    private int goldCoinLoot;

    public Enemy(String name, int HP, int AD, double AR, double DM, int goldCoinLoot) {
        super(name, HP, AD, AR, DM);
        setGoldCoinLoot(goldCoinLoot);
    }

    public Enemy randomizeEnemy(Enemy currentEnemy) {

        Random rand = new Random();
        int monsterNumber = rand.nextInt(3) + 1;
        switch (monsterNumber) {
            case 1: { //A zombie is spawned)
                Enemy zombie = new Enemy("Zombie", 75, 30, 0.5, 1.2, 25);
                currentEnemy = zombie;
                break;
            }
            case 2: { //A skeleton is spawned)
                Enemy skeleton = new Enemy("Skeleton", 100, 20 , 2, 1, 35);
                currentEnemy = skeleton;
                break;
            }
            case 3: { //A warrior is spawned)
                Enemy warrior = new Enemy("Warrior", 150, 40, 1, 0.8, 60);
                currentEnemy = warrior;
                break;
            }

        }
        return currentEnemy;
        // use an array of enemies?
    }


    public int getGoldCoinLoot() {
        return goldCoinLoot;
    }

    public void setGoldCoinLoot(int goldCoinLoot) {
        this.goldCoinLoot = goldCoinLoot;
    }
}

