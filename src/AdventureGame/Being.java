package AdventureGame;

public class Being {

    //Extends to character and enemy(including boss)
    private String name;
    private int health;
    private int attackDamage;
    private double attackRate;
    private double damageResistance;

    //constructor
    public Being(String name, int HP, int AD, double AR, double DM) {
        setName(name);
        setHealth(HP);
        setAttackDamage(AD);
        setAttackRate(AR);
        setDamageResistance(DM);
    }

    //methods
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public void setAttackDamage(int attackDamage) {
        this.attackDamage = attackDamage;
    }

    public double getAttackRate() {
        return attackRate;
    }

    public void setAttackRate(double attackRate) {
        this.attackRate = attackRate;
    }

    public double getDamageResistance() {
        return damageResistance;
    }

    public void setDamageResistance(double damageResistance) {
        this.damageResistance = damageResistance;
    }

}