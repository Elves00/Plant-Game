/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plant_game;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

/**
 *
 * @author breco
 */
public class View extends JPanel implements Observer {

    /**
     * @return the loadButtons
     */
    public JButton[] getLoadButtons() {
        return loadButtons;
    }

    private Model plantGameModel;
    private JPanel fieldCard;
    private JPanel field = new JPanel(new GridLayout(3, 3));
    private JLabel[][] fieldLabels = new JLabel[3][3];

    //Bar that holds the main game buttons
    private JPanel buttonPanel;

    private JPanel plantSelect = new JPanel();

    private JLabel warning = new JLabel("", SwingConstants.CENTER);
    private JPanel bottomPanel = new JPanel(new BorderLayout());

    private JButton[] plantingButtons;

    private JPanel gameOptions = new JPanel();

    private JPanel startView = new JPanel();
    ;
    private JPanel mainView = new JPanel();

    private CardLayout mainCard = new CardLayout();
    private BorderLayout border = new BorderLayout();

    private CardLayout card = new CardLayout();
    private CardLayout cardField = new CardLayout();

    //Highlight for water full and pollinating.
    //Create borders to highlight when plants are watered or pollinated
    private Border blueLine = BorderFactory.createLineBorder(Color.blue);
    private Border yellowLine = BorderFactory.createLineBorder(Color.yellow);
    private Border mixedLine = BorderFactory.createCompoundBorder(blueLine, yellowLine);

    private JPanel pickingPanel = new JPanel();
    private JPanel wateringPanel = new JPanel();

    private JLabel playerHeader = new JLabel("", SwingConstants.CENTER);

    //Save 
    private JButton[] saveSlot = new JButton[5];
    private JPanel savePanel = new JPanel();

    private JButton[] unlockSlot;

    private JPanel infoPanel = new JPanel(new BorderLayout());
    private JButton[] infoSlot = new JButton[8];
    private JPanel infoAreaButtons = new JPanel();
    private JTextArea infoArea = new JTextArea();
    private JPanel infoAreaPanel = new JPanel(new BorderLayout());
    private JScrollPane infoScroller;

    private String[] searchTerm = new String[]{"Information", "plants", "Plant a Plant", "Pick Plant", "Water", "Next day", "Unlock", "Save game"};

    //PLANT GAME START
    private JPanel optionsPanel = new JPanel(new BorderLayout());
    private JPanel loadGamePanelArange = new JPanel();
    private JPanel loadGamePanel = new JPanel(new BorderLayout());
    private JPanel newGamePanel = new JPanel();

    private JPanel startupPanel = new JPanel();
    private JButton newGame = new JButton("New Game");
    private JButton previousGame = new JButton("Previous Game");
    private JButton loadGame = new JButton("Load Game");

    private JLabel loadInfo = new JLabel("The Plant Game", SwingConstants.CENTER);

    private JButton[] loadButtons = new JButton[5];

    private JTextField username = new JTextField(20);
    private JButton submit = new JButton("Submit");

    //High scores end game
    private JList<Score> highScores = new JList();
    private JPanel highScorePanel = new JPanel(new BorderLayout());
    private JButton advance = new JButton("Continue");
    private JScrollPane highScoreScroll;
    private JPanel highScoreButtonPanel = new JPanel();

    private JPanel unlockPanel = new JPanel();

    //Back buttons which return player to the main plant game view.
    private JButton unlockBack = new JButton("Back");
    private JButton plantBack = new JButton("Back");
    private JButton waterBack = new JButton("Back");
    private JButton pickBack = new JButton("Back");
    private JButton highScoreBack = new JButton("Back");
    private JButton infoBack = new JButton("Back");
    private JButton saveBack = new JButton("Back");

    //The main game buttons displayed on the bottom of main view panel.
    private JButton plant = new JButton("Plant");
    private JButton water = new JButton("Water");
    private JButton pick = new JButton("Pick");
    private JButton nextDay = new JButton("Next Day");
    private JButton information = new JButton("Information");
    private JButton save = new JButton("Save");
    private JButton unlockShop = new JButton("Shop");
    private JButton mainMenu = new JButton("Main Menu");
    private JButton highScoresButton = new JButton("Highscores");

    // Declaration of objects of CardLayout class.
    private CardLayout cards;

    //So to follow MVC we need to be passed the information we need to know from update
    public View() {

        //Sets the panel layou to a card layout
        this.setLayout(this.mainCard);

        //Adds the start view to card position a
        this.add("a", this.startView);

        //Adds the main view to card position b
        this.add("b", this.mainView);

        //Sets the main view to have a border layout.
        this.mainView.setLayout(this.border);

        //Adds 8 jbuttons to InfoAreaButtons to represent the different information choices.
        for (int i = 0; i < 8; i++) {
            this.infoSlot[i] = new JButton(this.searchTerm[i]);
            this.infoAreaButtons.add(this.infoSlot[i]);
        }

        //Adds a back button to the info area buttons.
        this.infoAreaButtons.add(this.infoBack);
        //Dissalows player access to the info area ie cant type or edit.
        this.infoArea.setEditable(false);
        //Set up info scroller
        this.infoScroller = new JScrollPane(this.infoArea);
        //Display the info scroller in the centre of the main view.
        this.infoAreaPanel.add(this.infoScroller, BorderLayout.CENTER);
        //Set up the info panel containing the information buttons
        this.infoPanel.add(this.infoAreaButtons, BorderLayout.SOUTH);

        //Set up 5 updateSaveText game buttons to represent each updateSaveText slot.
        for (int i = 0; i < 5; i++) {
            this.saveSlot[i] = new JButton();
            this.savePanel.add(this.saveSlot[i]);
        }
        //Add the back button to updateSaveText display
        this.savePanel.add(this.saveBack);
        this.buttonPanel = new JPanel(getCard());

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

        //Set up the unlock buttons
        this.establishUnlockButtons();

        //Create scroll bar for highscore jlist.
        this.highScoreScroll = new JScrollPane(this.highScores);
        //High score display
        this.highScorePanel.add(this.highScoreScroll, BorderLayout.CENTER);
        this.highScorePanel.add(this.advance, BorderLayout.SOUTH);

        //Sets up the plant shop buttons
        this.establishPlantButtons();

        //Watering Panel
        this.wateringPanel.add(this.waterBack);

        //Picking Panel
        this.pickingPanel.add(this.pickBack);

        this.buttonPanel.add("a", this.gameOptions);
        this.buttonPanel.add("b", this.plantSelect);
        this.buttonPanel.add("c", this.wateringPanel);
        this.buttonPanel.add("d", this.pickingPanel);
        this.buttonPanel.add("e", this.savePanel);
        this.buttonPanel.add("f", this.unlockPanel);
        this.buttonPanel.add("g", this.infoPanel);

        this.highScoreButtonPanel.add(this.highScoreBack);
        /*SO THIS IS KINDA WORKING*/
        this.buttonPanel.add("h", this.highScoreButtonPanel);

        this.bottomPanel.add(this.warning, BorderLayout.NORTH);
        this.bottomPanel.add(this.buttonPanel, BorderLayout.SOUTH);
        this.mainView.add(this.bottomPanel, BorderLayout.SOUTH);

        this.mainView.add(this.playerHeader, BorderLayout.NORTH);

        this.fieldCard = new JPanel(getCardField());

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.fieldLabels[i][j] = new JLabel("", SwingConstants.CENTER);
                this.field.add(this.fieldLabels[i][j]);
            }
        }

        this.fieldCard.add("a", this.field);
        this.fieldCard.add("b", this.infoAreaPanel);
        this.fieldCard.add("c", this.highScorePanel);
        //Adds the mainView to the panel
        this.mainView.add(this.fieldCard, BorderLayout.CENTER);

        this.add("c", this.highScorePanel);
        //Adds details to the start panel
        plantGameStart();

    }

    /**
     * Sets up the shop with blank buttons.
     */
    public void establishPlantButtons() {
        this.plantingButtons = new JButton[PlantSet.values().length + 1];

        //Number of plant buttons plus a plantBack button
        for (int i = 0; i < PlantSet.values().length + 1; i++) {
            this.plantingButtons[i] = new JButton();
            this.plantingButtons[i].setVisible(false);
            this.plantSelect.add(this.plantingButtons[i]);
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
            this.unlockSlot[i].setVisible(false);
        }
    }

    public void plantGameStart() {

        setCards(new CardLayout());
        getStartView().setLayout(getCards());

        this.newGamePanel.add(this.getUsername());

        this.newGamePanel.add(this.getSubmit());

        Font fancy = new Font("verdana", Font.BOLD | Font.ITALIC, 28);

        this.loadInfo.setFont(fancy);

        //Panel for options buttons
        this.optionsPanel.add(loadInfo, BorderLayout.CENTER);
        this.startupPanel.add(getNewGame());
        this.startupPanel.add(getPreviousGame());
        this.startupPanel.add(getLoadGame());
        this.optionsPanel.add(startupPanel, BorderLayout.SOUTH);

        //Set up 5 load game buttons 
        for (int i = 0; i < 5; i++) {
            this.loadButtons[i] = new JButton("" + i);
        }

        for (JButton load : getLoadButtons()) {
            this.loadGamePanelArange.add(load);
        }

        loadGamePanel.add(loadGamePanelArange, BorderLayout.CENTER);

        this.getStartView().add("a", this.optionsPanel);
        this.getStartView().add("b", this.loadGamePanel);
        this.getStartView().add("c", this.newGamePanel);

    }

    public void addActionListener(ActionListener actionListener) {
        //Main plant game button lisiteners
        getPlant().addActionListener(actionListener);
        getWater().addActionListener(actionListener);
        getPick().addActionListener(actionListener);
        getInformation().addActionListener(actionListener);
        getNextDay().addActionListener(actionListener);
        getPlantBack().addActionListener(actionListener);
        getWaterBack().addActionListener(actionListener);
        getPickBack().addActionListener(actionListener);
        getUnlockBack().addActionListener(actionListener);
        getSave().addActionListener(actionListener);
        getSaveBack().addActionListener(actionListener);
        getUnlockShop().addActionListener(actionListener);
        getInfoBack().addActionListener(actionListener);
        getHighScoresButton().addActionListener(actionListener);
        getMainMenu().addActionListener(actionListener);
        getHighScoreBack().addActionListener(actionListener);

        //Unlock listeners
        for (JButton unlockSlot1 : getUnlockSlot()) {
            unlockSlot1.addActionListener(actionListener);
        }

        //Slave slot listiners
        for (int i = 0; i < 5; i++) {
            getSaveSlot()[i].addActionListener(actionListener);
        }

        //Action listners for planting options
        for (JButton plantingButton : getPlantingButtons()) {
            plantingButton.addActionListener(actionListener);
        }

        //Action listeners for info panel buttons
        for (int i = 0; i < 8; i++) {
            getInfoSlot()[i].addActionListener(actionListener);
        }

        //Start plant game button lisitners
        this.submit.addActionListener(actionListener);

        this.newGame.addActionListener(actionListener);
        this.previousGame.addActionListener(actionListener);
        this.loadGame.addActionListener(actionListener);

        this.getAdvance().addActionListener(actionListener);

        for (JButton loadButton : getLoadButtons()) {
            loadButton.addActionListener(actionListener);
        }

    }

    @Override
    public void addMouseListener(MouseListener mouseListener) {
        //Listeners for the field panels
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                getFieldLabels()[i][j].addMouseListener(mouseListener);
            }
        }
    }

    /**
     * Set text for loading
     *
     * @param saves
     */
    public void setLoadText(String[] saves) {

        for (int i = 0; i < 5; i++) {
            this.loadButtons[i].setText(saves[0]);
        }

        this.getCards().show(this.getStartView(), "b");

    }

    /**
     * Updates the player header to display player status
     *
     * @param playerHeader
     */
    public void updatePlayer(String playerHeader) {
        this.playerHeader.setText(playerHeader);
    }

    public void updateField(String[][] plants, Boolean[][] water, Boolean[][] pollin) {

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.getFieldLabels()[i][j].setText(plants[i][j]);
                if (water[i][j] && pollin[i][j]) {
                    this.getFieldLabels()[i][j].setBorder(mixedLine);
                } else if (water[i][j]) {
                    this.getFieldLabels()[i][j].setBorder(blueLine);
                } else if (pollin[i][j]) {

                    this.getFieldLabels()[i][j].setBorder(yellowLine);
                } else {
                    this.getFieldLabels()[i][j].setBorder(null);
                }
            }
        }
    }

    /**
     * Establishes the unlock panels buttons.
     *
     *
     * @param plantSetSize Size of the games plant set
     * @param unlockSize Size of the shop
     * @param unlockText Text of each shop slot
     */
    private void setUnlockDisplay(int plantSetSize, int unlockSize, String[] unlockText) {

        if (this.unlockSlot == null) {
            //Unlocks initial length starts as the base set - 3 + 1 as you always start with 3 plants and we need one extra slot for the back button
            this.unlockSlot = new JButton[plantSetSize - 2];
            //Unlock setup
            for (int i = 0; i < plantSetSize - 2; i++) {
                this.unlockSlot[i] = new JButton();
                this.unlockSlot[i].setVisible(false);
            }

        }

        //Unlock setup
        for (int i = 0; i < unlockSize; i++) {
            this.unlockSlot[i].setText(unlockText[i]);
            this.unlockSlot[i].setVisible(true);
            this.unlockPanel.add(this.getUnlockSlot()[i]);
        }
        //Places back button at the end
        this.unlockSlot[unlockSize] = this.unlockBack;
        this.unlockPanel.add(this.getUnlockSlot()[unlockSize]);

        this.buttonPanel.add("f", unlockPanel);
    }

    /**
     * Updates the unlock shop to display the text stored within the inputed
     * string array.
     *
     * Sets text of each button within unlock slot to match text stored in the
     * unlockText[]. After all text is updated shifts the back button to the
     * last slot.
     *
     * @param unlockSize
     * @param unlockText
     */
    private void updateUnlock(int unlockSize, String[] unlockText) {
        //Make other buttons invisible
        for (int i = unlockSize; i < unlockSlot.length; i++) {
            this.unlockSlot[i].setVisible(false);

        }
        //Redo button labels
        for (int i = 0; i < unlockSize; i++) {
            this.unlockSlot[i].setText(unlockText[i]);
            this.unlockSlot[i].setVisible(true);

        }
        //Add back button to end
        this.unlockSlot[unlockSize] = this.unlockBack;
        this.unlockSlot[unlockSize].setText("Back");
        this.unlockSlot[unlockSize].setVisible(true);

    }

    /**
     * Updates information displayed on the info area panel.
     *
     * @param infoArray
     */
    private void updateInformationDisplay(String[] infoArray) {
        String toDisplay = "";
        for (String infoArray1 : infoArray) {
            toDisplay += infoArray1 + "\n";
        }
        this.infoArea.setText(toDisplay);

    }

    private void updateShop(int shopSize, String[] shopText) {

        //Sets a hidden jbutton to have the text of a new plant
        this.plantingButtons[shopSize - 1] = new JButton();
        this.plantingButtons[shopSize - 1].setText(shopText[shopSize - 1]);
        this.plantingButtons[shopSize - 1].setVisible(true);

        //Add back button to end of planting buttons
        this.plantingButtons[shopSize] = this.plantBack;
        this.plantingButtons[shopSize].setText("Back");
        this.plantingButtons[shopSize].setVisible(true);

        //add the new jbutton and back button  to the plant select panel.
        this.plantSelect.add(this.plantingButtons[shopSize - 1]);
        this.plantSelect.add(this.plantingButtons[shopSize]);

    }

    private void setShop(int plantSetSize, int shopSize, String[] shopText) {
        System.out.println("Shop starting with:" + shopSize);
        System.out.println("SHOP TEXT");
        for (String shopText1 : shopText) {
            System.out.println(shopText1);
        }
        /*
        Update the shop buttons so that they display all items within the shop.
        Orignal buttons: one two three etc...
        After: broccolli cabage carrot etc...
         */

        //If the planting buttons havn't been established establish it.
        if (this.plantingButtons == null) {
            this.plantingButtons = new JButton[plantSetSize + 1];

            //Number of plant buttons plus a plantBack button
            for (int i = 0; i < plantSetSize + 1; i++) {
                this.plantingButtons[i] = new JButton();
                this.plantingButtons[i].setVisible(false);
                this.plantSelect.add(this.plantingButtons[i]);
            }
        }

        for (int i = 0; i < shopSize; i++) {
            //gets the associated button text from the players shop
            this.getPlantingButtons()[i].setText(shopText[i]);
            this.getPlantingButtons()[i].setVisible(true);
        }

        //Add back button after all plant buttons have been added so it is always left most option.
        this.getPlantingButtons()[shopSize] = this.plantBack;
        this.getPlantingButtons()[shopSize].setVisible(true);
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

        if (saveText.length != 5) {

            throw new ArrayIndexOutOfBoundsException();

        }

        for (int i = 0; i < 5; i++) {
            System.out.println("Save text: " + saveText[i]);
            this.getSaveSlot()[i].setText(saveText[i]);
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
        this.plantSelect.removeAll();
        this.unlockSlot = null;
        this.unlockPanel.removeAll();

        //Sets up the shop and unlocks with blank buttons.
        this.establishPlantButtons();
        this.establishUnlockButtons();
    }

    /**
     * Adds action listeners to both shop and unlock buttons.This method is
     * called by the controller after the game has ended to re add listeners
     * after the button slots are reset in preparation for the next game.
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
     * Updates the score display using data from the data class.Sets up a list
     * of scores composed of scores and names stored within the data class.Once
     * the list is created it is used to create a new Jlist with a scroller.
     *
     *
     * @param data
     */
    public void updateScore(String[] names, int[] scores) {

        //Remove the previous high score scorll from the view.
        this.highScorePanel.remove(highScoreScroll);

        OrderedList<Score> highscores = new OrderedList();

        //High scores only cares about 20 values.
        for (int i = 0; i < 20; i++) {
            //if there are defualt values stored in both arrays don't add.
            if (!(names[i] == null && scores[i] == 0)) {
                highscores.add(new Score(names[i], scores[i]));
            }

        }

        //Create a new jlist using the orderlist
        this.highScores = new JList<>(highscores.toArray());

        highScoreScroll = new JScrollPane(highScores);

        //add the jlist to the panel.
        this.highScorePanel.add(highScoreScroll, BorderLayout.CENTER);

    }

    /**
     * Updates the text stored in the warning message label
     *
     * @param warning text to update.
     */
    public void updateWarningMessage(String warning) {
        this.warning.setText(warning);
    }

    /**
     * Updates the class caused by changes in the observed model
     *
     * @param o
     * @param arg
     */
    @Override
    public void update(Observable o, Object arg) {

        Data data = (Data) arg;

        //If the game has ended update score and reset the button texts
        if (data.isEndGame()) {
            System.out.println("WE REACHED HERE");
            //score update
            updateScore(data.getNames(), data.getScores());
            resetButtons();
            this.mainCard.show(this, "c");
        }

        //if the game is starting
        if (data.isStart() == true) {
            //Show starting panel
            this.getCards().show(this.getStartView(), "a");

            //show new game panel
            if (data.isNewGame() == true) {
                this.getCards().show(this.getStartView(), "c");
            } //Display the load game options
            else if (data.isLoadGame() == true) {
                setLoadText(data.getLoadText());

            }

        }
        if (data.isMainMenu()) {
            resetButtons();
        }
        //The main game
        if (data.isMainGame() == true) {
            this.mainCard.show(this, "b");
            setShop(data.getPlantsetSize(), data.getShopSize(), data.getShopText());
        }

        if (data.isFieldUpdate() == true) {
            updateField(data.getViewPlants(), data.getWaterPlants(), data.getPollinatePlants());
            updatePlayer(data.getPlayer());
        }
        if (data.isShopStart()) {
            this.setShop(data.getPlantsetSize(), data.getShopSize(), data.getShopText());
        }
        if (data.isUnlockStart()) {
            this.setUnlockDisplay(data.getPlantsetSize(), data.getUnlockSize(), data.getUnlockText());
        }

        if (data.isShopUpdate()) {
            this.updateShop(data.getShopSize(), data.getShopText());
            updatePlayer(data.getPlayer());
        }
        if (data.isUnlockUpdate()) {
            this.updateUnlock(data.getUnlockSize(), data.getUnlockText());
            updatePlayer(data.getPlayer());
        }
        if (data.isSaveStart()) {

            updateSaveText(data.getSaveText());
        }

        if (data.isInfoUpdate()) {
            this.updateInformationDisplay(data.getInfoText());
        }

        if (data.isWarningCheck()) {
            this.updateWarningMessage(data.getWarning());
        } else {
            this.updateWarningMessage("");
        }

        if (data.isCheckScores()) {
            updateScore(data.getNames(), data.getScores());
        }
    }

    /**
     * @return the plantGameModel
     */
    public Model getPlantGameModel() {
        return plantGameModel;
    }

    /**
     * @return the field
     */
    public JPanel getField() {
        return field;
    }

    /**
     * @return the fieldLabels
     */
    public JLabel[][] getFieldLabels() {
        return fieldLabels;
    }

    /**
     * @return the buttonPanel
     */
    public JPanel getButtonPanel() {
        return buttonPanel;
    }

    /**
     * @return the plantSelect
     */
    public JPanel getPlantSelect() {
        return plantSelect;
    }

    /**
     * @return the plantingButtons
     */
    public JButton[] getPlantingButtons() {
        return plantingButtons;
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
     * @return the updateSaveText
     */
    public JButton getSave() {
        return save;
    }

    /**
     * @return the gameOptions
     */
    public JPanel getGameOptions() {
        return gameOptions;
    }

    /**
     * @return the card
     */
    public CardLayout getCard() {
        return card;
    }

    /**
     * @return the plantBack
     */
    public JButton getPlantBack() {
        return plantBack;
    }

    /**
     * @param plantBack the plantBack to set
     */
    public void setPlantBack(JButton plantBack) {
        this.plantBack = plantBack;
    }

    /**
     * @return the wateringPanel
     */
    public JPanel getWateringPanel() {
        return wateringPanel;
    }

    /**
     * @return the pickingPanel
     */
    public JPanel getPickingPanel() {
        return pickingPanel;
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
     * @return the saveSlot
     */
    public JButton[] getSaveSlot() {
        return saveSlot;
    }

    /**
     * @return the saveBack
     */
    public JButton getSaveBack() {
        return saveBack;
    }

    /**
     * @param plantGameModel the plantGameModel to set
     */
    public void setPlantGameModel(Model plantGameModel) {
        this.plantGameModel = plantGameModel;
    }

    /**
     * @return the unlockBack
     */
    public JButton getUnlockBack() {
        return unlockBack;
    }

    /**
     * @return the unlockSlot
     */
    public JButton[] getUnlockSlot() {
        return unlockSlot;
    }

    /**
     * @return the infoSlot
     */
    public JButton[] getInfoSlot() {
        return infoSlot;
    }

    /**
     * @return the infoBack
     */
    public JButton getInfoBack() {
        return infoBack;
    }

    /**
     * @return the fieldCard
     */
    public JPanel getFieldCard() {
        return fieldCard;
    }

    /**
     * @return the cardField
     */
    public CardLayout getCardField() {
        return cardField;
    }

    /**
     * @return the mainCard
     */
    public CardLayout getMainCard() {
        return mainCard;
    }

    /**
     * @return the newGame
     */
    public JButton getNewGame() {
        return newGame;
    }

    /**
     * @return the previousGame
     */
    public JButton getPreviousGame() {
        return previousGame;
    }

    /**
     * @return the loadGame
     */
    public JButton getLoadGame() {
        return loadGame;
    }

    /**
     * @return the submit
     */
    public JButton getSubmit() {
        return submit;
    }

    /**
     * @return the username
     */
    public JTextField getUsername() {
        return username;
    }

    /**
     * @return the mainView
     */
    public JPanel getMainView() {
        return mainView;
    }

    /**
     * @return the startView
     */
    public JPanel getStartView() {
        return startView;
    }

    /**
     * @param startView the startView to set
     */
    public void setStartView(JPanel startView) {
        this.startView = startView;
    }

    /**
     * @return the cards
     */
    public CardLayout getCards() {
        return cards;
    }

    /**
     * @param cards the cards to set
     */
    public void setCards(CardLayout cards) {
        this.cards = cards;
    }

    /**
     * @return the unlockShop
     */
    public JButton getUnlockShop() {
        return unlockShop;
    }

    /**
     * @param unlockShop the unlockShop to set
     */
    public void setUnlockShop(JButton unlockShop) {
        this.unlockShop = unlockShop;
    }

    /**
     * @return the advance
     */
    public JButton getAdvance() {
        return advance;
    }

    /**
     * @param advance the advance to set
     */
    public void setAdvance(JButton advance) {
        this.advance = advance;
    }

    /**
     * @return the highScoreBack
     */
    public JButton getHighScoreBack() {
        return highScoreBack;
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
