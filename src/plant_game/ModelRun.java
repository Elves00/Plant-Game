/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plant_game;

import java.io.FileNotFoundException;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controls the running of plant game.
 *
 * Manages methods such as next day plant a plant pick a plant
 *
 * @author breco
 */
public class ModelRun extends Observable {

    private final DBManager manager;

    private Player player;
    private PlantSelection shop;
    private UnlockShop unlocks;

    public ModelRun(DBManager manager) {
        this.manager = manager;
    }

    public Data nextDay(Data data) throws MoneyException, FileNotFoundException {

        try {

            //Progresses to the next day.
            getPlayer().nextDay();

            //Update the field
            data = updateField(data);

            data = playerData(data);

            manager.savePlayer(0, data);

        } catch (MoneyException me) {

            //Updates the player score
            data.setScore(getPlayer().getScore());

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

        return data;
    }

    public Data water(int x, int y, Data data) {
        try {
            getPlayer().waterPlant(x, y);
            //Update the field
            data = updateField(data);

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
        return data;
    }

    public Data pick(int i, int j, Data data) {

        try {
            //Pick a plant throws resource exception if unable to plant.
            getPlayer().pickPlant(i, j);
            //Update field with pick display.
            data.setFieldPick(true);
            //Update the field
            data = updateField(data);
            data.setFieldPick(false);

        } catch (ResourceException e) {
            data.setWarningCheck(true);
            data.setWarning(e.getMessage());
            //Notifys view of data changes
            setChanged();
            notifyObservers(data);
            data.setWarningCheck(false);

        }
        return data;
    }
    public Data updateShop(Data data) {
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
        manager.updateShop(0, shopContent[shop.size() - 1]);

        data.setShopUpdate(true);
        manager.savePlayer(0, data);
        //set change
        setChanged();

        notifyObservers(data);
        data.setShopUpdate(false);

        return data;
    }


    public Data updateFieldData(int selection, Data data) {
        data.setFieldDetails(player.getField().toFile());
        manager.saveField(0, data.getFieldDetails());
        return data;
    }

    public Data updateField(Data data) {

        //updates the current players field data in the database
        data = updateFieldData(0, data);

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
                            data.getPollinatePlants()[pollin[k]][pollin[5]] = true;

                        } //Avoids neigbours calling poisitons out of array bounds.
                        catch (ArrayIndexOutOfBoundsException a) {

                        }
                    }

                    for (int k = 0; k < 2; k++) {
                        try {
                            data.getPollinatePlants()[pollin[4]][pollin[k + 2]] = true;
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

    public Data plantAPlant(int selection, int x, int y, Data data) throws InstantiationException, IllegalAccessException {

        try {
            getPlayer().newPlant(getShop().getPlant(selection - 1), x - 1, y - 1);
            //Update the field
            data = updateField(data);
        } catch (ResourceException e) {
            data.setWarningCheck(true);
            data.setWarning(e.getMessage());
            //Notifys view of data changes
            setChanged();
            notifyObservers(data);
            data.setWarningCheck(false);

        }
        return data;
    }

    public Data viewUnlock(Data data) {

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

        return data;

    }

    public Data viewWater(Data data) {
        //Update the field
        data = updateField(data);
//        //set change
//        setChanged();
//        //pases the selcted save option to the plant game panel
//        notifyObservers("Water View");
        return data;

    }

    /**
     * Notify the view that the player intends to pick a plant.
     *
     * Once the view has been notified set field pick back to false.
     */
    public Data viewPick(Data data) {
        data.setFieldPick(true);
        //Update the field
        data = updateField(data);
        data.setFieldPick(false);
        return data;

    }

    
    /**
     * Unlocks a plant from the unlock shop for the current game.
     *
     *
     * @param selection
     */
    public Data unlock(int selection, Data data) {

        try {
            //Throws resource exception if player doesn't have enough money to unlock the selection.
            String unlocked = getUnlocks().price(getPlayer(), getShop(), selection);

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
            data = updateShop(data);
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
