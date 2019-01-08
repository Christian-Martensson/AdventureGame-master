package AdventureGame;

public class Item {

    private String name; //the name of the item.
    private int price;

    public Item (String name, int price) {
        setName(name);

        setPrice(price);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
