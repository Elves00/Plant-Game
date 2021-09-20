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
public class PlayerController extends JPanel implements ActionListener {

    //Buttons
    JButton nextDay;
    

    
    
    //View
    PlayerPanel playerPanel;
    //Model
    Player player;

    public PlayerController() {
        
        player = new Player("Brecon");
        playerPanel = new PlayerPanel(player);
        nextDay = new JButton("Next Day");
        nextDay.addActionListener(this);
        this.add(playerPanel);
        this.add(nextDay);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object sourceA = e.getSource();
        if (sourceA == nextDay) {
            try {
                player.nextDay();
            } catch (MoneyException ex) {
                Logger.getLogger(PlayerController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(PlayerController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public static void main(String[] args) {

        JFrame frame = new JFrame("Boid Flock");
        // kill all threads when frame closes
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new PlayerController());
        frame.pack();
        // position the frame in the middle of the screen
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screenDimension = tk.getScreenSize();
        Dimension frameDimension = frame.getSize();
        frame.setLocation((screenDimension.width - frameDimension.width) / 2,
                (screenDimension.height - frameDimension.height) / 2);
        //Frame starts showing
        frame.setVisible(true);
        // now display something while the main thread is still alive

    }
}
