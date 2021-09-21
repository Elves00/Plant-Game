/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plant_game;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Observable;
import java.util.Scanner;

/**
 *
 * @author breco
 */
public class PlantGameModel extends Observable {

    private Player player;
    private PlantSelection shop;
    private UnlockShop unlocks;
    private GameState files;
    private final String endline;
    private final Scanner scan;
    private OrderedList<Score> highscores;
    private Boolean input;

    /**
     * Sets up the initial variables of a plant game.
     */
    public PlantGameModel() {

        //Establishes file manage.
        this.files = new GameState();
        //Recieved gui input
        this.input = false;
        //Endline save.
        this.endline = "---------------------------------------------------------";
        //Sacnner to be used,
        this.scan = new Scanner(System.in);
    }

    /**
     * Provides the user with a selection of options to start the game and
     * collects there selection before returning.
     *
     * 1 = new game 2 = load previous game 3 = load a save file
     *
     * @return int selection representing what start option the user has
     * selected
     */
    private int gameOptions() {

        //Stores user input
        int anwser = 0;
        //Flag for main while loop
        boolean flag = true;

        //Asks user what game mode they would like to play and retrives int anwser
        while (flag) {
            try {
                System.out.println("1: New game");
                System.out.println("2: Previous Game");
                System.out.println("3: Load save file");
                System.out.print("Please enter your selection:");
                anwser = scan.nextInt();
                if (anwser >= 1 && anwser <= 3) {
                    flag = false;
                } else {
                    System.out.println("Please enter a selection between 1 and 3");
                }

            } catch (InputMismatchException ime) {
                System.out.println("Please enter a selection between 1 and 3");
                scan.next();
            }
        }
        return anwser;
    }

    protected void newGame(String name) throws MoneyException, FileNotFoundException {
        //Create player object with name
        setPlayer(getFiles().newPlayer(name));

        //set change
        setChanged();
        //pases the selcted save option to the plant game panel
        notifyObservers("Options Not Visible");

        System.out.println("Player created with name:" + this.player.getName());
        //Helpful prompt
        System.out.println("");
        System.out.println(endline);
        System.out.println("Welcome " + name + " if you have forgotten how to play the game selecting option 6 will bring you to the information menu");
        System.out.println("There you can find details about how to play");
        System.out.println(endline);
        System.out.println("");

    }

    /**
     * Creates a new game using a user generated name to establish player and
     * new files.
     *
     * Creates a new player using the file managers newPlayer option and
     * provides a helpful start up message for a new player
     *
     * @throws MoneyException
     * @throws FileNotFoundException
     */
    protected void newGame() throws MoneyException, FileNotFoundException {

        //set change
        setChanged();
        //pases the selcted save option to the plant game panel
        notifyObservers("Options c");
        //set change
        setChanged();
        //pases the selcted save option to the plant game panel
        notifyObservers("Shop Start");

    }

    /**
     * Loads a game from the current game file. If no game is available start a
     * new game.
     *
     * @throws IOException
     * @throws FileNotFoundException
     * @throws FileLoadErrorException
     */
    protected void previousGame() throws IOException, FileNotFoundException, FileLoadErrorException, InstantiationException, IllegalAccessException {

        try {

            //loads the last game.
            getFiles().loadCurrentGame();
            //Loads variables from file.
            setPlayer(getFiles().loadPlayer());
            setShop(getFiles().readShop());
            setUnlocks(getFiles().readUnlock());
            //set change

            setChanged();
            //pases the selcted save option to the plant game panel
            notifyObservers("Shop Start");
        } catch (IndexOutOfBoundsException on) {
            //There was no game stored in current game file so create a new game.
            System.out.println("There is no current game file starting new game...");
            newGame();
        }
    }

    /**
     * Notify the observe to display the options for loading game
     */
    protected void loadGameView() {
        //set change
        setChanged();
        //pases the selcted save option to the plant game panel
        notifyObservers("Options b");

    }

    protected void loadGame(int selection) throws IOException {

        this.getFiles().loadGame(selection);
        System.out.println("Save number" + selection + " loaded");
        //set change
        setChanged();
        //pases the selcted save option to the plant game panel
        notifyObservers("Options Not Visible");
        //set change
        setChanged();
        //pases the selcted save option to the plant game panel
        notifyObservers("Shop Start");

    }

    /**
     * Provides the user with options to load previously saved games.
     *
     * @throws IOException
     * @throws FileNotFoundException
     * @throws FileLoadErrorException
     */
    protected void loadGame() throws IOException, FileNotFoundException, FileLoadErrorException, MoneyException, InstantiationException, IllegalAccessException {

        //Stores user input
        int anwser = 0;
        //Finds current save files
        String[] array = getFiles().saveDisplay();

        //Promts a user to load one of the 5 save files and takes there input.
        System.out.println("Please select a game you would like to load:");
        boolean flag = true;
        while (flag) {
            try {
                //Prints options
                for (String i : array) {
                    System.out.println(i);
                }

                anwser = scan.nextInt();

                //Checks input is inbounds before leaving
                if (anwser >= 1 && anwser < 6) {
                    flag = false;
                } else {
                    System.out.println("Please enter a number between 1 and 5");
                }
                //Catch bad input
            } catch (InputMismatchException ime) {
                System.out.println("Please enter a number between 1 and 5");
            }
        }

        //If the file does not contain information a new game is started.
        if (array[anwser - 1].contains("null")) {
            System.out.println("File does not exist starting new game.");
            newGame();
        } else {

            //Loads the file 
            getFiles().loadGame(anwser);
            //Loads objects.
            setPlayer(getFiles().loadPlayer());
            setUnlocks(getFiles().readUnlock());
            setShop(getFiles().readShop());
        }

    }

    /**
     * Prompts a player to select a row keeping them there until an acceptable
     * input has been provided.
     *
     * @return int location representing the selected x location
     */
    private int xSelect() {

        int location = 0;
        while (location < 1 || location > 3) {
            try {
                System.out.print("Select row:");
                location = scan.nextInt();

            } catch (InputMismatchException ime) {
                System.out.println("Please select a row between 1 and 3");
                scan.next();
            }
        }

        return location;
    }

    /**
     * Prompts a player to select a column keeping them there until an
     * acceptable input has been provided.
     *
     * @return int location representing the selected y location
     */
    private int ySelect() {
        int location = 0;
        while (location < 1 || location > 3) {
            try {
                System.out.print("Select collumn:");
                location = scan.nextInt();

            } catch (InputMismatchException ime) {
                System.out.println("Please select a collumn between 1 and 3");
                scan.next();
            }
        }

        return location;
    }

    public void plantAPlantView() {
        //set change
        setChanged();
        //pases the selcted save option to the plant game panel
        notifyObservers("Shop Start");
    }

    public void plantAPlant(int selection, int x, int y) throws InstantiationException, IllegalAccessException {

        getPlayer().newPlant(getShop().getPlant(selection - 1), x - 1, y - 1);
//set change
        setChanged();
        //pases the selcted save option to the plant game panel
        notifyObservers("Plant");
    }

    /**
     * Gives players an option to buy and plant a plant anywhere within the
     * field.
     *
     * Gets players plant selection. Gets players plant location choice. Plants
     * plant in field at location.
     *
     * @throws MoneyException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    private void plantAPlant() throws MoneyException, InstantiationException, IllegalAccessException {

        //Variables to hold user input
        int select = -1;
        int x = 0;
        int y = 0;
        System.out.println("");

        //Displays the players shop and stores user selection.
        while (select < 1 || select > getShop().size()) {
            try {

                System.out.println("What would you like to plant?");
                System.out.println(getShop());
                select = scan.nextInt();
            } catch (InputMismatchException ime) {
                System.out.println("Please select a plant between 1 and " + getShop().size());
                System.out.println("");
                scan.next();
            }
        }
        //If a player can purchase the plant .
        if (getShop().purchasePlant(getPlayer(), select - 1)) {

            System.out.println("Where would you like to plant your " + getShop().getPlant(select - 1));
            //Ensures selection is in bounds.
            while (x < 1 || x > 3) {
                x = xSelect();
            }
            while (y < 1 || y > 3) {
                y = ySelect();
            }

            //If the requested spot contains a plant check if the player would like to replace the plant
            if (getPlayer().getField().getPlant(x - 1, y - 1) instanceof Plant && !(player.getField().getPlant(x - 1, y - 1) instanceof Dirt)) {
                System.out.print("Replacing " + getPlayer().getField().getPlant(x - 1, y - 1) + " with " + getShop().getPlant(select - 1) + " ");
                if (areYouSure()) {
                    getPlayer().newPlant(getShop().getPlant(select - 1), x - 1, y - 1);
                }
            } else {
                getPlayer().newPlant(getShop().getPlant(select - 1), x - 1, y - 1);
            }

        }

    }

    public void unlockView() {

        //set change
        setChanged();
        //pases the selcted save option to the plant game panel
        notifyObservers("Initial Unlock");
        //set change
        setChanged();
        //Notifys that a plant has been unlocked
        notifyObservers("Unlock");
    }

    public void unlock(int i) {
        getUnlocks().price(getPlayer(), getShop(), i);
        //set change
        setChanged();
        //Notifys that a plant has been unlocked
        notifyObservers("Unlock");

    }

    /**
     * Presents players with the option to purchase a variety of unlocks which
     * make plants available in the purchase a plant section.
     */
    private void unlock() {
        System.out.println("Welcome to the unlock shop");
        int select = 0;
        boolean input = true;
        while (input) {
            try {
                //Displays the unlocks avaliable to the palyer
                System.out.println("What would you like to unlock?");
                System.out.println(getUnlocks().view());
                //User selection
                select = scan.nextInt();

                //If selections is within bounds prompts them to confirm there choice
                if ((select > 0 && select <= getUnlocks().size())) {

                    if (areYouSure()) {
                        getUnlocks().price(getPlayer(), getShop(), select);
                    }
                    input = false;
                }

                //Catch input errors.
            } catch (InputMismatchException | ArrayIndexOutOfBoundsException ime) {
                System.out.println("Please select a plant between 1 and " + getUnlocks().size());
                System.out.println("");
                scan.next();
            }
        }
    }

    public void saveView() {
        //set change
        setChanged();
        //pases the selcted save option to the plant game panel
        notifyObservers("Initial Save");
    }

    public void save(int selection) throws IOException {
        //Saves state of current game to the current game slot.
        getFiles().saveCurrentGame(getShop(), getUnlocks(), getPlayer());
        //Saves game to selected location.
        getFiles().saveGame(getShop(), getUnlocks(), getPlayer(), selection);
        //success message.
        System.out.println("Save succefull");
        //set change
        setChanged();
        //pases the selcted save option to the plant game panel
        notifyObservers("Initial Save");
    }

    /**
     * Saves the current game to a user selected save slot.
     *
     * There are 5 slots available to the player to chose. When a player selects
     * one the games current details will be stored in the save file in the
     * corresponding slot.
     *
     * @throws IOException
     */
    private void save() throws IOException {

        System.out.println("Saving...");
        //Saves state of current game to the current game slot.
        getFiles().saveCurrentGame(getShop(), getUnlocks(), getPlayer());
        //Stores user input
        int saveslot = 0;
        //String to store save infomation
        String[] saveFiles = new String[5];

        //Checks and repeats until save slot is entered by user correctly within bounds of 1-5
        while (saveslot < 1 || saveslot > 5) {
            try {
                //String of slave slots
                saveFiles = getFiles().saveDisplay();

                //Display save slots
                for (String i : saveFiles) {
                    System.out.println(i);
                }
                saveslot = scan.nextInt();

                //Catch input mistmatch errors and flush scanner.
            } catch (InputMismatchException ime) {
                System.out.println("Please enter a number between 1 and 5");
                scan.next();
            }
        }

        //If the save file is null auto override.
        if (saveFiles[saveslot - 1].contains("null")) {
            //Saves game to selected location.
            getFiles().saveGame(getShop(), getUnlocks(), getPlayer(), saveslot);
            //success message.
            System.out.println("Save succefull");
        } else {
            System.out.print("You are about to override a save ");
            if (areYouSure()) {
                //Saves game to selected location.
                getFiles().saveGame(getShop(), getUnlocks(), getPlayer(), saveslot);
                //success message.
                System.out.println("Save succefull");
            } else {
                System.out.println("Save canceled");
            }

        }

    }

    /**
     * Asks users if they are sure and returns a Boolean value representing
     * yes/no
     *
     * @return True or False based on user input
     */
    private boolean areYouSure() {

        int input = 0;
        while (input < 1 || input > 2) {

            try {
                //Prompt user for yes or no
                System.out.println("are you sure?");
                System.out.println("1:Yes 2:No");
                input = scan.nextInt();
                if (input < 1 || input > 2) {
                    System.out.println("Please select 1 or 2");
                }

            } catch (InputMismatchException ime) {
                System.out.println("Anwser must be 1 or 2 please try again.");
                scan.next();

            }

        }
        //Returns true if anwser is yes false if anwser is no
        return input == 1;
    }

    /**
     * Displays a choice wether to save the game before leaving. If yes is
     * selected saves the game as current game.
     *
     * @throws IOException
     */
    private void end() throws IOException {
        //If the player is about to leave asks if they would like to save the game.
        System.out.println("Would you like to save before you leave?");
        System.out.println("1:Yes 2:No");
        //Stores input
        int x = 0;
        while (x < 1 || x > 2) {
            try {

                x = scan.nextInt();
                //Catch input mistmatch errors and flush scanner.
            } catch (InputMismatchException ime) {
                System.out.println("Please select 1:Yes or 2:No");
                scan.next();
            }
        }
        if (x == 1) {
            //Allows player to save there game before leaving.
            save();
        }

    }

    public void initialView() {
        //set change
        setChanged();
        //pases the selcted save option to the plant game panel
        notifyObservers("Initial View");
    }

    public void nextDay() throws MoneyException, IOException {

        //Progresses to the next day.
        getPlayer().nextDay();
        //Saves game to current game.
        getFiles().saveCurrentGame(getShop(), getUnlocks(), getPlayer());
        //set change
        setChanged();
        //pases the selcted save option to the plant game panel
        notifyObservers("Next Day");
    }

    public void waterView() {
        //set change
        setChanged();
        //pases the selcted save option to the plant game panel
        notifyObservers("Water View");
    }

    public void water(int x, int y) {
        getPlayer().waterPlant(x, y);
        //set change
        setChanged();
        //pases the selcted save option to the plant game panel
        notifyObservers("Water");
    }

    /**
     * Waters a plant in the 3 by 3 player field grid specified by the player.
     */
    public void water() {
        //Variables to store user input
        int x = 0;
        int y = 0;

        //Create and store a string array representing the water state of plants within the field.
        String[][] waterState = getPlayer().getField().getWaterState();

        System.out.println("Which Plant would you like to water");

        //Prints the water state.
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(waterState[i][j] + " ");
            }
            System.out.println("");
        }

        //Gets user x and y input for water location.
        while (x < 1 || x > 3) {
            x = xSelect();
        }
        while (y < 1 || y > 3) {
            y = ySelect();
        }

        //Checks if the player still wants to water if the location contains dirt.
        if (getPlayer().getField().getPlant(x - 1, y - 1) instanceof Dirt) {
            System.out.println("You are about to water Dirt ");
            if (areYouSure()) {
                getPlayer().waterPlant(x - 1, y - 1);
            }
        } else {
            getPlayer().waterPlant(x - 1, y - 1);
        }
    }

    public void pickView() {
        //set change
        setChanged();
        //pases the selcted save option to the plant game panel
        notifyObservers("Pick View");
    }

    public void pick(int i, int j) {

        getPlayer().pickPlant(i, j);
        //set change
        setChanged();
        //pases the selcted save option to the plant game panel
        notifyObservers("Pick");
    }

    /**
     * Picks a plant in the 3 by 3 player field grid specified by the player.
     */
    public void pick() {

        int x = 0;
        int y = 0;
        System.out.println("Which Plant would you like to pick");
        String[][] valueState = getPlayer().getField().getValueState();

        //Prints the value of plants in the field.
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(valueState[i][j] + " ");
            }
            System.out.println("");
        }

        //Gets user selection for what plant to pick
        while (x < 1 || x > 3) {
            x = xSelect();
        }
        while (y < 1 || y > 3) {

            y = ySelect();

        }

        //Warns the user if they are going to pick dirt and asks to reconfirm
        if (getPlayer().getField().getPlant(x - 1, y - 1) instanceof Dirt) {
            System.out.println("You are about to pick Dirt ");
            if (areYouSure()) {
                getPlayer().pickPlant(x - 1, y - 1);
            }
        } else {
            getPlayer().pickPlant(x - 1, y - 1);
        }
    }

    public void alternatStart() throws IOException {

        //Highscores load.
        setHighscores((OrderedList<Score>) files.loadHighScore());

        //Establish inital plantselection and unlock shop.
        setShop(new PlantSelection());
        setUnlocks(new UnlockShop());
        //Condition for the main while loop which runs the game
        boolean flag;
        flag = true;
        //Variable for storing user input
        int anwser = 0;

        //Start of display
        System.out.println(endline);
        ArrayList<String> gi = files.information("Information");

        //Print out the game information upon startup.
        gi.forEach(i -> {
            System.out.println(i);
        });
        System.out.println("");

        //set change
        setChanged();
        //pases the selcted save option to the plant game panel
        notifyObservers("Options a");

        this.unlockView();
    }

    public void selected() {
        //set change
        setChanged();
        //pases the selcted save option to the plant game panel
        notifyObservers("Options Not Visible");
    }

    /**
     * Starts the game and collects user input for the main player choices such
     * as what action to do each day.
     */
    protected void start() {

        try {
            //Highscores load.
            setHighscores((OrderedList<Score>) getFiles().loadHighScore());

            //Establish inital plantselection and unlock shop.
            setShop(new PlantSelection());
            setUnlocks(new UnlockShop());
            //Condition for the main while loop which runs the game
            boolean flag;
            flag = true;
            //Variable for storing user input
            int anwser = 0;

            //Start of display
            System.out.println(endline);
            ArrayList<String> gi = getFiles().information("Information");

            //Print out the game information upon startup.
            gi.forEach(i -> {
                System.out.println(i);
            });
            System.out.println("");

            //Presents the player with an option to pick new gam/previousgame/load save file
            anwser = gameOptions();

            System.out.println(endline);

            //set change
            setChanged();
            // pases the selcted save option to the plant game panel
            notifyObservers("Options a");

            switch (anwser) {
                case 1:

                    //set change
                    setChanged();
                    // pases the selcted save option to the plant game panel
                    notifyObservers("Options c");
                    //Presents the player with the new game options

                    newGame();

                    break;
                case 2:
                    //loads the last game.
                    previousGame();
                    break;
                case 3:

                    //set change
                    setChanged();
                    // pases the selcted save option to the plant game panel
                    notifyObservers("Options b");
                    //Loads a specific game
                    loadGame();
                    break;
                default:
                    break;
            }

            //set change
            setChanged();
            // pases the selcted save option to the plant game panel
            notifyObservers("Options Not Visible");

            //set change
            setChanged();
            // pases the selcted save option to the plant game panel
            notifyObservers(this.getPlayer());

//            //set change
//            setChanged();
//            // pases the selcted save option to the plant game panel
//            notifyObservers(this.getPlayer());
//            
            //set change
            setChanged();
            // pases the selcted save option to the plant game panel
            notifyObservers(this.getUnlocks());

            flag = true;
            int plant;

            //Runs the main loop of the game.
            OUTER:

            while (flag) {
                //Presents the palyers  current field
                System.out.println(endline);
                System.out.println(getPlayer().getName() + " " + getPlayer());
                System.out.println("-------------Field-----------------");
                System.out.println(getPlayer().getField());
                System.out.println("-----------------------------------");
                System.out.println("$ Rent due in " + (5 - (getPlayer().getDay() % 5)) + " days.");
                System.out.println("What would you like to do:");
                System.out.print(" 1:Plant a plant");
                System.out.print(" 2:Water plant");
                System.out.print(" 3:Pick a plant");
                System.out.print(" 4:Progress to next day");
                System.out.print(" 5:Save Game");
                System.out.print(" 6:Information");
                System.out.print(" 7:Unlocks");
                System.out.println(" 9:Exit");
                System.out.print("Selection:");
                try {
                    plant = scan.nextInt();
                    switch (plant) {
                        case 1:
                            //Presents the players on where and how to plant a plant
                            plantAPlant();
                            //Notify observer in player?
                            break;
                        //If case is 2 send them to the watering view.
                        case 2: {
                            //Presents the user with options to water a plant within the player field
                            water();
                            break;
                        }
                        //If case is 3 send them to the picking view.
                        case 3: {
                            //Presents the user with options to pick a plant within the player field
                            pick();
                            break;
                        }
                        //If case is 4 progress to the next day next day remains in start loop as it breaks the while loop.
                        case 4:
                        try {
                            //sleep display
                            System.out.println("Z.z.Z.z.Z");
                            System.out.println("");
                            System.out.println("Evolution view     Growth view       Value view");
                            //String aray representing how evolved plants are
                            String[][] evolve = getPlayer().getField().getGrowthState();

                            //String array representing how grown plants are
                            String[][] grow = getPlayer().getField().getDayState();

                            //String array representing how much each plant is currently worth
                            String[][] money = getPlayer().getField().getValueState();

                            //prints each string array
                            for (int i = 0; i < 3; i++) {
                                for (int j = 0; j < 3; j++) {
                                    System.out.print(evolve[i][j] + " ");
                                }
                                System.out.print("       ");
                                for (int j = 0; j < 3; j++) {
                                    System.out.print(grow[i][j] + " ");
                                }

                                System.out.print("       ");
                                for (int j = 0; j < 3; j++) {
                                    System.out.print(money[i][j] + " ");
                                }
                                System.out.println("");
                            }
                            System.out.println("");

                            //set change
                            setChanged();
                            //pases the selcted save option to the plant game panel
                            notifyObservers("Next Day");
                            //Progresses to the next day.
                            getPlayer().nextDay();
                            //Saves game to current game.
                            getFiles().saveCurrentGame(getShop(), getUnlocks(), getPlayer());

                            //set change
                            setChanged();
                            //notify observers for change
                            notifyObservers(getPlayer());

                        } catch (MoneyException m) {
                            getFiles().endGame(getPlayer());
                            System.out.println("You wake broke and homless as the farm forecloses...");
                            break OUTER;
                        }
                        break;
                        //If case is 5 send player to save game options.
                        case 5:
                            //Saves the current game to a selected slot as described in save().
                            save();
                            break;
                        //If case is 6 send player to the information panel
                        case 6: {
                            System.out.println("Welcome to the information panel");
                            System.out.println("What would you like information on");
                            int x = 0;
                            while (x != 9) {
                                try {
                                    //Information for game
                                    System.out.println("1:General information");
                                    System.out.println("2:Plant information");

                                    //Information on player buttons
                                    System.out.println("3:Plant a Plant information");
                                    System.out.println("4:Pick a Plant information");
                                    System.out.println("5:Water a Plant information");
                                    System.out.println("6:Next day information");
                                    System.out.println("7:Unlocks information");
                                    System.out.println("8:Save game information");

                                    //Leave
                                    System.out.println("9:Exit");

                                    x = scan.nextInt();

                                    switch (x) {
                                        case 1: {
                                            //general information
                                            ArrayList<String> plants = getFiles().information("Information");
                                            plants.forEach(i -> {
                                                System.out.println(i);
                                            });
                                            System.out.println("");
                                            break;
                                        }
                                        case 2: {
                                            //plant information
                                            ArrayList<String> plants = getFiles().information("plants");
                                            plants.forEach(i -> {
                                                System.out.println(i);
                                            });
                                            System.out.println("");
                                            break;
                                        }
                                        case 3: {
                                            //plant a plant info
                                            ArrayList<String> plants = getFiles().information("Plant a Plant");
                                            plants.forEach(i -> {
                                                System.out.println(i);
                                            });
                                            System.out.println("");
                                            break;
                                        }
                                        case 4: {
                                            //pick a plant info
                                            ArrayList<String> plants = getFiles().information("Pick Plant");
                                            plants.forEach(i -> {
                                                System.out.println(i);
                                            });
                                            System.out.println("");
                                            break;
                                        }
                                        case 5: {
                                            //water a plant info
                                            ArrayList<String> plants = getFiles().information("Water");
                                            plants.forEach(i -> {
                                                System.out.println(i);
                                            });
                                            System.out.println("");
                                            break;
                                        }
                                        case 6: {
                                            //next day info
                                            ArrayList<String> plants = getFiles().information("Next day");
                                            plants.forEach(i -> {
                                                System.out.println(i);
                                            });
                                            System.out.println("");
                                            break;
                                        }
                                        case 7: {
                                            //unlock info
                                            ArrayList<String> plants = getFiles().information("Unlock");
                                            plants.forEach(i -> {
                                                System.out.println(i);
                                            });
                                            System.out.println("");
                                            break;
                                        }
                                        case 8: {
                                            //Save game info
                                            ArrayList<String> plants = getFiles().information("Save game");
                                            plants.forEach(i -> {
                                                System.out.println(i);
                                            });
                                            System.out.println("");
                                            break;
                                        }
                                        default:
                                            break;
                                    }

                                } catch (InputMismatchException ime) {
                                    scan.next();
                                }

                            }
                            break;
                        }
                        //If input is 7 sends player to the unlcok shop
                        case 7:
                            if (getUnlocks().size() != 0) {

                                //presents the player with the option of unlocking plants from the unlock shop.
                                unlock();

                            } else {
                                System.out.println("There is nothing left to unlock :)");
                            }
                            break;
                        //If input is 9 then player is sent to end game selections.
                        case 9:
                            //Ends the loop and the game.
                            System.out.print("Leaving game ");
                            if (areYouSure()) {

                                //Presents the player with the option of saving before the game ends
                                end();
                                break OUTER;
                            }
                            break;
                        default:
                            break;
                    }
                } catch (InputMismatchException ime) {
                    //flush scanner.
                    scan.next();

                }
            }
            System.out.println(endline);
            //Saves highscore
            getFiles().saveHighScore(getHighscores(), getPlayer());
            setHighscores((OrderedList<Score>) getFiles().loadHighScore());
            //Print highscores
            System.out.println("HIGH SCORES");
            System.out.println(getHighscores());

            System.out.println("Game ended thankyou for playing.");
            System.out.println(endline);

            //These should never be reached if they do get reached there is a problem
        } catch (MoneyException m) {
            System.err.println(m + "Player has invlaid amount of money");
        } catch (IOException m) {
            System.err.println(m + "There was a inputoutput based error");
        } catch (InstantiationException m) {
            System.err.println(m + "There was a instantation based error");
        } catch (IllegalAccessException m) {
            System.err.println(m + "There was a illegalaccess based error");
        }

    }

    /**
     * @return the player
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * @param player the player to set
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * @return the shop
     */
    public PlantSelection getShop() {
        return shop;
    }

    /**
     * @param shop the shop to set
     */
    public void setShop(PlantSelection shop) {
        this.shop = shop;
    }

    /**
     * @return the unlocks
     */
    public UnlockShop getUnlocks() {
        return unlocks;
    }

    /**
     * @param unlocks the unlocks to set
     */
    public void setUnlocks(UnlockShop unlocks) {
        this.unlocks = unlocks;
    }

    /**
     * @return the highscores
     */
    public OrderedList<Score> getHighscores() {
        return highscores;
    }

    /**
     * @param highscores the highscores to set
     */
    public void setHighscores(OrderedList<Score> highscores) {
        this.highscores = highscores;
    }

    /**
     * Runs the plant game as long as a player would like to play.
     *
     * @param args
     */
    public static void main(String[] args) {
        PlantGameModel game = new PlantGameModel();
        Scanner scan = new Scanner(System.in);

        //Starts and runs the game :)
        game.start();

        //User input
        int x = 0;
        while (x < 1 || x > 2) {
            try {
                System.out.println("Would you like to play again? 1:Yes 2:No");
                x = scan.nextInt();
                if (x == 1) {
                    game.start();
                    x = 0;
                }

                //Catch input mistmatch errors and flush scanner.
            } catch (InputMismatchException ime) {
                System.out.println("Please select 1:Yes or 2:No");
                scan.next();
            }
        }
        System.out.println("Goodbye.");
    }

    /**
     * @return the files
     */
    public GameState getFiles() {
        return files;
    }

    /**
     * @param files the files to set
     */
    public void setFiles(GameState files) {
        this.files = files;
    }

}
