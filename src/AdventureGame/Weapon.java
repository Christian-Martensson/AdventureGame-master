package AdventureGame;
import java.util.Scanner;

public class Weapon extends Item {

    private double attackRate;
    private int attackDamage;
    private boolean equipped;


    public Weapon(String name, double attackRate, int attackDamage, int price, boolean equipped) {
        setName(name);
        setAttackRate(attackRate);
        setAttackDamage(attackDamage);
        setPrice(price);
        setEquipped(equipped);
    }

    public double getAttackRate() {
        return attackRate;
    }

    public void setAttackRate(double attackRate) {
        this.attackRate = attackRate;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public void setAttackDamage(int attackDamage) {
        this.attackDamage = attackDamage;
    }


    public Weapon buyItem(Weapon currentWeapon, Character character, Potion potion) {
        Scanner input = new Scanner(System.in);

        boolean switchLoop = true;

        int menuChoice = 0;

        Weapon maceOfDoom = new Weapon("The Mace of DOOM", 1, 60, 100, false);
        Weapon katana = new Weapon("Katana", 3, 15, 50, false);
        Weapon swordOfArthur = new Weapon("The Sword of Arthur",2, 30,60,false);

        while (switchLoop) {
            boolean tryCatchLoop = true;
            while(tryCatchLoop) {
                try {
                    if (character.getCoins() == 1)
                        System.out.println("You have 1 coin.");
                    else {
                        System.out.printf("You have %s coins.\n", character.getCoins());
                    }
                    System.out.printf("You may buy the following weapons: \n1. %s: %s \n2. %s: %s \n3. %s: %s\n4. %s: %s\n5. Exit menu\n",
                            maceOfDoom.getName(), maceOfDoom.getPrice(), katana.getName(), katana.getPrice(),
                            swordOfArthur.getName(),swordOfArthur.getPrice(), potion.getName(), potion.getPrice());
                    menuChoice = input.nextInt();
                    tryCatchLoop = false;
                }catch (Exception e) {
                    System.out.println("Invalid input. Please enter a number from 1 to 5.");
                    input.next();
                    tryCatchLoop = true;
                }
            }

            switch (menuChoice) {
                case 1: {
                    if (character.getCoins() < maceOfDoom.getPrice()) {
                        System.out.println("Insufficient funds");
                        switchLoop = false;
                        break;
                    }
                    else {
                        character.setCoins(character.getCoins() - maceOfDoom.getPrice());
                        currentWeapon.setEquipped(false);
                        currentWeapon = maceOfDoom;
                        currentWeapon.equipWeapon(currentWeapon, character);
                        System.out.printf("You bought the %s for %s gold.\n", maceOfDoom.getName(), maceOfDoom.getPrice());
                        switchLoop = false;
                    }
                    break;
                }
                case 2: {
                    if (character.getCoins() < katana.getPrice()) {
                        System.out.println("Insufficient funds");
                        switchLoop = false;
                        break;
                    }
                    else {
                        character.setCoins(character.getCoins() - katana.getPrice());
                        currentWeapon.setEquipped(false);
                        currentWeapon = katana;
                        currentWeapon.equipWeapon(currentWeapon, character);
                        System.out.printf("You bought the %s for %s gold.\n", katana.getName(), katana.getPrice());
                        switchLoop = false;
                    }

                    break;

                }
                case 3: {
                    if (character.getCoins() < swordOfArthur.getPrice()) {
                        System.out.println("Insufficient funds");
                        switchLoop = false;
                        break;
                    }
                    else {
                        character.setCoins(character.getCoins() - swordOfArthur.getPrice());
                        currentWeapon.setEquipped(false);
                        currentWeapon = swordOfArthur;
                        currentWeapon.equipWeapon(currentWeapon, character);
                        System.out.printf("You bought the %s for %s gold.\n", swordOfArthur.getName(), swordOfArthur.getPrice());
                        switchLoop = false;
                    }
                    break;
                }
                case 4: {
                    if (character.getCoins() < 20) {
                        System.out.println("Insufficient funds");
                        switchLoop = false;
                        break;
                    }
                    else {
                        character.setCoins(character.getCoins() - 20);
                        character.setNumberOfHealthPotions(character.getNumberOfHealthPotions() + 1);
                        System.out.printf("You bought a %s for %s gold. You now have %s %ss.\n", potion.getName(), potion.getPrice(), character.getNumberOfHealthPotions(), potion.getName());

                        switchLoop = false;
                    }
                    break;
                }
                case 5: { // Exit menu
                    switchLoop = false;
                    break;
                }
                default:
                    System.out.println("Input out of range. Please enter a number from 1 to 3.");
                    break;
            }
        }
        return currentWeapon;
    }

    public void equipWeapon(Weapon currentWeapon, Character character) {
        character.setAttackRate(currentWeapon.getAttackRate()); //set character attackRate to weapon attackRate
        character.setAttackDamage(currentWeapon.getAttackDamage()); //set character attackDamage to weapon attackDamaage
        currentWeapon.setEquipped(true);
        System.out.printf("You have equipped %s, you now have %s AD and %s AR.\n", currentWeapon.getName(), character.getAttackDamage(), character.getAttackRate());

    }

    public boolean isEquipped() {
        return equipped;
    }

    public void setEquipped(boolean equipped) {
        this.equipped = equipped;
    }
}
