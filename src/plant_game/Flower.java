/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plant_game;

/**
 * A flower is a type of plant which is a pollinator so has pollinator return
 * true.
 *
 * @author breco
 */
public class Flower extends Plant {

    /**
     * Constructs a Flower with default values
     */
    public Flower() {
        super();
        setPollinator(true);
    }

    /**
     * Constructs a flower from a string. This is a File constructor
     * specifically set up to construct a plant from a line read from a file.
     *
     *
     * @param details String containing Flower details.
     */
    public Flower(String details) {
        super();
    }

    @Override
    /**
     * Flowers don't gain the benefit of pollination.
     *
     * @param pollinated a Boolean representing wether or not the plant was
     * pollinated
     */
    public void pollinate(boolean pollinated) {

    }

    @Override
    /**
     * Flowers are pollinators so pollinator returns true
     *
     * @return true
     */
    public boolean pollinator() {
        return true;
    }

    @Override
    /**
     * Standard nextDay action increment time planted and update plant value.
     * Implemented when the nextDay button is pressed. Increments the
     * time planted value by 1 Also increases the value of the crop by growth.
     */
    public void nextDay() {

        setTimePlanted(getTimePlanted() + 1);
        setValue(getGrowth() + getValue());
        checkEvolution();
        //Maybe unessacery 
        setPollinated(false);
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
    boolean grow() {
        //Grows every three days?
        this.setWaterCount(0);
        this.setGrowth(this.getGrowth() + 1);
        this.setTimePlanted(0);
        return true;
    }

    /**
     * By default if a flower uses a pick override it still replants with dirt.
     *
     * @return A Dirt object
     */
    @Override
    public Plant pickOverride() {
        return new Dirt();
    }

    /**
     * Flowers do not have a pick override and are replaced as normal.
     *
     * @return
     */
    @Override
    public boolean hasOverride() {
        return false;
    }

}
