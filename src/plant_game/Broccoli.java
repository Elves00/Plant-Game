/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plant_game;

import static java.lang.Integer.parseInt;
import java.util.StringTokenizer;

/**
 * Broccoli
 *
 * A green vegetable with a relatively long growth cycle and many evolutions.
 *
 * A broccoli plant includes methods inherited from plant and has no pick
 * overload.
 *
 * @author breco
 */
public class Broccoli extends Plant {

    /**
     * Constructs a broccoli plant with default values Grow time of 3 days Grow
     * counter 6 water limit 1.
     */
    public Broccoli() {
        super();
        setGrowTime(3);
        setValue(0);
        setGrowCounter(6);
        setWaterLimit(1);

        
    }

    /**
     * File constructor
     *
     * @param details
     */
    public Broccoli(String details) {
        super();
    }

    @Override
    /**
     * Broccoli begins gaining value on it's 3rd evolution
     */
    public void nextDay() {

        setTimePlanted(getTimePlanted() + 1);
        if (getGrowCounter() > 3) {
            setValue(getGrowth() + getValue());
        }
        checkEvolution();
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
    public boolean grow() {
        //Grows every three days?
        this.setWaterCount(0);
        this.setGrowth(this.getGrowth() + 1);
        //Unsure to keep or not
        this.setTimePlanted(0);
        return true;
    }

    /**
     * Broccoli does not remain after picking so not implemented.
     *
     * @return UnsupportedOperationException
     */
    @Override
    public Plant pickOverride() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Broccoli does not replant after being picked so returns false.
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
     * @return Plant name "broccoli"
     */
    @Override
    public String toString() {
        return "broccoli";
    }
}
