/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plant_game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author breco
 */
public class LoadGamePanel extends JPanel implements ActionListener, Observer {

    private JButton one;
    private JButton two;
    private JButton three;
    private JButton four;
    private JButton five;

    private PlantGameModel plantGameModel;

    public LoadGamePanel(PlantGameModel plantGameModel) {

        this.plantGameModel = plantGameModel;
        this.plantGameModel.addObserver(this);

        this.one = new JButton("one");
        this.two = new JButton("two");
        this.three = new JButton("three");
        this.four = new JButton("four");
        this.five = new JButton("five");

        this.one.addActionListener(this);
        this.two.addActionListener(this);
        this.three.addActionListener(this);
        this.four.addActionListener(this);
        this.five.addActionListener(this);

        this.add(one);
        this.add(two);
        this.add(three);
        this.add(four);
        this.add(five);

        this.setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Object sourceA = e.getSource();
        if (sourceA == this.one) {
            try {
                this.plantGameModel.getFiles().loadGame(0);
            } catch (IOException ex) {
                Logger.getLogger(LoadGamePanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (sourceA == this.two) {
            try {
                this.plantGameModel.getFiles().loadGame(1);
            } catch (IOException ex) {
                Logger.getLogger(LoadGamePanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (sourceA == this.three) {
            try {
                this.plantGameModel.getFiles().loadGame(2);
            } catch (IOException ex) {
                Logger.getLogger(LoadGamePanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (sourceA == this.four) {
            try {
                this.plantGameModel.getFiles().loadGame(3);
            } catch (IOException ex) {
                Logger.getLogger(LoadGamePanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (sourceA == this.five) {
            try {
                this.plantGameModel.getFiles().loadGame(4);
            } catch (IOException ex) {
                Logger.getLogger(LoadGamePanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @Override
    public void update(Observable o, Object arg) {

        if (arg.equals("Load Game Visible")) {
            this.setVisible(true);
        }
        if (arg.equals("Load Game Not Visible")) {
            this.setVisible(false);
        }
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
