/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plant_game;

/**
 * Saffron 
 * 
 * A highly saught after plant with a high selling price.
 * 
 * Saffron is a pollinator and does not gain in value each day after advancing growth stages. Instead it has a fixed price
 * that is set after reaching each growth stage. Growth stage 2 = 10$ Growth stage 3 = 20$
 * @author breco
 */
public class Saffron extends Flower {

    /**
     * Constructs a Saffron plant with default values. Tulips are pollinators
     * and will boost the value accumulated of nearby plants each day. Tulips
     * have only one evolution and do not increment in value the same way other
     * plants do.
     *
     */
    public Saffron() {
        super();
        setGrowTime(2);
        setValue(0);
        setGrowCounter(2);
        setWaterLimit(3);

    }

    /**
     * Creates a Saffron flower using details from a string. Used as a
     * constructor for a line read from a file.
     *
     * @param details String containing flower details.
     */
    public Saffron(String details) {
        super();
    }

    /**
     * increments the time planted,increases value, checks evolution
     */
    @Override
    public void nextDay() {

        setTimePlanted(getTimePlanted() + 1);
        //Saffron is expensive
        if (getGrowCounter() == 2) {
            setValue(10);
        }
         if (getGrowCounter() == 3) {
            setValue(20);
        }
        //Checks if the plant is evolving.
        checkEvolution();
    }

    @Override
    public String toString() {
        return "saffron";
    }

}
