package AdventureGame;

import java.util.Random;

public class Boss extends Enemy {

    private int currentX;
    private int currentY;


    public Boss(String name, int HP, int AD, double AR, double DM, int x, int y, int goldCoinLoot) {
        super(name, HP, AD, AR, DM, goldCoinLoot);
        setCurrentX(x);
        setCurrentY(y);
    }

    public static Boss spawnBoss() {
        Random rand = new Random();
        int yCoordinate = rand.nextInt(4) + 1;
        Boss dragonBoss = new Boss("Dragon", 250, 50, 1,2, 6, yCoordinate, 100);

        Room.getRoomWith(6, yCoordinate).setRoom(true);

        return dragonBoss;
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
}
