/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plant_game;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
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
 * This view is primarily concerned with displaying the information of the plant
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
    private JPanel mainView = new JPanel(new BorderLayout());
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
    private Border blueLine = BorderFactory.createLineBorder(Color.blue);
    private Border yellowLine = BorderFactory.createLineBorder(Color.yellow);
    private Border mixedLine = BorderFactory.createCompoundBorder(blueLine, yellowLine);

    public View() {

        //Sets the panel layout to a card layout to allowing swapping between main and start views and the end game view.
        this.setLayout(this.mainCard);

        //Adds the start view to card position a
        this.add("a", this.startView);

        //Adds the main view to card position b
        this.add("b", this.mainView);
        //Adds the forced end game highscore display to the views.
        this.add("c", this.endHighScore);

        //Establishs the unlock buttons to be displayed in the main game view.
        this.establishUnlockButtons();
        //Establishs the plant buttons to be displayed in the main game view.
        this.establishPlantButtons();

        //Add warning displays to the southPanel to display incorect inputs.
        this.mainSouthPanel.add(this.warning, BorderLayout.NORTH);
        //Adds all buttons associated with the main view to the southpanel.
        this.mainSouthPanel.add(this.buttonPanel, BorderLayout.SOUTH);
        //Adds the warning and buttons to the bottom of the main view.
        this.mainView.add(this.mainSouthPanel, BorderLayout.SOUTH);
        //Adds player infromation to the top of the main view.
        this.mainView.add(this.mainPlayerHeader, BorderLayout.NORTH);

        mainFieldCard = new JPanel(this.fieldCard);
        this.mainFieldCard.add("a", this.defaultField);
        this.mainFieldCard.add("b", this.informationField);
        this.mainFieldCard.add("c", this.highScoreField);
        //Adds the mainView to the panel
        this.mainView.add(this.mainFieldCard, BorderLayout.CENTER);

        //Adds a warning to the new game display.
        this.ViewNewGame.add(this.startWarning, BorderLayout.NORTH);
        getStartView().setLayout(getStartCard());
        //Inital load up display "a"
        this.getStartView().add("a", this.viewOptions);
        //After load game is pressed while in start up "b"
        this.getStartView().add("b", this.viewLoadGame);
        //After new game is pressed while in start up "c"
        this.getStartView().add("c", this.ViewNewGame);
        this.mainView.setBackground(Color.red);
        this.mainView.setBorder(blackLine);
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
        getButtonPanel().addActionListener(actionListener);
        this.viewLoadGame.addActionListener(actionListener);
        this.ViewNewGame.addActionListener(actionListener);
        this.viewOptions.addActionListener(actionListener);
        this.endHighScore.addActionListener(actionListener);
    }

    /**
     * Adds mouseListener to all defaultField labels.
     *
     * @param mouseListener
     */
    @Override
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
     * Updates the default field display.
     *
     * The default field displays information from the players field. Calling
     * this method updates this field to show any changed information in the
     * player field.
     *
     * If a plant is fully watered gives the panel a blue border. If a plant is
     * to be pollinated gives the panel a yellow border
     *
     * @param plants a String[][] of plants in the player field.
     * @param water a boolean[][] of if a plant has been fully watered.
     * @param pollin a boolean[][] of if a plant will be pollinated.
     */
    public void updateField(String[][] plants, Boolean[][] water, Boolean[][] pollin) {
        this.defaultField.updateField(plants, water, pollin);
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
     * Resets the planting buttons and unlock shop buttons.This method is called
     * once a game has ended resetting the planting buttons and unlock buttons
     * back to there defaults.
     *
     * This is to ensure that the next game does not have residual buttons from
     * the previous game.
     *
     */
    public void resetButtons() {
        //resets the listeners on button panels plant and unlcok shop 
        this.getButtonPanel().resetButtons();
    }

    /**
     * Set text of load game buttons based on string input.
     *
     * Sets each button name then switches the start view to display them. Also
     * sets if the button is visible based on the boolean array visible.
     *
     * @param saves String[] containing name for all buttons.
     */
    public void setLoadText(String[] saves, boolean[] visible) {
        this.viewLoadGame.setLoadText(saves, visible);
        this.getStartCard().show(this.getStartView(), "b");
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
     * Updates the class based on data changes in the observed model
     *
     * @param o
     * @param arg
     */
    @Override
    public void update(Observable o, Object arg) {

        Data data = (Data) arg;

        //Checks if the game has ended.
        if (data.isEndGame()) {
            //Updates the games scores.
            updateScore(data.getNames(), data.getScores());
            //Game is over reset shop and unlcok buttons
            resetButtons();
            this.mainCard.show(this, "c");
        }
        //Represents shifting to main menu. This means reseting the plant and unlcok buttons.
        if (data.isMainMenu()) {
            resetButtons();
        }

        //Checks for any warning messages in the data and updates the warning labels acordingly
        if (data.isWarningCheck()) {
            this.updateWarningMessage(data.getWarning());
        } else {
            this.updateWarningMessage("");
        }

        //Can happen in both start view and main view
        if (data.isSaveStart()) {
            updateSaveText(data.getSaveText());
        }

        //Checks if the game is in the start panel with options to load game, new game and previous game
        if (data.isStart() == true) {

            //updates wether or not the previous game is showing
            this.viewOptions.getPreviousGame().setVisible(data.isPreviousGame());

            //Show starting panel
            this.getStartCard().show(this.getStartView(), "a");

            //show new game panel
            if (data.isNewGame() == true) {
                this.getStartCard().show(this.getStartView(), "c");
            } //Display the load game options
            else if (data.isLoadGame() == true) {
                //Sets up the load game buttons
                setLoadText(data.getLoadText(), data.getLoadGameVisible());
            }
        }

        //The main game
        if (data.isMainGame() == true) {
            this.mainCard.show(this, "b");
            setShop(data.getPlantsetSize(), data.getShopSize(), data.getShopText());
        }
        //Sets shop display info
        if (data.isShopStart()) {
            this.setShop(data.getPlantsetSize(), data.getShopSize(), data.getShopText());
        }
        //Sets unlock display info
        if (data.isUnlockStart()) {
            this.setUnlockDisplay(data.getPlantsetSize(), data.getUnlockSize(), data.getUnlockText());
        }
        //Updates unlock
        if (data.isUnlockUpdate()) {
            this.updateUnlock(data.getUnlockSize(), data.getUnlockText());
            updatePlayer(data.getPlayer());
        }
        //Updates information display
        if (data.isInfoUpdate()) {
            this.updateInformationDisplay(data.getInfoText());
        }
        //Updates the scoreboard
        if (data.isCheckScores()) {
            updateScore(data.getNames(), data.getScores());
        }
        //Checks for field updates
        if (data.isFieldUpdate() == true) {
            updateField(data.getViewPlants(), data.getWaterPlants(), data.getPollinatePlants());
            updatePlayer(data.getPlayer());
        }
        //Updates the shop
        if (data.isShopUpdate()) {
            this.updateShop(data.getShopSize(), data.getShopText());
            updatePlayer(data.getPlayer());
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
