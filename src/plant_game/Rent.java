/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plant_game;

/**
 * An enumerated type containg information on the rental cycel of the game.
 * @author breco
 */
public enum Rent {
    
    ONE(10),TWO(15),THREE(20),FOUR(25);
    private final float fee;
    private Rent(float fee)
    {
        this.fee=fee;
    }
    /**
     * 
     * @param day An integer representing the rent for this week.
     * @return 
     */
    public float getFee(int day)
    {
      
            return this.fee;
    }
}
