/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plant_game;

/**
 *Plant Set contains a set of all plants available in the plant game. 
 * 
 *It is used by other classes to get new plants 
 * @author breco
 */
public enum PlantSet {
    
    BROCCOLI(new Broccoli()),CABBAGE(new Cabbage()),CARROT(new Carrot()),DIRT(new Dirt()),SAFFRON(new Saffron()),TRUFFLE(new Truffle()),TULIP(new Tulip());
   
    private Plant plant;
    
    /**
     * Establishes the plant in the plant set.
     * @param plant 
     */
    PlantSet(Plant plant)
    {
        this.plant=plant;
    }
    
   /**
    * Retrieves plant from the set.
    * @return 
    */
    public Plant getPlant()
    {
        return this.plant;
    }
    
    
    
}
