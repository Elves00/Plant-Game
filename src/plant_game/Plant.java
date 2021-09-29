/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plant_game;

import static java.lang.Boolean.parseBoolean;
import static java.lang.Integer.parseInt;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

/**
 * Plant abstract class represents all the common methods of plant objects and
 * contains the abstract method grow which is unique to each plant
 *
 * @author breco
 */
abstract class Plant implements ToFile {

    private String name;
    //Sets the amount of time required between each growth stage
    private int growTime;

    //Mointers how long it's been since planting or evolving
    private int timePlanted;

    //Holds the current sell price of the plant
    private int value;

    //tracks current growth phase
    private int growth;

    //Limits how many times a plant can grow.
    private int growCounter;

    //how much water a plant has been given
    private int waterCount;

    //How much water a plant needs before evolving
    private int waterLimit;

    //how much a plant costs
    private int price;

    //If the plant is a pollinator
    private boolean pollinator;
    //If the plant has been pollinated
    private boolean pollinated;

    /**
     * Constructor creates a plant with default values. All value except price
     * start at zero.
     */
    public Plant() {
        setTimePlanted(0);
        setGrowth(0);
        setWaterCount(0);
        setValue(0);
        setPrice(10);
        setPollinator(false);
        setPollinated(false);
        this.name = "";
    }

    /**
     * String constructor allowing for a string to set up all details of a
     * plant.
     *
     * Used for creating a plant from a line read in by a file.
     *
     * @param details String containing plants details such as growtime and
     * price.
     */
    public Plant(String details) {
        StringTokenizer st = new StringTokenizer(details);

        try {
            //Reads string input from file and converts it to relavant statistic.
            setGrowTime(parseInt(st.nextToken()));
            setTimePlanted(parseInt(st.nextToken()));
            setValue(parseInt(st.nextToken()));
            setGrowCounter(parseInt(st.nextToken()));
            setGrowth(parseInt(st.nextToken()));
            setWaterLimit(parseInt(st.nextToken()));
            setWaterCount(parseInt(st.nextToken()));
            setPrice(parseInt(st.nextToken()));
            setPollinator(parseBoolean(st.nextToken()));
            setPollinated(parseBoolean(st.nextToken()));

        } catch (NoSuchElementException a) {
            System.err.println(a);
        }

    }

    /**
     * If a plant is pollinated increase it's value by 1.
     *
     * @param pollinated a Boolean representing wether or not the plant was
     * pollinated
     */
    public void pollinate(boolean pollinated) {
        if (pollinated) {
            setValue(getValue() + 1);
            setPollinated(true);
        }
    }

    /**
     * By default a plant is not a pollinator so returns false on check.
     *
     * @return false
     */
    public boolean pollinator() {
        return false;
    }

    /**
     * Takes details from a plant and stores it in a string.
     *
     * @return A formated string to store information about plant to a file
     */
    public String toFile() {

        String details = "";
        details = details + getGrowTime() + " " + getTimePlanted() + " " + getValue() + " " + getGrowCounter() + " " + getGrowth() + " " + getWaterLimit() + " " + getWaterCount() + " " + getPrice() + " " + isPollinator() + " " + isPollinated();
        return details;
    }

    /**
     * Allows a string to be read from a file.
     *
     * @param detail
     */
    public void fromFile(String detail) {
        StringTokenizer st = new StringTokenizer(detail);
        setGrowTime(parseInt(st.nextToken()));
        setTimePlanted(parseInt(st.nextToken()));
        setValue(parseInt(st.nextToken()));
        setGrowCounter(parseInt(st.nextToken()));
        setGrowth(parseInt(st.nextToken()));
        setWaterLimit(parseInt(st.nextToken()));
        setWaterCount(parseInt(st.nextToken()));
        setPrice(parseInt(st.nextToken()));
        setPollinator(parseBoolean(st.nextToken()));
        //MAy need to add pollinated here
    }

    /**
     * Progresses a plant to it's next stage of growth.
     */
    abstract boolean grow();

    /**
     * Standard nextDay action increment time planted and update plant value.
     * Implemented when the nextDay button is pressed. Increments the time
     * planted value by 1 Also increases the value of the crop by growth.
     */
    public void nextDay() {

        setTimePlanted(getTimePlanted() + 1);
        setValue(getGrowth() + getValue());
        checkEvolution();
        setPollinated(false);
    }

    /**
     * Standard Water Action Increases water by one.
     */
    public void water() {
        setWaterCount(getWaterCount() + 1);
    }

    /**
     * A plant evolves to the next phase if it has meet the water limit and
     * growCounter
     */
    public void checkEvolution() {
        if (getWaterCount() >= getWaterLimit() && getGrowth() < getGrowCounter() && getTimePlanted() >= getGrowTime()) {
            grow();
            System.out.println(this.toString() + " is growing");
        }
    }

    /**
     * Returns the value of the plant.
     *
     * @return
     */
    public int pick() {
        return getValue();
    }

    /**
     * Pick override returns the plant that should fill the spot after the plant
     * is picked
     *
     * @return
     */
    abstract public Plant pickOverride();

    /**
     * Lets other classes know if there is a override method
     *
     * @return
     */
    abstract public boolean hasOverride();

    /**
     * @return the growTime
     */
    public int getGrowTime() {
        return growTime;
    }

    /**
     * @param growTime the growTime to set
     */
    protected void setGrowTime(int growTime) {
        this.growTime = growTime;
    }

    /**
     * @return the timePlanted
     */
    public int getTimePlanted() {
        return timePlanted;
    }

    /**
     * @param timePlanted the timePlanted to set
     */
    public void setTimePlanted(int timePlanted) {
        this.timePlanted = timePlanted;
    }

    /**
     * @return the value
     */
    public int getValue() {
        return value;
    }

    /**
     * Sets the value of the plant
     *
     * @param value the value to set
     */
    protected void setValue(int value) {
        this.value = value;
    }

    /**
     * @return the growCounter
     */
    public int getGrowCounter() {
        return growCounter;
    }

    /**
     * @param growCounter the growCounter to set
     */
    protected void setGrowCounter(int growCounter) {
        this.growCounter = growCounter;
    }

    /**
     * @return the growth
     */
    public int getGrowth() {
        return growth;
    }

    /**
     * @param growth the growth to set
     */
    protected void setGrowth(int growth) {
        this.growth = growth;
    }

    /**
     * @return the waterLimit
     */
    public int getWaterLimit() {
        return waterLimit;
    }

    /**
     * @param waterLimit the waterLimit to set
     */
    protected void setWaterLimit(int waterLimit) {
        this.waterLimit = waterLimit;
    }

    /**
     * @return the waterCount
     */
    public int getWaterCount() {
        return waterCount;
    }

    /**
     * @param waterCount the waterCount to set
     */
    protected void setWaterCount(int waterCount) {
        this.waterCount = waterCount;
    }

    /**
     * @return the price
     */
    public int getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    protected void setPrice(int price) {
        this.price = price;
    }

    /**
     * @return the pollinator
     */
    public boolean isPollinator() {
        return pollinator;
    }

    /**
     * @param pollinator the pollinator to set
     */
    public void setPollinator(boolean pollinator) {
        this.pollinator = pollinator;
    }

    /**
     * @return the pollinated
     */
    public boolean isPollinated() {
        return pollinated;
    }

    /**
     * @param pollinated the pollinated to set
     */
    public void setPollinated(boolean pollinated) {
        this.pollinated = pollinated;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

}
