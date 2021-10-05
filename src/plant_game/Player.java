/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plant_game;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Observable;

/**
 * The player represents the character controlled by the user for the game. It
 * holds all the components needed to play the game such as a field money and
 * energy.
 *
 * A player has attributes of Name Money Energy Day counter Field (A 3 by 3 gird
 * of plants) Score File Manager.
 *
 * @author breco
 */
public final class Player extends Observable {

    //Player name
    private String name;
    //How much money the player has
    private float money;
    //How much energy the player has
    private int energy;
    //tracks the current day
    private int day;
    //Tracks the Players Fields
    private PlantField field;
    //Tracks player score
    private int score;
    //Controlls file handling
    private GameState gameState;

    //Player energy limit
    static final int MAX_ENERGY = 100;

    /**
     * Defualt constructor establishes the player with default starting values
     * and gives them the name Lena.
     */
    public Player() throws MoneyException {
        setName("Lena");
        setMoney(200);
        setEnergy(MAX_ENERGY);
        setDay(0);
        setScore(0);
        setField(new PlantField());
        gameState = new GameState();
    }

    /**
     * Constructs a player with default starting values. Money 100$ Energy 100
     * Day 0 Field: All Dirt GameState: new Files Manager
     *
     * @param name used to create player name
     */
    public Player(String name) throws MoneyException {

        //Player name
        setName(name);
        //Player money
        setMoney(200);
        //Player energy
        setEnergy(MAX_ENERGY);
        //Player starting day
        setDay(0);
        setScore(0);
        //Player starting field
        setField(new PlantField());
        //Player file manager
        gameState = new GameState();
    }

    public Player(String name, float money, int energy, int day, int score) {
        setName(name);
        setMoney(money);
        setEnergy(energy);
        setDay(day);
        setScore(score);
        //Player starting field
        setField(new PlantField());
        //Player file manager
        gameState = new GameState();

    }

    /**
     * Progresses the player to the next day.At the start of a new day all crops
     * day counter is incremented one.Energy is reset to max energy and the
     * score is increased.
     *
     * If rent causes the players money to move into negatives a MoneyException
     * is thrown during the setMoney step to symbolize the game ending.
     *
     * @throws plant_game.MoneyException
     * @throws java.io.FileNotFoundException
     */
    public void nextDay() throws MoneyException, FileNotFoundException, IOException {

        getField().nextDay();
        setDay(getDay() + 1);
        setEnergy(MAX_ENERGY);
        setScore(getDay() * 100 + (int) getMoney());

        //Next day decreases money and chucks out a money exception if player is banktrupt
        if (rentDue() > 0) {
            System.out.println(rentDue() + "$ is due");
            setMoney(getMoney() - rentDue());
        }

        //set change
        setChanged();
        //notify observers for change
        notifyObservers(day);

        System.out.println(money);
        //Save the game after each turn
        gameState.savePlayer(this);

    }

    /**
     * Calculates the amount of money to be deducted from player depending on
     * how many days there have been.
     *
     * Rent will increase every 5 days by amount set out in Rent but will
     * increase extra after the 4th cycle.
     *
     * @return rent A floating number representing the price of a weeks rent.
     */
    private float rentDue() {
        float rent = 0;
        if (getDay() == 5) {
            rent = Rent.ONE.getFee(day);
        } else if (getDay() == 10) {
            rent = Rent.TWO.getFee(day);
        } else if (getDay() == 15) {
            rent = Rent.THREE.getFee(day);
        } else if (getDay() % 5 == 0) {
            rent = Rent.FOUR.getFee(day) + getDay() / 5;

        }

        return rent;
    }

    /**
     * Picks the plant at the x and y co-ordinates
     *
     * To pick a plant the player must have 10 or more energy. If the player has
     * insufficient energy a message is printed to console telling them to sleep
     *
     * If a plant is picked the plants current value is added to the palyers
     * money counter. Once a plant is picked it is replaced by dirt.
     *
     * @param x Represents the row number of the plant
     * @param y Represents the column number of the plant
     */
    public void pickPlant(int x, int y) {

        //Check player has enough energy to pick a plant.
        if ((getEnergy() - 20) >= 0) {
            setEnergy(getEnergy() - 20);
            increaseMoney(getField().pickPlant(x, y));
        } else {
            System.out.println("Out of energy go to sleep.");
        }

    }

    /**
     * Waters the plant at the x-y co-ordinates To water a plant a player must
     * have more than 10 energy
     *
     * If a player has less then 10 energy a message is printed console and the
     * given plant is not watered.
     *
     * @param x Represents the row number of the plant
     * @param y Represents the column number of the plant
     */
    public void waterPlant(int x, int y) {

        if ((getEnergy() - 10) >= 0) {
            setEnergy(getEnergy() - 10);
            getField().water(x, y);
        } else {
            System.out.println("Out of energy go to sleep.");
        }
    }

    /**
     * Places a new plant within the field array as long as player has enough
     * money and energy other wise prompt user with hint to go to sleep.
     *
     * @param plant The plant to be placed within the field
     * @param x The row number
     * @param y The column number
     */
    public void newPlant(Plant plant, int x, int y) {

        //Checks player has enough energy to preform the selected action.
        if ((getEnergy() - 20) >= 0 && (getMoney() - plant.getPrice()) >= 0) {
            //reduces player energy
            setEnergy(getEnergy() - 20);
            setMoney(getMoney() - plant.getPrice());
            //plants a new plant
            getField().newPlant(plant, x, y);
        } else if (getEnergy() - 20 < 0) {
            System.out.println("Not enough energy go to sleep.");
        } else {
            System.out.println("Out of money pick some plants");
        }

    }

    /**
     * @return the money
     */
    public float getMoney() {
        return money;
    }

    /**
     * Sets a users money.
     *
     * @param money integer value
     * @throws MoneyException (Thrown if a player has attempted to setMoney
     * while they have a negative amount of money)
     */
    public void setMoney(float money) throws MoneyException {
        //Throw an exception and end the game
        if (this.money < 0) {
            //so this shouldn't throw if the player is bellow and wants to pick
            System.out.println(money);
            throw new MoneyException();
        } else {
            this.money = money;
        }
    }

    /**
     * Method used by pick money in order to avoid condition where player can't
     * pick crops while in debt.
     *
     * @param money
     */
    private void increaseMoney(float money) {
        this.money += money;
    }

    /**
     * @return the energy
     */
    public int getEnergy() {
        return energy;
    }

    /**
     * @param energy the energy to set
     */
    public void setEnergy(int energy) {
        this.energy = energy;
    }

    /**
     * @return the day
     */
    public int getDay() {
        return day;
    }

    /**
     * @param day the day to set
     */
    public void setDay(int day) {
        this.day = day;
    }

    /**
     * @return the field
     */
    public PlantField getField() {
        return field;
    }

    /**
     * @param field the field to set
     */
    public void setField(PlantField field) {
        this.field = field;
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

    /**
     * @return the score
     */
    public int getScore() {
        return score;
    }

    /**
     * @param score the score to set
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     *
     * @return Player money and energy.
     */
    public String toString() {

        return "Money:" + getMoney() + "$ Energy:" + getEnergy();
    }
}
