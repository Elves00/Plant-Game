/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plant_game;

import java.io.IOException;

/**
 * This error represents a file being formatted incorrectly for the plant game.
 *
 * @author breco
 */
public class FileLoadErrorException extends IOException {

    public FileLoadErrorException(String message) {
        super();
    }

    public FileLoadErrorException() {
        super();
    }

}
