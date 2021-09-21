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
import java.io.IOException;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
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

    private CardLayout card;

    //Highlight for water full and pollinating.
    private Border blueLine;
    private Border yellowLine;

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

    public PlantGameMain(PlantGameModel plantGameModel) {
        BorderLayout border = new BorderLayout();
        this.setLayout(border);
        this.plantGameModel = plantGameModel;
        this.plantGameModel.addObserver(this);

        //Create borders to highlight when plants are watered or pollinated
        blueLine = BorderFactory.createLineBorder(Color.blue);
        yellowLine = BorderFactory.createLineBorder(Color.yellow);

        this.savePanel = new JPanel();
        saveSlot = new JButton[5];

        //Buttons
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

        this.add(this.buttonPanel, BorderLayout.SOUTH);
        this.add(this.playerHeader, BorderLayout.NORTH);
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

        this.add(this.field, BorderLayout.CENTER);

    }

    @Override
    public void update(Observable o, Object arg) {

        if (arg.equals("Plant") || arg.equals("Initial View") || arg.equals("Pick")) {
            System.out.println("Updating " + arg.toString());
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    this.getFieldLabels()[i][j].setText(this.getPlantGameModel().getPlayer().getField().getPlant(i, j).toString());

                    if (this.getPlantGameModel().getPlayer().getField().getPlantArray()[i][j].getWaterCount() >= this.getPlantGameModel().getPlayer().getField().getPlantArray()[i][j].getWaterLimit()) {

                        //Colours watered plants blue
                        this.getFieldLabels()[i][j].setBorder(blueLine);

                    } else {
                        this.getFieldLabels()[i][j].setBorder(null);
                    }

                    if (this.getPlantGameModel().getPlayer().getField().getPlantArray()[i][j].isPollinator()) {
                        int[] pollin = this.getPlantGameModel().getPlayer().getField().getNeighbours(i, j);

                        for (int k = 0; k < 2; k++) {
                            try {

                                this.getFieldLabels()[pollin[k]][pollin[5]].setBorder(yellowLine);
                                this.getFieldLabels()[pollin[4]][pollin[k + 2]].setBorder(yellowLine);
                            } //Avoids neigbours calling poisitons out of array bounds.
                            catch (ArrayIndexOutOfBoundsException a) {

                            }
                        }
                    }
                }
            }
            //Update player header
            this.playerHeader.setText(this.getPlantGameModel().getPlayer().toString());
        }
        //Load initial save
        if (arg.equals("Initial Save")) {
            try {
                String[] saveDisplay = this.getPlantGameModel().getFiles().saveDisplay();

                for (int i = 0; i < 5; i++) {
                    this.getSaveSlot()[i].setText(saveDisplay[i]);
                }

            } catch (IOException ex) {
                Logger.getLogger(PlantGameMain.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        if (arg.equals("Initial Unlock")) {

            //Unlock setup
            for (int i = 0; i < this.getPlantGameModel().getUnlocks().size(); i++) {
                this.unlockSlot[i].setText(this.getPlantGameModel().getUnlocks().toView(i));
                this.unlockSlot[i].setVisible(true);
                this.unlockPanel.add(this.getUnlockSlot()[i]);
            }
            //Places back button at the end
            this.unlockSlot[this.getPlantGameModel().getUnlocks().size()] = this.unlockBack;
            this.unlockPanel.add(this.getUnlockSlot()[this.getPlantGameModel().getUnlocks().size()]);

            this.buttonPanel.add("f", unlockPanel);

        }
        if (arg.equals("Unlock")) {
            //Make other buttons invisible
            for (int i = this.getPlantGameModel().getUnlocks().size(); i < PlantSet.values().length - 2; i++) {
                this.unlockSlot[i].setVisible(false);

            }
            //Redo button labels
            System.out.println("Unlock size:" + this.getPlantGameModel().getUnlocks().size());
            for (int i = 0; i < this.getPlantGameModel().getUnlocks().size(); i++) {
                this.unlockSlot[i].setText(this.getPlantGameModel().getUnlocks().toView(i));
                this.unlockSlot[i].setVisible(true);

            }
            //Add back button to end
            this.unlockSlot[this.getPlantGameModel().getUnlocks().size()] = this.unlockBack;
            this.unlockSlot[this.getPlantGameModel().getUnlocks().size()].setText("Back");
            this.unlockSlot[this.getPlantGameModel().getUnlocks().size()].setVisible(true);
        }

        if (arg.equals("Water")) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {

                    //Recolours plant if they have had there water level reset.
                    if (this.getPlantGameModel().getPlayer().getField().getPlantArray()[i][j].getWaterCount() >= this.getPlantGameModel().getPlayer().getField().getPlantArray()[i][j].getWaterLimit()) {
                        //Colours watered plants blue
                        this.getFieldLabels()[i][j].setBorder(blueLine);

                    } else {
                        this.getFieldLabels()[i][j].setBorder(null);
                    }
                }
            }
            //Update player header
            this.playerHeader.setText(this.getPlantGameModel().getPlayer().toString());
        }

        if (arg.equals("Next Day")) {
            System.out.println("Updating " + arg.toString());
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    //Shouldnt need to update text as everything happens elsewhere
//                    this.getFieldLabels()[i][j].setText(this.getPlantGameModel().getPlayer().getField().getPlant(i, j).toString());

                    //Recolours plant if they have had there water level reset.
                    if (this.getPlantGameModel().getPlayer().getField().getPlantArray()[i][j].getWaterCount() >= this.getPlantGameModel().getPlayer().getField().getPlantArray()[i][j].getWaterLimit()) {
                        //Colours watered plants blue
                        this.getFieldLabels()[i][j].setBorder(blueLine);

                    } else {
                        this.getFieldLabels()[i][j].setBorder(null);
                    }
                }
            }
            //Update player header
            this.playerHeader.setText(this.getPlantGameModel().getPlayer().toString());

        }

        if (arg.equals("Pick View")) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    //Displays how much money each plant is worth
                    this.getFieldLabels()[i][j].setText(this.getPlantGameModel().getPlayer().getField().getPlant(i, j).getValue() + "$");

                }
            }
        }
        //Watering
        if (arg.equals("Water View")) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    //Check if the plant is over watering limit
                    if (this.getPlantGameModel().getPlayer().getField().getPlantArray()[i][j].getWaterCount() >= this.getPlantGameModel().getPlayer().getField().getPlantArray()[i][j].getWaterLimit()) {
                        //Set the color to blue to represent it being watered.
                        this.getFieldLabels()[i][j].setBorder(blueLine);
                    }
                }
            }
        }

        if (arg.equals("Shop Start")) {
            for (int i = 0; i < this.getPlantGameModel().getShop().size(); i++) {
                try {

                    this.getPlantingButtons()[i].setText(this.getPlantGameModel().getShop().getPlantName(i));
                    this.getPlantingButtons()[i].setVisible(true);
                } catch (InstantiationException ex) {
                    Logger.getLogger(PlantGameMain.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(PlantGameMain.class.getName()).log(Level.SEVERE, null, ex);
                }
                this.getPlantingButtons()[this.getPlantGameModel().getShop().size()] = this.getPlantBack();
            }

            for (JButton i : this.plantingButtons) {
                this.plantSelect.add(i);
            }
        }

        if (arg.equals("Shop Update")) {
            try {

                this.getPlantingButtons()[this.getPlantGameModel().getShop().size() - 1] = new JButton();
                this.getPlantingButtons()[this.getPlantGameModel().getShop().size()] = this.getPlantBack();
                //Sets a hidden jbutton to have the text of a new plant
                this.getPlantingButtons()[this.getPlantGameModel().getShop().size() - 1].setText(this.getPlantGameModel().getShop().getPlantName(this.getPlantGameModel().getShop().size() - 1));
                this.plantSelect.add(this.plantingButtons[this.getPlantGameModel().getShop().size() - 1]);
                this.getPlantingButtons()[this.getPlantGameModel().getShop().size() - 1].setVisible(true);

            } catch (InstantiationException ex) {
                Logger.getLogger(PlantGameMain.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(PlantGameMain.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

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

}
