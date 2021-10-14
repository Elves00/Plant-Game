/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plant_game;

/**
 * Cabbage
 *
 * A leafy vegetable with a 4 day growth cycle needing moderate amounts of
 * water.
 *
 * @author breco
 */
public class Cabbage extends Plant {

    /**
     * Constructs a broccoli plant with default values
     */
    public Cabbage() {
        super();
        setGrowTime(4);
        setValue(0);
        setGrowCounter(4);
        setWaterLimit(2);
        setName("cabbage");

    }

    /**
     * File constructor
     *
     * @param details
     */
    public Cabbage(String details) {
        super();
    }

    /**
     * Moves a plant to it's next grow stage.
     *
     * Resets water count to zero and increments the growth counter Resets time
     * planted.
     *
     * @return True
     */
    @Override
    public boolean grow() {
        //Set water count to zero
        this.setWaterCount(0);
        //Increment growth
        this.setGrowth(this.getGrowth() + 1);
        //Reset time planted.
        this.setTimePlanted(0);
        //return success.
        return true;
    }

    /**
     * Cabbage does not remain after picking so not implemented.
     *
     * @return UnsupportedOperationException
     */
    @Override
    public Plant pickOverride() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Cabbage does not replant after being picked so returns false.
     *
     * @return false
     */
    @Override
    public boolean hasOverride() {
        return false;
    }

    /**
     *
     * @return plant name "cabbage"
     */
    @Override
    public String toString() {
        return "cabbage";
    }
}
