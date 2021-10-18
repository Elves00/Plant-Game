/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plant_game;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * The Opening panel of the game. Contains a panel displaying the game title and
 * initial options available to the user.
 *
 * @author breco
 */
public class ViewOptions extends JPanel {

    //Panel to hold buttons
    private JPanel startupPanel = new JPanel();

    //Buttons representing player options
    private JButton newGame = new JButton("New Game");
    private JButton previousGame = new JButton("Previous Game");
    private JButton loadGame = new JButton("Load Game");
    //Title
    private JLabel gameTitle = new JLabel("The Plant Game", SwingConstants.CENTER);
    //Font for title
    private Font fancy = new Font("verdana", Font.BOLD | Font.ITALIC, 28);

    public ViewOptions() {
        this.setLayout(new BorderLayout());
        this.add(gameTitle, BorderLayout.CENTER);
        gameTitle.setFont(fancy);
        this.startupPanel.add(newGame);
        this.startupPanel.add(previousGame);
        this.startupPanel.add(loadGame);
        this.add(startupPanel, BorderLayout.SOUTH);

    }

    /**
     * Adds listeners to the buttons representing the players options.
     *
     * @param actionListener
     */
    public void addActionListener(ActionListener actionListener) {
        this.getNewGame().addActionListener(actionListener);
        this.getPreviousGame().addActionListener(actionListener);
        this.getLoadGame().addActionListener(actionListener);
    }

    /**
     * @return the startupPanel
     */
    public JPanel getStartupPanel() {
        return startupPanel;
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
     * @return the fancy
     */
    public Font getFancy() {
        return fancy;
    }

    /**
     * @return the gameTitle
     */
    public JLabel getGameTitle() {
        return gameTitle;
    }

}
