/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plant_game;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author breco
 */
public class ViewButtonPanel extends JPanel {

    private JButton[] plantingButtons;

    private JButton[] unlockSlot;

    private JPanel loadGamePanel = new JPanel(new BorderLayout());
    private JPanel loadGamePanelArange = new JPanel();
    private JButton[] loadButtons = new JButton[5];
    //Main game buttons plant,save,pick,water,information,main menu etc
    private ViewGameButtonBar gameButtonBar = new ViewGameButtonBar();
    private JPanel plantSelect = new JPanel();
//Panels to display when picking or watering are selected.
    private JPanel pickingPanel = new JPanel();
    private JPanel wateringPanel = new JPanel();
    private JPanel savePanel = new JPanel();
    private JPanel unlockPanel = new JPanel();
    private JPanel infoPanel = new JPanel(new BorderLayout());
    private JPanel highScoreButtonPanel = new JPanel();

    private JButton plantBack = new JButton("Back");
    private JButton waterBack = new JButton("Back");
    private JButton pickBack = new JButton("Back");
    private JButton highScoreBack = new JButton("Back");
    private JButton infoBack = new JButton("Back");
    private JButton saveBack = new JButton("Back");
    private JButton unlockBack = new JButton("Back");

    private JButton[] saveSlot = new JButton[5];

    //String array of database search terms.
    private String[] searchTerm = new String[]{"Information", "plants", "Plant a Plant", "Pick Plant", "Water", "Next day", "Unlock", "Save game"};

    private JButton[] infoSlot = new JButton[8];
    private JPanel infoAreaButtons = new JPanel();

    private CardLayout card = new CardLayout();

    public ViewButtonPanel() {
        super();

        this.setLayout(card);

        //Adds 8 jbuttons to InfoAreaButtons to represent the different information choices.
        for (int i = 0; i < 8; i++) {
            this.infoSlot[i] = new JButton(this.getSearchTerm()[i]);
            this.infoSlot[i].setActionCommand("Info Button");
            this.infoAreaButtons.add(this.infoSlot[i]);
        }

        //Adds a back button to the info area buttons.
        this.infoAreaButtons.add(this.infoBack);

        //Set up the info panel containing the information buttons
        this.infoPanel.add(this.infoAreaButtons, BorderLayout.SOUTH);

        //Set up 5 updateSaveText game buttons to represent each updateSaveText slot.
        for (int i = 0; i < 5; i++) {
            this.saveSlot[i] = new JButton();
            this.savePanel.add(this.saveSlot[i]);
            this.saveSlot[i].setActionCommand("Save Button");
        }
        //Add the back button to updateSaveText display
        this.savePanel.add(this.saveBack);

        //Watering Panel
        this.wateringPanel.add(this.waterBack);
        //Buttons for highscore options
        this.highScoreButtonPanel.add(this.highScoreBack);

        //Picking Panel
        this.pickingPanel.add(this.pickBack);

        //Adds the main options to the button panel.
        add("a", this.gameButtonBar);
        add("b", this.plantSelect);
        add("c", this.wateringPanel);
        add("d", this.pickingPanel);
        add("e", this.savePanel);
        add("f", this.unlockPanel);
        add("g", this.infoPanel);
        add("h", this.highScoreButtonPanel);
    }

    /**
     * Adds actionListener to all buttons within the plant game
     *
     * @param actionListener
     */
    public void addActionListener(ActionListener actionListener) {

        getGameButtonBar().addActionListener(actionListener);
        getPlantBack().addActionListener(actionListener);
        getWaterBack().addActionListener(actionListener);
        getPickBack().addActionListener(actionListener);
        getUnlockBack().addActionListener(actionListener);
        getSaveBack().addActionListener(actionListener);
        getInfoBack().addActionListener(actionListener);

        getHighScoreBack().addActionListener(actionListener);
        //Unlock listeners
        for (JButton unlockSlot1 : getUnlockSlot()) {
            unlockSlot1.addActionListener(actionListener);
        }

        //Action listners for planting options
        for (JButton plantingButton : getPlantingButtons()) {
            plantingButton.addActionListener(actionListener);
        }

        //Action listeners for info panel buttons
        for (int i = 0; i < 8; i++) {
            getInfoSlot()[i].addActionListener(actionListener);
        }

        //Slave slot listiners
        for (int i = 0; i < 5; i++) {
            getSaveSlot()[i].addActionListener(actionListener);
        }
    }

    /**
     * Sets up the shop with blank buttons.
     */
    public void establishPlantButtons() {
        this.plantingButtons = new JButton[PlantSet.values().length + 1];

        //Number of plant buttons plus a plantBack button
        for (int i = 0; i < PlantSet.values().length + 1; i++) {
            this.plantingButtons[i] = new JButton();
            this.getPlantingButtons()[i].setVisible(false);
            this.getPlantSelect().add(getPlantingButtons()[i]);
            this.plantingButtons[i].setActionCommand("Planting Button");
        }

    }

    /**
     * Sets up the unlocks with blank buttons.
     */
    public void establishUnlockButtons() {
        //Unlocks initial length starts as the base set - 3 + 1 as you always start with 3 plants and we need one extra slot for the back button
        this.unlockSlot = new JButton[PlantSet.values().length - 2];
        //Unlock setup
        for (int i = 0; i < PlantSet.values().length - 2; i++) {
            this.unlockSlot[i] = new JButton();
            this.getUnlockSlot()[i].setVisible(false);
            this.unlockSlot[i].setActionCommand("Unlock Button");
        }
    }

    /**
     * Resets the planting buttons and unlock shop buttons.
     *
     * This method is called once a game has ended resetting the planting
     * buttons and unlock buttons back to there defaults. This is to ensure that
     * the next game does not have residual buttons from the previous game.
     *
     * @param data
     */
    public void resetButtons() {

        //remove all buttons from both unlock slot and plant select
        this.plantingButtons = null;
        this.getPlantSelect().removeAll();
        this.unlockSlot = null;
        this.getUnlockPanel().removeAll();

        //Sets up the shop and unlocks with blank buttons.
        this.establishPlantButtons();
        this.establishUnlockButtons();
    }

    /**
     * Adds action listeners to both shop and unlock buttons.This method is
     * called by the controller after the game has ended or shifted to the main
     * menu to re add listeners after the button slots are reset in preparation
     * for the next game.
     *
     * @param actionListener
     */
    public void updateListener(ActionListener actionListener) {

        //Unlock listeners
        for (JButton unlockSlot1 : getUnlockSlot()) {
            unlockSlot1.addActionListener(actionListener);
        }

        //Action listners for planting options
        for (JButton plantingButton : getPlantingButtons()) {
            plantingButton.addActionListener(actionListener);
        }

    }

    /**
     * Updates the unlock shop to display the text stored within an inputted
     * string array.
     *
     * Sets text of each button within unlock slot to match text stored in the
     * unlockText[]. After all text is updated shifts the back button to the
     * last slot.
     *
     * @param unlockSize
     * @param unlockText
     */
    public void updateUnlock(int unlockSize, String[] unlockText) {
        //Make other buttons invisible
        for (int i = unlockSize; i < getUnlockSlot().length; i++) {
            this.getUnlockSlot()[i].setVisible(false);

        }
        //Redo button labels
        for (int i = 0; i < unlockSize; i++) {
            this.getUnlockSlot()[i].setText(unlockText[i]);
            this.getUnlockSlot()[i].setVisible(true);
            this.getUnlockSlot()[i].setActionCommand("Unlock Button");

        }
        //Add back button to end
        this.unlockSlot[unlockSize] = this.getUnlockBack();
        this.getUnlockSlot()[unlockSize].setText("Back");
        this.getUnlockSlot()[unlockSize].setVisible(true);

    }

    /**
     * Updates the buttons displayed in the shop.
     *
     * Adds an item to the shop display by setting the second to last position
     * in the planting buttons array to equal the text of the last item in the
     * input shopText.
     *
     * The back button is then shifted to the end of the array.
     *
     *
     * @param shopSize
     * @param shopText
     */
    public void updateShop(int shopSize, String[] shopText) {

        //Sets a hidden jbutton to have the text of a new plant
        this.plantingButtons[shopSize - 1] = new JButton();
        this.getPlantingButtons()[shopSize - 1].setText(shopText[shopSize - 1]);
        this.getPlantingButtons()[shopSize - 1].setVisible(true);
        this.getPlantingButtons()[shopSize - 1].setActionCommand("Planting Button");

        //Add back button to end of planting buttons
        this.plantingButtons[shopSize] = this.getPlantBack();
        this.getPlantingButtons()[shopSize].setText("Back");
        this.getPlantingButtons()[shopSize].setVisible(true);

        //add the new jbutton and back button  to the plant select panel.
        this.getPlantSelect().add(this.getPlantingButtons()[shopSize - 1]);
        this.getPlantSelect().add(this.getPlantingButtons()[shopSize]);

    }

    /**
     * Updates the text on save buttons.
     *
     * Reads each string from saveText and set buttons 1-5 to contain the text.
     *
     * @param saveText a String[] of length 5 with text for each updateSaveText
     * button.
     */
    public void updateSaveText(String[] saveText) {

        //Save text should always have 5 values.
        if (saveText.length != 5) {
            throw new ArrayIndexOutOfBoundsException();
        }

        //Sets the names of each save button to match the inputted text array.
        for (int i = 0; i < 5; i++) {
            if (!saveText[i].equals("uGaTL@V%yiW3")) {
                this.getSaveSlot()[i].setText(saveText[i]);

            } else {
                this.getSaveSlot()[i].setText("" + (i + 1));
            }
        }

    }

    /**
     * Sets the plant buttons to display the text stored within an inputed text
     * array.
     *
     * @param plantSetSize
     * @param shopSize
     * @param shopText
     */
    public void setShop(int plantSetSize, int shopSize, String[] shopText) {

        for (String shopText1 : shopText) {
            System.out.println(shopText1);
        }
        /*
        Update the shop buttons so that they display all items within the shop.
        Orignal buttons: one two three etc...
        After: broccolli cabage carrot etc...
         */

        //If the planting buttons havn't been established establish it.
        if (this.getPlantingButtons() == null) {
            this.plantingButtons = new JButton[plantSetSize + 1];

            //Number of plant buttons plus a plantBack button
            for (int i = 0; i < plantSetSize + 1; i++) {
                this.plantingButtons[i] = new JButton();
                this.getPlantingButtons()[i].setVisible(false);
                this.getPlantSelect().add(this.getPlantingButtons()[i]);

            }
        }

        for (int i = 0; i < shopSize; i++) {
            //gets the associated button text from the players shop
            this.getPlantingButtons()[i].setText(shopText[i]);
            this.getPlantingButtons()[i].setVisible(true);
        }

        //Add back button after all plant buttons have been added so it is always left most option.
        this.getPlantingButtons()[shopSize] = this.getPlantBack();
        this.getPlantingButtons()[shopSize].setVisible(true);
        this.getPlantSelect().add(this.getPlantingButtons()[shopSize]);

    }

    /**
     * Establishes the unlock panels buttons.
     *
     * Creates an array of unlock buttons containing text matching the unlocks
     * avaliable to the underlying model. For example if the unlocks truffle and
     * tulip are avaliable two unlock slot buttons will be set to visible with
     * matching text. A back button is then added after them which will return
     * players to the main display.
     *
     * @param plantSetSize Size of the games plant set
     * @param unlockSize Size of the shop
     * @param unlockText Text of each shop slot
     */
    public void setUnlockDisplay(int plantSetSize, int unlockSize, String[] unlockText) {

        if (this.getUnlockSlot() == null) {
            //Unlocks initial length starts as the base set - 3 + 1 as you always start with 3 plants and we need one extra slot for the back button
            this.unlockSlot = new JButton[plantSetSize - 2];
            //Unlock setup
            for (int i = 0; i < plantSetSize - 2; i++) {
                this.unlockSlot[i] = new JButton();
                this.getUnlockSlot()[i].setVisible(false);
            }

        }

        //Sets the text of each button inside the unlock slot to match the information passed to it then sets there visibility to true.
        for (int i = 0; i < unlockSize; i++) {
            this.getUnlockSlot()[i].setText(unlockText[i]);
            this.getUnlockSlot()[i].setVisible(true);
            this.getUnlockPanel().add(this.getUnlockSlot()[i]);
        }
        //Places back button at the end of the currently visable unlocks
        this.unlockSlot[unlockSize] = this.getUnlockBack();
        this.getUnlockPanel().add(this.getUnlockSlot()[unlockSize]);

        //Adds the unlock view to the button panel.
        this.add("f", getUnlockPanel());
    }

    /**
     * @return the plantingButtons
     */
    public JButton[] getPlantingButtons() {
        return plantingButtons;
    }

    /**
     * @return the unlockSlot
     */
    public JButton[] getUnlockSlot() {
        return unlockSlot;
    }

    /**
     * @return the plantBack
     */
    public JButton getPlantBack() {
        return plantBack;
    }

    /**
     * @return the waterBack
     */
    public JButton getWaterBack() {
        return waterBack;
    }

    /**
     * @return the pickBack
     */
    public JButton getPickBack() {
        return pickBack;
    }

    /**
     * @return the highScoreBack
     */
    public JButton getHighScoreBack() {
        return highScoreBack;
    }

    /**
     * @return the infoBack
     */
    public JButton getInfoBack() {
        return infoBack;
    }

    /**
     * @return the saveBack
     */
    public JButton getSaveBack() {
        return saveBack;
    }

    /**
     * @return the saveSlot
     */
    public JButton[] getSaveSlot() {
        return saveSlot;
    }

    /**
     * @return the searchTerm
     */
    public String[] getSearchTerm() {
        return searchTerm;
    }

    /**
     * @return the infoSlot
     */
    public JButton[] getInfoSlot() {
        return infoSlot;
    }

    /**
     * @return the infoAreaButtons
     */
    public JPanel getInfoAreaButtons() {
        return infoAreaButtons;
    }

    /**
     * @return the gameButtonBar
     */
    public ViewGameButtonBar getGameButtonBar() {
        return gameButtonBar;
    }

    /**
     * @return the plantSelect
     */
    public JPanel getPlantSelect() {
        return plantSelect;
    }

    /**
     * @return the pickingPanel
     */
    public JPanel getPickingPanel() {
        return pickingPanel;
    }

    /**
     * @return the wateringPanel
     */
    public JPanel getWateringPanel() {
        return wateringPanel;
    }

    /**
     * @return the savePanel
     */
    public JPanel getSavePanel() {
        return savePanel;
    }

    /**
     * @return the unlockPanel
     */
    public JPanel getUnlockPanel() {
        return unlockPanel;
    }

    /**
     * @return the infoPanel
     */
    public JPanel getInfoPanel() {
        return infoPanel;
    }

    /**
     * @return the highScoreButtonPanel
     */
    public JPanel getHighScoreButtonPanel() {
        return highScoreButtonPanel;
    }

    /**
     * @return the card
     */
    public CardLayout getCard() {
        return card;
    }

    /**
     * @return the unlockBack
     */
    public JButton getUnlockBack() {
        return unlockBack;
    }

    /**
     * @return the loadGamePanel
     */
    public JPanel getLoadGamePanel() {
        return loadGamePanel;
    }

    /**
     * @return the loadGamePanelArange
     */
    public JPanel getLoadGamePanelArange() {
        return loadGamePanelArange;
    }

    /**
     * @return the loadButtons
     */
    public JButton[] getLoadButtons() {
        return loadButtons;
    }

}
