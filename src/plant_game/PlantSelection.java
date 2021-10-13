/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plant_game;

import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * Plant selection is used to manage the purchase of plants for the player to
 * plant. The shop has an ArrayList which contains all available plants the
 * player can purchase. This list can be updated by the unlock method.
 *
 * @author breco
 */
public class PlantSelection implements ToFile {

    private HashMap<Integer, Plant> plants;

    /**
     * Creates a PlantSelection object with the default plants available.
     *
     * plantArray default plants are Broccoli,cabbage,carrot
     */
    public PlantSelection() {

        plants = new HashMap<Integer, Plant>();
        plants.put(0, PlantSet.BROCCOLI.getPlant());
        plants.put(1, PlantSet.CABBAGE.getPlant());
        plants.put(2, PlantSet.CARROT.getPlant());

    }

    /**
     * Creates a PlantSelection object with various plants available as defined
     * by the input string plantList
     *
     * @param plantList A string containing plants to be added to the
     * PlantSelection plant array
     *
     */
    public PlantSelection(String plantList) {
        StringTokenizer st = new StringTokenizer(plantList);
//        setPlantArray(new Plant[PlantSet.values().length]);

        while (st.hasMoreTokens()) {
            String holder = st.nextToken();
            //Search through the plant set and add the appropriate plant to the filed
            for (PlantSet p : PlantSet.values()) {

                if (p.toString().equalsIgnoreCase(holder)) {
                    unlock(p.getPlant());

                }
            }

        }

    }

    /**
     * Adds a new plant to the plant selection panel if it is not already in the
     * panel.
     *
     * @param toUnlock The plant to be added to plant selection.
     */
    public void unlock(Plant toUnlock) {

        if (getPlants() == null) {
            setPlants(new HashMap<Integer, Plant>());
        }
        //If the plants set does not contain the plant add it.
        if (!plants.containsValue(toUnlock)) {
            getPlants().put(getPlants().size(), toUnlock);
        } //This should never occur.
        else {

            throw new IllegalArgumentException("Plant already unlocked");
        }

    }

    /**
     * Purchases a plant from Plant Selection Plant Array subtracting money from
     * the player based on the amount the plant price is.
     *
     * Returns true if the selection was within bounds and the player object had
     * enough money to purchase.
     *
     * Returns false if the player lacks the money to make the purchase
     *
     * @param player The player making the purchase
     * @param x A players integer selection of a plant within the Plant Array
     * @return Boolean representing wether purchasing a plant was successful or
     * not.
     *
     * @throws MoneyException Represents the player not having enough money to
     * make a purchase.
     */
    public Boolean purchasePlant(Player player, int x) throws MoneyException {
        try {

            //Checks integer is within range. Shouldnt ever reach into exceptions.
            if (x < 0 || x >= getPlants().size()) {
                throw new ArrayIndexOutOfBoundsException();
            }
            //Checks the player has enough money if so purchases the upgrade.
            if (player.getMoney() - getPlants().get(x).getPrice() > 0) {
                player.setMoney(player.getMoney() - getPlants().get(x).getPrice());

                return true;
                //Player cant afford let the player know.
            } else {
                return false;
            }
            //end

        } catch (ArrayIndexOutOfBoundsException a) {

            return false;

        }

    }

    /**
     * Returns a plant from the PlantSelection plantArray of matching index
     *
     * @param x Index to retrieve plant from
     * @return
     */
    public Plant getPlant(int x) throws InstantiationException, IllegalAccessException {
        try {

            //Ensures x is within bounds. Should not throw ever.
            if (x < 0 || x >= getPlants().size()) {
                throw new ArrayIndexOutOfBoundsException();
            }

            //Create new instance of plant
            Plant plant = getPlants().get(x).getClass().newInstance();

            return plant;

            //Catch any inputs out of range and dissplay an error line
        } catch (ArrayIndexOutOfBoundsException a) {

            return null;

        }

    }

    public String getPlantName(int x) throws InstantiationException, IllegalAccessException {
        try {

            //Ensures x is within bounds. Should not throw ever.
            if (x < 0 || x >= getPlants().size()) {
                throw new ArrayIndexOutOfBoundsException();
            }

            //Create new instance of plant
            Plant plant = getPlants().get(x);

            return plant.toString();

            //Catch any inputs out of range and dissplay an error line
        } catch (ArrayIndexOutOfBoundsException a) {
            return new Dirt().toString();
        }

    }

    /**
     * Size of the plantArray
     *
     * @return The size of the plant selection ArrayList
     */
    public int size() {
        return getPlants().size();
    }

    /**
     * @return A string of all plants currently within shop.
     */
    @Override
    public String toFile() {
        String tofile = "";

        for (int i = 0; i < getPlants().size(); i++) {
            tofile += getPlants().get(i).toString() + " ";
        }
        return tofile;
    }

    /**
     * @return the plants
     */
    public HashMap<Integer, Plant> getPlants() {
        return plants;
    }

    /**
     * @param plants the plants to set
     */
    public void setPlants(HashMap<Integer, Plant> plants) {
        this.plants = plants;
    }

    /**
     * Stores the details of the current plantArray in a string and returns it.
     *
     * @return A string representing the state of the PlantSelection
     */
    public String toString() {
        String option = "";

        for (int i = 0; i < getPlants().size(); i++) {
            option = option + (i + 1) + ". " + getPlants().get(i) + " " + getPlants().get(i).getPrice() + "$ ";
        }

        return option;
    }

}
