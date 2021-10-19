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
 * This class controlls the methods associated with saving and loading the model
 * from the jdbc database.
 *
 * @author breco
 */
public class Modelsave extends Observable {

    private final DBManager manager;

    //The main components of the model
    //A plant game model has a player shop and unlocks which controll the flow of the game.
    private Player player;
    private PlantSelection shop;
    private UnlockShop unlocks;

    //Search terms for loading information from the data base.
    private final String[] searchTerm = new String[]{"Information", "Plants", "Plant a Plant", "Pick Plant", "Water", "Next Day", "Unlock", "Save Game"};

    /**
     * establish the database manager for this model
     *
     * @param manager a DBManager.
     */
    public Modelsave(DBManager manager) {
        this.manager = manager;
    }

    /**
     * Updates data to reflect information needed to view a new game.
     *
     * Uses data from the data class to start a new game along with a plant
     * selection and unlock shop. This information is the used by the database
     * manager to load scores before notifying observers of data changes.
     *
     * @param data
     * @param plantselection plant selection of the current model.
     * @param unlock unlock shop of the current model.
     * @return
     * @throws MoneyException
     * @throws FileNotFoundException
     */
    protected Data VewNewGame(Data data, PlantSelection plantselection, UnlockShop unlock) throws MoneyException, FileNotFoundException {
        //Updates the shop and unlocks to match input.
        shop = plantselection;
        unlocks = unlock;

        //New game starting
        data.setNewGame(true);

        //Loads the scores table to data.
        data = manager.loadScores(data);

        //Notify data changes
        setChanged();
        notifyObservers(data);
        //new game finished
        data.setCheckScores(false);

        //Return the modified data.
        return data;

    }

    /**
     * Creates a new game with a player containing the inputted name.
     *
     * Takes in a players name and sets up a new player with that name. Then
     * updates database with information of the new player. Creates a new shop
     * and unlock and updates the database.
     *
     * Notifys the view of the changes to the data class afterwards.
     *
     * Special casses the inputed name is to long or triggers the special case.
     * In both scenarios a new game is not created and instead data is changed
     * to reflect the error and observers are notified.
     *
     * @param name Name of the player
     * @param data
     * @param player
     * @throws MoneyException
     * @throws FileNotFoundException
     */
    protected Data newGame(String name, Data data) throws MoneyException, FileNotFoundException {

        //This name is a special case used to reserve plant game slots.
        if (name.equals("uGaTL@V%yiW3")) {

            //set up a warning and send to data
            data.setWarning("User name is Invalid please try another name");
            data.setWarningCheck(true);
            setChanged();
            notifyObservers(data);
            //Throws an error to be caught elsewhere
            throw new IllegalArgumentException("Bad username");

        } else if (name.length() > 20) {
            //set up a warning and send to data
            data.setWarning("Username is to long please select a name with 20 characters or less");
            data.setWarningCheck(true);
            setChanged();
            notifyObservers(data);
            //Throws an error to be caught elsewhere
            throw new IllegalArgumentException("Bad username");

        } else {
            //Set up the player
            this.player = new Player();
            //Here we set up a new game using data from the database.
            data = manager.newGame(name);

            //the game may progress to the main game
            data.setMainGame(true);
            //Set up the inital plant selection size
            data.setPlantsetSize(PlantSet.values().length);
            //Set up initalshop size
            data.setShopSize(this.getShop().size());

            //shop content
            String[] shopContent = new String[this.getShop().size()];
            for (int i = 0; i < getShop().size(); i++) {
                try {
                    shopContent[i] = this.getShop().getPlant(i).toString();
                } catch (InstantiationException ex) {
                    Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            data.setShopText(shopContent);

            //No longer in start up revert to false
            data.setStart(false);
            //pass data to view.
            setChanged();
            notifyObservers(data);
            //Save to current game slot.
            data = this.save(0, data, player);
        }
        return data;
    }

    /**
     * Loads a selected plant game from the database.Will load a plant game from
     * the data base.
     *
     *
     * @param selection
     * @param data
     * @return
     */
    protected Data loadGame(int selection, Data data) {

        //SET ALL PLANTS 
        //SET ALL PLANT STATUS
        data = manager.loadGame(selection + 1);
        setPlayer(new Player(data.getPlayerName(), data.getMoney(), data.getEnergy(), data.getDay(), data.getScore()));

        //Sets all the plants but not all the stats
        getPlayer().getField().setAllPlants(data.getPlants());

        //Sets all the plants stats
        getPlayer().getField().setAllPlantStatus(data.getPlantsDescription());

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
        data.setShopSize(this.getShop().size());
        //shop content
        String[] shopContent = new String[this.getShop().size()];
        for (int i = 0; i < getShop().size(); i++) {
            try {
                shopContent[i] = this.getShop().getPlant(i).toString();
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
        return data;

    }

    /**
     * Notify the observe to display the options for loading game
     *
     * @param data
     * @return
     */
    protected Data viewLoadGame(Data data) {
        data.setLoadGame(true);
        //Sets up the load text in data
        data = manager.loadText(data);

        setChanged();
        notifyObservers(data);
        return data;

    }

    /**
     * Loads a game from the current game file.Calls load game for slot zero.
     *
     *
     * @param data
     * @return
     */
    protected Data previousGame(Data data) {
        //loads the last game.
        data = manager.loadGame(0);
        //Adds one in load method so start as -1
        data = loadGame(-1, data);
        return data;
    }

    /**
     * Updates the view of the saves
     *
     * @param data
     * @return
     */
    public Data viewSave(Data data) {
        //set save view start to true
        data.setSaveStart(true);
        //sets the text in the data class.
        data = manager.loadText(data);
        data.setSaveText(data.getLoadText());
        //passes data to view
        setChanged();
        notifyObservers(data);
        //set save view reset
        data.setSaveStart(false);
        return data;

    }

    /**
     * Saves the current game to the database.
     *
     * loads information about the state of the current game and passes it to
     * the manager in order to save.
     *
     * @param selection
     * @param data
     * @return
     */
    public Data save(int selection, Data data, Player player) {
        this.player = player;
        //updates current data wit shop and unlock
        data = manager.loadShop(0, data);
        data = manager.loadUnlock(0, data);
        data = manager.loadPlayer(0, data);
        data = fieldUpdateData(selection, data);
        data = playerData(data);
        manager.saveGame(selection, data);

        data.setSaveStart(true);
        manager.loadText(data);
        data.setSaveText(data.getLoadText());

        setChanged();
        //pases the selcted save option to the plant game panel
        notifyObservers(data);
        data.setSaveStart(false);
        return data;

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

    public Data fieldUpdateData(int selection, Data data) {

        data.setFieldDetails(player.getField().toFile());
        manager.saveField(0, data.getFieldDetails());
        return data;
    }

    /**
     * Retrieves information stored within the information table and passes it
     * to the view.
     *
     *
     * @param selection
     */
    public Data getInfo(int selection, Data data) {
        data = manager.loadInfo(searchTerm[selection], data);
        data.setInfoUpdate(true);

        //passes data to the view.
        setChanged();
        notifyObservers(data);
        //Update complete set back to fale
        data.setInfoUpdate(false);
        return data;
    }

    /**
     * @return the player
     */
    public Player getPlayer() {
        return player;
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

}
