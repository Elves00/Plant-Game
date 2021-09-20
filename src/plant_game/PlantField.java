/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plant_game;

import java.util.ArrayList;

/**
 * Plant Array stores a 3 by 3 grid containing plants and a static int
 * representing field size.
 *
 * Plant_Array maintains a 3 by 3 array with many methods available to alter the
 * contents of the array.
 *
 * Methods: getWaterState getValueState getGrowthState getDayState all return
 * string [][] containing information about the field state and the plants
 * within it/.
 *
 * setAllPlants:Sets all the plants within the field to match the plants defined
 * in the string. setAllPlantsStatus: Takes a string and updates all plants
 * details within the array to match
 *
 * newPlant: replaces a plant within the plantArray getPlant: returns a
 * specified plant within the plantArray nextDay: calls nextDay on all plants
 * within plantArray
 *
 * getPlant: returns a plant in the array based on row and column input.
 *
 * nextDay: Calls next day on all plants within the plant array.
 *
 * checkPolination: Cycles through the field looking for plants who pollinate
 * and calls the pollinate method for there row and column
 *
 * polinateNeighbours: Calls the pollinated method on all neighbours of the
 * inputted row and column water: waters a specified plant pickPlant: Implements
 * the pickPlant method on a specified plant within plantArray
 *
 * pick: Replaces a plant within the field array with dirt or a plant depending
 * on what plant was previously occupying the spot.When picked returns the
 * plants value.
 *
 * toFile: Prints the contents of the field to a string array
 *
 * toString:Prints the names of all plants within the field in a 3 by 3 grid.
 *
 * @author breco
 */
public final class PlantField {

    private Plant[][] plantArray;
    static final int arrayLength = 3;

    /**
     * Creates the planting field which is a 3 by 3 grid which plants can be
     * planted into
     */
    public PlantField() {
        plantArray = new Plant[arrayLength][arrayLength];
        //Creates a plant array of size 3 by 3 filled with dirt.
        for (int i = 0; i < arrayLength; i++) {
            for (int j = 0; j < arrayLength; j++) {
                getPlantArray()[i][j] = new Dirt();

            }
        }
    }

    /**
     * Cycles through the array and calculates how much each plant has been
     * watered.
     *
     * Saves the details to a string and returns at the ned.
     *
     * @return water A string array containing how much each plant has been
     * watered.
     */
    public String[][] getWaterState() {
        String[][] water = new String[3][3];
        String w;
        //Cycles the array
        for (int i = 0; i < arrayLength; i++) {
            for (int j = 0; j < arrayLength; j++) {
                //Gets how much a plant has been watered and how much it needs to be watered to evolve and saves to string.
                w = getPlantArray()[i][j].getWaterCount() + "/" + getPlantArray()[i][j].getWaterLimit();
                water[i][j] = w;
            }
        }
        return water;
    }

    /**
     * Cycles through the plant array creating a new string array of the same
     * size containing the value of each plant.
     *
     * @return value a string array containing the value of each plant within
     * the plant array.
     */
    public String[][] getValueState() {
        String[][] value = new String[3][3];
        String w = "";
        for (int i = 0; i < arrayLength; i++) {
            for (int j = 0; j < arrayLength; j++) {
                //Saves how much money each plant is worth and saves it in the appropriate location.
                w = "$" + getPlantArray()[i][j].getValue();
                value[i][j] = w;
            }
        }
        return value;
    }

    /**
     * Cycles through the plant array and returns a string array representing
     * how much each plant has grown.
     *
     * @return growth A string array displaying how much each plant has grown
     * and how many times it can grow,
     */
    public String[][] getGrowthState() {
        String[][] growth = new String[3][3];
        String w = "";
        for (int i = 0; i < arrayLength; i++) {
            for (int j = 0; j < arrayLength; j++) {
                //Finds out how much each plant has grown and how many more evolutions are possible
                w = getPlantArray()[i][j].getGrowth() + "/" + getPlantArray()[i][j].getGrowCounter();
                growth[i][j] = w;
            }
        }
        return growth;
    }

    /**
     * Cycles through the plant array creating a string array representing how
     * long each plant has been planted and how many days it needs to be planted
     * to evolve.
     *
     * @return days a string array representing how many days are needed before
     * a plant evolves.
     */
    public String[][] getDayState() {
        String[][] days = new String[3][3];
        String w = "";
        for (int i = 0; i < arrayLength; i++) {
            for (int j = 0; j < arrayLength; j++) {
                //PLACEHOLDER
                w = getPlantArray()[i][j].getTimePlanted() + "/" + getPlantArray()[i][j].getGrowTime();
                days[i][j] = w;
            }
        }
        return days;

    }

    /**
     * Sets all plants within the plant array to match plants found within the
     * inputted ArrayList.
     *
     * @param plants An ArrayList containing 9 plants
     */
    public void setAllPlants(ArrayList<Plant> plants) {
        int count = 9;
        for (int i = 0; i < arrayLength; i++) {
            for (int j = 0; j < arrayLength; j++) {
                getPlantArray()[i][j] = plants.get(arrayLength * arrayLength - count);
                count--;
            }
        }
    }

    /**
     * Sets all details of plants within the plant array to match details found
     * within the inputted ArrayList.
     *
     * @param details An ArrayList containing 9 unique strings representing
     * plant stats
     */
    public void setAllPlantStatus(ArrayList<String> details) {
        for (int i = 0; i < arrayLength; i++) {
            for (int j = 0; j < arrayLength; j++) {
                getPlantArray()[i][j].fromFile(details.get(i * 3 + j));
            }
        }
    }

    /**
     * Plants a new plant
     *
     * @param plant
     */
    public void newPlant(Plant plant, int x, int y) {
        getPlantArray()[x][y] = plant;
    }

    /**
     * Retrieves a specific plant from the array
     *
     * @param x
     * @param y
     * @return
     */
    public Plant getPlant(int x, int y) {
        return getPlantArray()[x][y];
    }

    /**
     * triggers the nextDay on all plants within the array.
     */
    public void nextDay() {
        for (int i = 0; i < arrayLength; i++) {
            for (int j = 0; j < arrayLength; j++) {
                getPlantArray()[i][j].nextDay();
            }
        }
        //checks who get's pollinated.
        checkPolination();
    }

    /**
     * Checks the array to if a plant has pollinated the field
     */
    public void checkPolination() {
        for (int i = 0; i < arrayLength; i++) {
            for (int j = 0; j < arrayLength; j++) {
                if (getPlantArray()[i][j].isPollinator()) {

                    polinateNeighbours(getNeighbours(i, j));
                }
            }
        }
    }

    /**
     * Pollinates plants above bellow left and right of a given plant
     *
     * @param neighbours
     */
    public void polinateNeighbours(int[] neighbours) {
        for (int i = 0; i < 2; i++) {
            try {

                getPlantArray()[neighbours[i]][neighbours[5]].pollinate(true);
                getPlantArray()[neighbours[4]][neighbours[i + 2]].pollinate(true);
            } //Avoids neigbours calling poisitons out of array bounds.
            catch (ArrayIndexOutOfBoundsException a) {

            }
        }
    }

    /**
     * Returns an int array of all neighbours of a given point within a 3 by 3
     * grid.
     *
     * @param x
     * @param y
     * @return int[] with x-1,x+1,y-1,y+1,x,y
     */
    public int[] getNeighbours(int x, int y) {
        //Array to store neighbours
        int[] neighbours = new int[6];
        //One up one down one left one right orignal positions.
        neighbours[0] = x - 1;
        neighbours[1] = x + 1;
        neighbours[2] = y - 1;
        neighbours[3] = y + 1;
        neighbours[4] = x;
        neighbours[5] = y;

        return neighbours;
    }

    /**
     * Waters a plants at specified grid co-ordinates
     */
    public void water(int x, int y) {
        try {
            if (x > arrayLength || y > arrayLength || x < 0 || y < 0) {
                //Check the access attempt is within the gird
                throw new ArrayIndexOutOfBoundsException();
            }

            getPlantArray()[x][y].water();

        } catch (ArrayIndexOutOfBoundsException a) {
            System.err.println("PLACEHOLDER WATER");
        }

    }

    /**
     * Picks a plant and adds it's value to the players money total. Also
     * replaces plant with dirt in array.
     *
     * @return The amount of money stored in picked plant
     *
     */
    public int pickPlant(int x, int y) {

        if (getPlant(x, y) instanceof Dirt) {
            System.out.println("Dirt can't be picked");
            return 0;
        } else {

            int money;
            money = this.getPlantArray()[x][y].pick();

            if (this.getPlantArray()[x][y].hasOverride()) {
                this.newPlant(this.getPlantArray()[x][y].pickOverride(), x, y);

            } else {

                this.newPlant(new Dirt(), x, y);

            }
            return money;
        }

    }

    /**
     * Returns a string array containing all the details of the plants currently
     * stored in the plant array
     *
     * @return String[]
     */
    public String[] toFile() {
        String[] croops = new String[6];
        String fieldContent = "";
        for (int i = 0; i < arrayLength; i++) {
            for (int j = 0; j < arrayLength; j++) {
                fieldContent = fieldContent + getPlantArray()[i][j].toFile() + " ";
            }
            croops[i] = fieldContent;

            fieldContent = "";
        }

        for (int i = 0; i < arrayLength; i++) {

            for (int j = 0; j < arrayLength; j++) {
                fieldContent = fieldContent + " " + getPlantArray()[i][j];
            }
            croops[i + 3] = fieldContent;
            fieldContent = "";
        }

        return croops;
    }

    /**
     * @return the plantArray
     */
    public Plant[][] getPlantArray() {
        return plantArray;
    }

    /**
     * @param plantArray the plantArray to set
     */
    public void setPlantArray(Plant[][] plantArray) {
        this.plantArray = plantArray;
    }

    /**
     * Prints out each plant held within the field array in a 3 by 3 grid.
     *
     * @return The name of each crop in the array
     */
    @Override
    public String toString() {
        String fieldContent = "";

        //Cycels through each array spot and adds the information to the string.
        for (int i = 0; i < arrayLength; i++) {
            fieldContent += "|";
            for (int j = 0; j < arrayLength; j++) {
                fieldContent = fieldContent + " " + getPlantArray()[i][j];
                if (getPlantArray()[i][j].toString().length() < 9) {
                    for (int k = getPlantArray()[i][j].toString().length() - 1; k < 9; k++) {
                        fieldContent += " ";
                    }
                }
            }
            fieldContent += "|";
            if (i < 2) {
                fieldContent += "\n";
            }

        }

        return fieldContent;
    }
}
