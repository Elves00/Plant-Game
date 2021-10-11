/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plant_game;

/**
 * This class is thrown when a player tries to preform an action without
 * sufficient resources
 *
 * @author breco
 */
public class ResourceException extends ArithmeticException {

    public ResourceException(String message) {

        super(message);

    }

    public ResourceException() {
        super();
    }

}
