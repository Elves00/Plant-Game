/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plant_game;

import static java.lang.Integer.parseInt;
import java.util.StringTokenizer;

/**
 * Carrot
 *
 * Represents a orange vegetable called a carrot. Carrot is a fast growing plant
 * requiring two waters. It does not remain after picking
 *
 * @author breco
 */
public class Carrot extends Plant {

    
    /**
     * Constructs a broccoli plant with default values
     */
    public Carrot() {
        super();
        setGrowTime(2);
        setValue(0);
        setGrowCounter(3);
        setWaterLimit(2);

    }

    /**
     * File constructor
     *
     * @param details
     */
    public Carrot(String details) {
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
        //Grows every three days?
        this.setWaterCount(0);
        this.setGrowth(this.getGrowth() + 1);

        this.setTimePlanted(0);
        return true;
    }

    /**
     * Carrot does not remain after picking so not implemented.
     *
     * @return UnsupportedOperationException
     */
    @Override
    public Plant pickOverride() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Carrot does not replant after being picked so returns false.
     *
     * @return false
     */
    @Override
    public boolean hasOverride() {
        return false;
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     * @return String "carrot"
     */
    @Override
    public String toString() {
        return "carrot";
    }

}
