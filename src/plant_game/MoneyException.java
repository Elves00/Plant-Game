/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plant_game;

/**
 * Throw this exception when a player is out of money and tries to make an
 * invalid purchase.
 *
 * @author breco
 */
public class MoneyException extends ArithmeticException {

    public MoneyException(String message) {

        super(message);

    }

    public MoneyException() {
        super();
    }

}
