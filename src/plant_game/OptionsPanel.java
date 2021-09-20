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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author breco
 */
public class OptionsPanel extends JPanel implements ActionListener, Observer {

    private JButton newGame;
    private JButton previousGame;
    private JButton loadGame;

    private LoadGamePanel loadGamePanel;

    private PlantGameModel plantGameModel;

    public OptionsPanel(PlantGameModel plantGameModel) {

        this.plantGameModel = plantGameModel;
        this.plantGameModel.addObserver(this);

        this.loadGamePanel = new LoadGamePanel(this.plantGameModel);

        newGame = new JButton("New Game");
        previousGame = new JButton("Previous Game");
        loadGame = new JButton("Load Game");

        this.newGame.addActionListener(this);
        this.previousGame.addActionListener(this);
        this.loadGame.addActionListener(this);

        this.add(this.newGame);
        this.add(this.loadGame);
        this.add(this.previousGame);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object sourceA = e.getSource();
        if (sourceA == this.newGame) {
            try {
                this.plantGameModel.newGame();
            } catch (MoneyException ex) {
                Logger.getLogger(OptionsPanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(OptionsPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (sourceA == this.loadGame) {
            try {
                this.plantGameModel.loadGame();

            } catch (IOException ex) {
                Logger.getLogger(OptionsPanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (MoneyException ex) {
                Logger.getLogger(OptionsPanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                Logger.getLogger(OptionsPanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(OptionsPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (sourceA == this.previousGame) {
            try {
                this.plantGameModel.previousGame();

                this.loadGamePanel.setVisible(true);
                this.add(this.loadGamePanel);

            } catch (IOException ex) {
                Logger.getLogger(OptionsPanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                Logger.getLogger(OptionsPanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(OptionsPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg.equals("Options Visible")) {
            this.setVisible(true);
        }
        if (arg.equals("Options Not Visible")) {
            this.setVisible(false);
        }

    }

    public static void main(String[] args) {
        PlantGameModel pgm = new PlantGameModel();
        OptionsPanel p = new OptionsPanel(pgm);
        JFrame frame = new JFrame("Boid Flock");
        // kill all threads when frame closes
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(p);
        frame.pack();
        // position the frame in the middle of the screen
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screenDimension = tk.getScreenSize();
        Dimension frameDimension = frame.getSize();
        frame.setLocation((screenDimension.width - frameDimension.width) / 2,
                (screenDimension.height - frameDimension.height) / 2);
        //Frame starts showing
        frame.setVisible(true);

    }
}
