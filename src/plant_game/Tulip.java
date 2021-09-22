/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plant_game;

/**
 *
 * @author breco
 */
public class Tulip extends Flower {

    /**
     * Constructs a Tulip plant with default values. Tulips are pollinators and
     * will boost the value accumulated of nearby plants each day. Tulips have
     * only one evolution and do not increment in value the same way other
     * plants do.
     *
     */
    public Tulip() {
        super();
        setGrowTime(1);
        setValue(0);
        setGrowCounter(1);
        setWaterLimit(3);
        setPollinator(false);

    }

    /**
     * increments the time planted,increases value, checks evolution
     */
    @Override
    public void nextDay() {
        //NEED TO IMPLEMENT ANOTHER TIME SINCE PLANTING 
        setTimePlanted(getTimePlanted() + 1);
        //Truffles are expensive
        if (getGrowCounter() == 1) {
            setValue(3);
            //Begins pollinating others
            setPollinator(true);
        }
        //Checks if the plant is evolving.
        checkEvolution();
        setPollinated(false);
    }

    /**
     * Creates a tulip using details from a string. Used as a constructor for a
     * line read from a file.
     *
     * @param details String containing flower details.
     */
    public Tulip(String details) {
        super();
    }

    @Override
    public String toString() {
        return "tulip";
    }

}
