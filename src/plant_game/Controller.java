/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plant_game;

import java.awt.CardLayout;
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
import javax.swing.JPanel;

/**
 *
 * @author breco
 */
public class Controller extends JFrame implements ActionListener, MouseListener {

    private Model model;
    //CARDS
    private View view;

    //Main field
    private boolean planting;
    private int plantToPlant;
    private boolean watering;
    private boolean picking;

    public Controller(Model plantGameModel, View plantGameMain) throws IOException {

        this.setTitle("Plant Game");
        //Starting conditions player is not planting watering or picking
        planting = false;
        plantToPlant = -1;
        watering = false;
        picking = false;
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

    //Adds action and mouse listeners to the view
    public void addListener() {
        this.view.addActionListener(this); //adds the panel as a listener for all actions within the plantGamePanel
        this.view.addMouseListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object sourceA = e.getSource();

        //Creates a new game shifting the view to the jtext field for creating a new player
        if (sourceA == view.getNewGame()) {

            try {

                this.model.newGame();
            } catch (MoneyException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //Gets text from the text field and creates a new player using the inputed text
        if (sourceA == view.getSubmit()) {
            try {
                this.model.newGame(view.getUsername().getText());

                this.model.initialView();
                this.view.getMainCard().show(this.view, "b");
                //return view to start card
                this.view.getCards().show(this.view.getStartView(), "a");
                this.pack();

            } catch (MoneyException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //If pressed opens the load game view showing 5 updateSaveText slots to load from
        if (sourceA == view.getLoadGame()) {
            this.model.loadGameView();
        }
        //Checks all load buttons for actions preformed.
        for (int i = 0; i < 5; i++) {
            if (sourceA == view.getLoadButtons()[i]) {
                this.model.loadGame(i);
                this.model.initialView();
                this.pack();
                //return view to start card
                this.view.getCards().show(this.view.getStartView(), "a");
            }
        }
        if (sourceA == view.getLoadGameBack()) {
            //return view to start card
            this.view.getCards().show(this.view.getStartView(), "a");
        }
        //loads the previous saved game.
        if (sourceA == view.getPreviousGame()) {
            this.model.previousGame();
            this.model.initialView();
            this.view.getMainCard().show(this.view, "b");
            this.pack();
        }
        //Player is planting a plant in the field
        if (sourceA == view.getPlant()) {

            //Swtich view to the selection of plants available to plant
            this.view.getCard().show(this.view.getButtonPanel(), "b");

        }
        //Checks which plant has been selected to plant in the field
        for (int i = 0; i < PlantSet.values().length; i++) {
            if (sourceA == view.getPlantingButtons()[i]) {
                //Sets the plant to be planted and sets the planting condition to true.
                //This means if a user presses in the field it will plant this plant.
                plantToPlant = i + 1;
                planting = true;
                break;

            }
        }
        //Returns the user to the main card and disables planting
        if (sourceA == view.getPlantBack()) {
            //Swtich view to the selection of plants available to plant
            this.view.getCard().show(this.view.getButtonPanel(), "a");
            planting = false;

        }
        //Return to the load screen.
        if (sourceA == view.getMainMenu()) {
            this.model.mainMenu();
            this.view.updateListener(this);
            //show the main menu
            this.view.getMainCard().show(this.view, "a");

        }
        //Highscores
        if (sourceA == view.getHighScoresButton()) {
            this.view.getCard().show(this.view.getButtonPanel(), "h");
            this.view.getCardField().show(this.view.getFieldCard(), "c");
            this.view.getAdvance().setVisible(false);
        }
        //Highscore back
        if (sourceA == view.getHighScoreBack()) {
            this.view.getCard().show(this.view.getButtonPanel(), "a");
            this.view.getCardField().show(this.view.getFieldCard(), "a");
            this.view.getAdvance().setVisible(true);
        }
        //Watering
        if (sourceA == view.getWater()) {
            //sets water condition to true.
            this.view.getCard().show(this.view.getButtonPanel(), "c");
            watering = true;
        }
        //Returns the user to the main card and disables watering
        if (sourceA == view.getWaterBack()) {
            this.view.getCard().show(this.view.getButtonPanel(), "a");
            watering = false;

        }
        //picking
        if (sourceA == view.getPick()) {
            this.model.pickView();
            this.view.getCard().show(this.view.getButtonPanel(), "d");
            //sets picking condition to true.
            picking = true;
        }
        //Returns the user to the main card and disables picking
        if (sourceA == view.getPickBack()) {
            this.view.getCard().show(this.view.getButtonPanel(), "a");
            picking = false;
            this.model.initialView();
        }
        //Progress the game to the next day.
        //This is where the end game condition occurs as next day will eventually output a money exception.
        if (sourceA == view.getNextDay()) {
            try {
                this.model.nextDay();
            } catch (IOException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //Save view
        if (sourceA == view.getSave()) {

            this.model.saveView();
            this.view.getCard().show(this.view.getButtonPanel(), "e");

        }
        //Checks which updateSaveText button was inputed and actions it.
        for (int i = 0; i < 5; i++) {
            if (sourceA == view.getSaveSlot()[i]) {

                this.model.save(i + 1);
                break;

            }
        }

        //Returns the user to the main card from the updateSaveText view
        if (sourceA == view.getSaveBack()) {

            this.view.getCard().show(this.view.getButtonPanel(), "a");

        }
        //UNLOCKS
        if (sourceA == view.getUnlockShop()) {
            model.unlockView();
            this.view.getCard().show(this.view.getButtonPanel(), "f");

        }

        /*Checks if the source was the unlock back button if not checks if it was from within the unlock slot
          Set up this way to avoid treating the back button as an unlock button since it is also within the unlock slot array.
         */
        if (sourceA == view.getUnlockBack()) {
            //Switchs the view back to the main button panel
            this.view.getCard().show(this.view.getButtonPanel(), "a");

        } else {
            //Cycle through all unlcok buttons.
            for (int i = 0; i < view.getUnlockSlot().length; i++) {

                if (sourceA == view.getUnlockSlot()[i]) {
                    //unlock the selected plant
                    this.model.unlock(i + 1);
                    //Re adds action listiener as it gets removed in view during unlock
                    this.view.getPlantingButtons()[this.model.getShop().size() - 1].addActionListener(this);
                    break;
                }
            }
        }
        //Information
        if (sourceA == view.getInformation()) {
            this.view.getCardField().show(this.view.getFieldCard(), "b");
            this.view.getCard().show(this.view.getButtonPanel(), "g");
        }
        //Send back to main menu
        if (sourceA == view.getInfoBack()) {

            this.view.getCardField().show(this.view.getFieldCard(), "a");
            this.view.getCard().show(this.view.getButtonPanel(), "a");
        }
        //Display information
        for (int i = 0; i < 8; i++) {
            if (sourceA == view.getInfoSlot()[i]) {
                this.model.getInfo(i);
                break;
            }
        }
        //Send the game back to the load game screen
        if (sourceA == view.getAdvance()) {

            this.view.getMainCard().show(this.view, "a");
            this.view.getCards().show(this.view.getStartView(), "a");
            this.view.updateListener(this);
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
                        this.model.waterView();
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

//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
