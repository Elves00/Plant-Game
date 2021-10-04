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
public class Controller extends JPanel implements ActionListener, MouseListener {

    private PlantGameModel model;
    //CARDS
    private PlantGameMain view;

    private CardLayout card;

    //Main field
    private boolean planting;
    private int plantToPlant;
    private boolean watering;
    private boolean picking;

    public Controller(PlantGameModel plantGameModel, PlantGameMain plantGameMain) throws IOException {

//        //Sets up a cardlayout to be used by this controller
//        card = new CardLayout();
//        this.setLayout(card);
        //Starting conditions player is not planting watering or picking
        this.planting = false;
        this.plantToPlant = -1;
        this.watering = false;
        this.picking = false;

        //Set up the game model
        this.model = plantGameModel;
//      model = new PlantGameModel();

        //Creates the main plant panel
        view = plantGameMain;

        this.view.addActionListener(this); //adds the panel as a listener for all actions within the plantGamePanel
        this.view.addMouseListener(this);

//        //Cardlayout views
        this.add(view);
        //Start game
        this.model.alternatStart();

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
//                this.card.show(this, "B");
            } catch (MoneyException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //If pressed opens the load game view showing 5 save slots to load from
        if (sourceA == view.getLoadGame()) {
            this.model.loadGameView();
        }

        //Large block of buttons used to load a save slot
        if (sourceA == view.getOne()) {
            try {
                this.model.loadGame(0);

                this.model.initialView();

            } catch (IOException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (sourceA == view.getTwo()) {
            try {
                this.model.loadGame(1);

                this.model.initialView();

            } catch (IOException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (sourceA == view.getThree()) {
            try {
                this.model.loadGame(2);

                this.model.initialView();

            } catch (IOException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (sourceA == view.getFour()) {
            try {
                this.model.loadGame(3);
                this.model.initialView();

            } catch (IOException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (sourceA == view.getFive()) {
            try {
                this.model.loadGame(4);
                this.model.initialView();

            } catch (IOException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //loads the previous saved game.
        if (sourceA == view.getPreviousGame()) {
            try {
                this.model.previousGame();
                this.model.initialView();
                this.view.getMainCard().show(this.view, "b");
            } catch (IOException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        /*
        Planting section has the buttons that manage planting
         */
        //Player is planting a plant in the field
        if (sourceA == view.getPlant()) {

            //Swtich view to the selection of plants available to plant
            this.view.getCard().show(this.view.getButtonPanel(), "b");

        }
        //Checks which plant has been selected to plant in the field.
        for (int i = 0; i < model.getShop().size(); i++) {
            if (sourceA == view.getPlantingButtons()[i]) {
                //Sets the plant to be planted and sets the planting condition to true.
                //This means if a user presses in the field it will plant this plant.
                plantToPlant = i + 1;
                planting = true;

            }
        }

        //Returns the user to the main card and disables planting
        if (sourceA == view.getPlantBack()) {
            //Swtich view to the selection of plants available to plant
            this.view.getCard().show(this.view.getButtonPanel(), "a");
            planting = false;
            System.out.println("Pressing back");
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
        if (sourceA == view.getNextDay()) {
            try {
                this.model.nextDay();
            } catch (MoneyException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (sourceA == view.getSave()) {
            this.model.saveView();
            this.view.getCard().show(this.view.getButtonPanel(), "e");

        }
        for (int i = 0; i < 5; i++) {
            if (sourceA == view.getSaveSlot()[i]) {
                try {
                    this.model.save(i + 1);

                } catch (IOException ex) {
                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        //Returns the user to the main card from the save view
        if (sourceA == view.getSaveBack()) {
            this.view.getCard().show(this.view.getButtonPanel(), "a");

        }
        //UNLOCKS
        if (sourceA == view.getUnlockShop()) {
            model.unlockView();
            this.view.getCard().show(this.view.getButtonPanel(), "f");

        }
        //Unlock buttons
        for (int i = 0; i < this.model.getUnlocks().size(); i++) {
            if (sourceA == view.getUnlockSlot()[i]) {

                this.model.unlock(i + 1);
                //Re adds action listiener as it gets removed in view during unlock
                this.view.getPlantingButtons()[this.model.getShop().size() - 1].addActionListener(this);
            }
        }
        //Returns the user to the main card from the unlock view
        if (sourceA == view.getUnlockBack()) {
            this.view.getCard().show(this.view.getButtonPanel(), "a");

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
            }
        }

    }

//MOUSE INPUTE
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
                    System.out.println("YOU PRESSED THE FIELD PLANT IS:" + this.model.getPlayer().getField().getPlantArray()[i][j].toString());
                    System.out.println("Water" + this.model.getPlayer().getField().getPlantArray()[i][j].getWaterCount() + "/" + this.model.getPlayer().getField().getPlantArray()[i][j].getWaterLimit());
                    System.out.println("Value" + this.model.getPlayer().getField().getPlantArray()[i][j].getValue());
                    System.out.println("X clicked is:" + i);
                    System.out.println("X clicked is:" + j);
                    if (planting) {
                        try {
                            //Plant a plant at row i collumn j
                            this.model.plantAPlant(plantToPlant, i + 1, j + 1);

                        } catch (InstantiationException ex) {
                            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IllegalAccessException ex) {
                            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                        }
//                        //Switch the panel view back to the main view from planting view
//                        this.view.getCard().show(this.view.getSouthPanel(), "a");
//                        //Switch the panel view back to the main view from planting view
//                        this.view.getCard().show(this.view.getSouthPanel(), "a");

                    }
                    if (watering) {
                        //Sets up the watering view highlighting wattered plants
                        this.model.waterView();
                        //Water the plant at row i collumn j
                        this.model.water(i, j);

                    }
                    if (picking) {
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

    public PlantGameModel getModel() {
        return this.model;
    }

    public static void main(String[] args) throws IOException {

        JFrame frame = new JFrame("Plant Game");
        PlantGameModel pg = new PlantGameModel();
        PlantGameMain pgm = new PlantGameMain();
        pg.addObserver(pgm);
        // kill all threads when frame closes
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new Controller(pg, pgm));
        frame.pack();
        //position the frame in the middle of the screen
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screenDimension = tk.getScreenSize();
        Dimension frameDimension = frame.getSize();
        frame.setLocation((screenDimension.width - frameDimension.width) / 2,
                (screenDimension.height - frameDimension.height) / 2);
        //Frame starts showing
        frame.setVisible(true);
        // now display something while the main thread is still alive

        //How the thing should actually work.
//        PlantGameModel model = new PlantGameModel();
//        PlantGameMain view = new PlantGameMain();
//        Controller controller = new Controller(model, view);
//        model.addObserver(view);
    }
}
