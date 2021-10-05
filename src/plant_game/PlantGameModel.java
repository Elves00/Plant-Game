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
        searchTerm = new String[]{"Information", "Plants", "Plant a Plant", "Pick Plant", "Water", "Next Day", "Unlock", "Save Game"};
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
        data = manager.loadInfo(searchTerm[i], data);
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

        try {
            this.save(0);

        } catch (IOException ex) {
            Logger.getLogger(PlantGameModel.class.getName()).log(Level.SEVERE, null, ex);
        }

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
        //Establish inital plantselection and unlock shop.
        setShop(new PlantSelection());
        setUnlocks(new UnlockShop());
        //New game is selected
        data.setNewGame(true);
        setChanged();
        notifyObservers(data);

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
            //SET ALL PLANTS 
            //SET ALL PLANT STATUS
            data = manager.loadGame(0);
            player = new Player(data.getPlayerName(), data.getMoney(), data.getEnergy(), data.getDay(), data.getScore());
            //Sets all the plants but not all the stats
            player.getField().setAllPlants(data.getPlants());

            //Sets all the plants stats
            player.getField().setAllPlantStatus(data.getPlantsDescription());

            //Load the unlock shop from the database for the selected save slot
            data = manager.selectUnlockShop(0, data);
            ArrayList<String> details = new ArrayList();
            details.add(data.getUnlock());
            details.add(data.getUnlockCost());

            UnlockShop lin = new UnlockShop(details);
            //Set the unlock shop with the details from jdbc
            setUnlocks(new UnlockShop(details));
            for (int i = 0; i < details.size(); i++) {
                System.out.println(details.get(i));
            }

            //Loads the shop from the database for the selected save slot
            data = manager.selectShop(0, data);
            setShop(data.getShop());

            System.out.println("Unlock----:" + data.getUnlock() + data.getUnlockCost());
            System.out.println("The unlock is:" + getUnlocks());

            System.out.println("Shop----:" + data.getShop());

            System.out.println("The shop is:" + getShop());
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

            data.setStart(false);
            data.setMainGame(true);
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
        //Sets up the load text in data
        data = manager.loadText(data);

        setChanged();
        notifyObservers(data);

    }

    protected void loadGame(int selection) throws IOException {

        //SET ALL PLANTS 
        //SET ALL PLANT STATUS
        data = manager.loadGame(selection + 1);
        player = new Player(data.getPlayerName(), data.getMoney(), data.getEnergy(), data.getDay(), data.getScore());

        //Sets all the plants but not all the stats
        player.getField().setAllPlants(data.getPlants());

        //Sets all the plants stats
        player.getField().setAllPlantStatus(data.getPlantsDescription());

        //Load the unlock shop from the database for the selected save slot
        data = manager.selectUnlockShop(selection + 1, data);
        ArrayList<String> details = new ArrayList();
        details.add(data.getUnlock());
        details.add(data.getUnlockCost());
        //Set the unlock shop with the details from jdbc
        setUnlocks(new UnlockShop(details));

        //Loads the shop from the database for the selected save slot
        data = manager.selectShop(selection + 1, data);
        setShop(data.getShop());

        System.out.println("Unlock----:" + data.getUnlock() + data.getUnlockCost());
        System.out.println("The unlock is:" + getUnlocks());

        System.out.println("Shop----:" + data.getShop());

        System.out.println("The shop is:" + getShop());

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
        System.out.println("WEEEEEEEEEE SET IT TRUE");
        //the game may progress to the main game
        data.setMainGame(true);
        //set change
        setChanged();
        //pases the selcted save option to the plant game panel
        notifyObservers(data);

    }

    public void plantAPlantView() {
        shopUpdate();
    }

    public Data fieldUpdateData(int selection, Data data) {
        data.setFieldDetails(player.getField().toFile());
        manager.saveField(0, data.getFieldDetails());
        return data;
    }

    public void fieldUpdate() {

        //updates the current players field data in the database
        data = fieldUpdateData(0, data);

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
                            this.data.getPollinatePlants()[pollin[4]][pollin[k + 2]] = true;
                        } //Avoids neigbours calling poisitons out of array bounds.
                        catch (ArrayIndexOutOfBoundsException a) {

                        }
                    }

                }
            }
        }
        data.setFieldUpdate(true);
        data = playerData(data);
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

        //Updates the shop in the database for the current player
        System.out.println("Updateing with :" + shopContent[shop.size() - 1]);
        manager.updateShop(0, shopContent[shop.size() - 1]);

        data.setShopUpdate(true);
        manager.savePlayer(0, data);
        //set change
        setChanged();
        //pases the selcted save option to the plant game panel
        notifyObservers(data);
        data.setShopUpdate(false);
    }

    public void unlock(int i) {

        //Remove from current save slot
        data = manager.updateUnlock(0, getUnlocks().toData(i - 1), data);
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
        manager.savePlayer(0, data);
        //set change
        setChanged();
        //pases the selcted save option to the plant game panel
        notifyObservers(data);
        //Unlock no longer starting
        data.setUnlockUpdate(false);
        shopUpdate();

    }

    public void saveView() {
        data.setSaveStart(true);
        manager.loadText(data);
        data.setSaveText(data.getLoadText());
        System.out.println("save text " + data.getUnlockText());
//        //set change
//        setChanged();
//        //pases the selcted save option to the plant game panel
//        notifyObservers("Initial Save");
        //set change
        setChanged();
        //pases the selcted save option to the plant game panel
        notifyObservers(data);
        data.setSaveStart(false);

    }

    public void save(int selection) throws IOException {

        //updates current data wit shop and unlock
        data = manager.selectShop(0, data);
        data = manager.selectUnlockShop(0, data);
        data = fieldUpdateData(selection, data);
        data = playerData(data);
        manager.saveGame(selection, data);

        data.setSaveStart(true);
        manager.loadText(data);
        data.setSaveText(data.getLoadText());

//        //set change
//        setChanged();
//        //pases the selcted save option to the plant game panel
//        notifyObservers("Initial Save");
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

    public void initialView() {
        //Update the field
        fieldUpdate();
    }

    public Data playerData(Data data) {
        data.setPlayer(this.getPlayer().toString());
        data.setPlayerName(player.getName());
        data.setMoney(player.getMoney());
        data.setEnergy(player.getEnergy());
        data.setDay(player.getDay());
        data.setScore(player.getScore());
        return data;
    }

    public Data shopData(Data data) {
        return data;
    }

    public Data unlockData(Data data) {
        return data;
    }

    public void nextDay() throws MoneyException, IOException {

        try {
            //Progresses to the next day.
            getPlayer().nextDay();

            //Update the field
            fieldUpdate();

            data = playerData(data);
            manager.savePlayer(0, data);

        } catch (MoneyException me) {
            System.out.println("Caught the moeny error in next day");
            //Updates the player score
            data.setScore(player.getScore());
            //Tells data base game is ending and to update data
            data = manager.endGame();
            setChanged();
            notifyObservers(data);
            //set end game back to false;
            data.setEndGame(false);
            //Throw the error to the controller.
            throw new MoneyException();

        }
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

    }

    public void pick(int i, int j) {
        data.setFieldPick(true);
        //Update the field
        fieldUpdate();
        data.setFieldPick(false);
        getPlayer().pickPlant(i, j);

    }

    public void alternatStart() throws IOException {

        //Highscores load.
        setHighscores((OrderedList<Score>) files.loadHighScore());

        //Condition for the main while loop which runs the game
        boolean flag;
        flag = true;
        //Variable for storing user input
        int anwser = 0;

        //Start of display
        System.out.println(endline);

//        //set change
//        setChanged();
//        //pases the selcted save option to the plant game panel
//        notifyObservers("Options a");
        data.setStart(true);

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
