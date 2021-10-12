/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plant_game;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author breco
 */
public class Model extends Observable {

    private Player player;
    private PlantSelection shop;
    private UnlockShop unlocks;
    private OrderedList<Score> highscores;
    private String[] searchTerm;

    private DBManager manager;
    Data data;

    /**
     * Sets up the initial variables of a plant game.
     */
    public Model() {
        //Information search terms for use in update
        searchTerm = new String[]{"Information", "Plants", "Plant a Plant", "Pick Plant", "Water", "Next Day", "Unlock", "Save Game"};
        manager = new DBManager();
        manager.constructDatabse();

    }

    /**
     * Calls the manager to determine the visibility of certain view components
     * based on table data.
     *
     *
     */
    public void start() {
        data = manager.start();
        setChanged();
        //pases the selcted save option to the plant game panel
        notifyObservers(data);
    }

    public void getInfo(int i) {
        data = manager.loadInfo(searchTerm[i], data);
        data.setInfoUpdate(true);
        setChanged();
        //pases the selcted save option to the plant game panel
        notifyObservers(data);
        data.setInfoUpdate(false);
    }

    /**
     * Creates a new game with a player containing the inputted name.
     *
     * @param name
     * @throws MoneyException
     * @throws FileNotFoundException
     */
    protected void newGame(String name) throws MoneyException, FileNotFoundException {
        if (name.equals("uGaTL@V%yiW3")) {

            data.setWarning("User name is Invalid please try another name");
            data.setWarningCheck(true);

            setChanged();
            notifyObservers(data);

            throw new IllegalArgumentException("Bad username");
        } else {
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
                    Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            data.setShopText(shopContent);
            //No longer in start up
            data.setStart(false);

            //set change
            setChanged();
            //pases the selcted save option to the plant game panel
            notifyObservers(data);

            this.save(0);
        }

    }

    /**
     * Saves the current game and notifies the data class of changes.
     *
     * Save game to current game and set main menu to true telling the data
     * class to preform main menu actions. afterwards create a new data class as
     * the options present to the player all require a fresh data object.
     */
    public void mainMenu() {
        this.save(0);
        this.data = manager.start();
        data.setMainMenu(true);
        setChanged();
        notifyObservers(data);
        data.setMainMenu(false);

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
//        data.setStart(true);
        //Loads the scores table to data to display in the view.
        data = manager.loadScores(data);

        setChanged();
        notifyObservers(data);
        data.setCheckScores(false);

    }

    /**
     * Loads a game from the current game file.
     *
     */
    protected void previousGame() {

        //loads the last game.
        data = manager.loadGame(0);
        loadGame(0);
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

    protected void loadGame(int selection) {

        //SET ALL PLANTS 
        //SET ALL PLANT STATUS
        data = manager.loadGame(selection + 1);
        player = new Player(data.getPlayerName(), data.getMoney(), data.getEnergy(), data.getDay(), data.getScore());

        //Sets all the plants but not all the stats
        player.getField().setAllPlants(data.getPlants());

        //Sets all the plants stats
        player.getField().setAllPlantStatus(data.getPlantsDescription());

        //Load the unlock shop from the database for the selected save slot
        data = manager.loadUnlock(selection + 1, data);
        ArrayList<String> details = new ArrayList();
        details.add(data.getUnlock());
        details.add(data.getUnlockCost());
        //Set the unlock shop with the details from jdbc
        setUnlocks(new UnlockShop(details));

        //Loads the shop from the database for the selected save slot
        data = manager.loadShop(selection + 1, data);
        setShop(data.getShop());

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
                Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        data.setShopText(shopContent);
        //No longer in start up
        data.setStart(false);
        //the game may progress to the main game
        data.setMainGame(true);
        //Loads the scores table to data to display in the view.
        data = manager.loadScores(data);

        setChanged();
        notifyObservers(data);
        data.setCheckScores(false);
        System.out.println("Load Game shop and unlock");
        System.out.println(shop);
        System.out.println(unlocks);

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
                //If the palyer is picking money is display rather than plant name
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

        try {
            getPlayer().newPlant(getShop().getPlant(selection - 1), x - 1, y - 1);
            //Update the field
            fieldUpdate();
        } catch (ResourceException e) {
            data.setWarningCheck(true);
            data.setWarning(e.getMessage());
            //Notifys view of data changes
            setChanged();
            notifyObservers(data);
            data.setWarningCheck(false);

        }
    }

    public void unlockView() {

//        System.out.println("UNLOCK VIEW CALLED");
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
                Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        data.setShopText(shopContent);

        //Updates the shop in the database for the current player
//        System.out.println("Updateing with :" + shopContent[shop.size() - 1]);
        manager.updateShop(0, shopContent[shop.size() - 1]);

        data.setShopUpdate(true);
        manager.savePlayer(0, data);
        //set change
        setChanged();
        //pases the selcted save option to the plant game panel
        notifyObservers(data);
        data.setShopUpdate(false);
    }

    /**
     * Unlocks a plant from the unlock shop for the current game.
     *
     *
     * @param i
     */
    public void unlock(int i) {

        try {
            //Throws resource exception if player doesn't have enough money to unlock the selection.
            String unlocked = getUnlocks().price(getPlayer(), getShop(), i);

            //Remove the selected unlock from current save slot in the database.
            data = manager.updateUnlock(0, unlocked, data);

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
            //Updates player data.
            data = playerData(data);
            //Saves the player to current save.
            manager.savePlayer(0, data);

            //set change
            setChanged();
            //pases data to observer.
            notifyObservers(data);

            //Unlock no longer starting
            data.setUnlockUpdate(false);
            //Updates the shop
            shopUpdate();
        } catch (ResourceException re) {
            //Sets warning message
            data.setWarning(re.getMessage());
            data.setWarningCheck(true);
            //set change
            setChanged();
            //passes data to the view.
            notifyObservers(data);
            //Set warning back to false after view updates.
            data.setWarningCheck(false);
        }

    }

    public void saveView() {
        data.setSaveStart(true);
        manager.loadText(data);
        data.setSaveText(data.getLoadText());
//        System.out.println("save text " + data.getUnlockText());
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

    public void save(int selection) {

        //updates current data wit shop and unlock
        data = manager.loadShop(0, data);
        data = manager.loadUnlock(0, data);
        data = fieldUpdateData(selection, data);
        data = playerData(data);
        manager.saveGame(selection, data);

        data.setSaveStart(true);
        manager.loadText(data);
        System.out.println("Save load text: " + data.getLoadText()[0]);
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

    public void nextDay() throws MoneyException, FileNotFoundException {

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

            //Updates the scores table within the database.
            manager.updateScores(data);

            //Tells data base game is ending and to update data
            data = manager.endGame();
            //Loads the scores table to data to display in the view.
            data = manager.loadScores(data);

            //Send data to view.
            setChanged();
            notifyObservers(data);
            data.setCheckScores(false);

            //set end game back to false;
            data.setEndGame(false);

            //Throw the error to the controller.
//            throw new MoneyException();
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
        try {
            getPlayer().waterPlant(x, y);
            //Update the field
            fieldUpdate();

        } catch (ResourceException e) {
            data.setWarningCheck(true);
            data.setWarning(e.getMessage());
            //Notifys view of data changes
            setChanged();
            notifyObservers(data);
            data.setWarningCheck(false);
        }
//        //set change
//        setChanged();
//        //pases the selcted save option to the plant game panel
//        notifyObservers("Water");
    }

    /**
     * Notify the view that the player intends to pick a plant.
     *
     * Once the view has been notified set field pick back to false.
     */
    public void pickView() {
        data.setFieldPick(true);
        //Update the field
        fieldUpdate();
        data.setFieldPick(false);

    }

    public void pick(int i, int j) {

        try {
            //Pick a plant throws resource exception if unable to plant.
            getPlayer().pickPlant(i, j);
            //Update field with pick display.
            data.setFieldPick(true);
            //Update the field
            fieldUpdate();
            data.setFieldPick(false);

        } catch (ResourceException e) {
            data.setWarningCheck(true);
            data.setWarning(e.getMessage());
            //Notifys view of data changes
            setChanged();
            notifyObservers(data);
            data.setWarningCheck(false);

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
     * @return the high scores
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

}
