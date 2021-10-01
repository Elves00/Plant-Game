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
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private String[] searchTerm;

    private DBManager manager;
    Data data;

    /**
     * Sets up the initial variables of a plant game.
     */
    public PlantGameModel() {
        //Information search terms for use in update
        searchTerm = new String[]{"Information", "plants", "Plant a Plant", "Pick Plant", "Water", "Next day", "Unlock", "Save game"};
        manager = new DBManager();
        manager.dbsetup();
        data = new Data();
        //Establishes file manage.
        this.files = new GameState();
        //Recieved gui input
        this.input = false;
        //Endline save.
        this.endline = "---------------------------------------------------------";
        //Sacnner to be used,
        this.scan = new Scanner(System.in);
    }

    public void getInfo(int i) {
//        //set change
//        setChanged();
//        //pases the selcted save option to the plant game panel
//        notifyObservers("Info " + i);

        ArrayList<String> info = new ArrayList();
        try {
            info = getFiles().information(searchTerm[i]); //set change
            //String array to store info in data
            String[] infoArray = new String[info.size()];

            for (int j = 0; j < infoArray.length; j++) {
                infoArray[j] = info.get(j) + "\n";
            }
            data.setInfoText(infoArray);

        } catch (IOException ex) {
            Logger.getLogger(PlantGameModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        data.setInfoUpdate(true);
        setChanged();
        //pases the selcted save option to the plant game panel
        notifyObservers(data);
        data.setInfoUpdate(false);
    }

    protected void newGame(String name) throws MoneyException, FileNotFoundException {

        //Here we set up a new game using data from the database.
        data = manager.newGame(name);
        setPlayer(new Player(name));

//        //Create player object with name
//        setPlayer(getFiles().newPlayer(name));
        //the game may progress to the main game
        data.setMainGame(true);
        //plant set size
        data.setPlantsetSize(PlantSet.values().length);
        //shop size
        data.setShopSize(this.shop.size());
        //shop content
        String[] shopContent = new String[this.shop.size()];
        for (int i = 0; i < shop.size(); i++) {
            try {
                shopContent[i] = this.shop.getPlant(i).toString();
            } catch (InstantiationException ex) {
                Logger.getLogger(PlantGameModel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(PlantGameModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        data.setShopText(shopContent);
        //No longer in start up
        data.setStart(false);
        //set change
        setChanged();
        //pases the selcted save option to the plant game panel
        notifyObservers(data);

//        //set change
//        setChanged();
//        //pases the selcted save option to the plant game panel
//        notifyObservers("Options Not Visible");
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
        //New game is selected
        data.setNewGame(true);
        setChanged();
        notifyObservers(data);

//        //set change
//        setChanged();
//        //pases the selcted save option to the plant game panel
//        notifyObservers("Options c");
//
//        //set change
//        setChanged();
//        //pases the selcted save option to the plant game panel
//        notifyObservers("Shop Start");
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

            //SET ALL PLANTS 
            //SET ALL PLANT STATUS
            data = manager.loadGame(0);
            player = new Player(data.getPlayerName(), data.getMoney(), data.getDay(), data.getScore());
            //Sets all the plants but not all the stats
            player.getField().setAllPlants(data.getPlants());

            //Sets all the plants stats
            player.getField().setAllPlantStatus(data.getPlantsDescription());

            //Loads variables from file.
//            setShop(getFiles().readShop());
            //Sets up the shop from database.
            setShop(data.getShop());
            setUnlocks(getFiles().readUnlock());

            //plant set size
            data.setPlantsetSize(PlantSet.values().length);
            //shop size
            data.setShopSize(this.shop.size());
            //shop content
            String[] shopContent = new String[this.shop.size()];
            for (int i = 0; i < shop.size(); i++) {
                try {
                    shopContent[i] = this.shop.getPlant(i).toString();
                } catch (InstantiationException ex) {
                    Logger.getLogger(PlantGameModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(PlantGameModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            data.setStart(false);

            //set change
            setChanged();
            //pases the selcted save option to the plant game panel
            notifyObservers(data);

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
        data.setLoadGame(true);
        try {

            //Set save display text
            data.setLoadText(this.files.saveDisplay());
        } catch (IOException ex) {
            Logger.getLogger(PlantGameModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        setChanged();
        notifyObservers(data);
//        //set change
//        setChanged();
//        //pases the selcted save option to the plant game panel
//        notifyObservers("Options b");

    }

    protected void loadGame(int selection) throws IOException {

        this.getFiles().loadGame(selection);

        //SET ALL PLANTS 
        //SET ALL PLANT STATUS
        data = manager.loadGame(selection + 1);
        player = new Player(data.getPlayerName(), data.getMoney(), data.getDay(), data.getScore());
        //Sets all the plants but not all the stats
        player.getField().setAllPlants(data.getPlants());

        //Sets all the plants stats
        player.getField().setAllPlantStatus(data.getPlantsDescription());

        //Loads objects.
//            setPlayer(getFiles().loadPlayer());
        setUnlocks(getFiles().readUnlock());
        setShop(getFiles().readShop());
        System.out.println("Save number" + selection + " loaded");
//        //set change
//        setChanged();
//        //pases the selcted save option to the plant game panel
//        notifyObservers("Options Not Visible");

        //the game may progress to the main game
        data.setMainGame(true);
        //plant set size
        data.setPlantsetSize(PlantSet.values().length);
        //shop size
        data.setShopSize(this.shop.size());
        //shop content
        String[] shopContent = new String[this.shop.size()];
        for (int i = 0; i < shop.size(); i++) {
            try {
                shopContent[i] = this.shop.getPlant(i).toString();
            } catch (InstantiationException ex) {
                Logger.getLogger(PlantGameModel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(PlantGameModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        data.setShopText(shopContent);
        //No longer in start up
        data.setStart(false);
        //set change
        setChanged();
        //pases the selcted save option to the plant game panel
        notifyObservers(data);

//        //set change
//        setChanged();
//        //pases the selcted save option to the plant game panel
//        notifyObservers("Shop Start");
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

        shopUpdate();

//        //set change
//        setChanged();
//        //pases the selcted save option to the plant game panel
//        notifyObservers("Shop Start");
    }

    /**
     * Updates the player
     */
    public void playerUpdate() {
        data.setPlayer(this.getPlayer().toString());
    }

    public void fieldUpdate() {
        //update data to contain the plant information
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (data.isFieldPick()) {
                    data.getViewPlants()[i][j] = getPlayer().getField().getPlant(i, j).getValue() + "$";
                } else {
                    data.getViewPlants()[i][j] = getPlayer().getField().getPlantArray()[i][j].toString();
                }

                data.getWaterPlants()[i][j] = getPlayer().getField().getPlantArray()[i][j].getWaterCount() >= getPlayer().getField().getPlantArray()[i][j].getWaterLimit();
                if (getPlayer().getField().getPlantArray()[i][j].isPollinator()) {
                    int[] pollin = this.getPlayer().getField().getNeighbours(i, j);

                    //Section searches through above and bellow the pollinator and sets there border to either yellow or mixed border
                    for (int k = 0; k < 2; k++) {
                        try {
                            this.data.getPollinatePlants()[pollin[k]][pollin[5]] = true;

                        } //Avoids neigbours calling poisitons out of array bounds.
                        catch (ArrayIndexOutOfBoundsException a) {

                        }
                    }

                    for (int k = 0; k < 2; k++) {
                        try {
                            this.data.getPollinatePlants()[pollin[k]][pollin[5]] = true;
                        } //Avoids neigbours calling poisitons out of array bounds.
                        catch (ArrayIndexOutOfBoundsException a) {

                        }
                    }

                }
            }
        }
        data.setFieldUpdate(true);
        playerUpdate();
        setChanged();
        //pases the selcted save option to the plant game panel
        notifyObservers(data);
        //Update finished set back to false.
        data.setFieldUpdate(false);

    }

    public void plantAPlant(int selection, int x, int y) throws InstantiationException, IllegalAccessException {

        getPlayer().newPlant(getShop().getPlant(selection - 1), x - 1, y - 1);
        //Update the field
        fieldUpdate();

//        setChanged();
//        //pases the selcted save option to the plant game panel
//        notifyObservers("Plant");
    }

    public void unlockView() {
        //unlock starting
        data.setUnlockStart(true);
        //store size of unlock
        data.setUnlockSize(getUnlocks().size());
        //Store the content of unlock into a string array
        String[] unlockText = new String[getUnlocks().size()];
        for (int i = 0; i < getUnlocks().size(); i++) {
            unlockText[i] = getUnlocks().toView(i);
        }
        data.setUnlockText(unlockText);

        //set change
        setChanged();
        //pases the selcted save option to the plant game panel
        notifyObservers(data);
        //Unlock no longer starting
        data.setUnlockStart(false);

//        //set change
//        setChanged();
//        //pases the selcted save option to the plant game panel
//        notifyObservers("Initial Unlock");
//        //set change
//        setChanged();
//        //Notifys that a plant has been unlocked
//        notifyObservers("Unlock");
    }

    public void shopUpdate() {
        //plant set size
        data.setPlantsetSize(PlantSet.values().length);
        //shop size
        data.setShopSize(this.shop.size());
        //shop content
        String[] shopContent = new String[this.shop.size()];
        for (int i = 0; i < shop.size(); i++) {
            try {
                shopContent[i] = this.shop.getPlant(i).toString();
            } catch (InstantiationException ex) {
                Logger.getLogger(PlantGameModel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(PlantGameModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        data.setShopText(shopContent);
        data.setShopUpdate(true);
        //set change
        setChanged();
        //pases the selcted save option to the plant game panel
        notifyObservers(data);
        data.setShopUpdate(false);
    }

    public void unlock(int i) {
        getUnlocks().price(getPlayer(), getShop(), i);

        //unlock starting
        data.setUnlockUpdate(true);
        //store size of unlock
        data.setUnlockSize(getUnlocks().size());
        //Store the content of unlock into a string array
        String[] unlockText = new String[getUnlocks().size()];
        for (int j = 0; j < getUnlocks().size(); j++) {
            unlockText[j] = getUnlocks().toView(j);
        }
        data.setUnlockText(unlockText);

        //set change
        setChanged();
        //pases the selcted save option to the plant game panel
        notifyObservers(data);
        //Unlock no longer starting
        data.setUnlockUpdate(false);
        shopUpdate();
//
//        //set change
//        setChanged();
//        //Notifys that a plant has been unlocked
//        notifyObservers("Unlock");
//
//        //Update the shop with new unlocked plant
//        setChanged();
//        notifyObservers("Shop Update");

    }

    public void saveView() {
        data.setSaveStart(true);
        try {
            data.setSaveText(getFiles().saveDisplay());

//        //set change
//        setChanged();
//        //pases the selcted save option to the plant game panel
//        notifyObservers("Initial Save");
        } catch (IOException ex) {
            Logger.getLogger(PlantGameModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        //set change
        setChanged();
        //pases the selcted save option to the plant game panel
        notifyObservers(data);
        data.setSaveStart(false);

    }

    public void save(int selection) throws IOException {
        //Saves state of current game to the current game slot.
        getFiles().saveCurrentGame(getShop(), getUnlocks(), getPlayer());
        //Saves game to selected location.
        getFiles().saveGame(getShop(), getUnlocks(), getPlayer(), selection);
        //success message.
        System.out.println("Save succefull");
        data.setSaveStart(true);
        try {
            data.setSaveText(getFiles().saveDisplay());

//        //set change
//        setChanged();
//        //pases the selcted save option to the plant game panel
//        notifyObservers("Initial Save");
        } catch (IOException ex) {
            Logger.getLogger(PlantGameModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        //set change
        setChanged();
        //pases the selcted save option to the plant game panel
        notifyObservers(data);
        data.setSaveStart(false);
//        //set change
//        setChanged();
//        //pases the selcted save option to the plant game panel
//        notifyObservers("Initial Save");
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

    public void initialView() {
        //Update the field
        fieldUpdate();
        //set change
//        setChanged();
//        //pases the selcted save option to the plant game panel
//        notifyObservers("Initial View");
    }

    public void nextDay() throws MoneyException, IOException {

        //Progresses to the next day.
        getPlayer().nextDay();
        //Saves game to current game.
        getFiles().saveCurrentGame(getShop(), getUnlocks(), getPlayer());

        //Update the field
        fieldUpdate();

//        //set change
//        setChanged();
//        //pases the selcted save option to the plant game panel
//        notifyObservers("Next Day");
    }

    public void waterView() {
        //Update the field
        fieldUpdate();
//        //set change
//        setChanged();
//        //pases the selcted save option to the plant game panel
//        notifyObservers("Water View");

    }

    public void water(int x, int y) {
        getPlayer().waterPlant(x, y);
        //Update the field
        fieldUpdate();

//        //set change
//        setChanged();
//        //pases the selcted save option to the plant game panel
//        notifyObservers("Water");
    }

    public void pickView() {
        data.setFieldPick(true);
        //Update the field
        fieldUpdate();
        data.setFieldPick(false);
//        //set change
//        setChanged();
//        //pases the selcted save option to the plant game panel
//        notifyObservers("Pick View");
    }

    public void pick(int i, int j) {
        data.setFieldPick(true);
        //Update the field
        fieldUpdate();
        data.setFieldPick(false);
        getPlayer().pickPlant(i, j);
//        //set change
//        setChanged();
//        //pases the selcted save option to the plant game panel
//        notifyObservers("Pick");
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

//        //set change
//        setChanged();
//        //pases the selcted save option to the plant game panel
//        notifyObservers("Options a");
        data.setStart(true);

    }

    public void selected() {
//        //set change
//        setChanged();
//        //pases the selcted save option to the plant game panel
//        notifyObservers("Options Not Visible");
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
     * @param shop the shop to set
     */
    public void setShop(String shop) {
        this.shop = new PlantSelection(shop);
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
