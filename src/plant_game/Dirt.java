/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plant_game;

/**
 * Dirt is a plant with no value and is used to represent a blank space in the
 * garden. Dirt will never grow regardless of how much water is poored or days
 * past.
 *
 * Dirt fills all 9 squares of the initial player gird
 *
 * @author breco
 */
public class Dirt extends Plant {

    /**
     * Defualt dirt constructor has zero set for all attributes.
     */
    public Dirt() {
        super();
        setGrowTime(0);
        setValue(0);
        setGrowCounter(0);
        setWaterLimit(10);
        setName("dirt");
    }

    /**
     * Sets up a dirt object from a string with stored data.
     *
     * @param details
     */
    public Dirt(String details) {
        super();
    }

    @Override
    /**
     * Dirt does not grow nor increase in value during next day cycle and will
     * retain all current stats.
     */
    public void nextDay() {
        //Dirt does not interact with next day.
    }

    /**
     * Dirt cannot grow
     *
     * @return UnsupportedOperationException
     */
    @Override
    public boolean grow() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Does not check if there is an evolution when called.
     */
    @Override
    public boolean checkEvolution() {
        return false;
    }

    /**
     * Dirt does not gain the benefit of being pollinated so the pollinate
     * method is overridden to be blank.
     *
     * @param pollinated
     */
    @Override
    public void pollinate(boolean pollinated) {
    }

    /**
     * Dirt does not remain after picking so not implemented.
     *
     * @return UnsupportedOperationException
     */
    @Override
    public Plant pickOverride() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Dirt does not replant after being picked so returns false.
     *
     * @return false
     */
    @Override
    public boolean hasOverride() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return false;
    }

    /**
     *
     * @return String "dirt"
     */
    @Override
    public String toString() {
        return "dirt";
    }

}
