/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plant_game;

import static java.lang.Integer.parseInt;
import java.util.StringTokenizer;

/**
 * Truffles are a rare and expensive slow growing plant.
 *
 * Truffle take 10 days to grow and have a pick override replanting themselves.
 *
 * @author breco
 */
public class Truffle extends Plant {

    /**
     * Constructs a Truffle plant with default values
     *
     */
    public Truffle() {
        super();
        setGrowTime(10);
        setValue(0);
        setGrowCounter(2);
        setWaterLimit(1);

    }

    /**
     * File constructor
     *
     * @param details
     */
    public Truffle(String details) {
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
    boolean grow() {
        //Grows every three days?
        this.setWaterCount(0);
        this.setGrowth(this.getGrowth() + 1);
        //Unsure to keep or not
        this.setTimePlanted(0);

        return true;
    }

    /**
     * increments the time planted,increases value, checks evolution
     */
    @Override
    public void nextDay() {

        setTimePlanted(getTimePlanted() + 1);
        //Truffles are expensive
        setValue(getGrowth() * 2 + getValue());
        checkEvolution();
        setPollinated(false);
    }

    /**
     * When pick a truffle resets it's values and returns a copy of itself to be
     * replanted
     *
     * @return Plant this
     */
    @Override
    public Plant pickOverride() {
        setValue(0);
        setWaterCount(0);
        setGrowth(0);
        setTimePlanted(0);
        return this;
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Truffle has an override.
     *
     * @return Boolean true
     */
    @Override
    public boolean hasOverride() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return true;
    }

    @Override
    public String toString() {
        return "truffle";
    }

}
