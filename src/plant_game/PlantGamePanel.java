/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plant_game;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author breco
 */
public class PlantGamePanel extends JPanel implements Observer {
    //Model

    private PlantGameModel plantGameModel;

    //Panels
    private JPanel southPanel;
    private JPanel northPanel;

    private Player player;
    private PlayerPanel playerPanel;

    private UnlockShop unlockShop;
    private UnlockShopPanel unlockShopPanel;

    private JPanel optionPanel;

    public PlantGamePanel(PlantGameModel plantGame) {
        super(new BorderLayout());
        plantGameModel = plantGame;
        plantGameModel.addObserver(this);
        

    }

    @Override
    public void update(Observable o, Object arg) {

   
        if (arg.equals(this.plantGameModel.getPlayer())) {
                 System.out.println("Player");
            this.player = this.plantGameModel.getPlayer();
            this.playerPanel = new PlayerPanel(this.player);
            this.add(this.playerPanel,BorderLayout.EAST);
        }
        
        if (arg.equals(this.plantGameModel.getUnlocks())) {
            System.out.println("Unlock");
            this.unlockShop = this.plantGameModel.getUnlocks();
            this.unlockShopPanel = new UnlockShopPanel(this.unlockShop);
              this.add(this.unlockShopPanel,BorderLayout.WEST);
        }
        
        
        

    }

    public static void main(String[] args) {
        PlantGameModel pgm = new PlantGameModel();
        PlantGamePanel p = new PlantGamePanel(pgm);
          JFrame frame = new JFrame("Boid Flock");
        // kill all threads when frame closes
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add( p);
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
        pgm.start();
        
        
    }

}
