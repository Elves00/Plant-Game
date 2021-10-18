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
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

/**
 * The view contains all the display components of the GUI.
 *
 * This view is primarily concerend with displaying the information of the plant
 * game.
 *
 *
 * @author breco
 */
public class View extends JPanel implements Observer {

    //Model view is observing
    private Model plantGameModel;

    private JLabel warning = new JLabel("", SwingConstants.CENTER);
    private JLabel startWarning = new JLabel("", SwingConstants.CENTER);
    private JPanel bottomPanel = new JPanel(new BorderLayout());

    //Highlight for water full and pollinating.
    //Create borders to highlight when plants are watered or pollinated
    private Border blueLine = BorderFactory.createLineBorder(Color.blue);
    private Border yellowLine = BorderFactory.createLineBorder(Color.yellow);
    private Border mixedLine = BorderFactory.createCompoundBorder(blueLine, yellowLine);

    //Main view componets field and player header.
    private JPanel mainView = new JPanel();
    private JLabel playerHeader = new JLabel("", SwingConstants.CENTER);
    private ViewField field = new ViewField();

    //Displays different views in the area occupied by the field.
    private JPanel fieldCard;

    //Holds and updates information displayed by the information button
    private ViewInformationPanelArea infoAreaPanel = new ViewInformationPanelArea();

//    private JPanel optionsPanel = new JPanel(new BorderLayout());
    //New game option
    private ViewNewGame ViewNewGame = new ViewNewGame();

    private ViewOptions viewOptions = new ViewOptions();

    //Panel which holds the three game loading options
    private JPanel startView = new JPanel();

    //Label which holds the game title.
    private JLabel gameTitle = new JLabel("The Plant Game", SwingConstants.CENTER);
//    private JLabel enterUsername = new JLabel("Enter your username", SwingConstants.CENTER);
    private JLabel selectLoad = new JLabel("Select game to load", SwingConstants.CENTER);

    //Load game option
    private JPanel loadGamePanel = new JPanel(new BorderLayout());
    private JPanel loadGamePanelArange = new JPanel();
    private JButton[] loadButtons = new JButton[5];

    private ViewHighScorePanel highScorePanel = new ViewHighScorePanel();
    private ViewHighScorePanel endHighScorePanel = new ViewHighScorePanel(new JButton("Continue"));

    private JButton loadGameBack = new JButton("Back");
    // Controlls the main games buttons panels 
    private ViewButtonPanel buttonPanel = new ViewButtonPanel();

    //Card layouts used by the various JPanels.
    private CardLayout cards;
    private CardLayout mainCard = new CardLayout();
    private CardLayout card = new CardLayout();
    private CardLayout cardField = new CardLayout();

    public View() {

        //Sets the panel layou to a card layout
        this.setLayout(this.mainCard);

        //Adds the start view to card position a
        this.add("a", this.startView);

        //Adds the main view to card position b
        this.add("b", this.mainView);

        //Sets the main view to have a border layout.
        this.mainView.setLayout(new BorderLayout());

        this.establishUnlockButtons();

        //Sets up the plant shop buttons
        this.establishPlantButtons();

        this.bottomPanel.add(this.warning, BorderLayout.NORTH);
        this.bottomPanel.add(this.buttonPanel, BorderLayout.SOUTH);

        this.mainView.add(this.bottomPanel, BorderLayout.SOUTH);

        this.mainView.add(this.playerHeader, BorderLayout.NORTH);

        this.fieldCard = new JPanel(getCardField());

        this.fieldCard.add("a", this.field);
        this.fieldCard.add("b", this.infoAreaPanel);
        this.fieldCard.add("c", this.highScorePanel);
        //Adds the mainView to the panel
        this.mainView.add(this.fieldCard, BorderLayout.CENTER);

        this.add("c", this.endHighScorePanel);
        //Adds details to the start panel
        plantGameStart();

    }

    /**
     * Sets up the shop with blank buttons.
     */
    public void establishPlantButtons() {
        //establishes the  shop in the button panel
        this.getButtonPanel().establishPlantButtons();

    }

    /**
     * Sets up the unlocks with blank buttons.
     */
    public void establishUnlockButtons() {
        //establishes the unlock shop in the button panel
        this.getButtonPanel().establishUnlockButtons();
    }

    public void plantGameStart() {

        setCards(new CardLayout());
        getStartView().setLayout(getCards());

        //Sets up a fancy title.
        Font fancy = new Font("verdana", Font.BOLD | Font.ITALIC, 28);
        //Set components in the start panel to match font.
        this.gameTitle.setFont(fancy);

        this.selectLoad.setFont(fancy);

        //Add a warning to the new game screen to display bad username input
        this.ViewNewGame.add(this.startWarning, BorderLayout.NORTH);

        //Panel for options buttons
//        this.optionsPanel.add(gameTitle, BorderLayout.CENTER);
//        this.startupPanel.add(getNewGame());
//        this.startupPanel.add(getPreviousGame());
//        this.startupPanel.add(getLoadGame());
//        this.optionsPanel.add(startupPanel, BorderLayout.SOUTH);
        //Set up 5 load game buttons 
        for (int i = 0; i < 5; i++) {
            this.loadButtons[i] = new JButton("" + i);
        }
        //Adds them to a panel arranging them ina  line.
        for (JButton load : getLoadButtons()) {
            this.loadGamePanelArange.add(load);
        }

        this.loadGamePanelArange.add(this.loadGameBack);
        //Adds the panel to the centre display of the load game panely.
        loadGamePanel.add(loadGamePanelArange, BorderLayout.SOUTH);
        loadGamePanel.add(this.selectLoad, BorderLayout.CENTER);

        this.getStartView().add("a", this.viewOptions);
        this.getStartView().add("b", this.loadGamePanel);
        this.getStartView().add("c", this.ViewNewGame);

    }

    /**
     * Adds actionListener to all buttons within the plant game
     *
     * @param actionListener
     */
    public void addActionListener(ActionListener actionListener) {
        //Main plant game button lisiteners

        getButtonPanel().addActionListener(actionListener);
        getLoadGameBack().addActionListener(actionListener);

        //Action listeners for laod game buttons.
        for (JButton loadButton : getLoadButtons()) {
            loadButton.addActionListener(actionListener);
        }

        //Button lisitners for options related to the first plant game view displayed on game launch
//        this.submit.addActionListener(actionListener);
        this.ViewNewGame.addActionListener(actionListener);
        this.viewOptions.addActionListener(actionListener);

//        this.highScorePanel.addActionListener(actionListener);
        this.endHighScorePanel.addActionListener(actionListener);

    }

    /**
     * Adds mouseListener to all field labels.
     */
    public void addMouseListener(MouseListener mouseListener) {
        field.addMouseListener(mouseListener);
    }

    /**
     * Updates the player header to display player status
     *
     * @param playerHeader
     */
    public void updatePlayer(String playerHeader) {
        this.playerHeader.setText(playerHeader);
    }

    /**
     * Updates the JLabels displaying field information.
     *
     * Updates the displayed field to correctly display the contents of the
     * models field and sets a border to indicate if a plant is watered and if a
     * plant is pollinated.
     *
     * @param plants
     * @param water
     * @param pollin
     */
    public void updateField(String[][] plants, Boolean[][] water, Boolean[][] pollin) {

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                //Updates field labels to display the correct plant
                this.getFieldLabels()[i][j].setText(plants[i][j]);
                //If a plant is watered and pollinated display a mixed line
                if (water[i][j] && pollin[i][j]) {
                    this.getFieldLabels()[i][j].setBorder(mixedLine);
                } else if (water[i][j]) {
                    this.getFieldLabels()[i][j].setBorder(blueLine);
                } else if (pollin[i][j]) {

                    this.getFieldLabels()[i][j].setBorder(yellowLine);
                } else {
                    //A plant has no border if no condition is met.
                    this.getFieldLabels()[i][j].setBorder(null);
                }
            }
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
    private void updateUnlock(int unlockSize, String[] unlockText) {

        //Update button panels unlockshop 
        this.getButtonPanel().updateUnlock(unlockSize, unlockText);

    }

    /**
     * Updates information displayed on the info area panel.
     *
     * @param infoArray
     */
    private void updateInformationDisplay(String[] infoArray) {
        infoAreaPanel.updateInformationDisplay(infoArray);

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
    private void updateShop(int shopSize, String[] shopText) {

        //Update button panels shop  
        this.getButtonPanel().updateShop(shopSize, shopText);

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
        //resets the listeners on button panels plant and unlcok shop 
        this.getButtonPanel().resetButtons();
    }

    /**
     * Set text of load game buttons based on string input.
     *
     * Sets each button name then switches the start view to display them.
     *
     * @param saves String[] containing name for all buttons.
     */
    public void setLoadText(String[] saves) {
        for (int i = 0; i < 5; i++) {
            this.loadButtons[i].setText(saves[i]);
        }
        this.getCards().show(this.getStartView(), "b");
    }

    /**
     * Sets the visibility of the load game buttons based on a boolean array.
     *
     * @param visible
     */
    public void setLoadGameVisibility(boolean[] visible) {
        for (int i = 0; i < 5; i++) {
            this.loadButtons[i].setVisible(visible[i]);
        }
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
    private void setUnlockDisplay(int plantSetSize, int unlockSize, String[] unlockText) {
        //set button panels unlock display 
        this.getButtonPanel().setUnlockDisplay(plantSetSize, unlockSize, unlockText);
    }

    /**
     * Sets the plant buttons to display the text stored within an inputed text
     * array.
     *
     * @param plantSetSize
     * @param shopSize
     * @param shopText
     */
    private void setShop(int plantSetSize, int shopSize, String[] shopText) {
        //Set button panels shop
        this.getButtonPanel().setShop(plantSetSize, shopSize, shopText);

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
        //set button panels save text display 
        this.getButtonPanel().updateSaveText(saveText);

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
        //set button panels update listiener display 
        this.getButtonPanel().updateListener(actionListener);

    }

    /**
     * Updates the score panel
     *
     *
     * @param names array of player names
     * @param scores array of player scores
     */
    public void updateScore(String[] names, int[] scores) {

        this.highScorePanel.updateScore(names, scores);
        this.endHighScorePanel.updateScore(names, scores);
    }

    /**
     * Updates the text stored in the warning message label
     *
     * @param warning text to update.
     */
    public void updateWarningMessage(String warning) {
        this.getWarning().setText(warning);
        this.startWarning.setText(warning);
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
            //score update
            updateScore(data.getNames(), data.getScores());
            resetButtons();
            this.mainCard.show(this, "c");

        }
        if (data.isLoadGameChanged()) {
            setLoadGameVisibility(data.getLoadGameVisible());
        }

        if (!data.isPreviousGame()) {
            this.viewOptions.getPreviousGame().setVisible(data.isPreviousGame());
        } else {
            this.viewOptions.getPreviousGame().setVisible(data.isPreviousGame());
        }

        //Checks if the game is in the start panel with options to load game, new game and previous game
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
        return this.field.getFieldLabels();
    }

    /**
     * @param plantGameModel the plantGameModel to set
     */
    public void setPlantGameModel(Model plantGameModel) {
        this.plantGameModel = plantGameModel;
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
        return this.viewOptions.getNewGame();
    }

    /**
     * @return the previousGame
     */
    public JButton getPreviousGame() {
        return this.viewOptions.getPreviousGame();
    }

    /**
     * @return the loadGame
     */
    public JButton getLoadGame() {
        return this.viewOptions.getLoadGame();
    }

    /**
     * @return the submit
     */
    public JButton getSubmit() {
        return this.ViewNewGame.getSubmit();
    }

    /**
     * @return the username
     */
    public JTextField getUsername() {
        return this.ViewNewGame.getUsername();
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
     * @return the advance
     */
    public JButton getAdvance() {
        return this.endHighScorePanel.getAdvance();
    }

    /**
     * @return the loadButtons
     */
    public JButton[] getLoadButtons() {
        return loadButtons;

    }

    /**
     * @return the warning
     */
    public JLabel getWarning() {
        return warning;
    }

    /**
     * @return the loadGameBack
     */
    public JButton getLoadGameBack() {
        return loadGameBack;
    }

    /**
     * @param loadGameBack the loadGameBack to set
     */
    public void setLoadGameBack(JButton loadGameBack) {
        this.loadGameBack = loadGameBack;
    }

    /**
     * @return the card
     */
    public CardLayout getCard() {
        return this.buttonPanel.getCard();
    }

    /**
     * @return the buttonPanel
     */
    public ViewButtonPanel getButtonPanel() {
        return buttonPanel;
    }
}
