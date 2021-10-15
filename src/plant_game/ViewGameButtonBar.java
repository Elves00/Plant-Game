/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plant_game;

import java.awt.event.ActionListener;
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

    private JButton plant = new JButton("Plant");
    private JButton water = new JButton("Water");
    private JButton pick = new JButton("Pick");
    private JButton nextDay = new JButton("Next Day");
    private JButton information = new JButton("Information");
    private JButton save = new JButton("Save");
    private JButton unlockShop = new JButton("Shop");
    private JButton mainMenu = new JButton("Main Menu");
    private JButton highScoresButton = new JButton("Highscores");

    public ViewGameButtonBar() {

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
