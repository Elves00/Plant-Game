/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plant_game;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 *
 * @author breco
 */
public class Controller extends JFrame implements ActionListener, MouseListener {

    private final Model model;
    //CARDS
    private final View view;

    //Main field
    private boolean planting;
    private int plantToPlant;
    private boolean watering;
    private boolean picking;

    public Controller(Model plantGameModel, View plantGameMain) throws IOException {

        this.setTitle("Plant Game");

        //Starting conditions player is not planting watering or picking
        this.planting = false;
        this.plantToPlant = -1;
        this.watering = false;
        this.picking = false;

        //Set  the main plant panel
        this.view = plantGameMain;
        //Set up the game model
        this.model = plantGameModel;
        //Call start to change visibility of buttons based on database state
        this.model.start();

        //Adds listeners to the view.
        this.addListener();

        //Cardlayout views
        this.add(view);

        // kill all threads when frame closes
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Pack the frame to resize to appropriate size
        this.pack();
        //position the frame in the middle of the screen
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screenDimension = tk.getScreenSize();
        Dimension frameDimension = this.getSize();
        this.setLocation((screenDimension.width - frameDimension.width) / 2,
                (screenDimension.height - frameDimension.height) / 2);
        //Frame starts showing
        this.setVisible(true);
    }

    /**
     * Adds action and mouse listeners to the view
     *
     */
    public void addListener() {
        this.view.addActionListener(this);
        this.view.addMouseListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object sourceA = e.getSource();

        //Switch case is used where aplicable but some cannot be accessed via switch case due to chaning text.
        String action = e.getActionCommand();
        switch (action) {
            case "New Game": {
                //Creates a new game shifting the view to the jtext field for creating a new player
                try {
                    this.model.newGame();
                } catch (MoneyException ex) {
                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            break;
            case "Submit": {
                try {
                    //Gets text from the text field and creates a new player using the inputed text
                    this.model.newGame(view.getUsername().getText());
                } catch (MoneyException | FileNotFoundException ex) {
                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            this.model.viewInitalField();
            this.view.getMainCard().show(this.view, "b");
            //return view to start card
            this.view.getStartCard().show(this.view.getStartView(), "a");
            this.pack();

            break;

            case "Load Game":
                //If pressed opens the load game view showing 5 updateSaveText slots to load from
                this.model.viewLoadGame();

                break;
            case "Back":
                if (sourceA == view.getLoadGameBack()) {
                    //return view to start card
                    this.view.getStartCard().show(this.view.getStartView(), "a");
                }
                //Highscore back
                if (sourceA == view.getButtonPanel().getHighScoreBack()) {
                    this.view.getCard().show(this.view.getButtonPanel(), "a");
                    this.view.getFieldCard().show(this.view.getMainFieldCard(), "a");
                    this.view.getAdvance().setVisible(true);
                }
                //Returns the user to the main card and disables planting
                if (sourceA == view.getButtonPanel().getPlantBack()) {
                    //Swtich view to the selection of plants available to plant
                    this.view.getCard().show(this.view.getButtonPanel(), "a");
                    planting = false;

                }
                //Returns the user to the main card and disables watering
                if (sourceA == view.getButtonPanel().getWaterBack()) {
                    this.view.getCard().show(this.view.getButtonPanel(), "a");
                    watering = false;

                }
                //Returns the user to the main card and disables picking
                if (sourceA == view.getButtonPanel().getPickBack()) {
                    this.view.getCard().show(this.view.getButtonPanel(), "a");
                    picking = false;
                    this.model.viewInitalField();
                }
                //Returns the user to the main card from the updateSaveText view
                if (sourceA == view.getButtonPanel().getSaveBack()) {

                    this.view.getCard().show(this.view.getButtonPanel(), "a");

                }
                //Send back to main menu
                if (sourceA == view.getButtonPanel().getInfoBack()) {

                    this.view.getFieldCard().show(this.view.getMainFieldCard(), "a");
                    this.view.getCard().show(this.view.getButtonPanel(), "a");
                }
                if (sourceA == view.getButtonPanel().getUnlockBack()) {
                    //Switchs the view back to the main button panel
                    this.view.getCard().show(this.view.getButtonPanel(), "a");

                }

                break;
            case "Previous Game":
                //loads the previous saved game.

                this.model.previousGame();
                this.model.viewInitalField();
                this.view.getMainCard().show(this.view, "b");
                this.pack();

                break;
            case "Plant":
                //Player is planting a plant in the field
                //Swtich view to the selection of plants available to plant
                this.view.getCard().show(this.view.getButtonPanel(), "b");
                break;
            case "Main Menu":
                //Return to the start view.
                this.model.mainMenu();
                this.view.updateListener(this);
                //show the main menu
                this.view.getMainCard().show(this.view, "a");

                break;
            case "Highscores":
                //Highscores

                this.view.getCard().show(this.view.getButtonPanel(), "h");
                this.view.getFieldCard().show(this.view.getMainFieldCard(), "c");
                this.view.getAdvance().setVisible(false);

                break;
            case "Water":
                //Watering
                //sets water condition to true.
                this.view.getCard().show(this.view.getButtonPanel(), "c");
                watering = true;
                break;
            case "Pick":

                //picking
                this.model.viewPick();
                this.view.getCard().show(this.view.getButtonPanel(), "d");
                //sets picking condition to true.
                picking = true;

                break;
            case "Next Day":
                //Progress the game to the next day.
                //This is where the end game condition occurs as next day will eventually output a money exception.
      
            try {
                this.model.nextDay();
            } catch (IOException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);

            }

            break;
            case "Save":   //Save view

                this.model.viewSave();
                this.view.getCard().show(this.view.getButtonPanel(), "e");

                break;
            case "Unlock":
                //UNLOCKS
                model.viewUnlock();
                this.view.getCard().show(this.view.getButtonPanel(), "f");

                break;
            case "Information":

                this.view.getFieldCard().show(this.view.getMainFieldCard(), "b");
                this.view.getCard().show(this.view.getButtonPanel(), "g");

                break;
            case "Continue":
                //Send the game back to the load game screen

                this.view.getMainCard().show(this.view, "a");
                this.view.getStartCard().show(this.view.getStartView(), "a");
                this.view.updateListener(this);

                break;
            case "Info Button":
                //Display information
                for (int i = 0; i < 8; i++) {
                    if (sourceA == view.getButtonPanel().getInfoSlot()[i]) {
                        this.model.getInfo(i);
                        break;
                    }
                }
                break;
            case "Load Button":
                //Checks all load buttons for actions preformed.
                for (int i = 0; i < 5; i++) {
                    if (sourceA == view.getLoadButtons()[i]) {
                        this.model.loadGame(i);
                        this.model.viewInitalField();
                        this.pack();
                        //return view to start card
                        this.view.getStartCard().show(this.view.getStartView(), "a");
                    }
                }
                break;
            case "Save Button":
                //Checks which updateSaveText button was inputed and actions it.
                for (int i = 0; i < 5; i++) {
                    if (sourceA == view.getButtonPanel().getSaveSlot()[i]) {

                        this.model.save(i + 1);
                        break;

                    }
                }
                break;
            case "Unlock Button":
                //Cycle through all unlcok buttons.
                for (int i = 0; i < view.getButtonPanel().getUnlockSlot().length; i++) {

                    if (sourceA == view.getButtonPanel().getUnlockSlot()[i]) {
                        //unlock the selected plant
                        this.model.unlock(i + 1);
                        //Re adds action listiener as it gets removed in view during unlock
                        this.view.getButtonPanel().getPlantingButtons()[this.model.getShop().size() - 1].addActionListener(this);
                        break;
                    }
                }
                break;
            case "Planting Button":
                //Checks which plant has been selected to plant in the field
                for (int i = 0; i < PlantSet.values().length; i++) {
                    if (sourceA == view.getButtonPanel().getPlantingButtons()[i] && !action.equals("Back")) {

                        //Sets the plant to be planted and sets the planting condition to true.
                        //This means if a user presses in the field it will plant this plant.
                        plantToPlant = i + 1;
                        planting = true;
                        break;

                    }
                }
                break;
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {
        Object sourceA = e.getSource();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (sourceA == this.view.getFieldLabels()[i][j]) {
                    if (planting) {
                        try {
                            //Plant a plant at row i collumn j
                            this.model.plantAPlant(plantToPlant, i + 1, j + 1);

                        } catch (InstantiationException ex) {
                            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IllegalAccessException ex) {
                            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                    if (watering) {
                        //Sets up the watering view highlighting wattered plants
                        this.model.viewWater();
                        //Water the plant at row i collumn j
                        this.model.water(i, j);

                    }
                    if (picking) {
                        //picks the plant at click position.
                        this.model.pick(i, j);

                    }

                }
            }
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }
}
