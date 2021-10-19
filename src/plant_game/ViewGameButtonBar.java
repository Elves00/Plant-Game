
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plant_game;

import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author breco
 */
public class ViewGameButtonBar extends JPanel {

    //The main game buttons displayed on the game options panel
    //Holds the main game options such as plant water and pick buttons.
    private JPanel gameOptions = new JPanel();

    private JButton plant;
    private JButton water;
    private JButton pick;
    private JButton nextDay;
    private JButton information;
    private JButton save;
    private JButton unlockShop;
    private JButton mainMenu = new JButton("Main Menu");
    private JButton highScoresButton;
    private ButtonImages buttonImages = new ButtonImages();

    public ViewGameButtonBar() {

        plant = new JButton("Plant", new ImageIcon(buttonImages.getPlant()));
        water = new JButton("Water", new ImageIcon(buttonImages.getWater()));
        pick = new JButton("Pick", new ImageIcon(buttonImages.getPick()));
        nextDay = new JButton("Next Day", new ImageIcon(buttonImages.getNextDay()));
        information = new JButton("Information", new ImageIcon(buttonImages.getInfo()));
        unlockShop = new JButton("Unlock", new ImageIcon(buttonImages.getShop()));
        save = new JButton("Save", new ImageIcon(buttonImages.getSave()));
        highScoresButton = new JButton("Highscores", new ImageIcon(buttonImages.getScore()));
        //Adds the different game buttons to the main game bar.
        this.gameOptions.add(this.plant);
        this.gameOptions.add(this.water);
        this.gameOptions.add(this.pick);
        this.gameOptions.add(this.nextDay);
        this.gameOptions.add(this.save);
        this.gameOptions.add(this.information);
        this.gameOptions.add(this.unlockShop);
        this.gameOptions.add(this.highScoresButton);
        this.gameOptions.add(this.mainMenu);

        this.add(gameOptions);
    }

    /**
     * Adds actionListener to all buttons within the plant game
     *
     * @param actionListener
     */
    public void addActionListener(ActionListener actionListener) {

        //Main plant game button lisiteners
        getPlant().addActionListener(actionListener);
        getWater().addActionListener(actionListener);
        getPick().addActionListener(actionListener);
        getInformation().addActionListener(actionListener);
        getNextDay().addActionListener(actionListener);
        getSave().addActionListener(actionListener);
        getUnlockShop().addActionListener(actionListener);
        getHighScoresButton().addActionListener(actionListener);
        getMainMenu().addActionListener(actionListener);

    }

    /**
     * @return the gameOptions
     */
    public JPanel getGameOptions() {
        return gameOptions;
    }

    /**
     * @return the plant
     */
    public JButton getPlant() {
        return plant;
    }

    /**
     * @return the water
     */
    public JButton getWater() {
        return water;
    }

    /**
     * @return the pick
     */
    public JButton getPick() {
        return pick;
    }

    /**
     * @return the nextDay
     */
    public JButton getNextDay() {
        return nextDay;
    }

    /**
     * @return the information
     */
    public JButton getInformation() {
        return information;
    }

    /**
     * @return the save
     */
    public JButton getSave() {
        return save;
    }

    /**
     * @return the unlockShop
     */
    public JButton getUnlockShop() {
        return unlockShop;
    }

    /**
     * @return the mainMenu
     */
    public JButton getMainMenu() {
        return mainMenu;
    }

    /**
     * @return the highScoresButton
     */
    public JButton getHighScoresButton() {
        return highScoresButton;
    }
}
