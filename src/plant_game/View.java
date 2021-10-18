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
//    private Model plantGameModel;
    //The first panel to be displayed. Displays information for when game is first loaded.
    private JPanel startView = new JPanel();

    /* The 3 panels of the start view display information for when the game is first loaded or isn't
    in the main running phase.
    
    New game is displayed when a player selects to start a new game.
    Options is displayed when a player has not selected to load/go to previous or start a new game
    Load game is displayed when a player selects to load a game  */
    private ViewNewGame ViewNewGame = new ViewNewGame();
    private ViewOptions viewOptions = new ViewOptions();
    private ViewLoadGame viewLoadGame = new ViewLoadGame();

    //Labels to display warning messages when a bad input is made
    private JLabel warning = new JLabel("", SwingConstants.CENTER);
    private JLabel startWarning = new JLabel("", SwingConstants.CENTER);

    //High score panel to be displayed when a player selects the highscore button
    private ViewHighScorePanel highScoreField = new ViewHighScorePanel();
    //High score panel to be displayed when a game ends and is forced to view highscores
    private ViewHighScorePanel endHighScore = new ViewHighScorePanel(new JButton("Continue"));

    //Main view to be displayed once a game has been selected
    private JPanel mainView = new JPanel();
    //Displays a players stats such as energy and money
    private JLabel mainPlayerHeader = new JLabel("", SwingConstants.CENTER);
    //Holds 3 panels containg views which can be displayed in the centre of the main view.
    private JPanel mainFieldCard;
    //Displays a view of the players field
    private ViewField defaultField = new ViewField();
    //A panel to positon button panel on the bottom of the main view.
    private JPanel mainSouthPanel = new JPanel(new BorderLayout());

    // Holds most buttons associated with the main south panel.
    private ViewButtonPanel buttonPanel = new ViewButtonPanel();

    //Holds and updates information displayed by the information button
    private ViewInformation informationField = new ViewInformation();

    //Swaps what is displayed in the startup display
    private CardLayout startCard = new CardLayout();
    //Swaps between the start up view and the main view
    private CardLayout mainCard = new CardLayout();
    //Swaps the what is displayed in the centre of the mainView (The field)
    private CardLayout fieldCard = new CardLayout();

    //Various borders that get
    private Border blueLine = BorderFactory.createLineBorder(Color.blue);
    private Border yellowLine = BorderFactory.createLineBorder(Color.yellow);
    private Border mixedLine = BorderFactory.createCompoundBorder(blueLine, yellowLine);

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

        this.mainSouthPanel.add(this.warning, BorderLayout.NORTH);
        this.mainSouthPanel.add(this.buttonPanel, BorderLayout.SOUTH);

        this.mainView.add(this.mainSouthPanel, BorderLayout.SOUTH);

        this.mainView.add(this.mainPlayerHeader, BorderLayout.NORTH);

        this.mainFieldCard = new JPanel(new CardLayout());

        this.mainFieldCard.add("a", this.defaultField);
        this.mainFieldCard.add("b", this.informationField);
        this.mainFieldCard.add("c", this.highScoreField);
        //Adds the mainView to the panel
        this.mainView.add(this.mainFieldCard, BorderLayout.CENTER);

        this.add("c", this.endHighScore);

        //Adds a warning to the new game display.
        this.ViewNewGame.add(this.startWarning, BorderLayout.NORTH);
        getStartView().setLayout(getStartCard());
        //Inital load up display "a"
        this.getStartView().add("a", this.viewOptions);
        //After load game is pressed while in start up "b"
        this.getStartView().add("b", this.viewLoadGame);
        //After new game is pressed while in start up "c"
        this.getStartView().add("c", this.ViewNewGame);

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

    /**
     * Adds actionListener to all buttons within the plant game
     *
     * @param actionListener
     */
    public void addActionListener(ActionListener actionListener) {
        //Main plant game button lisiteners

        getButtonPanel().addActionListener(actionListener);
        this.viewLoadGame.addActionListener(actionListener);
        this.ViewNewGame.addActionListener(actionListener);
        this.viewOptions.addActionListener(actionListener);

//        this.highScoreField.addActionListener(actionListener);
        this.endHighScore.addActionListener(actionListener);

    }

    /**
     * Adds mouseListener to all defaultField labels.
     */
    public void addMouseListener(MouseListener mouseListener) {
        defaultField.addMouseListener(mouseListener);
    }

    /**
     * Updates the player header to display player status
     *
     * @param playerHeader
     */
    public void updatePlayer(String playerHeader) {
        this.mainPlayerHeader.setText(playerHeader);
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
                //Updates defaultField labels to display the correct plant
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
        informationField.updateInformationDisplay(infoArray);

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
            this.viewLoadGame.getLoadButtons()[i].setText(saves[i]);
        }
        this.getStartCard().show(this.getStartView(), "b");
    }

    /**
     * Sets the visibility of the load game buttons based on a boolean array.
     *
     * @param visible
     */
    public void setLoadGameVisibility(boolean[] visible) {
        for (int i = 0; i < 5; i++) {
            this.viewLoadGame.getLoadButtons()[i].setVisible(visible[i]);
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

        this.highScoreField.updateScore(names, scores);
        this.endHighScore.updateScore(names, scores);
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
            this.getStartCard().show(this.getStartView(), "a");

            //show new game panel
            if (data.isNewGame() == true) {
                this.getStartCard().show(this.getStartView(), "c");
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
     * @return the defaultField
     */
    public JPanel getField() {
        return defaultField;
    }

    /**
     * @return the fieldLabels
     */
    public JLabel[][] getFieldLabels() {
        return this.defaultField.getFieldLabels();
    }

    /**
     * @return the mainFieldCard
     */
    public JPanel getMainFieldCard() {
        return mainFieldCard;
    }

    /**
     * @return the fieldCard
     */
    public CardLayout getFieldCard() {
        return fieldCard;
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
     * @return the startCard
     */
    public CardLayout getStartCard() {
        return startCard;
    }

    /**
     * @param startCard the startCard to set
     */
    public void setStartCard(CardLayout startCard) {
        this.startCard = startCard;
    }

    /**
     * @return the advance
     */
    public JButton getAdvance() {
        return this.endHighScore.getAdvance();
    }

    /**
     * @return the loadButtons
     */
    public JButton[] getLoadButtons() {
        return this.viewLoadGame.getLoadButtons();

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
        return this.viewLoadGame.getLoadGameBack();
    }

    /**
     * @param loadGameBack the loadGameBack to set
     */
    public void setLoadGameBack(JButton loadGameBack) {
        this.viewLoadGame.setLoadGameBack(loadGameBack);
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
