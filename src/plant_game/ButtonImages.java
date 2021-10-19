/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plant_game;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * Loads images from file.
 *
 * Images used for the game button bar.
 *
 * @author breco
 */
public class ButtonImages {

    //Icons for buttons
    private BufferedImage plant;
    private BufferedImage water;
    private BufferedImage pick;
    private BufferedImage nextDay;
    private BufferedImage info;
    private BufferedImage shop;
    private BufferedImage save;
    private BufferedImage score;

    //Loads all buttons from a the image file.
    public ButtonImages() {
        try {
            plant = ImageIO.read(new File("./plant.png"));
            water = ImageIO.read(new File("./water.png"));
            pick = ImageIO.read(new File("./pick.png"));
            nextDay = ImageIO.read(new File("./nextDay.png"));
            info = ImageIO.read(new File("./info.png"));
            shop = ImageIO.read(new File("./shop.png"));
            save = ImageIO.read(new File("./save.png"));
            score = ImageIO.read(new File("./score.png"));
        } catch (IOException ex) {
            Logger.getLogger(ButtonImages.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * @return the nextDay
     */
    public BufferedImage getNextDay() {
        return nextDay;
    }

    /**
     * @param nextDay the nextDay to set
     */
    public void setNextDay(BufferedImage nextDay) {
        this.nextDay = nextDay;
    }

    /**
     * @return the plant
     */
    public BufferedImage getPlant() {
        return plant;
    }

    /**
     * @param plant the plant to set
     */
    public void setPlant(BufferedImage plant) {
        this.plant = plant;
    }

    /**
     * @return the water
     */
    public BufferedImage getWater() {
        return water;
    }

    /**
     * @param water the water to set
     */
    public void setWater(BufferedImage water) {
        this.water = water;
    }

    /**
     * @return the pick
     */
    public BufferedImage getPick() {
        return pick;
    }

    /**
     * @param pick the pick to set
     */
    public void setPick(BufferedImage pick) {
        this.pick = pick;
    }

    /**
     * @return the info
     */
    public BufferedImage getInfo() {
        return info;
    }

    /**
     * @param info the info to set
     */
    public void setInfo(BufferedImage info) {
        this.info = info;
    }

    /**
     * @return the shop
     */
    public BufferedImage getShop() {
        return shop;
    }

    /**
     * @param shop the shop to set
     */
    public void setShop(BufferedImage shop) {
        this.shop = shop;
    }

    /**
     * @return the save
     */
    public BufferedImage getSave() {
        return save;
    }

    /**
     * @param save the save to set
     */
    public void setSave(BufferedImage save) {
        this.save = save;
    }

    /**
     * @return the score
     */
    public BufferedImage getScore() {
        return score;
    }

    /**
     * @param score the score to set
     */
    public void setScore(BufferedImage score) {
        this.score = score;
    }

}
