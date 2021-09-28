/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plant_game;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

/**
 *
 * @author breco
 */
public class PlantGameMain extends JPanel implements Observer {

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

    private PlantGameModel plantGameModel;
    private JPanel fieldCard;
    private JPanel field;
    private JLabel[][] fieldLabels;

    private JPanel buttonPanel;
    private JPanel plantSelect;

    private JButton[] plantingButtons;

    private JButton plant;
    private JButton water;
    private JButton pick;
    private JButton nextDay;
    private JButton information;
    private JButton unlockShop;
    private JButton save;
    private JPanel gameOptions;

    private JPanel startView;
    private JPanel mainView;

    private CardLayout mainCard;

    private CardLayout card;
    private CardLayout cardField;

    //Highlight for water full and pollinating.
    private Border blueLine;
    private Border yellowLine;
    private Border mixedLine;

    //Used to send a user plantBack to another view.
    private JButton plantBack;
    private JButton waterBack;
    private JButton pickBack;

    private JPanel pickingPanel;
    private JPanel wateringPanel;

    private JLabel playerHeader;

    private JButton saveBack;
    private JButton[] saveSlot;
    private JPanel savePanel;

    private JButton unlockBack;
    private JButton[] unlockSlot;
    private JPanel unlockPanel;

    private JPanel infoPanel;
    private JButton[] infoSlot;
    private JPanel infoAreaButtons;
    private JTextField infoArea;
    private JButton infoBack;
    private String[] searchTerm;

    //PLANT GAME START
    private JPanel optionsPanel;
    private JPanel loadGamePanel;
    private JPanel newGamePanel;

    private JPanel startupPanel;
    private JButton newGame;
    private JButton previousGame;
    private JButton loadGame;
    private JTextArea loadInfo;
    private JLabel title;

    private JButton one;
    private JButton two;
    private JButton three;
    private JButton four;
    private JButton five;

    private JTextField username;
    private JButton submit;

    // Declaration of objects of CardLayout class.
    private CardLayout cards;

    public PlantGameMain() {

    }

    //So to follow MVC we need to be passed the information we need to know from update
    public PlantGameMain(PlantGameModel plantGameModel) {
        //Main card which will include a start up/main and end panel.
        mainCard = new CardLayout();
        this.setLayout(mainCard);

        //Start view a
        this.startView = new JPanel();
        this.add("a", this.startView);

        //Main view b
        this.mainView = new JPanel();
        this.add("b", this.mainView);

        BorderLayout border = new BorderLayout();
        this.mainView.setLayout(border);

        //view and observer
        this.plantGameModel = plantGameModel;
        this.plantGameModel.addObserver(this);

        //Create borders to highlight when plants are watered or pollinated
        blueLine = BorderFactory.createLineBorder(Color.blue);
        yellowLine = BorderFactory.createLineBorder(Color.yellow);
        mixedLine = BorderFactory.createCompoundBorder(blueLine, yellowLine);

        //Information search terms for use in update
        searchTerm = new String[]{"Information", "plants", "Plant a Plant", "Pick Plant", "Water", "Next day", "Unlock", "Save game"};

        //Infomation
        this.infoPanel = new JPanel(new BorderLayout());
        this.infoAreaButtons = new JPanel();
        infoSlot = new JButton[8];

        //Add all buttons to info slot
        for (int i = 0; i < 8; i++) {
            infoSlot[i] = new JButton();
            infoAreaButtons.add(this.infoSlot[i]);
        }
        infoBack = new JButton("Back");
        infoAreaButtons.add(this.infoBack);

        infoArea = new JTextField();
        infoArea.setEditable(false);

        infoPanel.add(infoAreaButtons, BorderLayout.SOUTH);

        //Save 
        this.savePanel = new JPanel();
        saveSlot = new JButton[5];

        for (int i = 0; i < 5; i++) {
            saveSlot[i] = new JButton();
        }

        for (int i = 0; i < 5; i++) {
            savePanel.add(this.saveSlot[i]);
        }

        this.saveBack = new JButton("Back");
        this.savePanel.add(this.saveBack);

        //UNLOCK EVERYTHING HERE.
        this.unlockPanel = new JPanel();
        this.unlockBack = new JButton("Back");
        this.plantBack = new JButton("Back");
        this.waterBack = new JButton("Back");
        this.pickBack = new JButton("Back");

        this.plant = new JButton("Plant");
        this.water = new JButton("Water");
        this.pick = new JButton("Pick");
        this.nextDay = new JButton("Next Day");
        this.information = new JButton("Information");
        this.save = new JButton("Save");
        this.unlockShop = new JButton("Shop");
        this.playerHeader = new JLabel("", SwingConstants.CENTER);

        //this.plantGameModel.getPlayer().toString()
        card = new CardLayout();
        //Bottom bar
        this.buttonPanel = new JPanel(getCard());
        //Options panel
        this.gameOptions = new JPanel();

        //Options button
        this.gameOptions.add(plant);
        this.gameOptions.add(water);
        this.gameOptions.add(pick);
        this.gameOptions.add(nextDay);
        this.gameOptions.add(save);
        this.gameOptions.add(information);
        this.gameOptions.add(this.unlockShop);

        //Unlocks initial length starts as the base set - 3 + 1 as you always start with 3 plants and we need one extra slot for the back button
        this.unlockSlot = new JButton[PlantSet.values().length - 2];
        //Unlock setup
        for (int i = 0; i < PlantSet.values().length - 2; i++) {
            this.unlockSlot[i] = new JButton();
            this.unlockSlot[i].setVisible(false);
        }

        //Plant selection
        this.plantSelect = new JPanel();
        this.plantingButtons = new JButton[PlantSet.values().length + 1];

        //Number of plant buttons plus a plantBack button
        for (int i = 0; i < PlantSet.values().length + 1; i++) {
            this.plantingButtons[i] = new JButton();
            this.plantingButtons[i].setVisible(false);
            this.plantSelect.add(this.plantingButtons[i]);
        }

        //Watering Panel
        this.wateringPanel = new JPanel();
        this.wateringPanel.add(this.waterBack);

        //Picking Panel
        this.pickingPanel = new JPanel();
        this.pickingPanel.add(this.pickBack);

        this.buttonPanel.add("a", gameOptions);
        this.buttonPanel.add("b", plantSelect);
        this.buttonPanel.add("c", wateringPanel);
        this.buttonPanel.add("d", pickingPanel);
        this.buttonPanel.add("e", savePanel);
        this.buttonPanel.add("f", unlockPanel);
        this.buttonPanel.add("g", infoPanel);

        this.mainView.add(this.buttonPanel, BorderLayout.SOUTH);
        this.mainView.add(this.playerHeader, BorderLayout.NORTH);
        this.cardField = new CardLayout();
        this.fieldCard = new JPanel(getCardField());
        this.field = new JPanel(new GridLayout(3, 3));
        this.fieldLabels = new JLabel[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.fieldLabels[i][j] = new JLabel("", SwingConstants.CENTER);
            }
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.field.add(this.fieldLabels[i][j]);
            }
        }

        this.fieldCard.add("a", this.field);
        this.fieldCard.add("b", this.infoArea);

        //Adds the mainView to the panel
        this.mainView.add(this.fieldCard, BorderLayout.CENTER);

        //Adds details to the start panel
        try {
            plantGameStart();
        } catch (IOException ex) {
            Logger.getLogger(PlantGameMain.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void plantGameStart() throws IOException {

        cards = new CardLayout();
        startView.setLayout(cards);
        //

        this.optionsPanel = new JPanel(new BorderLayout());
        this.loadGamePanel = new JPanel();
        this.newGamePanel = new JPanel();

        this.username = new JTextField(20);
        this.newGamePanel.add(this.getUsername());

        this.submit = new JButton("Submit");
//        this.submit.addActionListener(this);
        this.newGamePanel.add(this.getSubmit());

        this.newGame = new JButton("New Game");
        this.previousGame = new JButton("Previous Game");
        this.loadGame = new JButton("Load Game");
        this.loadInfo = new JTextArea();
        ArrayList<String> gameInfo = this.plantGameModel.getFiles().information("Information");
        String formated = "";
        for (int i = 0; i < gameInfo.size(); i++) {
            formated += gameInfo.get(i) + "\n";
        }
        //Sets up text in load info and prevents users from editing.
        this.loadInfo.setText(formated);
        this.loadInfo.setEditable(false);

        //Panel for options buttons
        this.startupPanel = new JPanel();

        this.optionsPanel.add(loadInfo, BorderLayout.NORTH);
        this.startupPanel.add(getNewGame());
        this.startupPanel.add(getPreviousGame());
        this.startupPanel.add(getLoadGame());
        this.optionsPanel.add(startupPanel, BorderLayout.SOUTH);

        this.one = new JButton("one");
        this.two = new JButton("two");
        this.three = new JButton("three");
        this.four = new JButton("four");
        this.five = new JButton("five");

        this.loadGamePanel.add(getOne());
        this.loadGamePanel.add(getTwo());
        this.loadGamePanel.add(getThree());
        this.loadGamePanel.add(getFour());
        this.loadGamePanel.add(getFive());

        this.startView.add("a", this.optionsPanel);
        this.startView.add("b", this.loadGamePanel);
        this.startView.add("c", this.newGamePanel);

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

        //Unlock listeners
        for (int i = 0; i < getUnlockSlot().length; i++) {

            getUnlockSlot()[i].addActionListener(actionListener);
        }

        //Slave slot listiners
        for (int i = 0; i < 5; i++) {
            getSaveSlot()[i].addActionListener(actionListener);
        }

        //Action listners for planting options
        for (int i = 0; i < getPlantingButtons().length; i++) {
            getPlantingButtons()[i].addActionListener(actionListener);
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

        this.one.addActionListener(actionListener);
        this.two.addActionListener(actionListener);
        this.three.addActionListener(actionListener);
        this.four.addActionListener(actionListener);
        this.five.addActionListener(actionListener);

    }

    public void addMouseListener(MouseListener mouseListener) {
        //Listeners for the field panels
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                getFieldLabels()[i][j].addMouseListener(mouseListener);
            }
        }
    }

    public void startGame() {

    }

    /**
     * Set text for loading
     */
    public void setLoadText(String[] saves) {

        this.one.setText(saves[0]);
        this.two.setText(saves[1]);
        this.three.setText(saves[2]);
        this.four.setText(saves[3]);
        this.five.setText(saves[4]);
        System.out.println("Swaping to panel b");
        this.cards.show(this.startView, "b");

    }

    public void setSaves() {

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
     * Updates the player header to correctly display player information
     */
    public void updatePlayer() {
        this.playerHeader.setText(this.getPlantGameModel().getPlayer().toString());
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
     * Updates the field to correctly display player field information
     */
    public void updateField() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                //If plant is at or above it's water limit set its border to blue
                if ((this.getPlantGameModel().getPlayer().getField().getPlantArray()[i][j].getWaterCount() >= this.getPlantGameModel().getPlayer().getField().getPlantArray()[i][j].getWaterLimit())) {
                    //Colours watered plants mixed
                    this.getFieldLabels()[i][j].setBorder(blueLine);

                } else {
                    this.getFieldLabels()[i][j].setBorder(null);
                }

                //Updates the text of each panel.
                this.getFieldLabels()[i][j].setText(this.getPlantGameModel().getPlayer().getField().getPlant(i, j).toString());

            }
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                //If the plant is a pollinator set it's neighbours borders to yellow
                if (this.getPlantGameModel().getPlayer().getField().getPlantArray()[i][j].isPollinator()) {
                    int[] pollin = this.getPlantGameModel().getPlayer().getField().getNeighbours(i, j);

                    //Section searches through above and bellow the pollinator and sets there border to either yellow or mixed border
                    for (int k = 0; k < 2; k++) {
                        try {

                            if (this.getFieldLabels()[pollin[k]][pollin[5]].getBorder() == blueLine || this.getFieldLabels()[pollin[k]][pollin[5]].getBorder() == mixedLine) {
                                this.getFieldLabels()[pollin[k]][pollin[5]].setBorder(mixedLine);
                            } else {
                                this.getFieldLabels()[pollin[k]][pollin[5]].setBorder(yellowLine);
                            }

                        } //Avoids neigbours calling poisitons out of array bounds.
                        catch (ArrayIndexOutOfBoundsException a) {

                        }
                    }

                    for (int k = 0; k < 2; k++) {
                        try {

                            if (this.getFieldLabels()[pollin[4]][pollin[k + 2]].getBorder() == blueLine || this.getFieldLabels()[pollin[4]][pollin[k + 2]].getBorder() == mixedLine) {
                                this.getFieldLabels()[pollin[4]][pollin[k + 2]].setBorder(mixedLine);
                            } else {
                                this.getFieldLabels()[pollin[4]][pollin[k + 2]].setBorder(yellowLine);
                            }

                        } //Avoids neigbours calling poisitons out of array bounds.
                        catch (ArrayIndexOutOfBoundsException a) {

                        }
                    }
                }
            }
        }

    }

    private void unlockStart(int plantSetSize, int unlockSize, String[] unlockText) {

        if (this.unlockSlot == null) {
            //Unlocks initial length starts as the base set - 3 + 1 as you always start with 3 plants and we need one extra slot for the back button
            this.unlockSlot = new JButton[plantSetSize - 2];
            //Unlock setup
            for (int i = 0; i < plantSetSize - 2; i++) {
                this.unlockSlot[i] = new JButton();
                this.unlockSlot[i].setVisible(false);
            }

        }
        System.out.println("Inital size" + unlockSize);
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

    private void unlockUpdate(int unlockSize, String[] unlockText) {
        //Make other buttons invisible
        for (int i = unlockSize; i < unlockSlot.length; i++) {
            this.unlockSlot[i].setVisible(false);

        }
        //Redo button labels
        System.out.println("Unlock size:" + unlockSize);
        for (int i = 0; i < unlockSize; i++) {
            this.unlockSlot[i].setText(unlockText[i]);
            this.unlockSlot[i].setVisible(true);

        }
        //Add back button to end
        this.unlockSlot[unlockSize] = this.unlockBack;
        this.unlockSlot[unlockSize].setText("Back");
        this.unlockSlot[unlockSize].setVisible(true);

        //Update player header
        updatePlayer();
    }

    private void shopUpdate(int shopSize, String[] shopText) {
        System.out.println("SHOP SIZE IS:"+shopSize);
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

        //update player
        updatePlayer();

    }

    private void shopStart(int plantSetSize, int shopSize, String[] shopText) {

        /*
            Update the shop buttons so that they display all items within the shop.
            Orignal buttons: one two three etc...
            After: broccolli cabage carrot etc...
         */
        //If the planting buttons havnt been established establish it.
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

    @Override
    public void update(Observable o, Object arg) {

        Data data = (Data) arg;
        if (data.isStart() == true) {
            //Show starting panel

            this.cards.show(this.startView, "a");

            //show new game panel
            if (data.isNewGame() == true) {
                this.cards.show(this.startView, "c");

            } //Display the load game options
            else if (data.isLoadGame() == true) {
                setLoadText(data.getLoadText());

            }

        }

        //The main game
        if (data.isMainGame() == true) {
            this.mainCard.show(this, "b");
            shopStart(data.getPlantsetSize(), data.getShopSize(), data.getShopText());
        }

        if (data.isFieldUpdate() == true) {
            updateField(data.getViewPlants(), data.getWaterPlants(), data.getPollinatePlants());
            updatePlayer(data.getPlayer());
        }
        if (data.isShopStart()) {
            this.shopStart(data.getPlantsetSize(), data.getShopSize(), data.getShopText());
        }
        if (data.isUnlockStart()) {
            this.unlockStart(data.getPlantsetSize(), data.getUnlockSize(), data.getUnlockText());
        }

        if (data.isShopUpdate()) {
            System.out.println("SHOP UPDATING");
            this.shopUpdate(data.getShopSize(), data.getShopText());
            updatePlayer(data.getPlayer());
        }
        if (data.isUnlockUpdate()) {
              this.unlockUpdate(data.getUnlockSize(), data.getUnlockText());
            updatePlayer(data.getPlayer());
        }
//        if (arg.equals("Options a")) {
//
//            System.out.println("Swaping to panel a");
//            this.cards.show(this.startView, "a");
//        }
//        if (arg.equals("Options b")) {
//            try {
//                setLoadText(plantGameModel.getFiles().saveDisplay());
//            } catch (IOException ex) {
//                Logger.getLogger(PlantGameMain.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//        if (arg.equals("Options c")) {
//
//            System.out.println("Swapping to panel c");
//            this.cards.show(this.startView, "c");
//        }
//        if (arg.equals("Options Not Visible")) {
//            this.mainCard.show(this, "b");
//        }
//
//        if (arg.equals("Plant") || arg.equals("Initial View") || arg.equals("Pick")) {
//            System.out.println("UPDATE");
//            this.updateField();
//            //Update player header
//            updatePlayer();
//        }
//        //Load initial save
//        if (arg.equals("Initial Save")) {
//            try {
//                String[] saveDisplay = this.getPlantGameModel().getFiles().saveDisplay();
//
//                for (int i = 0; i < 5; i++) {
//                    this.getSaveSlot()[i].setText(saveDisplay[i]);
//                }
//
//            } catch (IOException ex) {
//                Logger.getLogger(PlantGameMain.class.getName()).log(Level.SEVERE, null, ex);
//            }
//
//        }
//
//        if (arg.equals("Initial Unlock")) {
//
//            System.out.println("Inital size" + this.getPlantGameModel().getUnlocks().size());
//            //Unlock setup
//            for (int i = 0; i < this.getPlantGameModel().getUnlocks().size(); i++) {
//                this.unlockSlot[i].setText(this.getPlantGameModel().getUnlocks().toView(i));
//                this.unlockSlot[i].setVisible(true);
//                this.unlockPanel.add(this.getUnlockSlot()[i]);
//            }
//            //Places back button at the end
//            this.unlockSlot[this.getPlantGameModel().getUnlocks().size()] = this.unlockBack;
//            this.unlockPanel.add(this.getUnlockSlot()[this.getPlantGameModel().getUnlocks().size()]);
//
//            this.buttonPanel.add("f", unlockPanel);
//
//        }
//        if (arg.equals("Unlock")) {
//            //Make other buttons invisible
//            for (int i = this.getPlantGameModel().getUnlocks().size(); i < PlantSet.values().length - 2; i++) {
//                this.unlockSlot[i].setVisible(false);
//
//            }
//            //Redo button labels
//            System.out.println("Unlock size:" + this.getPlantGameModel().getUnlocks().size());
//            for (int i = 0; i < this.getPlantGameModel().getUnlocks().size(); i++) {
//                this.unlockSlot[i].setText(this.getPlantGameModel().getUnlocks().toView(i));
//                this.unlockSlot[i].setVisible(true);
//
//            }
//            //Add back button to end
//            this.unlockSlot[this.getPlantGameModel().getUnlocks().size()] = this.unlockBack;
//            this.unlockSlot[this.getPlantGameModel().getUnlocks().size()].setText("Back");
//            this.unlockSlot[this.getPlantGameModel().getUnlocks().size()].setVisible(true);
//
//            //Update player header
//            updatePlayer();
//        }
//
//        if (arg.equals("Water")) {
//            this.updateField();
//            //Update player header
//            updatePlayer();
//        }
//
//        if (arg.equals("Next Day")) {
//            System.out.println("Updating " + arg.toString());
//            this.updateField();
//            //Update player header
//            updatePlayer();
//
//        }
//
//        if (arg.equals("Pick View")) {
//            for (int i = 0; i < 3; i++) {
//                for (int j = 0; j < 3; j++) {
//                    //Displays how much money each plant is worth
//                    this.getFieldLabels()[i][j].setText(this.getPlantGameModel().getPlayer().getField().getPlant(i, j).getValue() + "$");
//
//                }
//            }
//        }
//        //Watering
//        if (arg.equals("Water View")) {
//            this.updateField();
//
//        }
//
//        if (arg.equals("Shop Start")) {
//            /*
//            Update the shop buttons so that they display all items within the shop.
//            Orignal buttons: one two three etc...
//            After: broccolli cabage carrot etc...
//             */
//
//            for (int i = 0; i < this.getPlantGameModel().getShop().size(); i++) {
//                try {
//                    //gets the associated button text from the players shop
//                    this.getPlantingButtons()[i].setText(this.getPlantGameModel().getShop().getPlantName(i));
//                    this.getPlantingButtons()[i].setVisible(true);
//                } catch (InstantiationException ex) {
//                    Logger.getLogger(PlantGameMain.class.getName()).log(Level.SEVERE, null, ex);
//                } catch (IllegalAccessException ex) {
//                    Logger.getLogger(PlantGameMain.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//            //Add back button after all plant buttons have been added so it is always left most option.
//            this.getPlantingButtons()[this.getPlantGameModel().getShop().size()] = this.plantBack;
//            this.getPlantingButtons()[this.getPlantGameModel().getShop().size()].setVisible(true);
//            this.getPlantSelect().add(this.getPlantingButtons()[this.getPlantGameModel().getShop().size()]);
//
//        }
//
//        if (arg.equals("Shop Update")) {
//            try {
//
//                //Sets a hidden jbutton to have the text of a new plant
//                this.plantingButtons[this.getPlantGameModel().getShop().size() - 1] = new JButton();
//                this.plantingButtons[this.getPlantGameModel().getShop().size() - 1].setText(this.getPlantGameModel().getShop().getPlantName(this.getPlantGameModel().getShop().size() - 1));
//                this.plantingButtons[this.getPlantGameModel().getShop().size() - 1].setVisible(true);
//
//                //Add back button to end of planting buttons
//                this.plantingButtons[this.getPlantGameModel().getShop().size()] = this.plantBack;
//                this.plantingButtons[this.getPlantGameModel().getShop().size()].setText("Back");
//                this.plantingButtons[this.getPlantGameModel().getShop().size()].setVisible(true);
//
//                //add the new jbutton and back button  to the plant select panel.
//                this.plantSelect.add(this.plantingButtons[this.getPlantGameModel().getShop().size() - 1]);
//                this.plantSelect.add(this.plantingButtons[this.getPlantGameModel().getShop().size()]);
//
//                //update player
//                updatePlayer();
//            } catch (InstantiationException ex) {
//                Logger.getLogger(PlantGameMain.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (IllegalAccessException ex) {
//                Logger.getLogger(PlantGameMain.class.getName()).log(Level.SEVERE, null, ex);
//            }
//
//        }
//        for (int i = 0; i < 8; i++) {
//            if (arg.equals("Info " + i)) {
//                ArrayList<String> plants;
//                try {
//                    plants = this.plantGameModel.getFiles().information(searchTerm[i]);
//                    String toDisplay = "";
//                    for (int j = 0; j < plants.size(); j++) {
//                        toDisplay += plants.get(j) + "\n";
//                    }
//                    this.infoArea.setText(toDisplay);
//                    this.infoArea.setEditable(false);
//                    System.out.println("");
//                } catch (IOException ex) {
//                    Logger.getLogger(PlantGameMain.class.getName()).log(Level.SEVERE, null, ex);
//                }
//
//            }
//        }
    }

    /**
     * @return the plantGameModel
     */
    public PlantGameModel getPlantGameModel() {
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
     * @return the save
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
    public void setPlantGameModel(PlantGameModel plantGameModel) {
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
     * @return the one
     */
    public JButton getOne() {
        return one;
    }

    /**
     * @return the two
     */
    public JButton getTwo() {
        return two;
    }

    /**
     * @return the three
     */
    public JButton getThree() {
        return three;
    }

    /**
     * @return the four
     */
    public JButton getFour() {
        return four;
    }

    /**
     * @return the five
     */
    public JButton getFive() {
        return five;
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

}
