package AdventureGame;
// https://coderanch.com/t/665005/java/Creating-rooms-Text-Based-Adventure
// https://www.reddit.com/r/java/comments/15wx4g/text_based_adventure_game_help/

import java.util.ArrayList;
import java.util.Random;

public class Room {

    private static ArrayList<Room> rooms = new ArrayList<>();
    private String name;
    private String descriptionOfRoom;
    private int x;
    private int y;
    private boolean emptyRoom;
    private boolean ContainsKey;


    public Room(String name, String desc, int x, int y, boolean emptyRoom, boolean containsKey) {
        this.setName(name);
        setDescriptionOfRoom(desc);
        setX(x);
        setY(y);
        setRoom(emptyRoom);
        setContainsKey(containsKey);
    }


    public static void createRooms(Character character) {
        Random rand = new Random();
        getRooms().clear();
        character.setCurrentX(1);
        character.setCurrentY(2);


        for (int y = 4; y >= 1; y--) {
            for (int x = 1; x <= 6; x++) {
                boolean emptyRoom = false;
                int randomNumber = rand.nextInt(100) + 1;
                if (randomNumber < 50) {
                    emptyRoom = false;
                }
                if (randomNumber >= 50) {
                    emptyRoom = true;
                }
                Room name = new Room(x + "x" + y,"A room", x , y, emptyRoom, false);
                getRooms().add(name);
            }
        }
        // set starting room to not empty
        getRoomWith(1,2).setRoom(true);
        removeIsolatedRooms();
        getRoomWith(4,1).setRoom(true);
        getRoomWith(4,2).setRoom(true);
        getRoomWith(4,3).setRoom(true);
        getRoomWith(4,4).setRoom(true);

    }

    public static void removeIsolatedRooms() { // doesn't work perfectly.
        int a;
        boolean b = true;

        for (a = 0; a < getRooms().size(); a++){
            if (isEmpty(getRooms().get(a).getX() + 1, getRooms().get(a).getY())
                    && isEmpty(getRooms().get(a).getX() - 1 , getRooms().get(a).getY())
                    && isEmpty(getRooms().get(a).getX(), getRooms().get(a).getY() + 1)
                    && isEmpty(getRooms().get(a).getX(), getRooms().get(a).getY() - 1)) {

            getRoomWith(getRooms().get(a).getX(), getRooms().get(a).getY()).setRoom(true);

            }
        }
    }

    public static boolean isEmpty(int x, int y) {
        int a;
        boolean b = true;

        for (a = 0; a < getRooms().size(); a++){
            if (x == getRooms().get(a).getX() && y == getRooms().get(a).getY()) {
                b = getRooms().get(a).isEmptyRoom();
            }
        }
        return b;
    }

    public static boolean checkValidRoute(Character character, Movement moveChar, Boss dragonBoss) {

        boolean goalReached = false;
        int counter = 0;
        while(!goalReached) {
            counter++;
            moveChar.moveRandom(character, moveChar, dragonBoss);
            if (character.getCurrentX() == 6 && character.getCurrentY() == dragonBoss.getCurrentY()) {
                goalReached = true;
                break;
            }

            if (counter > 400) {
                goalReached = false;
                break;
            }
        }
        character.setCurrentX(1);
        character.setCurrentY(2);
        return goalReached;
    }

    public static Room getRoomWith(int x, int y) {
        int a;
        Room room = null;

        for (a = 0; a < getRooms().size(); a++){
            if (x == getRooms().get(a).getX() && y == getRooms().get(a).getY()) {
                room = getRooms().get(a);
            }
        }
        return room;
    }

    public static void printRoom(Character character, Boss dragonBoss) {
        System.out.println("------------------------------------------------------");
        for (int index = 0; index < getRooms().size(); index++) {
            if ((rooms.get(index).getX() == character.getCurrentX()) && rooms.get(index).getY() == character.getCurrentY()) {
                character.printCharacter(character);
            }
            else if ((rooms.get(index).getX() == 6) && rooms.get(index).getY() == dragonBoss.getCurrentY()) { // The boss room
                System.out.printf("[ BOSS  ]");
            }
            else {
                if (getRooms().get(index).isEmptyRoom()) {
                    System.out.printf("[   x   ]", getRooms().get(index).getName(), booleanToSymbol(getRooms().get(index).isEmptyRoom()));
                }
                else {
                    System.out.printf("         ");
                }
            }
            if (index == 5 || index == 11 || index == 17) {
                System.out.println();
            }
        }
        System.out.println("\n------------------------------------------------------");
        System.out.println();
    }



    public static String booleanToSymbol(boolean isEmptyRoom) {
        String symbol = null;

        if (isEmptyRoom == true) {
            symbol = "+";

        }
        if (isEmptyRoom == false) {
            symbol = "-";
        }
        return symbol;

    }

    public static void spawnKey() {
        Random rand = new Random();
        boolean continueLoop = true;
        int xCoordinate = 0;
        int yCoordinate = 0;
        while (continueLoop) {
            xCoordinate = rand.nextInt(3) + 2;
            yCoordinate = rand.nextInt(4) + 1;
            if (!(getRoomWith(xCoordinate, yCoordinate).isEmptyRoom())) {
                continueLoop = true;
            }

            else {
                continueLoop = false;
            }
        }
        getRoomWith(4, yCoordinate).setContainsKey(true);



    }
    public static Room withKey() {
        int counter;
        boolean containsKey = true;
        Room room = null;

        for (counter = 0; counter < getRooms().size(); counter++){
            if (getRooms().get(counter).isContainsKey() == containsKey) {
                room = getRooms().get(counter);
            }

        }
        System.out.println(room.getName());
        return room;
    }

    public static ArrayList<Room> getRooms() {
        return rooms;
    }

    public static void setRooms(ArrayList<Room> rooms) {
        Room.rooms = rooms;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescriptionOfRoom() {
        return descriptionOfRoom;
    }

    public void setDescriptionOfRoom(String descriptionOfRoom) {
        this.descriptionOfRoom = descriptionOfRoom;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isEmptyRoom() {
        return emptyRoom;
    }

    public void setRoom(boolean emptyRoom) {
        this.emptyRoom = emptyRoom;
    }

    public boolean isContainsKey() {
        return ContainsKey;
    }

    public void setContainsKey(boolean containsKey) {
        ContainsKey = containsKey;
    }
}
