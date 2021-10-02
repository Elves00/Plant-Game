/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plant_game;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author breco
 */
public class PlantGameStart extends JPanel implements Observer {

    private PlantGameModel plantGameModel;

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
    private CardLayout card;

    public PlantGameStart(PlantGameModel plantGameModel) throws IOException {
        card = new CardLayout();
        this.setLayout(card);

        this.plantGameModel = plantGameModel;
        this.plantGameModel.addObserver(this);

        this.optionsPanel = new JPanel(new BorderLayout());
        this.loadGamePanel = new JPanel();
        this.newGamePanel = new JPanel();

        this.username = new JTextField(20);
        this.newGamePanel.add(this.username);

        this.submit = new JButton("Submit");
//        this.submit.addActionListener(this);
        this.newGamePanel.add(this.submit);

        newGame = new JButton("New Game");
        previousGame = new JButton("Previous Game");
        loadGame = new JButton("Load Game");
        loadInfo = new JTextArea();
        ArrayList<String> gameInfo = plantGameModel.getFiles().information("Information");
        String formated = "";
        for (int i = 0; i < gameInfo.size(); i++) {
            formated += gameInfo.get(i) + "\n";
        }
        //Sets up text in load info and prevents users from editing.
        loadInfo.setText(formated);
        loadInfo.setEditable(false);

        //Panel for options buttons
        startupPanel = new JPanel();

        this.optionsPanel.add(loadInfo, BorderLayout.NORTH);
        this.startupPanel.add(newGame);
        this.startupPanel.add(previousGame);
        this.startupPanel.add(loadGame);
        this.optionsPanel.add(startupPanel, BorderLayout.SOUTH);

        this.one = new JButton("one");
        this.two = new JButton("two");
        this.three = new JButton("three");
        this.four = new JButton("four");
        this.five = new JButton("five");

        loadGamePanel.add(one);
        loadGamePanel.add(two);
        loadGamePanel.add(three);
        loadGamePanel.add(four);
        loadGamePanel.add(five);

        this.add("a", this.optionsPanel);
        this.add("b", this.loadGamePanel);
        this.add("c", this.newGamePanel);

    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg.equals("Options a")) {

            System.out.println("Swaping to panel a");
            this.card.show(this, "a");
        }
        if (arg.equals("Options b")) {
            try {
                String[] saves = this.plantGameModel.getFiles().saveDisplay();

                this.one.setText(saves[0]);
                this.two.setText(saves[1]);
                this.three.setText(saves[2]);
                this.four.setText(saves[3]);
                this.five.setText(saves[4]);
                System.out.println("Swaping to panel b");
                this.card.show(this, "b");
            } catch (IOException ex) {
                Logger.getLogger(PlantGameStart.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        if (arg.equals("Options c")) {
            System.out.println("Swapping to panel c");
            this.card.show(this, "c");
        }
        if (arg.equals("Options Not Visible")) {
            this.setVisible(false);
        }
    }

    /**
     * @return the optionsPanel
     */
    public JPanel getOptionsPanel() {
        return optionsPanel;
    }

    /**
     * @param optionsPanel the optionsPanel to set
     */
    public void setOptionsPanel(JPanel optionsPanel) {
        this.optionsPanel = optionsPanel;
    }

    /**
     * @return the loadGamePanel
     */
    public JPanel getLoadGamePanel() {
        return loadGamePanel;
    }

    /**
     * @param loadGamePanel the loadGamePanel to set
     */
    public void setLoadGamePanel(JPanel loadGamePanel) {
        this.loadGamePanel = loadGamePanel;
    }

    /**
     * @return the newGamePanel
     */
    public JPanel getNewGamePanel() {
        return newGamePanel;
    }

    /**
     * @param newGamePanel the newGamePanel to set
     */
    public void setNewGamePanel(JPanel newGamePanel) {
        this.newGamePanel = newGamePanel;
    }

    /**
     * @return the newGame
     */
    public JButton getNewGame() {
        return newGame;
    }

    /**
     * @param newGame the newGame to set
     */
    public void setNewGame(JButton newGame) {
        this.newGame = newGame;
    }

    /**
     * @return the previousGame
     */
    public JButton getPreviousGame() {
        return previousGame;
    }

    /**
     * @param previousGame the previousGame to set
     */
    public void setPreviousGame(JButton previousGame) {
        this.previousGame = previousGame;
    }

    /**
     * @return the loadGame
     */
    public JButton getLoadGame() {
        return loadGame;
    }

    /**
     * @param loadGame the loadGame to set
     */
    public void setLoadGame(JButton loadGame) {
        this.loadGame = loadGame;
    }

    /**
     * @return the one
     */
    public JButton getOne() {
        return one;
    }

    /**
     * @param one the one to set
     */
    public void setOne(JButton one) {
        this.one = one;
    }

    /**
     * @return the two
     */
    public JButton getTwo() {
        return two;
    }

    /**
     * @param two the two to set
     */
    public void setTwo(JButton two) {
        this.two = two;
    }

    /**
     * @return the three
     */
    public JButton getThree() {
        return three;
    }

    /**
     * @param three the three to set
     */
    public void setThree(JButton three) {
        this.three = three;
    }

    /**
     * @return the four
     */
    public JButton getFour() {
        return four;
    }

    /**
     * @param four the four to set
     */
    public void setFour(JButton four) {
        this.four = four;
    }

    /**
     * @return the five
     */
    public JButton getFive() {
        return five;
    }

    /**
     * @param five the five to set
     */
    public void setFive(JButton five) {
        this.five = five;
    }

    /**
     * @return the username
     */
    public JTextField getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(JTextField username) {
        this.username = username;
    }

    /**
     * @return the submit
     */
    public JButton getSubmit() {
        return submit;
    }

    /**
     * @param submit the submit to set
     */
    public void setSubmit(JButton submit) {
        this.submit = submit;
    }

    /**
     * @return the card
     */
    public CardLayout getCard() {
        return card;
    }

    /**
     * @param card the card to set
     */
    public void setCard(CardLayout card) {
        this.card = card;
    }

}
