package AdventureGame;
import java.util.Scanner;

public class Weapon extends Item {

    private double attackSpeed;
    private String attackSpeedWord;
    private int attackDamage;
    private boolean equipped;


    public Weapon(String name, double attackSpeed, int attackDamage, int price, boolean equipped, String attackSpeedWord) {
        super(name, price);
        setAttackSpeed(attackSpeed);
        setAttackDamage(attackDamage);
        setEquipped(equipped);
        setAttackSpeedWord(attackSpeedWord);
    }


    public double getAttackSpeed() {
        return attackSpeed;
    }

    public void setAttackSpeed(double attackSpeed) {
        this.attackSpeed = attackSpeed;
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

        Weapon maceOfDoom = new Weapon("The Mace of DOOM", 1, 80, 100, false, "normal");
        Weapon katana = new Weapon("The Katana", 3, 15, 50, false, "triple");
        Weapon swordOfArthur = new Weapon("The Sword of Arthur",2, 30,70,false, "double");

        while (switchLoop) {
            boolean tryCatchLoop = true;
            while(tryCatchLoop) {
                try {
                    if (character.getCoins() == 1)
                        System.out.println("You have 1 coin.");
                    else {
                        System.out.printf("You have %s coins.\n", character.getCoins());
                    }
                    System.out.printf("You may buy the following items: \n1. %s: %s Gold (%s Attack Damage, %s Attack Speed)\n2. %s: %s Gold (%s Attack Damage, %s Attack Speed)\n3. %s: %s Gold (%s Attack Damage, %s Attack Speed)\n4. %s: %s Gold\n5. Exit menu\n",
                            maceOfDoom.getName(), maceOfDoom.getPrice(), maceOfDoom.getAttackDamage(), maceOfDoom.getAttackSpeedWord(),
                            katana.getName(), katana.getPrice(), katana.getAttackDamage(), katana.getAttackSpeedWord(),
                            swordOfArthur.getName(),swordOfArthur.getPrice(), swordOfArthur.getAttackDamage(), swordOfArthur.getAttackSpeedWord(),
                            potion.getName(), potion.getPrice());
                    menuChoice = input.nextInt();
                    tryCatchLoop = false;
                }catch (Exception e) {
                    System.out.println("Invalid input. Please enter a number from 1 to 5.");
                    input.next();
                    tryCatchLoop = true;
                }
            }

            switch (menuChoice) { // Mace of doom
                case 1: {
                    if (character.getCoins() < maceOfDoom.getPrice()) {
                        System.out.println("Insufficient funds");
                        switchLoop = false;
                        break;
                    }
                    else if (currentWeapon.getName().equals("The Mace of DOOM")) {
                        System.out.println("You already have this weapon");
                        switchLoop = false;
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
                case 2: { // Katana
                    if (character.getCoins() < katana.getPrice()) {
                        System.out.println("Insufficient funds");
                        switchLoop = false;
                        break;
                    }
                    else if (currentWeapon.getName().equals("The Katana")) {
                        System.out.println("You already have this weapon");
                        switchLoop = false;
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
                case 3: { // Sword of Arthur
                    if (character.getCoins() < swordOfArthur.getPrice()) {
                        System.out.println("Insufficient funds");
                        switchLoop = false;
                        break;
                    }
                    else if (currentWeapon.getName().equals("Sword of Arthur")) {
                        System.out.println("You already have this weapon");
                        switchLoop = false;
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
        character.setAttackRate(currentWeapon.getAttackSpeed()); //set character attackSpeed to weapon attackSpeed
        character.setAttackDamage(currentWeapon.getAttackDamage()); //set character attackDamage to weapon attackDamaage
        currentWeapon.setEquipped(true);
        System.out.printf("You have equipped %s, you now have %s Attack Damage and %s Attack Speed.\n", currentWeapon.getName(), character.getAttackDamage(), character.getAttackRate());

    }

    public boolean isEquipped() {
        return equipped;
    }

    public void setEquipped(boolean equipped) {
        this.equipped = equipped;
    }

    public String getAttackSpeedWord() {
        return attackSpeedWord;
    }

    public void setAttackSpeedWord(String attackSpeedWord) {
        this.attackSpeedWord = attackSpeedWord;
    }
}
